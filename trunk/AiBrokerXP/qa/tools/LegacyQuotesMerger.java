package tools;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import aibroker.model.Quotes;
import aibroker.model.QuotesDb;
import aibroker.model.SequenceSelector;
import aibroker.model.drivers.csv.CsvReader;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.convenience.Databases;

public class LegacyQuotesMerger {

    public static void main(final String... args) throws IOException, SQLException {
        processQuotesLocation(source);
    }

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

    private static void processQuotesLocation(final File location) throws IOException, SQLException {
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

    private static final File           source      = new File("/home/iulian/AiBrokerXP/quotes/archive/----");

    private static final QuotesDb destination = Databases.SQL_DEFAULT.getInstance();

}
