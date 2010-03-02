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
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.List;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.generators.GenRule;
import org.purl.net.wonderland.kb.generators.GenRuleManager;

/**
 *
 * @author Iulian
 */
public class EtoGleem extends Personality {

    private GenRuleManager genRuleMgr;

    @Override
    public String getWelcomeMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi!\n"
                + "Welcome to Wanderland, the place where meaning has no boundary!\n"
                + "We are glad that you are interested in our world.\n"
                + "I will be your facilitator. My name is ");
        sb.append(getFullName());
        sb.append(" but you can call me ");
        sb.append(getName());
        sb.append(".\nIs there anything you would preffer us to talk about?");
        return sb.toString();
    }

    @Override
    public String getFullName() {
        return "Eto Gleem";
    }

    @Override
    public String getName() {
        return "Eto";
    }

    @Override
    public String getId() {
        return "etogleem";
    }

    @Override
    public void setKb(WKnowledgeBase kb) {
        super.setKb(kb);
        genRuleMgr = new GenRuleManager(kb);
        try {
            genRuleMgr.readGenerators(KbUtil.level1);
        } catch (Exception ex) {
            System.err.println("Could not read generation rules from knowledge base");
            System.err.println(ex);
            Globals.exit();
        }
    }

    @Override
    public String processMessages(List<CGraph> messages) throws Exception {
        for (CGraph message : messages) {
            List<GenRule> rules = genRuleMgr.findMatches(KbUtil.level1, message);
            for (GenRule rule : rules) {
                List<Projection> matches = rule.getProjections();
                for (Projection match : matches) {
                    CGraph fact = extract(rule, match);
                    apply(fact);
                }
            }
        }
        return "Done.";
    }

    private CGraph extract(GenRule rule, Projection match) {
        return null;
    }

    private void apply(CGraph fact) {
        fact.setSet(KbUtil.level2);
        kb.addGraph(fact);
    }
}
