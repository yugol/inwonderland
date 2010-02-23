/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.kb.transformations;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class DefaultTransformation implements ITransformation {

    private KnowledgeBase kb;
    private CGraph lhs;
    private int id;
    private List<Projection> projections;

    public void init(KnowledgeBase kb, CGraph lhs, int id) {
        this.kb = kb;
        this.lhs = lhs;
        this.id = id;
    }

    public int compare(ITransformation o1, ITransformation o2) {
        Integer id1 = new Integer(o1.getId());
        return id1.compareTo(o2.getId());
    }

    public int getId() {
        return id;
    }

    public CGraph getLhs() {
        return lhs;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }
}
