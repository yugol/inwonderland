/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public class OntologyTest {

    public OntologyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testOntology() throws FileNotFoundException {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        model.read(new BufferedInputStream(new FileInputStream(Globals.getAffectOntologyPath())), "RDF/XML");

        ExtendedIterator<OntClass> cit = model.listNamedClasses();
        while (cit.hasNext()) {
            OntClass oc = cit.next();
            System.out.println(oc.getURI());
        }

        System.out.println("");

        ExtendedIterator<OntProperty> pit = model.listAllOntProperties();
        while (pit.hasNext()) {
            OntProperty prop = pit.next();
            System.out.println(prop.getURI());
        }

        System.out.println("");

        Resource gew = model.getResource("http://www.semanticweb.org/ontologies/2010/0/Ontology1262693413691.owl#GEWEmotionalCategory");
        ExtendedIterator<Individual> iit = model.listIndividuals(gew);
        while (iit.hasNext()) {
            Individual indi = iit.next();
            System.out.println(indi.getLocalName());
        }

        model.close();
    }
}
