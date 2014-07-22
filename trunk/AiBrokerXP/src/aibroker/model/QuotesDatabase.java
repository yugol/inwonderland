package aibroker.model;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import aibroker.util.BrokerException;

public abstract class QuotesDatabase implements Iterable<Sequence> {

    protected final File                  dbLocation;
    protected final Map<String, Sequence> sequences = new LinkedHashMap<String, Sequence>();

    protected QuotesDatabase(final File dbLocation) {
        this.dbLocation = dbLocation;
    }

    public abstract Sequence add(SequenceBuilder tBuilder);

    public abstract void drop() throws Exception;

    public Sequence getSequence(final SequenceSelector selector) {
        final List<? extends Sequence> sequences = getSequences(selector);
        switch (sequences.size()) {
            case 0:
                return null;
            case 1:
                return sequences.get(0);
            default:
                throw new BrokerException("More than one sequnce returned");
        }
    }

    public final int getSequenceCount() {
        return sequences.size();
    }

    public abstract List<? extends Sequence> getSequences(SequenceSelector selector);

    @Override
    public Iterator<Sequence> iterator() {
        return sequences.values().iterator();
    }

    public void save() {
    }

    protected Sequence add(final Sequence sequence) {
        sequences.put(sequence.getName().toUpperCase(), sequence);
        return sequence;
    }

    protected Sequence getSequence(final String symbol) {
        return sequences.get(symbol);
    }

    protected abstract Quotes readQuotes(Sequence sequence);

}
