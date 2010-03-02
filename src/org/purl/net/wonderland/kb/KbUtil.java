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
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class KbUtil {

    public static final String topConceptType = toConceptTypeId("Top");
    public static final String posConceptType = toConceptTypeId("Pos");
    public static final String spTagConceptType = toConceptTypeId("SpTag");
    public static final String caseConceptType = toConceptTypeId("Case");
    public static final String comparisonConceptType = toConceptTypeId("Comparison");
    public static final String genderConceptType = toConceptTypeId("Gender");
    public static final String moodConceptType = toConceptTypeId("Mood");
    public static final String numberConceptType = toConceptTypeId("Number");
    public static final String personConceptType = toConceptTypeId("Person");
    public static final String tenseConceptType = toConceptTypeId("Tense");
    public static final String level1 = "level1";
    public static final String level2 = "level2";
    private static final NumberFormat messageNumberFormatter = new DecimalFormat("0000");
    private static final NumberFormat senseNumberFormatter = new DecimalFormat("00000000");

    public static String toIdIndex(int num) {
        return messageNumberFormatter.format(num);
    }

    public static String toLevel1FactId(int num) {
        return "l1_" + toIdIndex(num);
    }

    public static String toConceptTypeId(String ctl) {
        return "ct_" + removeQuotes(ctl);
    }

    public static String toRelationTypeId(String ctl) {
        return "rt_" + ctl;
    }

    public static String toConceptId(WTagging tagging, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(removeQuotes(tagging.getForm()));
        sb.append("#");
        sb.append(index);
        sb.append("=[");
        if (tagging.getPennTag() != null) {
            sb.append(removeQuotes(tagging.getPennTag()));
        }
        sb.append("]{");
        if (tagging.getPartsOfSpeech() != null) {
            sb.append(removeQuotes(tagging.getPartsOfSpeech()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String toRelationId(String label, int index) {
        return label + "~" + index;
    }

    public static String removeQuotes(String ctl) {
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
