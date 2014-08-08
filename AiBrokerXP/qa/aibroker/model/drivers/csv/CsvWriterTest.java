package aibroker.model.drivers.csv;

import org.junit.Test;
import aibroker.model.Seq;
import aibroker.model.cloud.sources.bursanoastra.BursanoastraConfig;
import aibroker.model.drivers.csv.CsvDb;
import aibroker.model.drivers.metastock.MetastockDb;
import aibroker.util.convenience.Databases;

public class CsvWriterTest {

    @Test
    public void testWrite() {
        final MetastockDb msDb = new MetastockDb(BursanoastraConfig.METASTOCK_DATABASE_FOLDER);
        final CsvDb csvDb = new CsvDb(Databases.CSV_DEFAULT.url);
        for (final Seq sequence : msDb) {
            csvDb.add(sequence);
        }
        csvDb.save();
    }

}
