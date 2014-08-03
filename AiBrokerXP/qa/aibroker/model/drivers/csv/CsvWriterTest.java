package aibroker.model.drivers.csv;

import org.junit.Test;
import aibroker.model.Sequence;
import aibroker.model.cloud.sources.bursanoastra.BursanoastraConfig;
import aibroker.model.drivers.csv.CsvDatabase;
import aibroker.model.drivers.metastock.MetastockDatabase;
import aibroker.util.convenience.Databases;

public class CsvWriterTest {

    @Test
    public void testWrite() {
        final MetastockDatabase msDb = new MetastockDatabase(BursanoastraConfig.METASTOCK_DATABASE_FOLDER);
        final CsvDatabase csvDb = new CsvDatabase(Databases.CSV_DEFAULT.url);
        for (final Sequence sequence : msDb) {
            csvDb.add(sequence);
        }
        csvDb.save();
    }

}
