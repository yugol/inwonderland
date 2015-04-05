package aibroker.model.drivers.amibroker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.TestConfig;
import aibroker.model.Seq;
import aibroker.model.SeqSel;
import aibroker.model.drivers.csv.CsvDb;
import aibroker.model.drivers.csv.CsvWriter;

public class AmiDbTest {

    private static final Logger      logger    = LoggerFactory.getLogger(AmiDbTest.class);

    public static final File         DB_FOLDER = new File("/home/iulian/temp/aibxpdb");
    private static final boolean     CLEANUP   = false;
    private static AmibrokerDb amiDb;

    @BeforeClass
    public static void setUpClass() throws IOException, InterruptedException {
        deleteTestDb();
        amiDb = new AmibrokerDb(DB_FOLDER);
        logger.debug("{} database created", DB_FOLDER.getAbsolutePath());
    }

    @AfterClass
    public static void tearDownClass() throws IOException, InterruptedException {
        if (CLEANUP) {
            deleteTestDb();
        }
    }

    private static void deleteTestDb() throws IOException, InterruptedException {
        FileUtils.deleteDirectory(DB_FOLDER);
        Thread.sleep(100);
        logger.debug("{} database deleted", DB_FOLDER.getAbsolutePath());
    }

    // @Test
    public void testDatabase() {
        if (!CLEANUP) {
            logger.info("Database {} was created. Now you can explore it with AmiBroker.", DB_FOLDER.getAbsolutePath());
        }
    }

    @Test
    public void testSequence() {
        final CsvDb csvDb = new CsvDb(TestConfig.TEST_CSV_DB_FOLDER);
        final Seq brk = csvDb.getSequence(SeqSel.fromName("brk"));
        amiDb.add(brk);
        AmibrokerSeq amiBrk = amiDb.getSequence("BRK");
        amiBrk.setMarket(255);
        amiDb.save();
        final AmibrokerDb amiDb2 = new AmibrokerDb(DB_FOLDER);
        amiBrk = amiDb2.getSequence("BRK");
        assertNotNull(amiBrk);
        assertEquals(255, amiBrk.getMarket());
        assertEquals(62, amiBrk.getQuotes().size());
        if (!CLEANUP) {
            System.out.println();
            CsvWriter.writeQuotes(System.out, amiBrk);
            System.out.println();
            System.out.println(amiBrk.toString());
            logger.info("The {} sequence was added. Now you can explore it with AmiBroker.", amiBrk.getName());
        }
    }

}