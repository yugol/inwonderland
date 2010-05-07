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
package org.purl.net.wonderland.cg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class KnowledgeBase {

    private final String language;
    private final Support support;
    private final Map<String, Set<ConceptualGraph>> facts = new HashMap<String, Set<ConceptualGraph>>();
    private final Map<String, Set<Rule>> rules = new HashMap<String, Set<Rule>>();

    public KnowledgeBase() {
        this("en", new Support());
    }

    public KnowledgeBase(String language, Support support) {
        this.language = language;
        this.support = support;
    }

    public String getLanguage() {
        return language;
    }

    public Support getSupport() {
        return support;
    }

    public Map<String, Set<ConceptualGraph>> getFacts() {
        return facts;
    }

    public void addFact(ConceptualGraph cg) {
        Set<ConceptualGraph> factSet = facts.get(cg.getSet());
        if (factSet == null) {
            factSet = new HashSet<ConceptualGraph>();
            facts.put(cg.getSet(), factSet);
        }
        factSet.add(cg);
    }

    public void addRule(Rule proc) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
