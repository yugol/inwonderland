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

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class IdUtil {

    public static final int UUID_LENGTH = 20;
    private static final int MASK_LEN = 6;
    private static final long MASK;
    private static final int CHAR_LUT_LEN = 62;
    private static final char[] CHAR_LUT;
    private static final Random rand = new Random();

    static {
        CHAR_LUT = new char[CHAR_LUT_LEN];
        for (int i = 0; i < 10; i++) {
            CHAR_LUT[i] = (char) ('0' + i);
        }
        for (int i = 0; i < 26; i++) {
            CHAR_LUT[10 + i] = (char) ('A' + i);
        }
        for (int i = 0; i < 26; i++) {
            CHAR_LUT[36 + i] = (char) ('a' + i);
        }

        long tempMask = 1;
        for (int i = 0; i < MASK_LEN - 1; i++) {
            tempMask <<= 1;
            tempMask += 1;
        }
        MASK = tempMask;
    }

    public static String newId() {
        StringBuilder id = new StringBuilder(20);
        UUID uuid = UUID.randomUUID();
        long body = uuid.getLeastSignificantBits();
        body = appendLong(body, id);
        body = uuid.getMostSignificantBits();
        body = appendLong(body, id);
        return new String(id);
    }

    private static long appendLong(long body, StringBuilder id) {
        for (int i = 0; i < UUID_LENGTH / 2; i++) {
            int index = (int) (body & MASK);
            if (index >= CHAR_LUT_LEN) {
                index = rand.nextInt(CHAR_LUT_LEN);
            }
            id.append(CHAR_LUT[index]);
            body >>>= MASK_LEN;
        }
        return body;
    }
}
