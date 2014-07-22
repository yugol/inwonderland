package aibroker;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConfig {

    public static final Logger logger                   = LoggerFactory.getLogger(TestConfig.class);
    public static final File   CSV_DB_PATH              = new File("/home/iulian/temp/aibxpdb.csv");
    public static final File   CSV_TEST_DATABASE_FOLDER = new File("test/res/csvdb");

}
