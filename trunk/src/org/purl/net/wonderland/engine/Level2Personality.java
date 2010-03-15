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
package org.purl.net.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import java.util.List;
import org.purl.net.wonderland.kb.KbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level2Personality extends Personality {

    @Override
    public String getWelcomeMessage() {
        return "recognizing tenses, finding collocations, logics";
    }

    @Override
    public String getFullName() {
        return "Level 2";
    }

    @Override
    public String getName() {
        return "(test) L2";
    }

    @Override
    public String getId() {
        return KbUtil.level2;
    }

    @Override
    public String processMessage(String message) throws Exception {
        List<CGraph> facts = parseMessage(message);
        for (CGraph fact : facts) {
            kb.addFact(fact, KbUtil.level1);
            fact = KbUtil.duplicate(fact);
            processTenses(fact);
            // processCollocations(fact);
            kb.addFact(fact, KbUtil.level2);
        }
        return "Done.";
    }
}
