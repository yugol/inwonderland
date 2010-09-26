/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import ro.uaic.info.wonderland.data.Article;

/**
 *
 * @author Iulian
 */
public class EmoSearchResult {

    int[] emoHits;
    Set[] artPool;
    int articleCount;
    int wordCount;
    long detectionTime;
    long harvestTime;
    EmoOntology emoOnt;

    public EmoSearchResult(EmoOntology emoOnt) {
        this.emoOnt = emoOnt;
        emoHits = new int[emoOnt.getEmotions().length];
        artPool = new Set[emoHits.length];
        for (int i = 0; i < emoHits.length; ++i) {
            emoHits[i] = 0;
            artPool[i] = new TreeSet<Article>();
        }
        articleCount = 0;
        wordCount = 0;
        detectionTime = 0;
    }

    public EmoOntology getEmoOnt() {
        return emoOnt;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articles) {
        this.articleCount = articles;
    }

    public long getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(long time) {
        this.detectionTime = time;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int words) {
        this.wordCount = words;
    }

    public long getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(long harvestTime) {
        this.harvestTime = harvestTime;
    }

    public Set[] getArtPool() {
        return artPool;
    }

    public int[] getEmoHits() {
        return emoHits;
    }

    public void add(Emotion emo, Article art) {
        emoHits[emo.getIndex()] += 1;
        artPool[emo.getIndex()].add(art);
    }

    public String toTextSummary() {
        StringBuffer sb = new StringBuffer();
        sb.append(getHarvestTime() / 1000d + " seconds for harvesting data\n");
        sb.append(getArticleCount() + " articles analyzed\n");
        sb.append(getWordCount() + " words processed\n");
        sb.append(getDetectionTime() / 1000d + " seconds for emotion detection\n");
        return sb.toString();
    }

    public Article getArticleById(String artId) {
        for (int i = 0; i < artPool.length; ++i) {
            Set articles = artPool[i];
            Iterator it = articles.iterator();
            while (it.hasNext()) {
                Article art = (Article) it.next();
                if (art.getId().equals(artId)) {
                    return art;
                }
            }
        }
        return null;
    }
}
