package tools;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.Test;
import aibroker.model.Quotes;
import aibroker.model.QuotesDatabase;
import aibroker.model.SequenceSelector;
import aibroker.model.drivers.csv.CsvReader;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.convenience.Databases;

public class LegacyQuotesMerger {

    private static final File           source      = new File("/home/iulian/AiBrokerXP/quotes/archive/----");
    private static final QuotesDatabase destination = Databases.SQL_DEFAULT.getInstance();

    private static String normalizeSymbol(final String symbol) {
        switch (symbol) {
            case "EURUSD_RO":
                return "EUR/USD_RON";
            case "SIBGOLD_RO":
                return "SIBGOLD_RON";
            default:
                return symbol;
        }
    }

    @Test
    public void listTickFiles() throws IOException, SQLException {
        processQuotesLocation(source);
    }

    private void processQuotesLocation(final File location) throws IOException, SQLException {
        for (final File file : location.listFiles()) {
            if (file.isDirectory()) {
                System.out.println("In: " + file.getCanonicalPath());
                processQuotesLocation(file);
            } else {
                final String fileName = file.getCanonicalPath();
                if (fileName.endsWith(".T.csv") && fileName.indexOf("CFD") < 0) {
                    String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf(".T.csv"));
                    // System.err.println(name);
                    final SequenceSelector selector = SequenceSelector.fromName(name);
                    selector.setSymbol(normalizeSymbol(selector.getSymbol()));
                    name = selector.getName();
                    final SqlSequence sequence = (SqlSequence) destination.getSequence(selector);
                    if (sequence != null) {
                        System.out.println("    Merging " + name + " <- " + fileName);
                        final Quotes fileQuotes = CsvReader.readQuotes(file);
                        sequence.mergeQuotes(fileQuotes);

                    } else {
                        // System.out.println("    " + name + ":  sequence not found");
                    }
                }
            }
        }
    }

}
