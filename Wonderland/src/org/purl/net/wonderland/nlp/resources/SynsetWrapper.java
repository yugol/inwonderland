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
package org.purl.net.wonderland.nlp.resources;

import java.util.ArrayList;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class SynsetWrapper {

    public final Synset synset;

    public SynsetWrapper(Synset synset) {
        this.synset = synset;
    }

    public Synset getHypernym() {
        Pointer[] ptrs = synset.getPointers(PointerType.HYPERNYM);
        if (ptrs.length > 0) {
            return WordNetWrapper.lookup(ptrs[0].getTargetOffset(), synset.getPOS());
        }
        return null;
    }

    public String[] getExplanations() {
        ArrayList<String> explanations = new ArrayList<String>();
        for (String item : synset.getGloss().split(";")) {
            item = item.trim();
            if (item.charAt(0) != '"') {
                explanations.add(item);
            } else {
                break;
            }
        }
        return explanations.toArray(new String[explanations.size()]);
    }

    public String[] getExamples() {
        ArrayList<String> examples = new ArrayList<String>();
        for (String item : synset.getGloss().split(";")) {
            item = item.trim();
            if (item.charAt(0) == '"') {
                examples.add(item);
            }
        }
        return examples.toArray(new String[examples.size()]);
    }

    public Synset getSynset() {
        return synset;
    }

    public String getFirstExplanation() {
        String[] ex = getExplanations();
        if (ex.length > 0) {
            return ex[0];
        }
        return null;
    }

    public String getFirstExample() {
        String[] ex = getExamples();
        if (ex.length > 0) {
            return ex[0];
        }
        return null;
    }
}
