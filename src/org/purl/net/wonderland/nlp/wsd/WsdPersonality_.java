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
package org.purl.net.wonderland.nlp.wsd;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import java.util.List;
import org.purl.net.wonderland.engine.Level2Personality;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbUtil_;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class WsdPersonality_ extends Level2Personality {

    @Override
    public String getWelcomeMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFullName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String processMessage(String message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CGraph parse(String words) throws Exception {
        List<CGraph> facts = parseMessage(words);
        projSlv.reset();
        CGraph fact = facts.get(0);
        fact = WkbUtil_.duplicate(fact);
        // processMoods(fact);
        processArticles(fact);
        // processCollocations(fact);
        kb.addFact(fact, WkbConstants.LEVEL2);
        return fact;

    }

    public Wkb getKb() {
        return kb;
    }
}
