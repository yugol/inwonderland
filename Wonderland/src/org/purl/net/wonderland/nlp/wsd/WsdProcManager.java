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

import java.io.File;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.Proc;
import org.purl.net.wonderland.kb.Procs;
import org.purl.net.wonderland.kb.ProcManager;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.util.IO;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class WsdProcManager {

    private static final String RULES_FILE_EXTENSION = ".rules.xml";
    //
    private final Wkb kb;
    private final ProcManager verbProcs = new ProcManager();
    // private WsdPersonality wsdPers = null;

    public WsdProcManager(Wkb kb) {
        this.kb = kb;
    }

    public boolean hasVerb(String lemma) {
        return verbProcs.getProcSet(lemma) != null;
    }

    public Procs getVerbProcs(String lemma) {
        Procs procs = verbProcs.getProcSet(lemma);
        if (procs == null) {
            try {
                File procFile = W.res(W.RES_WSD_VERB_MANUAL_FOLDER_PATH, lemma + RULES_FILE_EXTENSION);
                if (!procFile.exists()) {
                    procFile = W.res(W.RES_WSD_VERB_AUTOMATIC_FOLDER_PATH, lemma + RULES_FILE_EXTENSION);
                    if (!procFile.exists()) {
                        Verb v = new Verb(lemma);
                        v.storeProcRules(procFile);
                    }
                }
                procs = IO.readProcs(lemma, procFile, kb);
                procs.sort();

                Procs wsdProcs = new Procs(lemma);
                for (Proc proc : procs) {
                    wsdProcs.add(new WsdProc(proc));
                }
                procs = wsdProcs;

                verbProcs.putProcList(lemma, procs);
            } catch (Exception ex) {
                W.reportExceptionConsole(ex);
                procs = null;
            }
        }
        return procs;
    }
}


