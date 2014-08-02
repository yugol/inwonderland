package aibroker.model.export;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.convenience.Databases;
import aibroker.util.convenience.Selectors;

public class XlsxExporterTest {

    @Test
    public void testExport() throws IOException {
        final SqlSequence seq = Databases.DEFAULT().getSequence(Selectors.TLV);
        // System.out.println(seq.getQuotes().toString());
        final XlsxAnalysis expt = new XlsxAnalysis(seq);
        final File file = new File(Context.getExportFolder(), "TLV.test.xlsx");
        expt.save(file);
        System.out.println("Saved in: " + file.getCanonicalPath());
    }

}
