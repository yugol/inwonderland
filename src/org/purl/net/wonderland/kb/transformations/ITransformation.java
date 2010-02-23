/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.kb.transformations;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Iulian
 */
public interface ITransformation extends Comparator<ITransformation> {

    public void init(KnowledgeBase kb, CGraph cg, int id);

    public int getId();

    public CGraph getLhs();

    public void setProjections(List<Projection> projections);
}
