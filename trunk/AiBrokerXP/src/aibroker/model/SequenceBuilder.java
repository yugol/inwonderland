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

public class SequenceBuilder {

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

    public SequenceBuilder(final String name) {
        this(getSymbol(name), getSettlement(name));
        this.name = name;
    }

    public SequenceBuilder(final String symbol, final int month, final int year) {
        this(symbol, calculateSettlement(month, year));
    }

    public SequenceBuilder(final String symbol, final Moment settlement) {
        this.symbol = symbol;
        this.settlement = settlement;
    }

    public SequenceBuilder(final String symbol, final String settlement) {
        this(symbol, parseSettlement(settlement));
    }

    public SequenceBuilder(final String symbol, final String month, final int year) {
        this(symbol, calculateSettlement(Moment.monthNameToIndex(month), year));
    }

    public Boolean favourite() {
        return favourite;
    }

    public SequenceBuilder favourite(final Boolean favourite) {
        this.favourite = favourite;
        return this;
    }

    public Double fee() {
        return fee;
    }

    public SequenceBuilder fee(final double fee) {
        this.fee = fee;
        return this;
    }

    public Feed feed() {
        return feed;
    }

    public SequenceBuilder feed(final Feed feed) {
        this.feed = feed;
        return this;
    }

    public Moment firstDayOfTransaction() {
        return firstDayOfTransaction;
    }

    public SequenceBuilder firstDayOfTransaction(final Moment firstDayOfTransaction) {
        this.firstDayOfTransaction = firstDayOfTransaction;
        return this;
    }

    public SequenceBuilder firstDayOfTransaction(final String isoDate) {
        if (isNullOrBlank(isoDate)) {
            firstDayOfTransaction = null;
            return this;
        }
        return firstDayOfTransaction(Moment.fromIso(isoDate));
    }

    public Grouping grouping() {
        return grouping;
    }

    public SequenceBuilder grouping(final Grouping grouping) {
        this.grouping = grouping;
        return this;
    }

    public Double margin() {
        return margin;
    }

    public SequenceBuilder margin(final Double margin) {
        this.margin = margin;
        return this;
    }

    public Market market() {
        return market;
    }

    public SequenceBuilder market(final Market market) {
        this.market = market;
        return this;
    }

    public Double multiplier() {
        return multiplier;
    }

    public SequenceBuilder multiplier(final Double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public String name() {
        return name;
    }

    public SequenceBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public Sampling sampling() {
        return sampling;
    }

    public SequenceBuilder sampling(final Sampling sampling) {
        this.sampling = sampling;
        return this;
    }

    public Moment settlement() {
        return settlement;
    }

    public String support() {
        return support;
    }

    public SequenceBuilder support(final String support) {
        this.support = support;
        return this;
    }

    public String symbol() {
        return symbol;
    }

    public String tableId() {
        return tableId;
    }

    public SequenceBuilder tableId(final String tableId) {
        this.tableId = tableId;
        return this;
    }

    public SequenceSelector toSelector() {
        final SequenceSelector selector = new SequenceSelector();
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

    public SequenceBuilder updater(final Updater updater) {
        this.updater = updater;
        return this;
    }

}
