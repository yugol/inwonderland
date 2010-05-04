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
import java.io.File;
import org.purl.net.wonderland.kb.Wkb;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Memory {

    private Ltm ltm;
    private Stm stm;

    public Memory(File file) throws Exception {
        stm = new Stm(7);
        ltm = new Ltm(file);
    }

    public Ltm getLtm() {
        return ltm;
    }

    public Stm getStm() {
        return stm;
    }

    public Wkb getStorage() {
        return ltm.getDeclarative().getStorage();
    }

    public void save(File file) throws Exception {
        ltm.save(file);
    }
}
