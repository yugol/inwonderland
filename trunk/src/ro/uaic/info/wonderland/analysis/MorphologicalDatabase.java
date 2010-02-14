/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import java.util.Arrays;
import ro.uaic.info.wonderland.engine.EngineKnowledgeBase;

/**
 *
 * @author Iulian
 */
public abstract class MorphologicalDatabase {

    public static String[] possesivePronouns = new String[]{"mine", "yours", "thine", "his", "hers", "ours", "theirs"};
    public static String[] demonstrativePronouns = new String[]{"this", "these", "that", "those", "former", "latter", "other", "others", "same", "so", "such", "such-and-such", "suchlike", "one", "ones"};

    static {
        Arrays.sort(possesivePronouns);
        Arrays.sort(demonstrativePronouns);
    }

    public static PosProp getPronoun(PosProp prop) {

        // personal pronoun
        if (prop.lemma.equals("I")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("me")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("we")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("us")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("you")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("he")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("she")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("it")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("neu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("him")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("her")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("they")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("them")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("thou")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("thee")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("ye")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
            //
            //
            // possessive pronoun
        } else if (prop.lemma.equals("mine")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("yours")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("thine")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("his")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
        } else if (prop.lemma.equals("hers")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
        } else if (prop.lemma.equals("ours")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("theirs")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
            //
            //
            // demonstrative pronouns
        } else if (prop.lemma.equals("this")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
        } else if (prop.lemma.equals("these")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
        } else if (prop.lemma.equals("that")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
        } else if (prop.lemma.equals("those")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
        } else if (prop.lemma.equals("former")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("latter")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("other")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
        } else if (prop.lemma.equals("others")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
        } else if (prop.lemma.equals("same")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("so")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("such")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("such-and-such")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("suchlike")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
        } else if (prop.lemma.equals("one")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
        } else if (prop.lemma.equals("ones")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("PnDEM");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
        }
        return prop;
    }
}
