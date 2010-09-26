/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.semanticemo;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import ro.uaic.info.wonderland.search.EmoSearchEngine;

/**
 *
 * @author Iulian
 */
public class Visible {

    private static final String EMO_SEARCH_ENGINE = "emoSearchEngine";
    // private static final String EMO_ONTOLOGY = "emoOntology";
    public static final String TOPIC = "topic";
    public static final String EMO_SEARCH_RESULT = "emoSearchResult";
    public static final String ARTICLE_ID = "artid";
    public static final String WONDERLAND_DATA_PATH = "wonderland.data.path";

    public static EmoSearchEngine getSearchEngine(ServletContext context) throws FileNotFoundException {
        EmoSearchEngine se = (EmoSearchEngine) context.getAttribute(EMO_SEARCH_ENGINE);
        if (se == null) {
            se = new EmoSearchEngine();
            context.setAttribute(EMO_SEARCH_ENGINE, se);
        }
        return se;
    }
}
