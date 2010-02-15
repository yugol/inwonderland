/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import java.util.Arrays;

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

    public static WTagging getPronoun(WTagging prop) {

        // personal pronoun
        if (prop.lemma.equals("I")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("me")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("we")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("us")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("you")) {
            prop.pos = "PnPRS";
            prop.person = "nd";
        } else if (prop.lemma.equals("he")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("she")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("it")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "neu";
            prop.person = "rd";
        } else if (prop.lemma.equals("him")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("her")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("they")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("them")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("thou")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("thee")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("ye")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "nd";
            //
            //
            // possessive pronoun
        } else if (prop.lemma.equals("mine")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("yours")) {
            prop.pos = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("thine")) {
            prop.pos = "PnPOS";
            prop.pos = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("his")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "msc";
        } else if (prop.lemma.equals("hers")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "fem";
        } else if (prop.lemma.equals("ours")) {
            prop.pos = "PnPOS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("theirs")) {
            prop.pos = "PnPOS";
            prop.number = "plu";
            prop.person = "rd";
            //
            //
            // demonstrative pronouns
        } else if (prop.lemma.equals("this")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("these")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("that")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("those")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("former")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("latter")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("other")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("others")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("same")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("so")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("such")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("such-and-such")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("suchlike")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("one")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("ones")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        }
        return prop;
    }
}
