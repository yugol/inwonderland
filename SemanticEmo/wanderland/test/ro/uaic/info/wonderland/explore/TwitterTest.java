/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import static org.junit.Assert.*;
import twitter4j.TwitterException;

/**
 *
 * @author Iulian
 */
public class TwitterTest {

    public TwitterTest() {
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
    public void testQuery() throws TwitterException {
        Twitter twitter = new Twitter();
        Query query = new Query();
        query.setLang("en");
        query.setPage(1);
        query.setQuery("cern");
        query.setRpp(10);
        QueryResult result = twitter.search(query);
        List<Tweet> tweets = result.getTweets();
        System.out.println("hits:" + tweets.size());
        assertTrue(tweets.size() > 0);
        for (Tweet tweet : tweets) {
            System.out.println(tweet.getFromUser() + ":" + tweet.getText() + " //// " + tweet.getSource());
        }
    }
}
