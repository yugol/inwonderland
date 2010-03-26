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
package org.purl.net.wonderland.cg;

import java.util.Map;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class CogxmlIO {

    public static ConceptType createConceptType(Element elt, String lang) {
        ConceptType ct = new ConceptType(elt.getAttribute("id"));
        NodeList translationNodes = elt.getElementsByTagName("translation");
        for (int i = 0; i < translationNodes.getLength(); i++) {
            Element translationElement = (Element) translationNodes.item(i);
            if (translationElement.getAttribute("lang").equals(lang)) {
                ct.setDescription(translationElement.getAttribute("descr"));
                ct.setLabel(translationElement.getAttribute("label"));
            }
        }
        return ct;
    }

    public static void createConceptTypeOrderElement(Element elt, Map<String, ConceptType> conceptTypes) {
        ConceptType child = conceptTypes.get(elt.getAttribute("id1"));
        ConceptType parent = conceptTypes.get(elt.getAttribute("id2"));
        child.addParent(parent);
    }

    public static RelationType createRelationType(Element elt, String lang, Map<String, ConceptType> conceptTypes) {
        String signature[] = elt.getAttribute("idSignature").split(" ");
        RelationType rt = new RelationType(elt.getAttribute("id"), signature.length);
        for (int i = 0; i < signature.length; i++) {
            String[] ctids = signature[i].split(";");
            ConceptTypeSet ctSet = new ConceptTypeSet();
            for (int j = 0; j < ctids.length; j++) {
                ctSet.add(conceptTypes.get(ctids[j]));
            }
            rt.getSignature()[i] = ctSet;
        }
        NodeList translationNodes = elt.getElementsByTagName("translation");
        for (int i = 0; i < translationNodes.getLength(); i++) {
            Element translationElement = (Element) translationNodes.item(i);
            if (translationElement.getAttribute("lang").equals(lang)) {
                rt.setDescription(translationElement.getAttribute("descr"));
                rt.setLabel(translationElement.getAttribute("label"));
            }
        }
        return rt;
    }

    public static void createRelationTypeOrderElement(Element elt, Map<String, RelationType> relationTypes) {
        RelationType child = relationTypes.get(elt.getAttribute("id1"));
        RelationType parent = relationTypes.get(elt.getAttribute("id2"));
        child.setParent(parent);
    }

    public static Individual createIndividual(Element elt, Map<String, ConceptType> conceptTypes) {
        Individual ind = new Individual(elt.getAttribute("id"), conceptTypes.get(elt.getAttribute("idType")));
        ind.setLabel(elt.getAttribute("label"));
        return ind;
    }

    public static Support createSupport(Element elt, String lang) {
        Support support = new Support();

        Element conceptTypesElement = (Element) elt.getElementsByTagName("conceptTypes").item(0);
        NodeList ctypeNodes = conceptTypesElement.getElementsByTagName("ctype");
        for (int i = 0; i < ctypeNodes.getLength(); i++) {
            Element ctypeElement = (Element) ctypeNodes.item(i);
            support.add(createConceptType(ctypeElement, lang));
        }
        NodeList orderNodes = conceptTypesElement.getElementsByTagName("order");
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Element orderElement = (Element) orderNodes.item(i);
            createConceptTypeOrderElement(orderElement, support.getConceptTypes());
        }

        Element relationTypesElement = (Element) elt.getElementsByTagName("relationTypes").item(0);
        NodeList rtypeNodes = relationTypesElement.getElementsByTagName("rtype");
        for (int i = 0; i < rtypeNodes.getLength(); i++) {
            Element rtypeElement = (Element) rtypeNodes.item(i);
            support.add(createRelationType(rtypeElement, lang, support.getConceptTypes()));
        }
        orderNodes = relationTypesElement.getElementsByTagName("order");
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Element orderElement = (Element) orderNodes.item(i);
            createRelationTypeOrderElement(orderElement, support.getRelationTypes());
        }

        Element conformityElement = (Element) elt.getElementsByTagName("conformity").item(0);
        NodeList markerNodes = conformityElement.getElementsByTagName("marker");
        for (int i = 0; i < markerNodes.getLength(); i++) {
            Element markerElement = (Element) markerNodes.item(i);
            support.add(createIndividual(markerElement, support.getConceptTypes()));
        }

        return support;
    }

    public static Concept createConcept(Element elt, Support support) {
        Concept concept = new Concept(elt.getAttribute("id"));
        if (elt.getAttribute("referent").equals("individual")) {
            concept.setIndividual(support.getIndividuals().get(elt.getAttribute("idMarker")));
        }
        String conceptType = elt.getAttribute("idType");
        if (conceptType.length() > 0) {
            concept.addType(support.getConceptTypes().get(conceptType));
        } else {
            NodeList typeNodes = elt.getElementsByTagName("type");
            for (int i = 0; i < typeNodes.getLength(); i++) {
                Element typeElement = (Element) typeNodes.item(i);
                concept.addType(support.getConceptTypes().get(typeElement.getAttribute("id")));
            }
        }
        return concept;
    }

    public static Relation createRelation(Element elt, Support support) {
        Relation relation = new Relation(elt.getAttribute("id"), support.getRelationTypes().get(elt.getAttribute("idType")));
        return relation;
    }

    public static Edge createEdge(Element elt, ConceptualGraph cg) {
        Relation relation = cg.getRelations().get(elt.getAttribute("rid"));
        Concept concept = cg.getConcepts().get(elt.getAttribute("cid"));
        int label = Integer.parseInt(elt.getAttribute("label")) - 1;
        Edge edge = new Edge(relation, concept, label);
        return edge;
    }

    public static ConceptualGraph createConceptualGraph(Element elt, Support support) {
        ConceptualGraph cg = new ConceptualGraph(elt.getAttribute("id"));
        cg.setLabel(elt.getAttribute("label"));
        cg.setSet(elt.getAttribute("set"));

        NodeList conceptNodes = elt.getElementsByTagName("concept");
        for (int i = 0; i < conceptNodes.getLength(); i++) {
            Element conceptElement = (Element) conceptNodes.item(i);
            cg.add(createConcept(conceptElement, support));
        }

        NodeList relationNodes = elt.getElementsByTagName("relation");
        for (int i = 0; i < relationNodes.getLength(); i++) {
            Element relationElement = (Element) relationNodes.item(i);
            cg.add(createRelation(relationElement, support));
        }

        NodeList edgeNodes = elt.getElementsByTagName("edge");
        for (int i = 0; i < edgeNodes.getLength(); i++) {
            Element edgeElement = (Element) edgeNodes.item(i);
            cg.add(createEdge(edgeElement, cg));
        }

        return cg;
    }

    public static KnowledgeBase createKnowledgeBase(Element elt, String lang) {
        Element supportElement = (Element) elt.getElementsByTagName("support").item(0);
        Support support = createSupport(supportElement, lang);
        KnowledgeBase kb = new KnowledgeBase(lang, support);
        Node node = elt.getFirstChild();
        while (node != null) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elementNode = (Element) node;
                String tag = elementNode.getTagName();
                if (tag.equals("graph")) {
                    kb.addFact(createConceptualGraph(elementNode, support));
                }
            }
            node = node.getNextSibling();
        }
        return kb;
    }

    public static Element xmlConceptType(Document xmlDoc, ConceptType ct, String lang) {
        Element ctypeElement = xmlDoc.createElement("ctype");
        ctypeElement.setAttribute("id", ct.getId());
        ctypeElement.setAttribute("label", ct.getLabel());
        Element translationElement = xmlDoc.createElement("translation");
        translationElement.setAttribute("descr", ct.getDescription());
        translationElement.setAttribute("label", ct.getLabel());
        translationElement.setAttribute("lang", lang);
        ctypeElement.appendChild(translationElement);
        return ctypeElement;
    }

    public static Element xmlConceptTypeOrderElement(Document xmlDoc, ConceptType parent, ConceptType child) {
        Element orderElement = xmlDoc.createElement("order");
        orderElement.setAttribute("id1", child.getId());
        orderElement.setAttribute("id2", parent.getId());
        return orderElement;
    }

    public static Element xmlRelationType(Document xmlDoc, RelationType rt, String lang) {
        Element rtypeElement = xmlDoc.createElement("rtype");
        rtypeElement.setAttribute("id", rt.getId());
        StringBuilder signature = new StringBuilder();
        for (ConceptTypeSet cts : rt.getSignature()) {
            if (signature.length() > 0) {
                signature.append(" ");
            }
            boolean first = true;
            for (ConceptType ct : cts) {
                if (!first) {
                    signature.append(";");
                }
                signature.append(ct.getId());
                first = false;
            }
        }
        rtypeElement.setAttribute("idSignature", signature.toString());
        rtypeElement.setAttribute("label", rt.getLabel());
        Element translationElement = xmlDoc.createElement("translation");
        translationElement.setAttribute("descr", rt.getDescription());
        translationElement.setAttribute("label", rt.getLabel());
        translationElement.setAttribute("lang", lang);
        rtypeElement.appendChild(translationElement);
        return rtypeElement;
    }

    public static Element xmlRelationTypeOrderElement(Document xmlDoc, RelationType parent, RelationType child) {
        Element orderElement = xmlDoc.createElement("order");
        orderElement.setAttribute("id1", child.getId());
        orderElement.setAttribute("id2", parent.getId());
        return orderElement;
    }

    public static Element xmlIndividual(Document xmlDoc, Individual marker) {
        Element markerElement = xmlDoc.createElement("marker");
        markerElement.setAttribute("id", marker.getId());
        markerElement.setAttribute("idType", marker.getType().getId());
        markerElement.setAttribute("label", marker.getLabel());
        return markerElement;
    }

    public static Element xmlNestingType(Document xmlDoc, NestingType nt, String lang) {
        Element ctypeElement = xmlDoc.createElement("ntype");
        ctypeElement.setAttribute("id", nt.getId());
        ctypeElement.setAttribute("label", nt.getLabel());
        Element translationElement = xmlDoc.createElement("translation");
        translationElement.setAttribute("descr", nt.getDescription());
        translationElement.setAttribute("label", nt.getLabel());
        translationElement.setAttribute("lang", lang);
        ctypeElement.appendChild(translationElement);
        return ctypeElement;
    }

    public static Element xmlSupport(Document xmlDoc, Support support, String lang) {
        Element supportElement = xmlDoc.createElement("support");
        supportElement.setAttribute("name", "vocabulary");

        Element conceptTypesElement = xmlDoc.createElement("conceptTypes");
        for (ConceptType ct : support.getConceptTypes().values()) {
            Element ctypeElement = xmlConceptType(xmlDoc, ct, lang);
            conceptTypesElement.appendChild(ctypeElement);
        }
        for (ConceptType child : support.getConceptTypes().values()) {
            for (ConceptType parent : child.getParents()) {
                Element orderElement = xmlConceptTypeOrderElement(xmlDoc, parent, child);
                conceptTypesElement.appendChild(orderElement);
            }
        }
        supportElement.appendChild(conceptTypesElement);

        Element relationTypesElement = xmlDoc.createElement("relationTypes");
        for (RelationType rt : support.getRelationTypes().values()) {
            Element rtypeElement = xmlRelationType(xmlDoc, rt, lang);
            relationTypesElement.appendChild(rtypeElement);
        }
        for (RelationType child : support.getRelationTypes().values()) {
            RelationType parent = child.getParent();
            if (parent != null) {
                Element orderElement = xmlRelationTypeOrderElement(xmlDoc, parent, child);
                relationTypesElement.appendChild(orderElement);
            }
        }
        supportElement.appendChild(relationTypesElement);

        Element nestingTypesElement = xmlDoc.createElement("nestingTypes");
        {
            Element ntypeElement = xmlNestingType(xmlDoc, new NestingType(), lang);
            nestingTypesElement.appendChild(ntypeElement);
        }
        supportElement.appendChild(nestingTypesElement);

        Element conformityElement = xmlDoc.createElement("conformity");
        for (Individual ind : support.getIndividuals().values()) {
            Element markerElement = xmlIndividual(xmlDoc, ind);
            conformityElement.appendChild(markerElement);
        }
        supportElement.appendChild(conformityElement);

        return supportElement;
    }

    public static Element xmlConcept(Document xmlDoc, Concept c) {
        Element conceptElement = xmlDoc.createElement("concept");
        conceptElement.setAttribute("id", c.getId());
        if (c.getIndividual() != null) {
            conceptElement.setAttribute("referent", "individual");
            conceptElement.setAttribute("idMarker", c.getIndividual().getId());
        }
        ConceptTypeSet types = c.getTypes();
        if (types.size() == 1) {
            conceptElement.setAttribute("idType", types.iterator().next().getId());
        } else {
            for (ConceptType ct : types) {
                Element typeElement = xmlDoc.createElement("type");
                typeElement.setAttribute("id", ct.getId());
                conceptElement.appendChild(typeElement);
            }
        }
        return conceptElement;
    }

    public static Element xmlRelation(Document xmlDoc, Relation r) {
        Element relationElement = xmlDoc.createElement("relation");
        relationElement.setAttribute("id", r.getId());
        relationElement.setAttribute("idType", r.getType().getId());
        return relationElement;
    }

    public static Element xmlEdge(Document xmlDoc, Edge e) {
        Element edgeElement = xmlDoc.createElement("edge");
        edgeElement.setAttribute("cid", e.getConcept().getId());
        edgeElement.setAttribute("rid", e.getRelation().getId());
        edgeElement.setAttribute("label", "" + (e.getLabel() + 1));
        return edgeElement;
    }

    public static Element xmlConceptualGraph(Document xmlDoc, ConceptualGraph cg) {
        Element graphElement = xmlDoc.createElement("graph");
        graphElement.setAttribute("id", cg.getId());
        graphElement.setAttribute("label", cg.getLabel());
        graphElement.setAttribute("set", cg.getSet());
        graphElement.setAttribute("nature", "fact");

        for (Concept c : cg.getConcepts().values()) {
            Element conceptElement = xmlConcept(xmlDoc, c);
            graphElement.appendChild(conceptElement);
        }
        for (Relation r : cg.getRelations().values()) {
            Element relationElement = xmlRelation(xmlDoc, r);
            graphElement.appendChild(relationElement);
        }
        for (Edge e : cg.getEdges()) {
            Element edgeElement = xmlEdge(xmlDoc, e);
            graphElement.appendChild(edgeElement);
        }

        return graphElement;
    }

    public static Element xmlKnowledgeBase(Document xmlDoc, KnowledgeBase kb) {
        Element cogxmlElement = xmlDoc.createElement("cogxml");
        Element supportElement = xmlSupport(xmlDoc, kb.getSupport(), kb.getLanguage());
        cogxmlElement.appendChild(supportElement);
        for (Set<ConceptualGraph> cgs : kb.getFacts().values()) {
            for (ConceptualGraph cg : cgs) {
                Element graphElement = xmlConceptualGraph(xmlDoc, cg);
                cogxmlElement.appendChild(graphElement);
            }
        }
        return cogxmlElement;
    }
}
