package aibroker.util.convenience;

import java.util.LinkedHashMap;
import java.util.Map;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.Moment;

public class Selectors {

    private static class LockableSelector extends SequenceSelector {

        private boolean locked = false;

        @Override
        public void setFeed(final Feed feed) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setFeed(feed);
        }

        @Override
        public void setGrouping(final Grouping grpuping) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setGrouping(grpuping);
        }

        @Override
        public void setJoinSettlements(final boolean joinSettlements) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setJoinSettlements(joinSettlements);
        }

        @Override
        public void setMarket(final Market market) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setMarket(market);
        }

        @Override
        public void setSampling(final Sampling sampling) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setSampling(sampling);
        }

        @Override
        public void setSettlement(final Moment settlement) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setSettlement(settlement);
        }

        @Override
        public void setSymbol(final String symbol) {
            if (locked) { throw new UnsupportedOperationException(); }
            super.setSymbol(symbol);
        }

        private void lock() {
            locked = true;
        }

    }

    private static final Map<String, SequenceSelector> VALUES = new LinkedHashMap<String, SequenceSelector>();

    public static final LockableSelector               DEAPL  = new LockableSelector();
    static {
        DEAPL.setMarket(Market.FUTURES);
        DEAPL.setSymbol("DEAPL");
        DEAPL.setJoinSettlements(true);
        DEAPL.setFeed(Feed.ORIG);
        DEAPL.setGrouping(Grouping.TICK);
        DEAPL.setSampling(Sampling.SECOND);
        DEAPL.lock();
        VALUES.put("DEAPL", DEAPL);
    }

    public static SequenceSelector valueOf(final String name) {
        return VALUES.get(name);
    }

    public static SequenceSelector[] values() {
        return VALUES.values().toArray(new SequenceSelector[0]);
    }

}
