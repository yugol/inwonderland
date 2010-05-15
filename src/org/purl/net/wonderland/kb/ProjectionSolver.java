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

import cogitant.base.Environment;
import cogitant.base.Graph;
import cogitant.base.GraphObject;
import cogitant.base.IOHandler;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CgObject;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.DefaultProjection;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProjectionSolver {

    private final Wkb kb;
    private boolean connected = false; // a flag to disable if native cogitant library can't be loaded
    private Environment environment = null; // Cogitatnt evironmentn

    public ProjectionSolver(Wkb kb) {
        this.kb = kb;
    }

    public boolean connect() throws Exception {
        try {
            environment = new cogitant.jni.Environment();
            environment.setIOConfig(IOHandler.ConfigPropertyBool.AUTOCREATEINDIVIDUALS, true);
            environment.setIOConfig(IOHandler.ConfigPropertyBool.KEEPGRAPHOBJECTIDENTIFIERS, true);
            connected = true;
        } catch (Throwable t) {
            connected = false;
            System.out.println("native cogitant library can't be used");
            throw new Exception(t);
        }
        return connected;
    }

    private void disconnect() {
        connected = false;
        environment = null;
    }

    public void reset() throws Exception {
        if (connected) {
            disconnect();
        }
        connect();
        commitVocabulary(kb.getVocabulary());
    }

    private void commitVocabulary(Vocabulary vocabulary) throws Exception {
        if (connected) {
            StringWriter stringWriter = new StringWriter();
            CogxmlWriter.writeVocabulary(stringWriter, vocabulary, true, "en", false);
            environment.loadSupport(
                    new java.io.ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")),
                    IOHandler.Format.COGXML);
        }
    }

    private void commitGraph(CGraph graph) throws Exception {
        if (connected) {
            cogitant.base.Graph g = ((Environment) environment).findGraph(graph.getId());
            if (g != null) {
                ((Environment) environment).deleteGraph(g);
            }
            StringWriter stringWriter = new StringWriter();
            CogxmlWriter.writeGraphForCogitant(stringWriter, graph);
            try {
                ((Environment) environment).loadObjects(
                        new java.io.ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")),
                        IOHandler.Format.COGXML);

            } catch (Exception e) {
                System.out.println("problem with:\n" + stringWriter.toString());
            }

        }
    }

    private List<Projection> getProjections(CGraph g1, CGraph g2) throws Exception {
        if (connected) {
            List<Projection> result = new ArrayList<Projection>();

            // load graphs
            Graph cogitant_g1 = environment.findGraph(g1.getId());
            if (cogitant_g1 == null) {
                commitGraph(g1);
                cogitant_g1 = environment.findGraph(g1.getId());
            }
            Graph cogitant_g2 = environment.findGraph(g2.getId());
            if (cogitant_g2 == null) {
                commitGraph(g2);
                cogitant_g2 = environment.findGraph(g2.getId());
            }

            // find projections
            Collection<cogitant.base.Projection> projs = environment.projectionFind(cogitant_g1, cogitant_g2);

            // read projections
            for (cogitant.base.Projection proj : projs) {
                ArrayList<CgObject> targets = new ArrayList<CgObject>();
                HashMap<String, CgObject> sourceIndex = new HashMap<String, CgObject>();

                // map concepts
                Iterator<Concept> ic1 = g1.iteratorConcept(true);
                while (ic1.hasNext()) {
                    Concept concept = ic1.next();
                    GraphObject o1 = cogitant_g1.findByIdentifier(concept.getId());
                    if (o1 == null) {
                        System.out.println("can't find " + concept.getId() + " graph MUST be reloaded in cogitant");
                        continue;
                    }
                    GraphObject o2 = proj.imageOf(o1);
                    if (o2 != null) {
                        String tgtId = o2.identifier();
                        Concept targetConcept = g2.getConcept(tgtId, true);
                        sourceIndex.put(o1.identifier(), targetConcept);
                        targets.add(targetConcept);
                    }
                }

                // map relations
                Iterator<Relation> ir1 = g1.iteratorRelation(true);
                while (ir1.hasNext()) {
                    Relation relation = ir1.next();
                    GraphObject o1 = cogitant_g1.findByIdentifier(relation.getId());
                    if (o1 == null) {
                        System.out.println("can't find " + relation.getId() + " graph MUST be reloaded in cogitant");
                        continue;
                    }
                    GraphObject o2 = proj.imageOf(o1);
                    if (o2 != null) {
                        Relation targetRelation = g2.getRelation(o2.identifier(), true);
                        sourceIndex.put(o1.identifier(), targetRelation);
                        targets.add(targetRelation);
                    }
                }

                result.add(new DefaultProjection(g1, g2, sourceIndex, targets));
            }
            return result;
        }
        return null;
    }

    public Matches findMatches(Procs procs, CGraph cg) throws Exception {
        Matches matches = new Matches();
        for (Proc proc : procs) {
            List<Projection> projections = getProjections(proc.getLhs(), cg);
            matches.add(proc, projections);
        }
        return matches;
    }
}
