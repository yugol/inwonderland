package aibroker.model;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import aibroker.util.BrokerException;

public abstract class QuotesDb implements Iterable<Seq> {

    protected final File                  dbLocation;
    protected final Map<String, Seq> sequences = new LinkedHashMap<String, Seq>();

    protected QuotesDb(final File dbLocation) {
        this.dbLocation = dbLocation;
    }

    public abstract Seq add(SeqDesc tBuilder);

    public abstract void drop() throws Exception;

    public Seq getSequence(final SeqSel selector) {
        final List<? extends Seq> sequences = getSequences(selector);
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

    @Override
    public Iterator<Seq> iterator() {
        return sequences.values().iterator();
    }

    public void save() {
    }

    protected Seq add(final Seq sequence) {
        sequences.put(sequence.getName().toUpperCase(), sequence);
        return sequence;
    }

    protected Seq getSequence(final String symbol) {
        return sequences.get(symbol);
    }

    protected abstract List<? extends Seq> getSequences(SeqSel selector);

    protected abstract Quotes readQuotes(Seq sequence);

}
