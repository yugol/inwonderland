/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
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
package org.purl.net.wonderland.nlp.ilf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.purl.net.wonderland.cg.Concept;
import org.purl.net.wonderland.cg.ConceptType;
import org.purl.net.wonderland.cg.ConceptTypeSet;
import org.purl.net.wonderland.cg.ConceptualGraph;
import org.purl.net.wonderland.cg.Edge;
import org.purl.net.wonderland.cg.Individual;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.cg.Relation;
import org.purl.net.wonderland.cg.RelationType;
import org.purl.net.wonderland.cg.Support;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class IlfRep {

    private String text;
    private final List<Pred> prettyIlfs = new ArrayList<Pred>();
    private final List<Pred> ilfs = new ArrayList<Pred>();

    public void addPrettyIlf(Pred pred) {
        prettyIlfs.add(pred);
    }

    public List<Pred> getPrettyIlfs() {
        return prettyIlfs;
    }

    public List<Pred> getIlfs() {
        return ilfs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void sort() {
        Collections.sort(prettyIlfs, new FormComparator());
        Collections.sort(ilfs, new FormComparator());
    }

    public void addIlf(Pred pred) {
        ilfs.add(pred);
    }

    public boolean isOk() {
        return (ilfs.size() > 0);
    }

    public KnowledgeBase buildKnowledgeBase() {
        KnowledgeBase kb = new KnowledgeBase();
        Support support = kb.getSupport();

        ConceptType top = support.addConceptType("POS");

        RelationType link = support.addRelationType("link", 2);
        link.getSignature()[0].add(top);
        link.getSignature()[1].add(top);

        ConceptualGraph cg = new ConceptualGraph();
        cg.setLabel(text);
        cg.setSet("ilf");

        for (Pred pred : ilfs) {
            if (pred.getName().equals("w")) {

                Pred_w w = (Pred_w) pred;

                ConceptType cat = support.getConceptType(w.getCat());
                if (cat == null) {
                    cat = support.addConceptType(w.getCat());
                    ConceptType pos = support.getConceptType(w.getPos());
                    if (pos == null) {
                        pos = support.addConceptType(w.getPos());
                        pos.addParent(top);
                    }
                    cat.addParent(pos);
                }

                Individual marker = support.getIndividual(w.getWord());
                if (marker == null) {
                    marker = support.addIndividual(w.getWord(), top);
                }

                String conceptId = createConceptId(w.getSid(), w.getId1());
                Concept c = new Concept(conceptId);
                c.setIndividual(marker);
                c.addType(cat);
                cg.add(c);

            } else if (pred.getName().equals("rel")) {

                Pred_rel rel = (Pred_rel) pred;

                RelationType dep = support.getRelationType(rel.getDep());
                if (dep == null) {
                    dep = support.addRelationType(rel.getDep(), 2);
                    dep.getSignature()[0].add(top);
                    dep.getSignature()[1].add(top);
                    dep.setParent(link);
                }

                Relation r = new Relation(dep);
                cg.add(r);
                Edge edge = new Edge(r, cg.getConcepts().get(rel.getV2()), 0);
                cg.add(edge);
                edge = new Edge(r, cg.getConcepts().get(rel.getV1()), 1);
                cg.add(edge);

            }
        }
        kb.addFact(cg);
        return kb;
    }

    private String createConceptId(String sid, String id1) {
        return "G" + sid + "_" + id1;
    }
}

class FormComparator implements Comparator<Pred> {

    public int compare(Pred o1, Pred o2) {
        if (!o1.getName().equals(o2.getName())) {
            if (o1.getName().equals("w")) {
                return -1;
            }
            if (o1.getName().equals("e")) {
                if (o2.getName().equals("w")) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (o1.getName().equals("rel")) {
                if (o2.getName().equals("e")) {
                    return 1;
                } else if (o2.getName().equals("w")) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (o1.getName().equals("syn")) {
                if (o2.getName().equals("e")) {
                    return 1;
                } else if (o2.getName().equals("w")) {
                    return 1;
                } else if (o2.getName().equals("rel")) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return o1.compareTo(o2);
    }
}
