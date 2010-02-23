/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.engine;

import org.purl.net.wonderland.kb.EngineKnowledgeBase;
import edu.stanford.nlp.ling.HasWord;
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
