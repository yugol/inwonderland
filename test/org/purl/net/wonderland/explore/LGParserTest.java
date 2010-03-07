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
package org.purl.net.wonderland.explore;

import edu.northwestern.at.morphadorner.tools.lgparser.LGParser;
import net.sf.jlinkgrammar.CNode;
import net.sf.jlinkgrammar.Linkage;
import net.sf.jlinkgrammar.ParseOptions;
import net.sf.jlinkgrammar.Sentence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Globals;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class LGParserTest {

    public LGParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void parseTest() {
        LGParser parser = new LGParser(Globals.getLgParserPath());
        Sentence sent = parser.parse("How do you do?");


        ParseOptions opts = new ParseOptions();

        CNode root, current, next, previous;
        Linkage linkage;
        int numLinkages;
        int num_to_query;
        int i;

        if (sent.sentence_num_linkages_found() > 0) {
            // We have to walk all the linakges throwing away the bad ones.
            num_to_query = Math.min(sent.sentence_num_linkages_post_processed(), 1000);


            for (i = 0; i < num_to_query; ++i) {

                if ((sent.sentence_num_violations(i) > 0) && (!opts.parse_options_get_display_bad())) {
                    continue;
                }

                // O.K. we have our fisrt valid linkage.  Do we want to print them all? No just one.
                // TODO - optimize this somehow
                linkage = new Linkage(i, sent, opts);
                // linkage = new Linkage(0, sent, opts);
                int j, first_sublinkage;

                // In effect we are saying display sublinkages
                linkage.linkage_compute_union();
                numLinkages = linkage.linkage_get_num_sublinkages();
                first_sublinkage = numLinkages - 1;


                for (j = first_sublinkage; j < numLinkages; ++j) {
                    linkage.linkage_set_current_sublinkage(j);
                    root = linkage.linkage_constituent_tree();
                    // Now we can walk the linkage and print the structure
                    current = root;

                    int w = 0;
                    do {
                        opts.out.println(linkage.word.get(w++).toString());
                        System.out.println(current.toString());
                    } while (w < linkage.word.size());
                    // string = linkage_print_diagram();
                    // opts.out.println(string);
                }

            }
        }
    }

}
