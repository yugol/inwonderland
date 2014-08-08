package aibroker.model.drivers.sql;

import java.util.ArrayList;
import java.util.List;
import aibroker.model.QuotesDb;
import aibroker.model.SequenceDescriptor;
import aibroker.model.SequenceSelector;
import aibroker.util.Moment;

public class VirtualSqlSequence extends SqlSequence {

    private boolean           joinSettlements;
    private List<SqlSequence> baseSequences;

    VirtualSqlSequence(final QuotesDb qDb, final SequenceDescriptor sb) {
        super(qDb, sb);
    }

    public void add(final SqlSequence sequence) {
        if (baseSequences == null) {
            baseSequences = new ArrayList<SqlSequence>();
        }
        baseSequences.add(sequence);
    }

    public List<SqlSequence> getBaseSequences() {
        return baseSequences;
    }

    public boolean isJoinSettlements() {
        return joinSettlements;
    }

    public void setBaseSequences(final List<SqlSequence> baseSequences) {
        this.baseSequences = baseSequences;
    }

    public void setFavourite(final boolean favourite) {
        this.favourite = favourite;
    }

    public void setJoinSettlements(final boolean joinSettlements) {
        this.joinSettlements = joinSettlements;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSettlement(final Moment settlement) {
        this.settlement = settlement;
    }

    @Override
    public SequenceSelector toSelector() {
        final SequenceSelector selector = new SequenceSelector();
        selector.setFeed(getFeed());
        selector.setGrouping(getGrouping());
        selector.setMarket(getMarket());
        selector.setSampling(getSampling());
        selector.setSettlement(getSettlement());
        selector.setSymbol(getSymbol());
        selector.setJoinSettlements(isJoinSettlements());
        return selector;
    }

}
