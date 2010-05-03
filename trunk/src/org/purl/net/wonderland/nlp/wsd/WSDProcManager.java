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

import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.ProcManager;
import org.purl.net.wonderland.kb.WKB;
import org.purl.net.wonderland.util.IO;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class WSDProcManager {

    static Set<String> syntaxTags = new HashSet<String>();
    //
    private final WKB kb;
    private final ProcManager verbProcs = new ProcManager();
    private WSDPersonality wsdPers = null;

    public WSDProcManager(WKB kb) {
        this.kb = kb;
    }

    public boolean hasVerb(String lemma) {
        return verbProcs.getProcSet(lemma) != null;
    }

    public ProcList getVerbProcs(String lemma) {
        ProcList procs = verbProcs.getProcSet(lemma);
        if (procs == null) {
            try {
                File procFile = new File(Configuration.getWsdFolder(), "procs/manual/verb/" + lemma + ".rules");
                if (!procFile.exists()) {
                    procFile = new File(Configuration.getWsdFolder(), "procs/automatic/verb/" + lemma + ".rules");
                    if (!procFile.exists()) {
                        Verb v = new Verb(lemma);
                        List<Rule> rules = v.getVerbNetProcs(getWSDPers());
                        IO.writeRules(rules, procFile);
                    }
                }
                kb.importWordNetHypernymHierarchy(lemma, POS.VERB);
                procs = IO.readProcs(lemma, procFile, kb);
                procs.sort();
                verbProcs.putProcList(lemma, procs);
            } catch (Exception ex) {
                Configuration.reportExceptionConsole(ex);
                procs = null;
            }
        }
        return procs;
    }

    private WSDPersonality getWSDPers() {
        if (wsdPers == null) {
            wsdPers = new WSDPersonality();
        }
        return wsdPers;
    }
}


