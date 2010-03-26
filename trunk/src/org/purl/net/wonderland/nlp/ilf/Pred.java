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

import java.util.Hashtable;
import java.util.Map;
import org.purl.net.wonderland.util.Compare;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Pred implements Comparable<Pred> {

    protected final String name;
    protected final Map<String, String> args;

    public Pred(String name) {
        this.name = name;
        args = new Hashtable<String, String>();
    }

    public String getName() {
        return name;
    }

    public String get(String argname) {
        return args.get(argname);
    }

    public void set(String argname, String argval) {
        args.put(argname, argval);
    }

    public int compareTo(Pred other) {
        int cmp = Compare.compare(this.args.size(), other.args.size());
        if (cmp == 0) {
            int v1 = Integer.parseInt(this.args.get("gov").substring(1));
            int v2 = Integer.parseInt(other.args.get("gov").substring(1));
            return Compare.compare(v1, v2);
        } else {
            return cmp;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append("(");
        sb.append(args.get("gov"));
        String dep = args.get("dep");
        if (dep != null) {
            sb.append(", ");
            sb.append(dep);
        }
        sb.append(")");
        return sb.toString();
    }
}
