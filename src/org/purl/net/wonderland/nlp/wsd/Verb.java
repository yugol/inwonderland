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
package org.purl.net.wonderland.nlp.wsd;

import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.nlp.resources.PropBankWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.ui.KbFileFilter;
import org.purl.net.wonderland.util.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Verb {

    static String normalizeThematicRoleName(String name) {
        return StringUtils.capitalize(name.toLowerCase());
    }
    //
    private final String lemma;
    private final List<VerbFrame> frames;

    public Verb(String lemma) throws Exception {
        this.lemma = lemma;
        System.out.println("* " + lemma);
        this.frames = new ArrayList<VerbFrame>();
        readPropBankData(lemma);
    }

    private void readPropBankData(String lemma) throws Exception {
        File pbFile = PropBankWrapper.getVerbFile(lemma);
        Document xmlDoc = IO.readXmlFile(pbFile);

        NodeList rolesetNodes = xmlDoc.getElementsByTagName("roleset");
        for (int i = 0; i < rolesetNodes.getLength(); i++) {
            Element rolesetElement = (Element) rolesetNodes.item(i);

            String[] vnclss = rolesetElement.getAttribute("vncls").split(" ");
            for (String vncls : vnclss) {
                if (VerbNetWrapper.getClassFile(vncls) == null) {
                    String[] vnclsParts = vncls.split("-");
                    if (vnclsParts.length > 0) {
                        List<String> possibleClasses = VerbNetWrapper.getClassesLike(vnclsParts[0]);
                        if (possibleClasses != null) {
                            for (String cls : possibleClasses) {
                                readRoleset(rolesetElement, lemma, cls);
                            }
                        } else {
                            readRoleset(rolesetElement, lemma, null);
                        }
                    } else {
                        readRoleset(rolesetElement, lemma, null);
                    }
                } else {
                    readRoleset(rolesetElement, lemma, vncls);
                }
            }
        }
    }

    private void readRoleset(Element rolesetElement, String lemma, String vncls) throws Exception {
        // read roles
        Map<String, Themrole> roles = new HashMap<String, Themrole>();
        NodeList roleNodes = rolesetElement.getElementsByTagName("role");
        for (int j = 0; j < roleNodes.getLength(); j++) {
            Element roleElement = (Element) roleNodes.item(j);
            String n = roleElement.getAttribute("n");
            String desc = roleElement.getAttribute("descr");
            String vntheta = null;
            NodeList vnroleNodes = roleElement.getElementsByTagName("vnrole");
            if (vnroleNodes.getLength() > 0) {
                Element vnroleElement = (Element) vnroleNodes.item(0);
                vntheta = normalizeThematicRoleName(vnroleElement.getAttribute("vntheta"));
            }
            Themrole role = new Themrole(n, desc, vntheta);
            roles.put(n, role);
            if (vntheta != null) {
                roles.put(vntheta, role);
            }
        }

        // create roleset
        VerbFrame roleset = new VerbFrame(lemma, rolesetElement.getAttribute("id"), vncls, roles);
        frames.add(roleset);

        // read PropBank examples
        NodeList exampleNodes = rolesetElement.getElementsByTagName("example");
        for (int i = 0; i < exampleNodes.getLength(); i++) {
            Element exampleElement = (Element) exampleNodes.item(i);
            Element textElement = (Element) exampleElement.getElementsByTagName("text").item(0);
            Example example = new Example(lemma, Example.Type.PropBank, textElement.getTextContent().trim());
            NodeList argNodes = exampleElement.getElementsByTagName("arg");
            for (int j = 0; j < argNodes.getLength(); j++) {
                Element argElement = (Element) argNodes.item(j);
                String n = argElement.getAttribute("n");
                String value = argElement.getTextContent();
                Themrole role = roles.get(n);
                if (role != null) {
                    example.getArgs().put(role, value.trim());
                }
            }
            roleset.getExamples().add(example);
        }
    }

    public List<VerbFrame> getFrames() {
        return frames;
    }

    public String getLemma() {
        return lemma;
    }

    public List<Rule> getVerbNetProcs() throws Exception {
        WKnowledgeBase kb = WsdManager.pers.getKb();
        List<Rule> procs = new ArrayList<Rule>();
        Concept cHypt, cConc;
        Relation rHypt, rConc;
        for (VerbFrame frame : frames) {
            for (Example example : frame.getExamples()) {
                if (example.getType() == Example.Type.VerbNet) {
                    CGraph cg = WsdManager.pers.parse(example.getText());
                    Rule proc = new Rule(KbUtil.newUniqueId(), KbUtil.toProcName(lemma, KbUtil.newUniqueId()));


                    // find verb
                    Concept verb = null;
                    Iterator<Concept> cit = cg.iteratorConcept();
                    while (cit.hasNext()) {
                        Concept c = cit.next();
                        if (c.getIndividual().equals(example.getVerbLemma())) {
                            verb = c;
                            break;
                        }
                    }
                    if (verb == null) {
                        throw new WonderlandException("verb not found");
                    }


                    // create verb couple
                    cHypt = new Concept(KbUtil.newUniqueId());
                    cHypt.setIndividual(kb.addIndividual(lemma));
                    cHypt.setType(KbUtil.Vb);
                    cHypt.addType(KbUtil.toConceptTypeId("ind"));
                    cHypt.setHypothesis(true);
                    proc.addVertex(cHypt);

                    cConc = new Concept(KbUtil.newUniqueId());
                    cConc.setType(KbUtil.Pos);
                    for (String sense : frame.getSenses()) {
                        String ctId = kb.addConceptType(sense, null);
                        cConc.addType(ctId);
                    }
                    cConc.setConclusion(true);
                    proc.addVertex(cConc);

                    proc.addCouple(cHypt.getId(), cConc.getId());


                    // find dependencies
                    Set<String> discovered = new HashSet<String>();
                    discovered.add(verb.getId());

                    Iterator<String> sit1 = cg.iteratorAdjacents(verb.getId());
                    while (sit1.hasNext()) {
                        String rId = sit1.next();
                        if (!discovered.contains(rId)) {
                            discovered.add(rId);
                            Relation r = cg.getRelation(rId);
                            Iterator<String> sit2 = cg.iteratorAdjacents(rId);
                            while (sit2.hasNext()) {
                                String cId = sit2.next();
                                if (!discovered.contains(cId)) {
                                    Concept c = cg.getConcept(cId);
                                    String individual = c.getIndividual();
                                    for (Themrole tr : example.getArgs().keySet()) {
                                        String content = example.getArgs().get(tr);
                                        if (content.equals(individual)) {
                                            cHypt = new Concept(KbUtil.newUniqueId());
                                            cHypt.setType(KbUtil.Pos);
                                            cHypt.setHypothesis(true);
                                            proc.addVertex(cHypt);

                                            cConc = new Concept(KbUtil.newUniqueId());
                                            cConc.setType(KbUtil.Pos);
                                            cConc.setConclusion(true);
                                            proc.addVertex(cConc);

                                            proc.addCouple(cHypt.getId(), cConc.getId());


                                            rHypt = new Relation(KbUtil.newUniqueId());
                                            rHypt.setType(r.getType());
                                            rHypt.setHypothesis(true);
                                            proc.addVertex(rHypt);

                                            rConc = new Relation(KbUtil.newUniqueId());
                                            rConc.setType(KbUtil.toRelationTypeId(tr.getVnThemrole()));
                                            rConc.setConclusion(true);
                                            proc.addVertex(rConc);

                                        }
                                    }
                                }
                            }
                        }
                    }


                    // done
                    procs.add(proc);
                }
            }
        }
        return procs;
    }
}
