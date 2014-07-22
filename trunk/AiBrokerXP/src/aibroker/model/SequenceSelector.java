package aibroker.model;

import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.Moment;
import aibroker.util.StringUtil;

public class SequenceSelector implements Cloneable {

    public static SequenceSelector fromName(String name) {
        final SequenceSelector selector = new SequenceSelector();
        if (!StringUtil.isNullOrBlank(name)) {
            name = name.trim();
            selector.setSymbol(SequenceBuilder.getSymbol(name));
            final String settlement = SequenceBuilder.getSettlement(name);
            if (!StringUtil.isNullOrBlank(settlement)) {
                selector.setSettlement(SequenceBuilder.parseSettlement(settlement));
            }
        }
        return selector;
    }

    private Feed     feed;
    private Grouping grouping;
    private Market   market;
    private Moment   settlement;
    private Sampling sampling;
    private String   symbol;
    private boolean  joinSettlements = false;

    public SequenceSelector() {
    }

    @Override
    public SequenceSelector clone() throws CloneNotSupportedException {
        return (SequenceSelector) super.clone();
    }

    public Feed getFeed() {
        return feed;
    }

    public Grouping getGrouping() {
        return grouping;
    }

    public Market getMarket() {
        return market;
    }

    public String getName() {
        return SequenceBuilder.getName(symbol, settlement);
    }

    public Sampling getSampling() {
        return sampling;
    }

    public Moment getSettlement() {
        return settlement;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isJoinSettlements() {
        return joinSettlements && market.isDerivative();
    }

    public void setFeed(final Feed feed) {
        this.feed = feed;
    }

    public void setGrouping(final Grouping grpuping) {
        grouping = grpuping;
    }

    public void setJoinSettlements(final boolean joinSettlements) {
        this.joinSettlements = joinSettlements;
    }

    public void setMarket(final Market market) {
        this.market = market;
    }

    public void setSampling(final Sampling sampling) {
        this.sampling = sampling;
    }

    public void setSettlement(final Moment settlement) {
        this.settlement = settlement;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public SequenceBuilder toBuilder() {
        final SequenceBuilder sb = new SequenceBuilder(getSymbol(), getSettlement());
        sb.feed(getFeed());
        sb.grouping(getGrouping());
        sb.market(getMarket());
        sb.sampling(getSampling());
        return sb;
    }

    @Override
    public String toString() {
        return "SequenceSelector [market=" + market + ", feed=" + feed + ", sampling=" + sampling + ", grouping=" + grouping + ", symbol=" + symbol + ", settlement=" + settlement + "]";
    }

}