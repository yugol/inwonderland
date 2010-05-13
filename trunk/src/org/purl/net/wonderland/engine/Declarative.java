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
package org.purl.net.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.util.Gazetteers;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Declarative {

    private final Wkb storage;
    private final Hierarchy cth;

    Declarative(File file) throws Exception {
        this.storage = new Wkb(file);
        this.cth = storage.getVocabulary().getConceptTypeHierarchy();
    }

    void save(File file) throws Exception {
        storage.save(file);
    }

    public Wkb getStorage() {
        return storage;
    }

    public Hierarchy getCth() {
        return cth;
    }

    public List<String> getImportWordNetSenses(Concept c) {
        String lemma = c.getIndividual();
        String[] types = c.getType();

        List<String> senseTypes = null;

        if (cth.isKindOf(types, WkbConstants.PROPERNOUN_CT)) {
            senseTypes = Gazetteers.getWnSensesFromNamedEntity(lemma);
            storage.importWordNetHypernymHierarchy(senseTypes);
        } else if (cth.isKindOf(types, WkbConstants.COMMONNOUN_CT)) {
            senseTypes = storage.importWordNetHypernymHierarchy(lemma, POS.NOUN);
        } else if (cth.isKindOf(types, WkbConstants.VERB_CT)) {
            senseTypes = storage.importWordNetHypernymHierarchy(lemma, POS.VERB);
        } else if (cth.isKindOf(types, WkbConstants.ADJECTIVE_CT)) {
            senseTypes = storage.importWordNetHypernymHierarchy(lemma, POS.ADJECTIVE);
        } else if (cth.isKindOf(types, WkbConstants.ADVERB_CT)) {
            senseTypes = storage.importWordNetHypernymHierarchy(lemma, POS.ADVERB);
        }

        return senseTypes;
    }

    List<String> deriveThematicRolesTypes(String[] types) {
        List<String> thematicRoleTypes = new ArrayList<String>();
        for (String conceptType : types) {
            for (String registeredType : Gazetteers.wn2themroletype.keySet()) {
                if (cth.isKindOf(conceptType, registeredType)) {
                    thematicRoleTypes.add(Gazetteers.wn2themroletype.get(registeredType));
                }
            }
        }
        return thematicRoleTypes;
    }
}
