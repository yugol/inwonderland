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
package org.purl.net.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.util.Compare;

/**
 *
 * @author Iulian
 */
public class Engine {

    private Wkb kb;
    private File lastFile = null;
    private Personality personality;

    public File getLastFile() {
        return lastFile;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
        this.personality.setKb(kb);
    }

    public Engine() throws Exception {
        personality = new EtoGleem();
        openKb(null);
    }

    public void openKb(File file) throws Exception {
        if (file == null || Configuration.getDefaultParseKBFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            file = Configuration.getDefaultParseKBFile();
            lastFile = null;
        } else {
            lastFile = file;
        }
        kb = new Wkb(file);
        personality.setKb(kb);
    }

    public void saveKb(File file) throws Exception {
        if (Configuration.getDefaultParseKBFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            lastFile = null;
        } else {
            kb.save(file);
            lastFile = file;
        }
    }

    public String processMessage(String msg) throws Exception {
        return personality.processMessage(msg);
    }

    public CGraph getFact(int idx, String level) {
        return kb.getFactGraph(WkbUtil.toFactId(idx, level));
    }

    public int getFactCount(String level) {
        return kb.getFactCount(level);
    }

    public WTagging[] getFactWTaggings(int idx, boolean newTagsOnly, String level) {
        CGraph cg = getFact(idx, level);

        List<Concept> concepts = new ArrayList<Concept>();
        Iterator<Concept> it = cg.iteratorConcept();
        while (it.hasNext()) {
            concepts.add(it.next());
        }
        Collections.sort(concepts, new ConceptIdComparator());

        WTagging[] props = new WTagging[concepts.size()];
        for (int i = 0; i < props.length; ++i) {
            props[i] = kb.conceptLabelsToWTagging(concepts.get(i), newTagsOnly);
        }

        return props;
    }

    public int getFactCount() {
        return kb.getFactCount();
    }
}

class ConceptIdComparator implements Comparator<Concept> {

    public int compare(Concept o1, Concept o2) {
        int id1 = WkbUtil.getConceptIndex(o1.getId());
        int id2 = WkbUtil.getConceptIndex(o2.getId());
        return Compare.compare(id1, id2);
    }
}