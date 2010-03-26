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
package org.purl.net.wonderland.ui;

import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.Formatting;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class SynsetData {

    private final Synset sense;
    private final String lemma;

    SynsetData(Synset sense, String item) {
        this.sense = sense;
        IndexWord word = WordNetWrapper.lookup(item, sense.getPOS());
        this.lemma = word.getLemma().replace(" ", "_");
    }

    @Override
    public String toString() {
        return sense.getWord(0).getLemma();
    }

    public String getExplanation() {
        return sense.getGloss().split(";")[0];
    }

    public String getSenseHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<u>Synonyms</u><br/>");
        html.append(getWordsHTML());
        html.append("<br/><br/>");
        html.append(getGlossHTML());
        html.append("<br/>");
        html.append("<u>Synset data</u><br/>");
        html.append(getSynsetDataHTML());
        return html.toString();
    }

    public String getGlossHTML() {
        StringBuilder html = new StringBuilder();
        String[] gloss = sense.getGloss().split(";");
        html.append("<u>Explanation</u><br/>");
        html.append(gloss[0]);
        html.append("<br/><br/>");
        if (gloss.length > 1) {
            html.append("<u>Examples</u>");
            html.append("<table border='0' cellpadding='0'>");
            for (int i = 1; i < gloss.length; i++) {
                html.append("<tr>");
                html.append("<td>");
                html.append(i + ".&nbsp;");
                html.append("</td>");
                html.append("<td>");
                html.append(gloss[i]);
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
                html.append("<b><font color='red'>");
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
