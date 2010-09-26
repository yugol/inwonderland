/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public class EmoOntology {

    EmoCategory[] emoCategories = null;
    Emotion[] emotions = null;

    public EmoCategory[] getEmoCategories() {
        return emoCategories;
    }

    public Emotion[] getEmotions() {
        return emotions;
    }

    public EmoOntology() throws FileNotFoundException {
        emoCategories = readCategories();
        emotions = readEmotions();
    }

    EmoCategory[] readCategories() throws FileNotFoundException {
        ArrayList<EmoCategory> categs = new ArrayList<EmoCategory>();

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        model.read(new BufferedInputStream(new FileInputStream(Globals.getAffectOntologyPath())), "RDF/XML");

        Resource categ = model.getResource(Globals.ontologyIdPrefix + "GEWEmotionalCategory");
        Property labelProp = model.getProperty(Globals.ontologyIdPrefix + "hasLabel");
        Property indexProp = model.getProperty(Globals.ontologyIdPrefix + "hasIndex");

        ExtendedIterator<Individual> iit = model.listIndividuals(categ);
        while (iit.hasNext()) {
            Individual indi = iit.next();
            try {
                int index = (Integer) indi.getPropertyValue(indexProp).asNode().getLiteralValue();
                String label = (String) indi.getPropertyValue(labelProp).asNode().getLiteralValue();
                EmoCategory emoCat = new EmoCategory((byte) index, label);
                categs.add(emoCat);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

        EmoCategory[] array = categs.toArray(new EmoCategory[0]);
        Arrays.sort(array);
        return array;
    }

    Emotion[] readEmotions() throws FileNotFoundException {
        List<Emotion> emos = new ArrayList<Emotion>();

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        model.read(new BufferedInputStream(new FileInputStream(Globals.getAffectOntologyPath())), "RDF/XML");

        Resource emotionCls = model.getResource(Globals.ontologyIdPrefix + "UtilitarianEmotion");
        Property labelValence = model.getProperty(Globals.ontologyIdPrefix + "hasValence");
        Property labelControl = model.getProperty(Globals.ontologyIdPrefix + "hasControl");
        Property labelIntensity = model.getProperty(Globals.ontologyIdPrefix + "hasIntensity");
        Property labelProp = model.getProperty(Globals.ontologyIdPrefix + "hasLabel");
        Property labelTextMark = model.getProperty(Globals.ontologyIdPrefix + "hasTextMark");

        ExtendedIterator<Individual> iit = model.listIndividuals(emotionCls);
        while (iit.hasNext()) {
            Individual indi = iit.next();
            try {
                String label = (String) indi.getPropertyValue(labelProp).asNode().getLiteralValue();
                int valence = (Integer) indi.getPropertyValue(labelValence).asNode().getLiteralValue();
                int control = (Integer) indi.getPropertyValue(labelControl).asNode().getLiteralValue();
                int intensity = (Integer) indi.getPropertyValue(labelIntensity).asNode().getLiteralValue();
                byte category = getEmoGEWCategory(valence, control);

                Emotion emo = new Emotion((byte) valence, (byte) control, (byte) intensity, label, category);

                StmtIterator mit = indi.listProperties(labelTextMark);
                while (mit.hasNext()) {
                    String marker = (String) mit.next().getObject().asNode().getLiteralValue();
                    emo.getMarkers().add(marker);
                }

                emos.add(emo);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

        Emotion[] array = emos.toArray(new Emotion[0]);
        Arrays.sort(array);
        for (int i = 0; i < array.length; ++i) {
            array[i].setIndex((short) i);
        }
        return array;
    }

    byte getEmoGEWCategory(int valence, int control) {
        float angle = EmoUtil.cartesianToAngle(valence, control);
        EmoCategory bestEmoCat = getEmoCategories()[0];
        float bestDistance = 100;
        for (EmoCategory emoCat : getEmoCategories()) {
            float distance = Math.abs(angle - emoCat.getOrientation());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestEmoCat = emoCat;
            }
        }
        return bestEmoCat.getIndex();
    }

    public EmoCategory getEmoCategory(Emotion emo) {
        return emoCategories[emo.getEmoCategory() - 1];
    }
}
