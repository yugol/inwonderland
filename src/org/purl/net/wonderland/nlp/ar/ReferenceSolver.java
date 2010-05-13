/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.purl.net.wonderland.nlp.ar;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.util.IdUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 * anaphora resolution
 */
public class ReferenceSolver {

    private final Wkb kb;
    private final Hierarchy cTypes;
    private final CGraph story;
    private final List<Concept> targets;
    private final Map<String, Concept> directTargets;

    ReferenceSolver(Wkb kb) {
        this.kb = kb;
        this.cTypes = kb.getVocabulary().getConceptTypeHierarchy();
        this.story = kb.getLowConf();
        this.targets = new ArrayList<Concept>();
        this.directTargets = new Hashtable<String, Concept>();
    }

    public void addFact(CGraph fact) {
        Map<Concept, Concept> refs = new Hashtable<Concept, Concept>();

        Iterator<Concept> cit = fact.iteratorConcept();
        while (cit.hasNext()) {
            Concept c = cit.next();
            Concept t = getTarget(c);
            if (t != null) {
                refs.put(c, t);
            } else {
                String[] type = c.getType();
                Concept c2 = new Concept(IdUtil.newId());
                c2.setType(type);
                c2.setIndividual(c.getIndividual());
                story.addVertex(c2);
                if (cTypes.isKindOf(type, WkbConstants.NOUN_CT)) {
                    targets.add(c2);
                }
                refs.put(c, c2);
                if (cTypes.isKindOf(type, WkbConstants.PROPERNOUN_CT)) {
                    directTargets.put(c2.getIndividual(), c2);
                }
            }
        }

        Iterator<Relation> rit = fact.iteratorRelation();
        while (rit.hasNext()) {
            Relation r = rit.next();
            Relation r2 = new Relation(IdUtil.newId());
            r2.setType(r.getType());
            story.addVertex(r2);
            Iterator<CREdge> eit = fact.iteratorEdge(r.getId());
            while (eit.hasNext()) {
                CREdge e = eit.next();
                Concept c2 = refs.get(fact.getConcept(e));
                story.addEdge(c2.getId(), r2.getId(), e.getNumOrder());
            }
        }

    }

    private Concept getTarget(Concept c) {
        String[] type = c.getType();
        if (cTypes.isKindOf(type, WkbConstants.PROPERNOUN_CT)) {
            return findByIndividual(c);
        }
        if (cTypes.isKindOf(type, WkbConstants.PRONOUN_CT)) {
            return findByType(c);
        }
        if (cTypes.isKindOf(type, WkbConstants.POSSESSIVEADVERB_CT)) {
            return findByType(c);
        }
        return null;
    }

    private Concept findByIndividual(Concept c) {
        return directTargets.get(c.getIndividual());
    }

    private Concept findByType(Concept c) {
        for (int i = targets.size() - 1; i >= 0; --i) {
            Concept t = targets.get(i);
            if (isMatch(t, c)) {
                return t;
            }
        }
        return null;
    }

    private boolean isMatch(Concept t, Concept c) {
        Attr cAttr = kb.getAttr(c);
        Attr tAttr = kb.getAttr(t);
        if (cAttr.getNumber() != null && tAttr.getNumber() != null) {
            if (!cAttr.getNumber().equals(tAttr.getNumber())) {
                return false;
            }
        }
        if (cAttr.getGender() != null && tAttr.getGender() != null) {
            if (!cAttr.getGender().equals(tAttr.getGender())) {
                return false;
            }
        }
        return true;
    }
}
