package aibroker.agents.sibex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import aibroker.Context;

public class SibexFuturesMarketLoggerTest {

    @Test
    public void testBackupLogFile() throws IOException {
        final File src = new File(Context.getSibexLogFilePath());
        final File dst = new File(Context.getBackupFolder(), src.getName());
        assertFalse(dst.exists());
        try {
            SibexFuturesMarketLogger.backupLogFile();
            assertTrue(dst.exists());
        } finally {
            if (dst.exists()) {
                dst.deleteOnExit();
            }
        }
    }

}
