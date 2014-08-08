package aibroker.model;

import static aibroker.util.StringUtil.isNullOrBlank;
import java.util.Calendar;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.util.Moment;
import aibroker.util.StringUtil;

public class SeqDesc {

    public static Moment calculateSettlement(final int month, int year) {
        if (year < 1000) {
            year += 2000;
        }
        final Moment settlement = new Moment(year, month, 1);
        settlement.add(Calendar.MONTH, 1);
        settlement.add(Calendar.DAY_OF_YEAR, -1);
        return settlement;
    }

    public static String getName(final String symbol, final Moment settlement) {
        return getName(symbol, toSettlementString(settlement));
    }

    public static String getName(final String symbol, final String settlement) {
        if (isNullOrBlank(settlement)) { return symbol; }
        return symbol + SETTLEMENT_SEPERATOR + settlement;
    }

    public static String getSettlement(final String name) {
        final String[] parts = name.split(SETTLEMENT_SEPERATOR);
        if (parts.length > 1) { return parts[1]; }
        return null;
    }

    public static String getSymbol(final String name) {
        return name.split(SETTLEMENT_SEPERATOR)[0];
    }

    public static Moment parseSettlement(final String settlement) {
        if (StringUtil.isNullOrBlank(settlement)) { return null; }
        Moment moment = null;
        try {
            moment = Moment.fromIso(settlement);
        } catch (final Exception ex) {
            final int year = 2000 + Integer.parseInt(settlement.substring(3));
            final int month = Moment.monthNameToIndex(settlement.substring(0, 3));
            moment = new Moment(year, month, 1, 23, 59, 59);
            moment.add(Calendar.MONTH, 1);
            moment.add(Calendar.DAY_OF_YEAR, -1);
        }
        return moment;
    }

    public static String toSettlementString(final Moment moment) {
        if (moment == null) { return null; }
        final String month = Moment.indexToShortMonthName(moment.get(Calendar.MONTH));
        final String year = String.format("%02d", moment.get(Calendar.YEAR) % 100);
        return month + year;
    }

    public static final String SETTLEMENT_SEPERATOR = "-";

    private final String       symbol;
    protected Moment           settlement;
    private String             name;
    private Market             market;
    private Feed               feed;

    private Sampling           sampling;
    private Grouping           grouping;
    private String             support;
    private Double             multiplier;

    private Double             margin;
    private double             fee;
    private Updater            updater              = Updater.NONE;
    private Moment             firstDayOfTransaction;

    private String             tableId;

    private Boolean            favourite            = false;

    private int                blockSize            = 1;
    private float              lastPrice;

    public SeqDesc(final String name) {
        this(getSymbol(name), getSettlement(name));
        this.name = name;
    }

    public SeqDesc(final String symbol, final int month, final int year) {
        this(symbol, calculateSettlement(month, year));
    }

    public SeqDesc(final String symbol, final Moment settlement) {
        this.symbol = symbol;
        this.settlement = settlement;
    }

    public SeqDesc(final String symbol, final String settlement) {
        this(symbol, parseSettlement(settlement));
    }

    public SeqDesc(final String symbol, final String month, final int year) {
        this(symbol, calculateSettlement(Moment.monthNameToIndex(month), year));
    }

    public int blockSize() {
        return blockSize;
    }

    public void blockSize(final int blockSize) {
        this.blockSize = blockSize;
    }

    public Boolean favourite() {
        return favourite;
    }

    public SeqDesc favourite(final Boolean favourite) {
        this.favourite = favourite;
        return this;
    }

    public Double fee() {
        return fee;
    }

    public SeqDesc fee(final double fee) {
        this.fee = fee;
        return this;
    }

    public Feed feed() {
        return feed;
    }

    public SeqDesc feed(final Feed feed) {
        this.feed = feed;
        return this;
    }

    public Moment firstDayOfTransaction() {
        return firstDayOfTransaction;
    }

    public SeqDesc firstDayOfTransaction(final Moment firstDayOfTransaction) {
        this.firstDayOfTransaction = firstDayOfTransaction;
        return this;
    }

    public SeqDesc firstDayOfTransaction(final String isoDate) {
        if (isNullOrBlank(isoDate)) {
            firstDayOfTransaction = null;
            return this;
        }
        return firstDayOfTransaction(Moment.fromIso(isoDate));
    }

    public Grouping grouping() {
        return grouping;
    }

    public SeqDesc grouping(final Grouping grouping) {
        this.grouping = grouping;
        return this;
    }

    public float lastPrice() {
        return lastPrice;
    }

    public void lastPrice(final float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double margin() {
        return margin;
    }

    public SeqDesc margin(final Double margin) {
        this.margin = margin;
        return this;
    }

    public Market market() {
        return market;
    }

    public SeqDesc market(final Market market) {
        this.market = market;
        return this;
    }

    public Double multiplier() {
        return multiplier;
    }

    public SeqDesc multiplier(final Double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public String name() {
        return name;
    }

    public SeqDesc name(final String name) {
        this.name = name;
        return this;
    }

    public Sampling sampling() {
        return sampling;
    }

    public SeqDesc sampling(final Sampling sampling) {
        this.sampling = sampling;
        return this;
    }

    public Moment settlement() {
        return settlement;
    }

    public String support() {
        return support;
    }

    public SeqDesc support(final String support) {
        this.support = support;
        return this;
    }

    public String symbol() {
        return symbol;
    }

    public String tableId() {
        return tableId;
    }

    public SeqDesc tableId(final String tableId) {
        this.tableId = tableId;
        return this;
    }

    public SeqSel toSelector() {
        final SeqSel selector = new SeqSel();
        selector.setMarket(market());
        selector.setSymbol(symbol());
        selector.setSettlement(settlement());
        selector.setFeed(feed());
        selector.setSampling(sampling());
        selector.setGrouping(grouping());
        return selector;
    }

    public Updater updater() {
        return updater;
    }

    public SeqDesc updater(final Updater updater) {
        this.updater = updater;
        return this;
    }

}
