/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iulian
 */
public class TextToNumber {

    static Map<String, String> cardinals = new HashMap<String, String>();
    static Map<String, String> ordinals = new HashMap<String, String>();

    static {
        cardinals.put("zero", "0");
        cardinals.put("nough", "0");
        cardinals.put("oh", "0");
        cardinals.put("love", "0");
        cardinals.put("nil", "0");
        cardinals.put("one", "1");
        cardinals.put("two", "2");
        cardinals.put("three", "3");
        cardinals.put("four", "4");
        cardinals.put("five", "5");
        cardinals.put("six", "6");
        cardinals.put("seven", "7");
        cardinals.put("eight", "8");
        cardinals.put("nine", "9");
        cardinals.put("ten", "10");
        cardinals.put("thousand", "1000");
        cardinals.put("thirty-seven", "37");

        ordinals.put("first", "1");
        ordinals.put("second", "2");
        ordinals.put("thid", "3");
        ordinals.put("fourth", "4");
        ordinals.put("fifth", "5");
        ordinals.put("sixth", "6");
        ordinals.put("seventh", "7");
        ordinals.put("eighth", "8");
        ordinals.put("ninth", "9");
        ordinals.put("tenth", "10");
    }

    public static String getValue(String text) {
        text = text.trim().toLowerCase();
        try {
            Double.parseDouble(text);
            return text;
        } catch (RuntimeException rte) {
            String value = cardinals.get(text);
            if (value == null) {
                value = ordinals.get(text);
            }
            return value;
        }
    }
}
