/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.data.twitter;

import java.util.ArrayList;
import java.util.List;
import ro.uaic.info.wonderland.data.Article;
import ro.uaic.info.wonderland.data.Harvester;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;

/**
 *
 * @author Iulian
 */
public class TwitterHarvester implements Harvester {

    static int tweetsPerPage = 15;
    static int pageCount = 10;

    boolean active = true;
    Twitter twitter = new Twitter();
    Query query = new Query();

    public TwitterHarvester() {
        query.setRpp(tweetsPerPage);
    }

    public List<Article> harvest(String topic) {
        List<Article> articles = new ArrayList<Article>();
        query.setLang("en");
        articles.addAll(harvestTest(topic));
        if (articles.size() <= tweetsPerPage) {
            query.setLang("");
            articles.addAll(harvestTest(topic));
        }
        return articles;
    }

    public List<Article> harvestTest(String topic) {
        List<Article> articles = new ArrayList<Article>();
        query.setQuery(topic);
        for (int i = 1; i <= pageCount; ++i) {
            query.setPage(i);
            try {
                QueryResult result = twitter.search(query);
                for (Tweet tweet : result.getTweets()) {
                    Article art = new Article(topic);
                    art.setContent(tweet.getText());
                    art.setUrl("http://twitter.com/" + tweet.getFromUser());
                    articles.add(art);
                }
                if (articles.size() < i * tweetsPerPage) {
                    return articles;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return articles;
    }

    public String getName() {
        return "Twitter";
    }

    public String getId() {
        return "twitter";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
