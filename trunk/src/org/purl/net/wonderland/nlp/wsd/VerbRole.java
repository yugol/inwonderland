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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VerbRole {

    static String normalizeThematicRoleName(String name) {
        return WsdUtil.capitalize(name.toLowerCase());
    }
    private final String pbN;
    private final String pbDescr;
    private final String vnType;
    private final Set<VnRestr> vnSelrestrs;
    private int hits; // used for role selection in creating graph procedures

    public VerbRole(String pnN, String pbDescr, String vnType) {
        this.pbN = pnN;
        this.pbDescr = pbDescr;
        this.vnType = vnType;
        this.vnSelrestrs = new HashSet<VnRestr>();
        hits = 0;
    }

    public String getPbDescr() {
        return pbDescr;
    }

    public String getPbN() {
        return pbN;
    }

    public String getVnType() {
        return vnType;
    }

    public Set<VnRestr> getVnSelrestrs() {
        return vnSelrestrs;
    }

    public int getHits() {
        return hits;
    }

    public void resetHits() {
        this.hits = 0;
    }

    public void incrementHits() {
        this.hits += 1;
    }
}
