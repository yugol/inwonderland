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

import org.purl.net.wonderland.kb.EngineKnowledgeBase;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.File;
import java.util.List;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class MessageProcessor {

    EngineKnowledgeBase ekb;
    Personality personality;

    public MessageProcessor() throws Exception {
        ekb = new EngineKnowledgeBase();
        setPersonality(new EtoGleem());
    }

    public String processMessage(String msg) {
        String resp = "Done.";

        for (List<WTagging> sentence : Pipeline.getTokens(msg)) {
            Object[] parse = Pipeline.parse(sentence);
            sentence = (List<WTagging>) parse[0];
            List<TypedDependency> deps = (List<TypedDependency>) parse[1];
            ekb.addSentenceFact(sentence, deps);
        }

        return resp;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
        this.personality.setKb(ekb);
    }

    public File getLastFile() {
        return ekb.getLastFile();
    }

    public void saveKb(File lastFile) throws Exception {
        ekb.saveKb(lastFile);
    }

    public void openKb(File file) throws Exception {
        ekb.openKb(file);
        personality.setKb(ekb);
    }

    public EngineKnowledgeBase getKb() {
        return ekb;
    }
}
