/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class WkbConstants {

    //
    public static final String TYPE_SEPARATOR = ";";
    public static final String ID_SEPARATOR = " ";
    // morphology types
    public static final String PARTOFSPEECH = "partOfSpeech";
    public static final String GRAMMATICALGENDER = "grammaticalGender";
    public static final String GRAMMATICALNUMBER = "grammaticalNumber";
    public static final String GRAMMATICALCASE = "grammaticalCase";
    public static final String GRAMMATICALTENSE = "grammaticalTense";
    public static final String PERSON = "person";
    public static final String DEGREE = "degree";
    public static final String VERBFORMMOOD = "verbFormMood";
    public static final String DEFINITNESS = "definiteness";
    public static final String ASPECT = "aspect";
    // morphology (POS)
    public static final String ADVERB = "adverb";
    public static final String NOUN = "noun";
    public static final String VERB = "verb";
    public static final String ADJECTIVE = "adjective";
    public static final String PROPERNOUN = "properNoun";
    public static final String POSSESIVEADJECIVE = "possessiveAdjective";
    public static final String PRONOUN = "pronoun";
    public static final String DEMONSTRATIVEDETERMINER = "demonstrativeDeterminer";
    public static final String DEMONSTRATICEPRONOUN = "demonstrativePronoun";
    public static final String CARDINALNUMBER = "cardinalNumeral";
    public static final String GERUND = "gerund";
    public static final String INDICATIVE = "indicative";
    public static final String PAST = "past";
    public static final String PRESENT = "present";
    public static final String SINGULAR = "singular";
    public static final String THIRDPERSON = "thirdPerson";
    public static final String COMMONNOUN = "commonNoun";
    public static final String PERSONALPRONOUN = "personalPronoun";
    public static final String PREPOSITION = "preposition";
    public static final String PLURAL = "plural";
    public static final String MODAL = "modal";
    public static final String INFINITIVE = "infinitive";
    public static final String PARTICIPLE = "participle";
    public static final String SUBORDONATINGCONJUNCTION = "subordinatingConjunction";
    public static final String ADPOSITION = "adposition";
    public static final String COORDINATINGCONJUNCTION = "coordinatingConjunction";
    public static final String COMPARATIVE = "comparative";
    public static final String SUPERLATIVE = "superlative";
    public static final String MANNERADVERB = "mannerAdverb";
    public static final String ORDINALADJECTIVE = "ordinalAdjective";
    public static final String RELATIVEPRONOUN = "relativePronoun";
    public static final String RELATIVEADVERB = "relativeAdverb";
    public static final String POSSESIVEPRONOUN = "possessivePronoun";
    public static final String INTERROGATIVEADVERB = "interrogativeAdverb";
    public static final String EXISTENTIALPRONOUN = "existentialPronoun";
    public static final String GENERALADVERB = "generalAdverb";
    public static final String POSSESIVEPARTICLE = "possessiveParticle";
    public static final String INTERJECTION = "interjection";
    // punctuation
    public static final String POINT = "point";
    public static final String QUOTE = "quote";
    public static final String QUESTIONMARK = "questionMark";
    public static final String COMMA = "comma";
    public static final String EXCLAMATIVEPOINT = "exclamativePoint";
    public static final String OPENPARRENTHESIS = "openParenthesis";
    public static final String CLOSEPARENTHESIS = "closeParenthesis";
    public static final String SEMICOLON = "semiColon";
    public static final String COLON = "colon";
    public static final String PUNCTUATION = "punctuation";
    public static final String INTERROGATIVEPUNCTUATION = "interrogativePunctuation";
    public static final String INTERROGATIVEPUNCTUATION_CT = WkbUtil.toConceptTypeId(INTERROGATIVEPUNCTUATION);
    public static final String PUNCTUATION_CT = WkbUtil.toConceptTypeId(PUNCTUATION);
    // WordNet root concept types
    public static final String WN_SENSE_CT = WkbUtil.toConceptTypeId("wnSense");
    public static final String WN_NOUN = "wnNn";
    public static final String WN_ADJECTIVE = "wnJj";
    public static final String WN_ADVERB = "wnRb";
    public static final String WN_VERB = "wnVb";
    // VerbNet selection restrictions (thematic roles types)
    public static final String ABSTRACT_CT = "abstract";
    public static final String ANIMAL = "animal";
    public static final String ANIMATE = "animate";
    public static final String BODY_PART = "body_part";
    public static final String COMESTIBLE = "comestible";
    public static final String COMMUNICATION = "communication";
    public static final String CONCRETE = "concrete";
    public static final String CURRENCY = "currency";
    public static final String ELONGATED = "elongated";
    public static final String FORCE = "force";
    public static final String GARMENT = "garment";
    public static final String HUMAN = "human";
    public static final String INT_CONTROL = "int_control";
    public static final String LOCATION = "location";
    public static final String MACHINE = "machine";
    public static final String NONRIGID = "nonrigid";
    public static final String ORGANIZATION = "organization";
    public static final String POINTY = "pointy";
    public static final String REFL = "refl";
    public static final String REGION = "region";
    public static final String SCALAR = "scalar";
    public static final String SOLID = "solid";
    public static final String SOUND = "sound";
    public static final String SUBSTANCE = "substance";
    public static final String TIME = "time";
    public static final String VEHICLE = "vehicle";
    // some concept types from the knowledge base support
    public static final String TOP_CT = WkbUtil.toConceptTypeId("Top");
    public static final String PROCOP_CT = WkbUtil.toConceptTypeId("ProcOp");
    public static final String PROCOP_KEEP = "ProcOp_Keep";
    public static final String PROCOP_KEEP_CT = WkbUtil.toConceptTypeId(PROCOP_KEEP);
    public static final String PROCOP_REPLACE = "ProcOp_Replace";
    public static final String PROCOP_REPLACE_CT = WkbUtil.toConceptTypeId(PROCOP_REPLACE);
    public static final String PROCOP_ADD = "ProcOp_Add";
    public static final String PROCOP_ADD_CT = WkbUtil.toConceptTypeId(PROCOP_ADD);
    public static final String LINKARG = "LinkArg";
    public static final String LINKARG_CT = WkbUtil.toConceptTypeId(LINKARG);
    public static final String SPTAG_CT = WkbUtil.toConceptTypeId("SpTag");
    // morphology concept types
    public static final String PARTOFSPEECH_CT = WkbUtil.toConceptTypeId(PARTOFSPEECH);
    public static final String GRAMMATICALCASE_CT = WkbUtil.toConceptTypeId(GRAMMATICALCASE);
    public static final String DEGREE_CT = WkbUtil.toConceptTypeId(DEGREE);
    public static final String GRAMMATICALGENDER_CT = WkbUtil.toConceptTypeId(GRAMMATICALGENDER);
    public static final String VERBFORMMOOD_CT = WkbUtil.toConceptTypeId(VERBFORMMOOD);
    public static final String GRAMMATICALNUMBER_CT = WkbUtil.toConceptTypeId(GRAMMATICALNUMBER);
    public static final String PERSON_CT = WkbUtil.toConceptTypeId(PERSON);
    public static final String ASPECT_CT = WkbUtil.toConceptTypeId(ASPECT);
    public static final String GRAMMATICALTENSE_CT = WkbUtil.toConceptTypeId(GRAMMATICALTENSE);
    public static final String DEFINITNESS_CT = WkbUtil.toConceptTypeId(DEFINITNESS);
    public static final String NOUN_CT = WkbUtil.toConceptTypeId(NOUN);
    public static final String COMMONNOUN_CT = WkbUtil.toConceptTypeId(COMMONNOUN);
    public static final String PROPERNOUN_CT = WkbUtil.toConceptTypeId(PROPERNOUN);
    public static final String VERB_CT = WkbUtil.toConceptTypeId(VERB);
    public static final String ADVERB_CT = WkbUtil.toConceptTypeId(ADVERB);
    public static final String PRONOUN_CT = WkbUtil.toConceptTypeId(PRONOUN);
    public static final String POSSESIVEADJECIVE_CT = WkbUtil.toConceptTypeId(POSSESIVEADJECIVE);
    public static final String ADJECTIVE_CT = WkbUtil.toConceptTypeId(ADJECTIVE);
    public static final String ADPOSITION_CT = WkbUtil.toConceptTypeId(ADPOSITION);
    public static final String INDICATIVE_CT = WkbUtil.toConceptTypeId(INDICATIVE);
    // fact levels
    public static final String LEVEL1 = "level1";
    public static final String LEVEL2 = "level2";
    public static final String LEVEL3 = "level3";
    public static final String LEVEL4 = "level4";
    public static final String LEVEL5 = "level5";
    // other
}
