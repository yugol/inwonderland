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

    public static String[] possesivePronouns = new String[] {"mine", "yours", "thine", "his", "hers", "ours", "theirs"};

    static {
        Arrays.sort(possesivePronouns);
    }

    public static PosProp getPronoun(PosProp prop) {

        // personal pronoun
        if (prop.lemma.equals("I")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("me")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("we")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("us")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("you")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("he")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("she")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("it")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("neu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("him")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("her")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("they")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("them")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        } else if (prop.lemma.equals("thou")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("thee")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("ye")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPRS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } //
        //
        // possessive pronoun
        else if (prop.lemma.equals("mine")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("yours")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("thine")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("nd");
        } else if (prop.lemma.equals("his")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("msc");
        } else if (prop.lemma.equals("hers")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
            prop.gender = EngineKnowledgeBase.ctLabel2ctId("fem");
        } else if (prop.lemma.equals("ours")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("st");
        } else if (prop.lemma.equals("theirs")) {
            prop.type = EngineKnowledgeBase.ctLabel2ctId("PnPOS");
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
            prop.person = EngineKnowledgeBase.ctLabel2ctId("rd");
        }
        return prop;
    }
}
