/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.File;
import java.util.List;
import ro.uaic.info.wonderland.analysis.StanfordParserWrapper;

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

        for (List<? extends HasWord> sent : StanfordParserWrapper.getSentences(msg)) {
            Tree parse = StanfordParserWrapper.parse(sent);
            Sentence<TaggedWord> posTags = StanfordParserWrapper.getPOSTags(parse);
            List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
            ekb.addSentenceFact(posTags, deps);
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
