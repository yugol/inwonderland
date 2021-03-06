/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, mergeWtags, publish, distribute, sublicense, and/or sell
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
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.nlp.ParseResult;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public abstract class Personality {

    public enum FactNature {

        STATEMENT, QUESTION, COMMAND
    }
    protected List<String> report = null;
    protected Mem memory = null;
    private int currentFactId;

    public void setMemory(Mem memory) {
        this.memory = memory;
        currentFactId = memory.getStorage().getFactCount(WkbConstants.LEVEL3);
    }

    public abstract String getWelcomeMessage();

    public abstract String getFullName();

    public abstract String getName();

    public abstract String getId();

    public int getCurrentFactId() {
        return currentFactId;
    }

    protected List<CGraph> parseMessage(String message) {
        Wkb kb = memory.getStorage();
        List<CGraph> facts = new ArrayList<CGraph>();

        for (List<WTagging> sentence : Pipeline.tokenizeAndSplit(message)) {
            ParseResult parseResult = Pipeline.parse(sentence);
            facts.add(kb.buildFactGraph(parseResult));
        }

        return facts;
    }

    private String createReport(List<String> report) {
        StringBuilder sb = new StringBuilder();
        for (String line : report) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(line);
        }
        return sb.toString();
    }

    protected void preHandleMessage() throws Exception {
        report = new ArrayList<String>();
    }

    public String handleMessage(String message) throws Exception {
        preHandleMessage();
        processMessage(message);
        postHandleMessage();
        return createReport(report);
    }

    protected void preProcessFacts() throws Exception {
    }

    protected void processMessage(String message) throws Exception {
        List<CGraph> facts = parseMessage(message);
        preProcessFacts();
        for (CGraph fact : facts) {
            ++currentFactId;
            handleFact(fact);
        }
    }

    protected abstract CGraph handleFact(CGraph fact) throws Exception;

    protected void postHandleMessage() throws Exception {
        if (report.size() == 0) {
            report.add("Ok.");
        }
    }
}
