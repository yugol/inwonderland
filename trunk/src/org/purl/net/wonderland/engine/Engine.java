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
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.util.Compare;

/**
 *
 * @author Iulian
 */
public class Engine {

    private Memory memory;
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
        this.personality.setMemory(memory);
    }

    public Engine() throws Exception {
        personality = new EtoGleem();
        openKb(null);
    }

    public void openKb(File file) throws Exception {
        if (file == null || W.getDefaultWkbFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            file = W.getDefaultWkbFile();
            lastFile = null;
        } else {
            lastFile = file;
        }
        memory = new Memory(file);
        personality.setMemory(memory);
    }

    public void saveKb(File file) throws Exception {
        if (W.getDefaultWkbFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            lastFile = null;
        } else {
            memory.save(file);
            lastFile = file;
        }
    }

    public String processMessage(String msg) throws Exception {
        return personality.processMessage(msg);
    }

    public CGraph getFact(int idx, String level) {
        return memory.getStorage().getFactGraph(WkbUtil.toFactId(idx, level));
    }

    public int getFactCount(String level) {
        return memory.getStorage().getFactCount(level);
    }

    public WTagging[] getFactWTaggings(int idx, boolean newTagsOnly, String level) {
        CGraph cg = getFact(idx, level);

        WTagging[] props = new WTagging[cg.getConcepts().size()];
        int conceptIndex = 0;
        for (Concept c : cg.getConcepts()) {
            props[conceptIndex] = memory.getStorage().conceptLabelsToWTagging(c, newTagsOnly);
            props[conceptIndex].setIndex((conceptIndex + 1) + "");
            ++conceptIndex;
        }

        return props;
    }

    public int getFactCount() {
        return memory.getStorage().getFactCount();
    }
}
