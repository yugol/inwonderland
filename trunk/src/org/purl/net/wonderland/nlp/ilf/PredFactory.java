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
public class PredFactory {

    public static Pred createPredicate(String rep) {
        int pos = rep.indexOf("(");
        String name = rep.substring(0, pos);
        String[] args = rep.substring(pos + 1).split(",");
        Pred_ pred = null;
        if (name.equals("e")) {
            Pred_e p = new Pred_e();
            p.setSid(args[0]);
            p.setId1(args[1]);
            p.setV1(args[2]);
            pred = p;
        } else if (name.equals("w")) {
            Pred_w p = new Pred_w();
            p.setSid(args[0]);
            p.setId1(args[1]);
            p.setWord(removeQuotes(args[2]));
            p.setPos(removeQuotes(args[3]));
            p.setCat(removeQuotes(args[4]).toUpperCase());
            pred = p;
        } else if (name.equals("syn")) {
            Pred_syn p = new Pred_syn();
            p.setSid(args[0]);
            p.setId1(args[1]);
            p.setOffset(args[2]);
            pred = p;
        } else if (name.equals("rel")) {
            Pred_rel p = new Pred_rel();
            p.setSid(args[0]);
            p.setId1(args[1]);
            p.setId2(args[2]);
            p.setDep(removeQuotes(args[3]));
            p.setV1(args[4]);
            p.setV2(args[5]);
            pred = p;
        }
        return pred;
    }

    private static String removeQuotes(String str) {
        return str.substring(1, str.length() - 1);
    }
}
