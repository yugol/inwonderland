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
package org.purl.net.wonderland.explore;

import aminePlatform.kernel.lexicons.Identifier;
import aminePlatform.kernel.lexicons.LexiconException;
import aminePlatform.kernel.ontology.OntologyException;
import aminePlatform.util.AmineList;
import aminePlatform.util.cg.CG;
import aminePlatform.util.parserGenerator.ParsingException;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.generators.GenRuleParser.LFCGParser;
import org.purl.net.wonderland.util.IO;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class AmineCGParserTest {

    public AmineCGParserTest() {
    }

    @Test
    public void parseLFCG() throws ParsingException, IOException, OntologyException, LexiconException {
        File cgFile = new File(Globals.getTestFolder(), "simple_cg.txt");
        String lf = IO.getFileContentAsString(cgFile);
        System.out.println(lf);
        LFCGParser parser = new LFCGParser();
        parser.getLexicon().addRelationTypeEntry(new Identifier("Agnt"));
        parser.getLexicon().addRelationTypeEntry(new Identifier("Dest"));
        parser.getLexicon().addRelationTypeEntry(new Identifier("Inst"));
        AmineList lst = new AmineList();
        CG cg = parser.parse(lf, lst);
        System.out.println(cg.getNbrConcepts());
        System.out.println(cg.getNbrRelations());
    }
}
