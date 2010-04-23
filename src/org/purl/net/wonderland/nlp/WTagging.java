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
package org.purl.net.wonderland.nlp;

import edu.northwestern.at.utils.corpuslinguistics.adornedword.AdornedWord;
import edu.stanford.nlp.ling.HasTag;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.purl.net.wonderland.kb.KbUtil;

/**
 *
 * @author Iulian
 */
public class WTagging implements HasWord, HasTag, AdornedWord {

    private static final String empty = "";
    // word form tags
    private String writtenForm = null; // word form
    private String lemma = null; // dictionary form
    // interface implementation fields
    private String pennTag = null; // Penn Treebank POS tag
    private String morphAdorderTag = null; // MorphAdorder POS tag
    private String spelling = null;
    // morphology tags
    private String partOfSpeech = null; // Wonderland POS tag
    private String grammaticalGender = null; // masculine, feminine, neuter, common
    private String grammaticalNumber = null; // singular, plural
    private String grammaticalCase = null; // nominative, genitive, dative, accusative, ...
    private String person = null; // first, second, third
    private String degree = null; // comparative, superlative
    private String verbFormMood = null; // indicative, subjunctive, conditional, ...
    private String grammaticalTense = null; // present, past, future, ...
    private String definiteness = null; // definite, indefinite, unarticulated
    // other types
    private Set<String> moreTypes = new HashSet<String>();
    // collocation mark
    private boolean collocation = false;

    public void copyWTags(WTagging other) {
        this.partOfSpeech = other.partOfSpeech;
        this.grammaticalGender = other.grammaticalGender;
        this.grammaticalNumber = other.grammaticalNumber;
        this.grammaticalCase = other.grammaticalCase;
        this.person = other.person;
        this.degree = other.degree;
        this.verbFormMood = other.verbFormMood;
        this.grammaticalTense = other.grammaticalTense;
    }

    public void copyWTagsAndLemma(WTagging other) {
        copyWTags(other);
        this.lemma = other.lemma;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String comp) {
        if (empty.equals(comp)) {
            comp = null;
        }
        this.degree = comp;
    }

    public String getWrittenForm() {
        return writtenForm;
    }

    public void setWrittenForm(String form) {
        if (empty.equals(form)) {
            form = null;
        }
        this.writtenForm = form;
    }

    public String getGrammaticalGender() {
        return grammaticalGender;
    }

    public void setGrammaticalGender(String gender) {
        if (empty.equals(gender)) {
            gender = null;
        }
        this.grammaticalGender = gender;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        if (empty.equals(lemma)) {
            lemma = null;
        }
        this.lemma = lemma;
    }

    public String getVerbFormMood() {
        return verbFormMood;
    }

    public void setVerbFormMood(String mood) {
        if (empty.equals(mood)) {
            mood = null;
        }
        this.verbFormMood = mood;
    }

    public String getGrammaticalNumber() {
        return grammaticalNumber;
    }

    public void setGrammaticalNumber(String number) {
        if (empty.equals(number)) {
            number = null;
        }
        this.grammaticalNumber = number;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        if (empty.equals(person)) {
            person = null;
        }
        this.person = person;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String pos) {
        if (empty.equals(pos)) {
            pos = null;
        }
        this.partOfSpeech = pos;
    }

    public String getGrammaticalTense() {
        return grammaticalTense;
    }

    public void setGrammaticalTense(String tense) {
        if (empty.equals(tense)) {
            tense = null;
        }
        this.grammaticalTense = tense;
    }

    public String getDefiniteness() {
        return definiteness;
    }

    public void setDefiniteness(String article) {
        if (empty.equals(article)) {
            article = null;
        }
        this.definiteness = article;
    }

    public String getGrammaticalCase() {
        return grammaticalCase;
    }

    public void setGrammaticalCase(String wcase) {
        if (empty.equals(wcase)) {
            wcase = null;
        }
        this.grammaticalCase = wcase;
    }

    public String getPennTag() {
        return pennTag;
    }

    public void setPennTag(String pennTag) {
        if (empty.equals(pennTag)) {
            pennTag = null;
        }
        this.pennTag = pennTag;
    }

    public String[] asTypes() {
        ArrayList<String> types = new ArrayList<String>();
        if (partOfSpeech != null) {
            for (String pos : partOfSpeech.split(";")) {
                types.add(pos);
            }
        }
        if (person != null) {
            types.add(person);
        }
        if (grammaticalCase != null) {
            types.add(grammaticalCase);
        }
        if (grammaticalNumber != null) {
            types.add(grammaticalNumber);
        }
        if (grammaticalGender != null) {
            types.add(grammaticalGender);
        }
        if (degree != null) {
            types.add(degree);
        }
        if (verbFormMood != null) {
            types.add(verbFormMood);
        }
        if (grammaticalTense != null) {
            types.add(grammaticalTense);
        }
        types.addAll(moreTypes);
        String[] typeArray = new String[types.size()];
        for (int i = 0; i < typeArray.length; ++i) {
            typeArray[i] = KbUtil.toConceptTypeId(types.get(i));
        }
        return typeArray;
    }

    public String toCsvString() {
        String[] chunks = new String[10];
        chunks[0] = ((writtenForm == null) ? ("") : (writtenForm));
        chunks[1] = ((lemma == null) ? ("") : (lemma));
        chunks[2] = ((partOfSpeech == null) ? ("") : (partOfSpeech));
        chunks[3] = ((grammaticalGender == null) ? ("") : (grammaticalGender));
        chunks[4] = ((grammaticalNumber == null) ? ("") : (grammaticalNumber));
        chunks[5] = ((grammaticalCase == null) ? ("") : (grammaticalCase));
        chunks[6] = ((person == null) ? ("") : (person));
        chunks[7] = ((degree == null) ? ("") : (degree));
        chunks[8] = ((verbFormMood == null) ? ("") : (verbFormMood));
        chunks[9] = ((grammaticalTense == null) ? ("") : (grammaticalTense));
        return StringUtils.join(chunks, ",");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pennTag);
        sb.append(" \t");
        sb.append(morphAdorderTag);
        sb.append(" \t");
        sb.append(writtenForm);
        sb.append(" [");
        sb.append(lemma);
        sb.append("] \t\t");
        sb.append(partOfSpeech);
        return sb.toString();
    }

    public String word() {
        return writtenForm;
    }

    public void setWord(String word) {
        setWrittenForm(word);
    }

    public String tag() {
        return pennTag;
    }

    public void setTag(String tag) {
        setPennTag(tag);
    }

    public String getToken() {
        return writtenForm;
    }

    public void setToken(String token) {
        setWrittenForm(token);
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getStandardSpelling() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStandardSpelling(String standardSpelling) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLemmata() {
        return getLemma();
    }

    public void setLemmata(String lemmata) {
        setLemma(lemmata);
    }

    public String getPartsOfSpeech() {
        return morphAdorderTag;
    }

    public void setPartsOfSpeech(String partsOfSpeech) {
        morphAdorderTag = partsOfSpeech;
    }

    public int getTokenType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTokenType(int tokenType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCollocation() {
        return collocation;
    }

    public void setCollocation(boolean collocation) {
        this.collocation = collocation;
    }

    public void addMoreType(String type) {
        if (type != null) {
            type = type.trim();
            if (!empty.equals(type)) {
                moreTypes.add(type);
            }
        }
    }
}
