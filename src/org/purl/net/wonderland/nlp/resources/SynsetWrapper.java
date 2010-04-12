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
package org.purl.net.wonderland.nlp.resources;

import java.util.ArrayList;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.nlp.ilf.IlfRep;
import org.purl.net.wonderland.nlp.ilf.Pred;
import org.purl.net.wonderland.util.Formatting;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class SynsetWrapper {

    private final int index;
    private final String lemma;
    private final Synset sense;
    private IlfRep ilf;

    public SynsetWrapper(int index, String item, Synset sense) {
        this.index = index;
        this.sense = sense;
        if (item != null) {
            IndexWord word = WordNetWrapper.lookup(item, sense.getPOS());
            this.lemma = word.getLemma().replace(" ", "_");
        } else {
            this.lemma = null;
        }
        try {
            this.ilf = IlfWnWrapper.getPrettyIlf(Formatting.toWordNetOffsetKeyNum(sense.getPOS(), sense.getOffset()));
        } catch (Exception ex) {
            this.ilf = null;
            Configuration.reportExceptionConsole(ex);
        }
    }

    public IlfRep getIlf() {
        return ilf;
    }

    @Override
    public String toString() {
        if (index >= 0) {
            return (index + 1) + ". " + sense.getWord(0).getLemma();
        } else {
            return sense.getWord(0).getLemma();
        }
    }

    public Synset getHypernym() {
        Pointer[] ptrs = sense.getPointers(PointerType.HYPERNYM);
        if (ptrs.length > 0) {
            return WordNetWrapper.lookup(ptrs[0].getTargetOffset(), sense.getPOS());
        }
        return null;
    }

    public String[] getExplanations() {
        ArrayList<String> explanations = new ArrayList<String>();
        for (String item : sense.getGloss().split(";")) {
            item = item.trim();
            if (item.charAt(0) != '"') {
                explanations.add(item);
            } else {
                break;
            }
        }
        return explanations.toArray(new String[explanations.size()]);
    }

    public String[] getExamples() {
        ArrayList<String> examples = new ArrayList<String>();
        for (String item : sense.getGloss().split(";")) {
            item = item.trim();
            if (item.charAt(0) == '"') {
                examples.add(item);
            }
        }
        return examples.toArray(new String[examples.size()]);
    }

    public String getSenseHTML() {
        StringBuilder html = new StringBuilder();

        html.append("<u>Synonyms</u><br/>");
        html.append(getWordsHTML());
        html.append("<br/><br/>");

        String explanations = getExplanationsHTML();
        if (explanations.length() > 0) {
            html.append("<u>Explanations</u><br/>");
            html.append(explanations);
            html.append("<br/>");
        }

        String examples = getExamplesHTML();
        if (examples.length() > 0) {
            html.append("<u>Examples</u><br/>");
            html.append(examples);
            html.append("<br/>");
        }

        html.append("<u>Synset data</u><br/>");
        html.append(getSynsetDataHTML());
        html.append("<br/>");

        return html.toString();
    }

    public String getExplanationsHTML() {
        StringBuilder html = new StringBuilder();
        String[] explanations = getExplanations();
        if (explanations.length > 0) {
            html.append("<table border='0' cellpadding='0'>");
            for (int i = 0; i < explanations.length; i++) {
                html.append("<tr>");
                html.append("<td valign='top'>");
                html.append("-&nbsp&nbsp;");
                html.append("</td>");
                html.append("<td>");
                html.append(explanations[i]);
                html.append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        return html.toString();
    }

    public String getExamplesHTML() {
        StringBuilder html = new StringBuilder();
        String[] examples = getExamples();
        if (examples.length > 0) {
            html.append("<table border='0' cellpadding='0'>");
            for (int i = 0; i < examples.length; i++) {
                html.append("<tr>");
                html.append("<td valign='top'>");
                html.append((i + 1) + ".&nbsp;");
                html.append("</td>");
                html.append("<td>");
                html.append(examples[i]);
                html.append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        return html.toString();
    }

    public String getWordsHTML() {
        StringBuilder html = new StringBuilder();

        Word[] words = sense.getWords();
        for (int i = 0; i < words.length; i++) {
            Word word = words[i];
            if (i > 0) {
                html.append(", ");
            }
            String wordLemma = word.getLemma();
            if (wordLemma.equalsIgnoreCase(lemma)) {
                html.append("<b><font color='fuchsia'>");
                html.append(wordLemma.replace("_", " "));
                html.append("</font></b>");
            } else {
                html.append(wordLemma.replace("_", " "));
            }
        }

        return html.toString();
    }

    public String getSynsetDataHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<table border='0' cellpadding='0'>");

        String key = sense.getKey().toString();
        String offset = Formatting.toWordNetOffset(sense.getOffset());
        String offsetKeyAlpha = Formatting.toWordNetOffsetKeyAlpha(sense.getPOS(), sense.getOffset());
        String offsetKeyNum = Formatting.toWordNetOffsetKeyNum(sense.getPOS(), sense.getOffset());
        String senseKey = WordNetWrapper.offsetKeyAlphaTpSenseKey(offsetKeyAlpha);

        html.append("<tr>");
        html.append("<td>Key:</td>");
        html.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
        html.append("<td align='right'>");
        html.append(key);
        html.append("</td>");
        html.append("</tr>");

        html.append("<tr>");
        html.append("<td>Offset:</td>");
        html.append("<td></td>");
        html.append("<td align='right'>");
        html.append(offset);
        html.append("</td>");
        html.append("</tr>");

        html.append("<tr>");
        html.append("<td>Offset key (alpha):</td>");
        html.append("<td></td>");
        html.append("<td align='right'>");
        html.append(offsetKeyAlpha);
        html.append("</td>");
        html.append("</tr>");

        html.append("<tr>");
        html.append("<td>Offset key (num):</td>");
        html.append("<td></td>");
        html.append("<td align='right'>");
        html.append(offsetKeyNum);
        html.append("</td>");
        html.append("</tr>");

        html.append("<tr>");
        html.append("<td>Sense key:</td>");
        html.append("<td></td>");
        html.append("<td align='right'>");
        html.append(senseKey);
        html.append("</td>");
        html.append("</tr>");

        html.append("</table>");
        return html.toString();
    }

    public String getIlfHTML() {
        StringBuilder html = new StringBuilder();
        if (ilf == null) {
            html.append("Data not available !!!");
        } else {

            html.append(getTextCountHTML(ilf.getText()));
            html.append("<br/><br/>");
            html.append("<table border='0' cellpadding='0'>");

            html.append("<tr>");
            html.append("<th>");
            html.append("ilf");
            html.append("</th>");
            html.append("<th>");
            html.append("&nbsp;&nbsp;&nbsp;");
            html.append("</th>");
            html.append("<th>");
            html.append("pretty-ilf");
            html.append("</th>");
            html.append("<tr>");

            html.append("<tr>");
            html.append("<td valign='top'>");
            for (Pred pred : ilf.getIlfs()) {
                html.append(getPredHTML(pred.toString()));
                html.append("<br/>");
            }
            html.append("</td>");
            html.append("<td></td>");
            html.append("<td valign='top'>");
            for (Pred pred : ilf.getPrettyIlfs()) {
                html.append(getPredHTML(pred.toString()));
                html.append("<br/>");
            }
            html.append("</td>");
            html.append("</tr>");

            html.append("</table>");
        }
        return html.toString();
    }

    private String getPredHTML(String rep) {
        int pos = rep.indexOf("(");
        StringBuilder html = new StringBuilder("<font color='blue'><b>");
        html.append(rep.substring(0, pos));
        html.append("</b></font> ");
        html.append(rep.substring(pos));
        return html.toString();
    }

    private String getTextCountHTML(String rep) {
        StringBuilder html = new StringBuilder();
        String[] words = rep.split(" ");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            html.append(word);
            html.append("<font color='fuchsia'>");
            html.append("/" + (i + 1));
            html.append("</font> ");
        }
        return html.toString();
    }

    private String getDummyHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<table border='0' cellpadding='0'>");
        html.append("<tr>");
        html.append("<td></td>");
        html.append("<td>");
        html.append("");
        html.append("</td>");
        html.append("</tr>");
        html.append("</table>");
        return html.toString();
    }
}
