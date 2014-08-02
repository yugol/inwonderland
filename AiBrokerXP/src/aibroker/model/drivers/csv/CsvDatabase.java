package aibroker.model.drivers.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import aibroker.model.Quotes;
import aibroker.model.QuotesDatabase;
import aibroker.model.Sequence;
import aibroker.model.SequenceDescriptor;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Grouping;
import aibroker.util.BrokerException;
import aibroker.util.FileUtil;

public class CsvDatabase extends QuotesDatabase {

    private class QuotesFilter implements FilenameFilter {

        @Override
        public boolean accept(final File dir, String name) {
            name = name.toUpperCase();
            if (name.endsWith(DEFAULT_EXT.toUpperCase())) { return true; }
            if (name.endsWith(OHLC_EXT.toUpperCase())) { return true; }
            if (name.endsWith(TICK_EXT.toUpperCase())) { return true; }
            return false;
        }

    }

    public static final String  DELIMITER   = ",";
    public static final String  PREFIX      = "_";
    private final static String DEFAULT_EXT = "." + FileUtil.CSV_EXTENSION;
    private final static String OHLC_EXT    = ".EOD." + FileUtil.CSV_EXTENSION;
    private final static String TICK_EXT    = ".T." + FileUtil.CSV_EXTENSION;

    public CsvDatabase(final File dbFolder) {
        super(dbFolder);
        readMaster();
    }

    @Override
    public Sequence add(final Sequence sequence) {
        return super.add(sequence);
    }

    @Override
    public Sequence add(final SequenceDescriptor tBuilder) {
        return add(new CsvSequence(this, tBuilder));
    }

    @Override
    public void drop() throws IOException {
        FileUtils.deleteDirectory(dbLocation);
    }

    @Override
    public List<Sequence> getSequences(final SequenceSelector selector) {
        final List<Sequence> sequences = new ArrayList<Sequence>();
        final Sequence sequence = getSequence(selector.getName());
        sequences.add(sequence);
        return sequences;
    }

    @Override
    public void save() {
        if (!dbLocation.exists()) {
            dbLocation.mkdirs();
        }
        for (final Sequence sequence : this) {
            String fileName = null;
            if (sequence instanceof CsvSequence) {
                final CsvSequence csvSequence = (CsvSequence) sequence;
                switch (csvSequence.getType()) {
                    case OHLC:
                        fileName = sequence.getName() + OHLC_EXT;
                        break;
                    case TICK:
                        fileName = sequence.getName() + TICK_EXT;
                        break;
                }
            } else {
                fileName = PREFIX + sequence.getName() + DEFAULT_EXT;
            }
            final File csvFile = new File(dbLocation, fileName);
            if (csvFile.exists()) {
                csvFile.delete();
            }
            PrintStream out = null;
            try {
                out = new PrintStream(csvFile);
                CsvWriter.writeQuotes(out, sequence);
            } catch (final FileNotFoundException e) {
                throw new BrokerException(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    private void readMaster() {
        if (!dbLocation.exists()) {
            dbLocation.mkdirs();
        }
        for (final File qFile : dbLocation.listFiles(new QuotesFilter())) {
            final String fileName = qFile.getName().toUpperCase();

            String sequenceName = fileName.substring(0, fileName.indexOf("."));
            if (sequenceName.startsWith(PREFIX)) {
                sequenceName = sequenceName.substring(1);
            }
            final SequenceDescriptor tBuilder = new SequenceDescriptor(sequenceName);

            if (fileName.endsWith(TICK_EXT.toUpperCase())) {
                tBuilder.grouping(Grouping.TICK);
            } else if (fileName.endsWith(OHLC_EXT.toUpperCase())) {
                tBuilder.grouping(Grouping.OHLC);
            }

            add(tBuilder);

        }
    }

    @Override
    protected Quotes readQuotes(final Sequence sequence) {
        File csv = null;
        if (sequence instanceof CsvSequence) {
            final CsvSequence csvSequence = (CsvSequence) sequence;
            switch (csvSequence.getType()) {
                case OHLC:
                    csv = new File(dbLocation, sequence.getName() + OHLC_EXT);
                    break;
                case TICK:
                    csv = new File(dbLocation, sequence.getName() + TICK_EXT);
                    break;
            }
        } else {
            csv = new File(dbLocation, sequence.getName() + DEFAULT_EXT);
        }
        if (csv.exists()) {
            sequence.setQuotes(CsvReader.readQuotes(csv));
        } else {
            sequence.setQuotes(new Quotes());
        }
        return sequence.getQuotes();
    }

}
