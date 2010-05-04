/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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
package org.purl.net.wonderland.kb;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcManager {

    private final Map<String, ProcList> allProcs;

    public ProcManager() {
        allProcs = new Hashtable<String, ProcList>();
    }

    public ProcManager(Wkb kb) throws Exception {
        this();
    }

    public int getProcCount() {
        int count = 0;
        for (ProcList set : allProcs.values()) {
            count += set.size();
        }
        return count;
    }

    public void putProcList(String name, ProcList procs) {
        allProcs.put(name, procs);
    }

    public ProcList getProcSet(String name) {
        return allProcs.get(name);
    }

}
