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
package org.purl.net.wonderland.nlp.resources.verbnet;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbForm {

    private final String lemma;
    private final Map<String, Set<String>> vnClasses = new Hashtable<String, Set<String>>();

    public VerbForm(String lemma) {
        this.lemma = lemma;
    }

    public String getLemma() {
        return lemma;
    }

    public Set<String> getVnClasses() {
        return vnClasses.keySet();
    }

    public void addVnClass(String vc) {
        if (!vnClasses.containsKey(vc)) {
            vnClasses.put(vc, new HashSet<String>());
        }
    }

    public void addWnSense(String vc, String wnSense) {
        addVnClass(vc);
        vnClasses.get(vc).add(wnSense);
    }

    public Set<String> getWnSenses(String vc) {
        return vnClasses.get(vc);
    }
}
