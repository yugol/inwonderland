/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.util.Corpus;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MessageProcessorTest {

    static File candidateFile = new File("gold_candidate.xml");

    public MessageProcessorTest() {
        candidateFile.delete();
    }

    private void mergeToCandidate(MessageProcessor instance) throws ParserConfigurationException, SAXException, IOException {
        Corpus candidate = new Corpus();
        candidate.buildFrom(candidateFile);
        candidate.addKnowledgeBase(instance.getKb());
        candidate.writeToFile(candidateFile);
    }



    /*
    resp = instance.processMessage("Is he the singer you were telling me about?");
    assertEquals("Done.", resp);
    resp = instance.processMessage("The film was very interesting.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("Open the door please!");
    assertEquals("Done.", resp);
    resp = instance.processMessage("This is the boy who broke my window.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("This is the road to Swansea.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("The elephant is a big animal.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("Elephants are big animals.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("I remember the Sunday we left London.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("The winter of 1954 lasted five months.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("I think I met her in the January of 1980.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("She visited us several times during the spring of that year.");
    assertEquals("Done.", resp);
    resp = instance.processMessage("And do you call this a car?");
    assertEquals("Done.", resp);
     */
    // @Test
    public void testArticles() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("The elephant is a big animal.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("We have a few friends here.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I want to put an end to it.");
        assertEquals("Done.", resp);

        File file = new File("articles.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testPersonalPronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("I must see her now.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("They gave me some flowers.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Thank you!");
        assertEquals("Done.", resp);
        resp = instance.processMessage("We shell visit London tomorrow.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("You are very tired.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("They go to work by tube.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("He offered her some nice flowers.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I saw him here yesterday.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I looked at them.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She told us what happened.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I will never forget it");
        assertEquals("Done.", resp);

        File file = new File("personal_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testPossesivePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("The car is mine.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The house is yours.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The dog is his.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The jewelry is hers.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The children are ours.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The television is theirs.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The globe is thine.");
        assertEquals("Done.", resp);

        File file = new File("possesive_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testDemonstrativePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("This is an interesting book.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("These are the problems you have to do for next time.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("That was a serious problem.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("What are those on the table?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Andy and Janet are cousins : the former is a student, the latter is a pupil.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She eats this half of the pear and you may eat the other.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Some guests drank beer, the others drank wine.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("George learns in the same school as his friend.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("You say that she is a good pianist but I don't think so.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Such is the present situation.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Let's suppose that you go into such-and-such a shop and ask for such-and-such.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I don't like books about love, romance and suchlike.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Mother has brought two blouses today: a white one and a red one.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("These are your tests; they are the ones you wrote yesterday.");
        assertEquals("Done.", resp);

        File file = new File("demonstrative_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testReflexivePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("The one who noticed this for the first time was myself.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Talk about yourself.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Accidentally, the hunter shot himself.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She cooked herself a big breakfast.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The trouble is in the machine itself.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("One may cut oneself with such a sharp knife.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Mrs. Brown, like ourselves, had no ideea about the accident.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Make yourself at home!");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The cooks themselves eat after all the guests have finished.");
        assertEquals("Done.", resp);

        File file = new File("reflexive_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testIndefinitePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("All that glitters is not gold.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The boys are both sleeping.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Either will be fine.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Neither shoe feels comfortable.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("We were fined 50 dollars each.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("We still have much to learn.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("A few people survived, but many died.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She gave the child two cakes but he asked for more.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Most of my friends will be here at 5 o'clock.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I don't like this tie; show me another.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Some books are cheap, others are expensive.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("When I opened the box of eggs, I found that several of them were broken.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She has had enough to eat.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Everybody is at home.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Everything is possible here.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Everyone needs some free time for rest and relaxation.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("We took some of the books to the auction.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Don't give her any!");
        assertEquals("Done.", resp);
        resp = instance.processMessage("None dared to do it.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("There is somebody in front of our house.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I have something to tell you.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Everybody likes someone.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I didn't see anybody.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("You can eat anything you like.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She is the most thrifty person of anyone I know.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Nobody is at home.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Is there nothing she can do?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("They have enough and we do poorly live.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Several of the workers went home sick.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("One never knows what one can do till one tries.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("You never know what you can do till you try.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("They speak English in this hotel.");
        assertEquals("Done.", resp);

        File file = new File("indefinite_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testRelativePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("This is the girl who we met yesterday.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("These are the boys whom I talked to.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Jimmy, a boy to whom I lent twenty pounds.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The woman whose hat is red is our neighbour.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I'll help whoever needs my help.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The book which I bought yesterday is very expensive.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("She will take whichever books you don't want.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("This is the girl that Jack loves.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Listen to what I tell you.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I received the same grade as you did.");
        assertEquals("Done.", resp);

        File file = new File("relative_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    @Test
    public void testInterrogativePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("Who has broken this vase?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I have found a pen ; whose is it?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Whom did you meet there?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("What is she doing now?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Which of you saw her yesterday?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("To whom is she engaged?");
        assertEquals("Done.", resp);

        File file = new File("interrogative_pronouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    // @Test
    public void testNouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        // It  >'s< are probleme la proiectie

        resp = instance.processMessage("The ice melts in the sun."); // NN
        assertEquals("Done.", resp);
        resp = instance.processMessage("It is raining with cats and dogs."); // NNS
        assertEquals("Done.", resp);
        resp = instance.processMessage("Shannon goes to Liverpool."); // NNP
        assertEquals("Done.", resp);
        resp = instance.processMessage("Americans live in Americas."); // NNPS
        assertEquals("Done.", resp);

        File file = new File("nouns.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }
}
