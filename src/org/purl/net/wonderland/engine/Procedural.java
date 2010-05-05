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

import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.ProcManager;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.wsd.WsdProcManager;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Procedural {

    private ProcManager quick;
    private WsdProcManager wsd;

    Procedural(Wkb kb) throws Exception {
        quick = new ProcManager();
        readProcedureSet(kb, WkbUtil.PROC_SET_ARTICLES);
        readProcedureSet(kb, WkbUtil.PROC_SET_MOODS);
        readProcedureSet(kb, WkbUtil.PROC_SET_COLLO);
        wsd = new WsdProcManager(kb);
    }

    public ProcManager getQuick() {
        return quick;
    }

    public WsdProcManager getWsd() {
        return wsd;
    }

    private void readProcedureSet(Wkb kb, String set) throws Exception {
        ProcList procSet = kb.getProcRules(set);
        procSet.sort();
        quick.putProcList(set, procSet);
    }
}