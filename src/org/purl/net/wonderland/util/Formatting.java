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
package org.purl.net.wonderland.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import net.didion.jwnl.data.POS;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class Formatting {

    private static final NumberFormat timeFormatter = new DecimalFormat("0.0000");
    private static final NumberFormat senseNumberFormatter = new DecimalFormat("00000000");

    public static String toWordNetOffset(long offset) {
        return senseNumberFormatter.format(offset);
    }

    public static String toWordNetOffsetKeyAlpha(String particle, long offset) {
        return particle + Formatting.toWordNetOffset(offset);
    }

    public static String toWordNetOffsetKeyAlpha(POS pos, long offset) {
        return getAlpha(pos) + Formatting.toWordNetOffset(offset);
    }

    public static String toWordNetOffsetKeyNum(POS pos, long offset) {
        return getNumber(pos) + Formatting.toWordNetOffset(offset);
    }

    public static String getAlpha(POS pos) {
        if (pos == POS.NOUN) {
            return "n";
        }
        if (pos == POS.ADJECTIVE) {
            return "a";
        }
        if (pos == POS.ADVERB) {
            return "r";
        }
        if (pos == POS.VERB) {
            return "v";
        }
        return null;
    }

    public static String getNumber(POS pos) {
        if (pos == POS.NOUN) {
            return "1";
        }
        if (pos == POS.ADJECTIVE) {
            return "3";
        }
        if (pos == POS.ADVERB) {
            return "4";
        }
        if (pos == POS.VERB) {
            return "2";
        }
        return null;
    }

    static String formatTime(double d) {
        return timeFormatter.format(d);
    }
}
