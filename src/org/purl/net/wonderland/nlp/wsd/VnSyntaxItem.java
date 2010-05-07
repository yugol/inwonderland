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
import java.util.List;
import java.util.Set;
import org.purl.net.wonderland.cg.Path;
import org.purl.net.wonderland.cg.Vertex;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnSyntaxItem {

    private final String type;
    private final String value;
    private final Set<VnRestr> synrestrs;
    private final Set<VnRestr> selrestrs;
    private WTagging word = null;
    private Path path = null;

    public VnSyntaxItem(String type, String value) {
        this.type = type;
        if (value == null || "".equals(value)) {
            this.value = null;
        } else {
            this.value = value;
        }
        this.synrestrs = new HashSet<VnRestr>();
        this.selrestrs = new HashSet<VnRestr>();
    }

    public String getValue() {
        return value;
    }

    public Set<VnRestr> getSelrestrs() {
        return selrestrs;
    }

    public Set<VnRestr> getSynrestrs() {
        return synrestrs;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type);
        if (value != null) {
            sb.append(" (");
            sb.append(value);
            sb.append(")");
        }
        return sb.toString();
    }

    public WTagging getWord() {
        return word;
    }

    public void setWord(WTagging word) {
        this.word = word;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
