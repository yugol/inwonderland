package aibroker.util.convenience;

import java.util.LinkedHashMap;
import java.util.Map;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.Moment;

public class Selectors {

    public static SeqSel valueOf(final String name) {
        return VALUES.get(name);
    }

    public static SeqSel[] values() {
        return VALUES.values().toArray(new SeqSel[0]);
    }

    private static final Map<String, SeqSel> VALUES                 = new LinkedHashMap<String, SeqSel>();

    public static final SeqSel               DEAPL                  = new SeqSel();
    public static final SeqSel               DEDJIA_RON_DEC13_DAILY = new SeqSel();
    public static final SeqSel               TLV                    = new SeqSel();

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

    static {
        TLV.setMarket(Market.REGS);
        TLV.setSymbol("TLV");
        TLV.setFeed(Feed.ORIG);
        TLV.setSampling(Sampling.DAILY);
        VALUES.put("TLV", TLV);
    }

}
