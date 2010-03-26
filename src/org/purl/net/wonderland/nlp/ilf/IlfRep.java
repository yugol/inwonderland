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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class IlfRep {

    private String text;
    private final List<Pred> prettyIlfs = new ArrayList<Pred>();
    private final List<Pred> ilfs = new ArrayList<Pred>();

    public void addPrettyIlf(Pred pred) {
        prettyIlfs.add(pred);
    }

    public List<Pred> getPrettyIlfs() {
        return prettyIlfs;
    }

    public List<Pred> getIlfs() {
        return ilfs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void sort() {
        Collections.sort(prettyIlfs, new FormComparator());
        Collections.sort(ilfs, new FormComparator());
    }

    public void addIlf(Pred pred) {
        ilfs.add(pred);
    }

    public boolean isOk() {
        return (ilfs.size() > 0);
    }
}

class FormComparator implements Comparator<Pred> {

    public int compare(Pred o1, Pred o2) {
        if (!o1.getName().equals(o2.getName())) {
            if (o1.getName().equals("e")) {
                return -1;
            }
            if (o1.getName().equals("w")) {
                if (o2.getName().equals("e")) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (o1.getName().equals("rel")) {
                if (o2.getName().equals("e")) {
                    return 1;
                } else if (o2.getName().equals("w")) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (o1.getName().equals("syn")) {
                if (o2.getName().equals("e")) {
                    return 1;
                } else if (o2.getName().equals("w")) {
                    return 1;
                } else if (o2.getName().equals("rel")) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return o1.compareTo(o2);
    }
}
