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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Entities {

    private final List<Chunk> entities;

    public Entities() {
        this.entities = new ArrayList<Chunk>();
    }

    public void add(Chunk ck) {
        entities.add(ck);
    }

    public List<Chunk> findMatchesProperNoun(Chunk query) {
        List<Chunk> matches = new ArrayList<Chunk>();

        String queryLemma = query.getLemma();
        for (int i = entities.size() - 1; i >= 0; --i) {
            Chunk candidate = entities.get(i);
            if (candidate.getLemma().equals(queryLemma)) {
                matches.add(candidate);
            }
        }

        return ((matches.size() > 0) ? (matches) : (null));
    }

    List<Chunk> findMatchesPronoun(Chunk query) {
        List<Chunk> matches = new ArrayList<Chunk>();

        for (int i = entities.size() - 1; i >= 0; --i) {
            Chunk candidate = entities.get(i);

            if (query.getGrammaticalNumber() != null && candidate.getGrammaticalNumber() != null) {
                if (!query.getGrammaticalNumber().equals(candidate.getGrammaticalNumber())) {
                    continue;
                }
            }
            if (query.getGrammaticalGender() != null && candidate.getGrammaticalGender() != null) {
                if (!query.getGrammaticalGender().equals(candidate.getGrammaticalGender())) {
                    continue;
                }
            }

            matches.add(candidate);
        }

        return ((matches.size() > 0) ? (matches) : (null));
    }
}
