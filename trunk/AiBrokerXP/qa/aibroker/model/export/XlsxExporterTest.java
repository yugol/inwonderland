package aibroker.model.export;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.Sequence;
import aibroker.util.convenience.Databases;
import aibroker.util.convenience.Selectors;

public class XlsxExporterTest {

    @Test
    public void testExport() throws IOException {
        final File file = new File(Context.getExportFolder(), "DEDJIA_RON.test.xlsx");
        final Sequence seq = Databases.DEFAULT().getSequence(Selectors.DEDJIA_RON_DEC13_DAILY);
        System.out.println(seq.getQuotes().toString());
        final XlsxExporter expt = new XlsxExporter();
        expt.add(seq);
        expt.save(file);
        System.out.println("Saved in: " + file.getCanonicalPath());
    }

}
