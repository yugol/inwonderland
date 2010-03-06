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

import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class Engine {

    private WKnowledgeBase kb;
    private Vocabulary vocabulary;
    private File lastFile = null;
    private Personality personality;

    public File getLastFile() {
        return lastFile;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
        this.personality.setKb(kb);
    }

    public Engine() throws Exception {
        personality = new EtoGleem();
        openKb(null);
    }

    public void openKb(File file) throws Exception {
        if (file == null || Globals.getDefaultParseKBFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            file = Globals.getDefaultParseKBFile();
            lastFile = null;
        } else {
            lastFile = file;
        }
        kb = new WKnowledgeBase(file);
        vocabulary = kb.getVocabulary();
        personality.setKb(kb);
    }

    public void saveKb(File file) throws Exception {
        if (Globals.getDefaultParseKBFile().getAbsolutePath().equals(file.getAbsolutePath())) {
            lastFile = null;
        } else {
            kb.save(file);
            lastFile = file;
        }
    }

    public String processMessage(String msg) throws Exception {
        return personality.processMessage(msg);
    }

    public CGraph getFact(int idx, String level) {
        if (KbUtil.level1.equals(level)) {
            return kb.getFactGraph(KbUtil.toLevel1FactId(idx));
        }
        if (KbUtil.level2.equals(level)) {
            return kb.getFactGraph(KbUtil.toLevel2FactId(idx));
        }
        return null;
    }

    public int getFactCount(String level) {
        if (KbUtil.level1.equals(level)) {
            return kb.getLevel1FactCount();
        }
        if (KbUtil.level2.equals(level)) {
            return kb.getLevel2FactCount();
        }
        return 0;
    }

    public int getFactCount() {
        return kb.getFactCount();
    }

    public WTagging[] getFactWTaggings(int idx, boolean newTagsOnly, String level) {
        CGraph cg = getFact(idx, level);
        WTagging[] props = new WTagging[cg.getConcepts().size()];
        for (int j = 1; j <= props.length; ++j) {
            props[j - 1] = conceptLabelsToWTagging(KbUtil.getConcept(cg, j), newTagsOnly);
        }
        return props;
    }

    private WTagging conceptLabelsToWTagging(Concept c, boolean newTagsOnly) {
        WTagging prop = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        String id = c.getId();
        for (String type : c.getType()) {
            try {
                if (cth.isKindOf(type, KbUtil.Pos)) {
                    if (newTagsOnly && cth.isKindOf(type, KbUtil.SpTag)) {
                        prop = new WTagging();
                        break;
                    } else {
                        prop.setPos(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                    }
                } else if (cth.isKindOf(type, KbUtil.Case)) {
                    prop.setWcase(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Comparison)) {
                    prop.setComp(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Gender)) {
                    prop.setGender(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Mood)) {
                    prop.setMood(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Number)) {
                    prop.setNumber(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Person)) {
                    prop.setPerson(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                } else if (cth.isKindOf(type, KbUtil.Tense)) {
                    prop.setTense(vocabulary.getConceptTypeLabel(type, kb.getLanguage()));
                }
            } catch (RuntimeException ex) {
                System.err.println("At concept: " + type + " : " + id + " -> " + KbUtil.getConceptForm(id));
                throw ex;
            }
        }
        prop.setForm(KbUtil.getConceptForm(id));
        prop.setLemma(c.getIndividual());
        prop.setPennTag(KbUtil.getConceptPennTag(id));
        prop.setPartsOfSpeech(KbUtil.getConceptMaTag(id));
        return prop;
    }
}
