package aibroker.agents.sibex;

import java.io.IOException;
import org.junit.Test;

public class SibexFuturesMarketLoggerTest {

    @Test
    public void testBackupLogFile() throws IOException {
        SibexFuturesMarketLogger.backupLogFile();
    }

}
