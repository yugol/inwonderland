/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.kb;

import org.purl.net.wonderland.kb.EngineKnowledgeBase;
import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.transformations.ITransformation;
import org.purl.net.wonderland.util.CodeTimer;
import org.purl.net.wonderland.util.IO;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class ProjectionTest {

    public ProjectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSimpleProjection() throws Exception {
        File kbFile = new File(Globals.getTestFolder(), "basic_projection.cogxml");
        EngineKnowledgeBase kb = new EngineKnowledgeBase();
        kb.getTransformationManager().setClassPath(IO.getClassPathRoot(CGT_Test1.class));
        kb.openKb(kbFile);
        List<ITransformation> matches = null;
        int count = 10;
        CodeTimer timer = new CodeTimer(count + " projections");
        for (int i = 0; i < count; ++i) {
            matches = kb.applyTransformations("test", EngineKnowledgeBase.toLevel1FactId(1));
        }
        timer.stop();
        assertEquals(1, matches.size());
    }
}
