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
public class Pred_w extends Pred_ {

    public Pred_w() {
        super("w");
    }

    public String getCat() {
        return args.get("cat");
    }

    public void setCat(String cat) {
        args.put("cat", cat);
    }

    public String getPos() {
        return args.get("pos");
    }

    public void setPos(String pos) {
        args.put("pos", pos);
    }

    public String getWord() {
        return args.get("word");
    }

    public void setWord(String word) {
        args.put("word", word);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", ");
        sb.append(getWord());
        sb.append(", ");
        sb.append(getPos());
        sb.append(", ");
        sb.append(getCat());
        sb.append(")");
        return sb.toString();
    }
}
