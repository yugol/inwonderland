/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.search;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import ro.uaic.info.wonderland.data.Article;
import ro.uaic.info.wonderland.data.Harvester;
import ro.uaic.info.wonderland.data.twitter.TwitterHarvester;
import ro.uaic.info.wonderland.emo.EmoDetector;
import ro.uaic.info.wonderland.emo.EmoOntology;
import ro.uaic.info.wonderland.emo.EmoSearchResult;

/**
 *
 * @author Iulian
 */
public class EmoSearchEngine {

    List<Harvester> harvesters;
    EmoOntology emoOnt;
    EmoDetector emoDetector;

    public List<Harvester> getHarvesters() {
        return harvesters;
    }

    public EmoOntology getEmoOnt() {
        return emoOnt;
    }

    public EmoSearchEngine() throws FileNotFoundException {
        harvesters = new ArrayList<Harvester>();
        harvesters.add(new TwitterHarvester());
        emoOnt = new EmoOntology();
        emoDetector = new EmoDetector(emoOnt);
    }

    public synchronized EmoSearchResult search(String topic) {
        long t0, t1;
        emoDetector.initDetection();
        EmoSearchResult esr = emoDetector.getResult();

        t0 = System.currentTimeMillis();
        List<Article> articles = harvestArticles(topic);
        t1 = System.currentTimeMillis();
        esr.setHarvestTime(t1 - t0);

        t0 = System.currentTimeMillis();
        for (Article art : articles) {
            emoDetector.findEmotions(art);
        }
        t1 = System.currentTimeMillis();
        esr.setDetectionTime(t1 - t0);
        esr.setArticleCount(articles.size());

        return esr;
    }

    private List<Article> harvestArticles(String topic) {
        List<Article> articles = new ArrayList<Article>();
        for (Harvester hv : harvesters) {
            if (hv.isActive()) {
                articles.addAll(hv.harvest(topic));
            }
        }
        return articles;
    }
}
