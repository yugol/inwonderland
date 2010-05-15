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

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TypedDependency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.cg.Concept;
import org.purl.net.wonderland.cg.ConceptType;
import org.purl.net.wonderland.cg.ConceptualGraph;
import org.purl.net.wonderland.cg.Edge;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.cg.Relation;
import org.purl.net.wonderland.cg.RelationType;
import org.purl.net.wonderland.kb.WkbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ParseResult {

    private final List<WTagging> taggedSentence;
    private final Tree parseTree;
    private final List<TypedDependency> dependencies;
    private final Map<TreeGraphNode, WTagging> tgn2wt;
    private final Map<TreeGraphNode, Integer> tgn2int;
    private KnowledgeBase kb = null;
    private Map<WTagging, Concept> wt2c = null;

    public ParseResult(List<WTagging> tags, Tree parseTree, List<TypedDependency> dependencies) {
        this.taggedSentence = tags;
        this.parseTree = parseTree;
        this.dependencies = dependencies;
        this.tgn2wt = new HashMap<TreeGraphNode, WTagging>();
        this.tgn2int = new HashMap<TreeGraphNode, Integer>();
        mapDepsToWords();
    }

    private void mapDepsToWords() {
        for (TypedDependency tdep : dependencies) {
            mapTgn2Wt(tdep.gov());
            mapTgn2Wt(tdep.dep());
        }
    }

    private void mapTgn2Wt(TreeGraphNode node) {
        String label = node.nodeString();
        int tmp = label.lastIndexOf("-") + 1;
        tmp = Integer.parseInt(label.substring(tmp));
        tgn2int.put(node, tmp);
        tgn2wt.put(node, taggedSentence.get(tmp - 1));
    }

    public List<TypedDependency> getDependencies() {
        return dependencies;
    }

    public Tree getParseTree() {
        return parseTree;
    }

    public List<WTagging> getTaggedSentence() {
        return taggedSentence;
    }

    public WTagging getTaggedWord(int index) {
        return taggedSentence.get(index);
    }

    public TypedDependency getDependency(int index) {
        return dependencies.get(index);
    }

    public int getSentenceSize() {
        return taggedSentence.size();
    }

    public int getIndex(TreeGraphNode node) {
        return tgn2int.get(node);
    }

    public ConceptualGraph getConceptualGraph() {
        return getKb().getFacts().values().iterator().next().iterator().next();
    }

    public Concept getConcept(WTagging word) {
        return wt2c.get(word);
    }

    public KnowledgeBase getKb() {
        if (kb == null) {
            kb = new KnowledgeBase();

            ConceptualGraph cg = new ConceptualGraph();
            wt2c = new HashMap<WTagging, Concept>();

            for (WTagging wt : taggedSentence) {
                Concept c = new Concept();
                for (String typeId : wt.asTypes()) {
                    ConceptType cType = kb.getSupport().addConceptType(typeId);
                    cType.setLabel(WkbUtil.toConceptTypeLabel(typeId));
                    c.addType(cType);
                }
                c.setIndividual(kb.getSupport().addIndividual(wt.getLemma(), null));
                c.setTag(wt);
                cg.add(c);
                wt2c.put(wt, c);
            }

            for (TypedDependency tdep : dependencies) {
                Concept gov = wt2c.get(tgn2wt.get(tdep.gov()));
                Concept dep = wt2c.get(tgn2wt.get(tdep.dep()));
                String rTypeLabel = tdep.reln().getShortName();
                String rTypeId = WkbUtil.toRelationTypeId(rTypeLabel);
                RelationType rType = kb.getSupport().addRelationType(rTypeId, 2);
                rType.setLabel(rTypeLabel);
                Relation r = new Relation(rType);
                cg.add(r);
                cg.add(new Edge(r, dep, 0));
                cg.add(new Edge(r, gov, 1));
            }

            kb.addFact(cg);
        }
        return kb;
    }
}
