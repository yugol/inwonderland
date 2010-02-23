/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.kb.transformations;

import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.solver.SolverCogitant;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import ro.uaic.info.wonderland.util.IO;

/**
 *
 * @author Iulian
 */
public class TransformationManager {

    public static String toClassName(String group, int id) {
        return "CGT_" + StringUtils.capitalize(group.toLowerCase()) + id;
    }
    private Map<String, Set<ITransformation>> transformations = new Hashtable<String, Set<ITransformation>>();
    private SolverCogitant solver = new SolverCogitant();
    private KnowledgeBase kb = null;
    private String classPath;

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public TransformationManager() {
        classPath = IO.getClassPathRoot(ITransformation.class);
    }

    public void setKb(KnowledgeBase kb) {
        solver.disconnect();
        this.kb = kb;
    }

    public void add(CGraph cg) {
        String[] chunks = cg.getName().split("_", 3);
        try {
            String group = chunks[0].toLowerCase();
            int id = Integer.parseInt(chunks[1]);
            String className = toClassName(group, id);
            ITransformation t = (ITransformation) Class.forName(classPath + "." + className).newInstance();
            t.init(kb, cg, id);
            add(group, t);
        } catch (Exception ex) {
            System.err.println("Error adding rule: " + cg.getId());
            System.err.println(ex);
        }
    }

    public List<ITransformation> apply(String group, CGraph cg) throws Exception {
        if (!solver.isConnected()) {
            solver.connect();
            solver.commitVocabulary(kb.getVocabulary());
            solver.resetCommitedGraphs();
        }
        List<ITransformation> matches = new ArrayList<ITransformation>();
        for (ITransformation t : transformations.get(group)) {
            CGraph lhs = t.getLhs();
            List<Projection> projections = solver.getProjections(lhs, cg);
            if (projections.size() > 0) {
                t.setProjections(projections);
                matches.add(t);
            } else {
                t.setProjections(null);
            }
        }
        solver.removeGraph(cg);
        return matches;
    }

    private void add(String group, ITransformation t) {
        if (!transformations.containsKey(group)) {
            transformations.put(group, new TreeSet<ITransformation>());
        }
        transformations.get(group).add(t);
    }
}
