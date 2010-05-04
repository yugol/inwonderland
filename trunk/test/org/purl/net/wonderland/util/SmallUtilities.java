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
package org.purl.net.wonderland.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.MorphologicalDatabase;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;

/**
 *
 * @author Iulian
 */
public class SmallUtilities {

    @Test
    public void announcement() {
        System.out.println("These procedures are used for maintenance only.");
    }

    @Test
    public void testNormalizeConceptTypes() throws Exception {
        System.out.println("Normalizing concept types in default .cogxml file");
        WkbUtil.normalizeKbFile(Configuration.getDefaultParseKBFile());
        //WkbUtil.normalizeKbFile(new File("C:\\Users\\Iulian\\Projects\\wonderland\\res\\test\\bedtime.cogxml"));
    }

    // @Test
    public void reIndexGoldCorpus() throws Exception {
        System.out.println("Re-indexing gold corpus");
        File goldFile = new File(Configuration.getCorporaFolder(), "egcp.train.level1.xml");
        Corpus.normalizeCorpusFile(goldFile);
    }

    // @Test
    public void findTaggingDuplicates() {
        System.out.println("Find polysemy entries in morphological database");
        for (String form : MorphologicalDatabase.getAllForms()) {
            List<WTagging> taggings = MorphologicalDatabase.getAllTagings(form);
            if (taggings.size() > 1) {
                displayTaggings(taggings, form);
            }
        }
    }

    // @Test
    public void findFormInDatabase() {
        System.out.println("Find form in morphological database");
        String form = "whatever";
        List<WTagging> taggings = MorphologicalDatabase.getAllTagings(form);
        if (taggings.size() > 0) {
            displayTaggings(taggings, form);
        }
    }

    // @Test
    public void getSentencesFromCorpus() throws ParserConfigurationException, SAXException {
        System.out.println("Getting sentences from corpus");
        Corpus gold = new Corpus();
        File goldFile = new File(Configuration.getCorporaFolder(), "gold.xml");
        gold.buildFrom(goldFile);

        for (int i = 1; i <= gold.getSentenceCount(); ++i) {
            String sentence = gold.getSentenceStringByIndex(i);
            System.out.println(sentence);
        }
    }

    private void displayTaggings(List<WTagging> taggings, String form) {
        System.out.println("");
        System.out.println(form);
        for (WTagging tagging : taggings) {
            System.out.println(tagging.toCsvString());
        }
    }

    // @Test
    public void listWordNet() {
        System.out.println("Collocations listing");
        WordNetWrapper.listCollocations();
    }

    // @Test
    public void buildVerbNetIndexes() throws FileNotFoundException {
        System.out.println("Indexing VerbNet");
        VerbNetWrapper.init();
    }
}
