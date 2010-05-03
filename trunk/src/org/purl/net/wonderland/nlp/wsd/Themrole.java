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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class Themrole {

    private final String pbN;
    private final String pbDescr;
    private final String vnThemrole;
    private final List<String> plusRestrs;
    private final List<String> minusRestrs;
    private int hits; // used for role selection

    public Themrole(String pnN, String pbDescr, String vnThemrole) {
        this.pbN = pnN;
        this.pbDescr = pbDescr;
        this.vnThemrole = vnThemrole;
        this.plusRestrs = new ArrayList<String>();
        this.minusRestrs = new ArrayList<String>();
        hits = 0;
    }

    public String getPbDescr() {
        return pbDescr;
    }

    public String getPbN() {
        return pbN;
    }

    public String getVnThemrole() {
        return vnThemrole;
    }

    public List<String> getMinusRestrs() {
        return minusRestrs;
    }

    public List<String> getPlusRestrs() {
        return plusRestrs;
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
