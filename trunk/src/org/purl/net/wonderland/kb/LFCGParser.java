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
package org.purl.net.wonderland.kb;

import aminePlatform.kernel.lexicons.Identifier;
import aminePlatform.kernel.lexicons.Lexicon;
import aminePlatform.kernel.lexicons.LexiconException;
import aminePlatform.kernel.ontology.Ontology;
import aminePlatform.kernel.ontology.OntologyException;
import aminePlatform.kernel.ontology.Type;
import aminePlatform.util.parserGenerator.LFParserGenerator;
import aminePlatform.util.parserGenerator.ParsingException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class LFCGParser extends LFParserGenerator {

    public static class DummyLexicon extends Lexicon {

        public DummyLexicon() throws LexiconException, OntologyException {
            super(new Identifier(""), new Ontology());
        }

        @Override
        public Type getTypeCS(Identifier typeIdentifier) {
            System.out.println(typeIdentifier.getName());
            Type t = super.getTypeCS(typeIdentifier);
            if (t == null) {
                addConceptTypeEntry(typeIdentifier);
            }
            return t;
        }


    }

    public LFCGParser() throws ParsingException, LexiconException, OntologyException {
        super(new DummyLexicon());
    }
}
