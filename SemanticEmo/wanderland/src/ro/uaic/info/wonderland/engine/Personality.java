/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;

/*
Bashful	Long beard	Brown top, green hat, long eyelashes
Doc	Short beard	Red tunic, brown hat, glasses
Dopey	Beardless	Green tunic, purple hat, big ears
Grumpy	Long beard	Red tunic, brown hat, scowl
Happy	Short beard	Brown top, orange headpiece, smile
Sleepy	Long beard	Green top, blue hat, heavy eyelids
Sneezy	Short beard	brown jacket, orange headpiece, red nose
 */
/**
 *
 * @author Iulian
 */
public abstract class Personality {

    KnowledgeBase kb = null;

    public void setKb(KnowledgeBase kb) {
        this.kb = kb;
    }

    public abstract String getWelcomeMessage();

    public abstract String getFullName();

    public abstract String getName();

    public abstract String getId();
}
