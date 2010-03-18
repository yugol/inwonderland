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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class RolesetExample {

    public static final class FrameEntry {

        public static final String sep = "|";
        private final String phrase;
        private final String value;
        private final String[] synrestrs;
        private final String[] selrestrs;

        public FrameEntry(String phrase, String value, String synrestrs, String selrestrs) {
            this.phrase = phrase;
            this.value = value;
            this.synrestrs = synrestrs.split(sep);
            this.selrestrs = selrestrs.split(sep);
        }

        public String getPhrase() {
            return phrase;
        }

        public String getValue() {
            return value;
        }

        public String[] getSelrestrs() {
            return selrestrs;
        }

        public String[] getSynrestrs() {
            return synrestrs;
        }
    }
    //
    private final String text;
    private final Map<ThematicRole, String> args;
    private final List<FrameEntry> frame;

    public RolesetExample(String text) {
        this.text = text;
        this.args = new HashMap<ThematicRole, String>();
        this.frame = new ArrayList<FrameEntry>();
    }

    public String getText() {
        return text;
    }

    public Map<ThematicRole, String> getArgs() {
        return args;
    }

    public List<FrameEntry> getFrame() {
        return frame;
    }
}
