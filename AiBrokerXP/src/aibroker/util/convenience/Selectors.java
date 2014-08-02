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

    public static SequenceSelector valueOf(final String name) {
        return VALUES.get(name);
    }

    public static SequenceSelector[] values() {
        return VALUES.values().toArray(new SequenceSelector[0]);
    }

    private static final Map<String, SequenceSelector> VALUES                 = new LinkedHashMap<String, SequenceSelector>();

    public static final SequenceSelector               DEAPL                  = new SequenceSelector();
    public static final SequenceSelector               DEDJIA_RON_DEC13_DAILY = new SequenceSelector();

    static {
        DEAPL.setMarket(Market.FUTURES);
        DEAPL.setSymbol("DEAPL");
        DEAPL.setJoinSettlements(true);
        DEAPL.setFeed(Feed.ORIG);
        DEAPL.setGrouping(Grouping.TICK);
        DEAPL.setSampling(Sampling.SECOND);
        VALUES.put("DEAPL", DEAPL);
    }

    static {
        DEDJIA_RON_DEC13_DAILY.setMarket(Market.FUTURES);
        DEDJIA_RON_DEC13_DAILY.setSymbol("DEDJIA_RON");
        DEDJIA_RON_DEC13_DAILY.setSettlement(Moment.fromIso("2013-12-31"));
        DEDJIA_RON_DEC13_DAILY.setJoinSettlements(false);
        DEDJIA_RON_DEC13_DAILY.setFeed(Feed.ORIG);
        DEDJIA_RON_DEC13_DAILY.setSampling(Sampling.DAILY);
        VALUES.put("DEDJIA_RON_DEC13_DAILY", DEDJIA_RON_DEC13_DAILY);
    }

}
