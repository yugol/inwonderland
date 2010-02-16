/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import java.util.Arrays;

/**
 *
 * @author Iulian
 */
public abstract class MorphologicalDatabase {

    public static String[] possesivePronouns = new String[]{"mine", "yours", "thine", "his", "hers", "ours", "theirs"};
    public static String[] demonstrativePronouns = new String[]{"this", "these", "that", "those", "former", "latter", "other", "others", "same", "so", "such", "such-and-such", "suchlike", "one", "ones"};
    public static String[] reflexivePronouns = new String[]{"myself", "yourself", "himself", "herself", "itself", "oneself", "ourselves", "yourselves", "themselves"};
    public static String[] indefinitePronouns = new String[]{"all", "both", "either", "neither", "each", "much", "many", "more", "most", "another", "others", "several", "enough", "everybody", "everything", "everyone", "some", "any", "none", "somebody", "something", "someone", "anybody", "anything", "anyone", "nobody", "nothing", "enough", "several", "one", "you", "they"};
    public static String[] relativePronouns = new String[]{"who", "whom", "whose", "whoever", "which", "whichever", "that", "what", "as"};
    public static String[] interrogativePronouns = new String[]{"who", "whose", "whom", "what", "which"};

    static {
        Arrays.sort(possesivePronouns);
        Arrays.sort(demonstrativePronouns);
        Arrays.sort(reflexivePronouns);
        Arrays.sort(indefinitePronouns);
        Arrays.sort(relativePronouns);
    }

    public static WTagging tagPronoun(WTagging prop) {

        // personal pronoun
        if (prop.lemma.equals("I")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("me")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("we")) {
            prop.wTag = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("us")) {
            prop.wTag = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("you")) {
            prop.wTag = "PnPRS";
            prop.person = "nd";
        } else if (prop.lemma.equals("he")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("she")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("it")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.gender = "neu";
            prop.person = "rd";
        } else if (prop.lemma.equals("him")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("her")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("they")) {
            prop.wTag = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("them")) {
            prop.wTag = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("thou")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("thee")) {
            prop.wTag = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("ye")) {
            prop.wTag = "PnPRS";
            prop.number = "plu";
            prop.person = "nd";
            //
            //
            // possessive pronoun
        } else if (prop.lemma.equals("mine")) {
            prop.wTag = "PnPOS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("yours")) {
            prop.wTag = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("thine")) {
            prop.wTag = "PnPOS";
            prop.wTag = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("his")) {
            prop.wTag = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "msc";
        } else if (prop.lemma.equals("hers")) {
            prop.wTag = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "fem";
        } else if (prop.lemma.equals("ours")) {
            prop.wTag = "PnPOS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("theirs")) {
            prop.wTag = "PnPOS";
            prop.number = "plu";
            prop.person = "rd";
            //
            //
            // demonstrative pronouns
        } else if (prop.lemma.equals("this")) {
            prop.wTag = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("these")) {
            prop.wTag = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("that")) {
            prop.wTag = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("those")) {
            prop.wTag = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("former")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("latter")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("other")) {
            prop.wTag = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("others")) {
            prop.wTag = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("same")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("so")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("such")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("such-and-such")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("suchlike")) {
            prop.wTag = "PnDEM";
        } else if (prop.lemma.equals("one")) {
            prop.wTag = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("ones")) {
            prop.wTag = "PnDEM";
            prop.number = "plu";
            //
            //
            // reflexive pronouns
        } else if (prop.lemma.equals("myself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("yourself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("himself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "msc";
        } else if (prop.lemma.equals("herself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "fem";
        } else if (prop.lemma.equals("itself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "neu";
        } else if (prop.lemma.equals("oneself")) {
            prop.wTag = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
        } else if (prop.lemma.equals("ourselves")) {
            prop.wTag = "PnREF";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("yourselves")) {
            prop.wTag = "PnREF";
            prop.number = "plu";
            prop.person = "nd";
        } else if (prop.lemma.equals("themselves")) {
            prop.wTag = "PnREF";
            prop.number = "plu";
            prop.person = "rd";
            //
            //
            // reflexive pronouns
        } else if (Arrays.binarySearch(indefinitePronouns, prop.lemma) >= 0) {
            tagIndefinitePronoun(prop);
        }
        return prop;
    }

    public static void tagIndefinitePronoun(WTagging prop) {
        prop.wTag = "PnIDF";
    }

    public static void tagRelativePronoun(WTagging prop) {
        prop.wTag = "PnREL";
    }

    public static void tagInterrogativePronoun(WTagging prop) {
        prop.wTag = "PnINT";
    }
}
