/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import edu.northwestern.at.utils.corpuslinguistics.adornedword.AdornedWord;
import edu.stanford.nlp.ling.HasTag;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.util.StringUtils;
import java.util.ArrayList;
import net.didion.jwnl.data.Synset;

/**
 *
 * @author Iulian
 */
public class WTagging implements HasWord, HasTag, AdornedWord {

    private static final String empty = "";
    // word form tags
    private String form = null; // word form
    private String lemma = null; // dictionary form
    // mapping tags
    private String pennTag = null; // Penn Treebank POS tag
    private String maTag = null; // MorphAddorder POS tag
    private String spelling = null;
    private Synset[] senses = null; // from WordNet
    // Wonderland tags
    private String pos = null; // Wonderland POS tag
    private String gender = null; // masculine, feminine, neuter, common
    private String number = null; // singular, plural
    private String wcase = null; // nominative, genitive, dative, accusative
    private String person = null; // first, second, third
    private String comp = null; // comparative, superlative
    private String mood = null; // indicative, subjunctive, conditional, ...
    private String tense = null; // present, past, future, ...
    // collocation mark
    private boolean collocation = false;

    public void copyWTags(WTagging other) {
        this.pos = other.pos;
        this.gender = other.gender;
        this.number = other.number;
        this.wcase = other.wcase;
        this.person = other.person;
        this.comp = other.comp;
        this.mood = other.mood;
        this.tense = other.tense;
    }

    public void copyWTagsAndLemma(WTagging other) {
        copyWTags(other);
        this.lemma = other.lemma;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        if (empty.equals(comp)) {
            comp = null;
        }
        this.comp = comp;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        if (empty.equals(form)) {
            form = null;
        }
        this.form = form;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (empty.equals(gender)) {
            gender = null;
        }
        this.gender = gender;
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

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        if (empty.equals(mood)) {
            mood = null;
        }
        this.mood = mood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (empty.equals(number)) {
            number = null;
        }
        this.number = number;
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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        if (empty.equals(pos)) {
            pos = null;
        }
        this.pos = pos;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        if (empty.equals(tense)) {
            tense = null;
        }
        this.tense = tense;
    }

    public String getWcase() {
        return wcase;
    }

    public void setWcase(String wcase) {
        if (empty.equals(wcase)) {
            wcase = null;
        }
        this.wcase = wcase;
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

    public Synset[] getSenses() {
        return senses;
    }

    public void setSenses(Synset[] senses) {
        this.senses = senses;
    }

    public String[] asStringArray() {
        ArrayList<String> types = new ArrayList<String>();
        if (pos != null) {
            types.add(pos);
        }
        if (person != null) {
            types.add(person);
        }
        if (wcase != null) {
            types.add(wcase);
        }
        if (number != null) {
            types.add(number);
        }
        if (gender != null) {
            types.add(gender);
        }
        if (comp != null) {
            types.add(comp);
        }
        if (mood != null) {
            types.add(mood);
        }
        if (tense != null) {
            types.add(tense);
        }
        return types.toArray(new String[]{});
    }

    public String toCsvString() {
        String[] chunks = new String[10];
        chunks[0] = ((form == null) ? ("") : (form));
        chunks[1] = ((lemma == null) ? ("") : (lemma));
        chunks[2] = ((pos == null) ? ("") : (pos));
        chunks[3] = ((gender == null) ? ("") : (gender));
        chunks[4] = ((number == null) ? ("") : (number));
        chunks[5] = ((wcase == null) ? ("") : (wcase));
        chunks[6] = ((person == null) ? ("") : (person));
        chunks[7] = ((comp == null) ? ("") : (comp));
        chunks[8] = ((mood == null) ? ("") : (mood));
        chunks[9] = ((tense == null) ? ("") : (tense));
        return StringUtils.join(chunks, ",");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pennTag);
        sb.append(" \t");
        sb.append(maTag);
        sb.append(" \t");
        sb.append(form);
        sb.append(" [");
        sb.append(lemma);
        sb.append("] \t\t");
        sb.append(pos);
        return sb.toString();
    }

    public String word() {
        return form;
    }

    public void setWord(String word) {
        setForm(word);
    }

    public String tag() {
        return pennTag;
    }

    public void setTag(String tag) {
        setPennTag(tag);
    }

    public String getToken() {
        return form;
    }

    public void setToken(String token) {
        setForm(token);
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
        return lemma;
    }

    public void setLemmata(String lemmata) {
        setLemma(lemmata);
    }

    public String getPartsOfSpeech() {
        return maTag;
    }

    public void setPartsOfSpeech(String partsOfSpeech) {
        maTag = partsOfSpeech;
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
}
