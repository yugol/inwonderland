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
package org.purl.net.wonderland.nlp.ilf;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Pred_rel extends Pred_ {

    public Pred_rel() {
        super("rel");
    }

    public String getDep() {
        return args.get("dep");
    }

    public void setDep(String dep) {
        args.put("dep", dep);
    }

    public String getId2() {
        return args.get("id2");
    }

    public void setId2(String id2) {
        args.put("id2", id2);
    }

    public String getV1() {
        return args.get("v1");
    }

    public void setV1(String v1) {
        args.put("v1", v1);
    }

    public String getV2() {
        return args.get("v2");
    }

    public void setV2(String v2) {
        args.put("v2", v2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", ");
        sb.append(getId2());
        sb.append(", ");
        sb.append(getDep());
        sb.append(", ");
        sb.append(getV1());
        sb.append(", ");
        sb.append(getV2());
        sb.append(")");
        return sb.toString();
    }
}
