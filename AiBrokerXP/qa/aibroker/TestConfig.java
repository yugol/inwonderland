package aibroker;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.util.FileUtil;

public class TestConfig {

    public static final Logger logger             = LoggerFactory.getLogger(TestConfig.class);
    public static final File   TEST_RES_FOLDER    = new File("qa/res");
    public static final File   TEST_CSV_DB_FOLDER = new File(TEST_RES_FOLDER, "csvdb");
    public final static File   TEST_SQL_DB_FILE   = new File(Context.getQuotesFolder(), "test." + FileUtil.QDB_EXTENSION);
    public static final File   CSV_DB_PATH        = new File("/home/iulian/temp/aibxpdb.csv");

}
