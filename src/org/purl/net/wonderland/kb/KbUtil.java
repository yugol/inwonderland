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
package org.purl.net.wonderland.kb;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class KbUtil {

    // some concept types from the knowledge base support
    public static final String Top = toConceptTypeId("Top");
    public static final String Pos = toConceptTypeId("Pos");
    public static final String SpTag = toConceptTypeId("SpTag");
    public static final String Case = toConceptTypeId("Case");
    public static final String Comparison = toConceptTypeId("Comparison");
    public static final String Gender = toConceptTypeId("Gender");
    public static final String Mood = toConceptTypeId("Mood");
    public static final String Number = toConceptTypeId("Number");
    public static final String Person = toConceptTypeId("Person");
    public static final String Tense = toConceptTypeId("Tense");
    // POS concept types
    public static final String Nn = toConceptTypeId("Nn");
    public static final String Vb = toConceptTypeId("Vb");
    public static final String Jj = toConceptTypeId("Jj");
    public static final String Rb = toConceptTypeId("Rb");
    // fact levels
    public static final String level1 = "level1";
    public static final String level2 = "level2";
    // procedures
    public static final String proc = "proc";
    public static final String procSyntaxSet = "syn";
    // other
    private static final NumberFormat idLabelNumberFormatter = new DecimalFormat("00000");
    private static final NumberFormat senseNumberFormatter = new DecimalFormat("00000000");

    public static String newUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String toIdIndex(int num) {
        return idLabelNumberFormatter.format(num);
    }

    public static String toLevel1FactId(int num) {
        return "l1_" + toIdIndex(num);
    }

    public static String toLevel2FactId(int num) {
        return "l2_" + toIdIndex(num);
    }

    public static String toConceptTypeId(String ctl) {
        return "ct_" + handleQuotes(ctl);
    }

    public static String toRelationTypeId(String ctl) {
        return "rt_" + ctl;
    }

    public static String toConceptId(WTagging tagging, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(handleQuotes(tagging.getForm()));
        sb.append("#");
        sb.append(index);
        sb.append("=[");
        if (tagging.getPennTag() != null) {
            sb.append(handleQuotes(tagging.getPennTag()));
        }
        sb.append("]{");
        if (tagging.getPartsOfSpeech() != null) {
            sb.append(handleQuotes(tagging.getPartsOfSpeech()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String toRelationId(String label, int index) {
        return label + "~" + index;
    }

    public static String handleQuotes(String ctl) {
        if ("''".equals(ctl)) {
            ctl = "-OPQ-";
        } else if ("``".equals(ctl)) {
            ctl = "-CLQ-";
        }
        return ctl.replace("'", "`");
    }

    public static String getConceptForm(String id) {
        int end = id.lastIndexOf("#");
        return id.substring(0, end);
    }

    public static int getConceptIndex(String id) {
        int beg = id.lastIndexOf('#') + 1;
        int end = id.lastIndexOf('=');
        int index = Integer.parseInt(id.substring(beg, end));
        return index;
    }

    public static String getConceptPennTag(String id) {
        int beg = id.lastIndexOf("[") + 1;
        int end = id.lastIndexOf("]");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    public static String getConceptMaTag(String id) {
        int beg = id.lastIndexOf("{") + 1;
        int end = id.lastIndexOf("}");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    public static int getLabelIndex(String label) {
        int beg = label.lastIndexOf("-") + 1;
        return Integer.parseInt(label.substring(beg));
    }

    public static String toSenseName(String particle, long offset) {
        return particle + senseNumberFormatter.format(offset);
    }

    public static Concept getConcept(CGraph cg, int j) {
        for (Concept c : cg.getConcepts()) {
            if (j == KbUtil.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }
}
