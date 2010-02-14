/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import ro.uaic.info.wonderland.analysis.PosProp;

/**
 *
 * @author Iulian
 */
public class PosUtil {

    public static boolean areConsistent(PosProp gold, PosProp auto) {
        boolean consistent = true;
        if (!gold.form.equals(auto.form)) {
            System.out.println("form: " + gold.form + " / " + auto.form);
            consistent = false;
        }
        if (!gold.lemma.equals(auto.lemma)) {
            System.out.println("lemma: " + gold.lemma + " / " + auto.lemma);
            consistent = false;
        }
        if (gold.comparison != null) {
            if (!gold.comparison.equals(auto.comparison)) {
                System.out.println("comparison: " + gold.comparison + " / " + auto.comparison);
                consistent = false;
            }
        }
        if (gold.gender != null) {
            if (!gold.gender.equals(auto.gender)) {
                System.out.println("gender: " + gold.gender + " / " + auto.gender);
                consistent = false;
            }
        }
        if (gold.mood != null) {
            if (!gold.mood.equals(auto.mood)) {
                System.out.println("mood: " + gold.mood + " / " + auto.mood);
                consistent = false;
            }
        }
        if (gold.number != null) {
            if (!gold.number.equals(auto.number)) {
                System.out.println("number: " + gold.number + " / " + auto.number);
                consistent = false;
            }
        }
        if (gold.person != null) {
            if (!gold.person.equals(auto.person)) {
                System.out.println("person: " + gold.person + " / " + auto.person);
                consistent = false;
            }
        }
        if (gold.posType != null) {
            if (!gold.posType.equals(auto.posType)) {
                System.out.println("posType: " + gold.posType + " / " + auto.posType);
                consistent = false;
            }
        }
        if (gold.tense != null) {
            if (!gold.tense.equals(auto.tense)) {
                System.out.println("tense: " + gold.tense + " / " + auto.tense);
                consistent = false;
            }
        }
        if (gold.theCase != null) {
            if (!gold.theCase.equals(auto.theCase)) {
                System.out.println("theCase: " + gold.theCase + " / " + auto.theCase);
                consistent = false;
            }
        }

        return consistent;
    }
}
