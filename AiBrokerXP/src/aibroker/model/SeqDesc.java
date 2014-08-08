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

    public int getBlockSize() {
        return blockSize;
    }

    public Double getFee() {
        return fee;
    }

    public Feed getFeed() {
        return feed;
    }

    public Moment getFirstDayOfTransaction() {
        return firstDayOfTransaction;
    }

    public Grouping getGrouping() {
        return grouping;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public void getLastPrice(final float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getMargin() {
        return margin;
    }

    public Market getMarket() {
        return market;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public String getName() {
        return name;
    }

    public Sampling getSampling() {
        return sampling;
    }

    public Moment getSettlement() {
        return settlement;
    }

    public String getSupport() {
        return support;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTableId() {
        return tableId;
    }

    public Updater getUpdater() {
        return updater;
    }

    public Boolean isFavourite() {
        return favourite;
    }

    public void setBlockSize(final int blockSize) {
        this.blockSize = blockSize;
    }

    public SeqDesc setFavourite(final Boolean favourite) {
        this.favourite = favourite;
        return this;
    }

    public SeqDesc setFee(final double fee) {
        this.fee = fee;
        return this;
    }

    public SeqDesc setFeed(final Feed feed) {
        this.feed = feed;
        return this;
    }

    public SeqDesc setFirstDayOfTransaction(final Moment firstDayOfTransaction) {
        this.firstDayOfTransaction = firstDayOfTransaction;
        return this;
    }

    public SeqDesc setFirstDayOfTransaction(final String isoDate) {
        if (isNullOrBlank(isoDate)) {
            firstDayOfTransaction = null;
            return this;
        }
        return setFirstDayOfTransaction(Moment.fromIso(isoDate));
    }

    public SeqDesc setGrouping(final Grouping grouping) {
        this.grouping = grouping;
        return this;
    }

    public SeqDesc setMargin(final Double margin) {
        this.margin = margin;
        return this;
    }

    public SeqDesc setMarket(final Market market) {
        this.market = market;
        return this;
    }

    public SeqDesc setMultiplier(final Double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public SeqDesc setName(final String name) {
        this.name = name;
        return this;
    }

    public SeqDesc setSampling(final Sampling sampling) {
        this.sampling = sampling;
        return this;
    }

    public SeqDesc setSupport(final String support) {
        this.support = support;
        return this;
    }

    public SeqDesc setTableId(final String tableId) {
        this.tableId = tableId;
        return this;
    }

    public SeqDesc setUpdater(final Updater updater) {
        this.updater = updater;
        return this;
    }

    public SeqSel toSelector() {
        final SeqSel selector = new SeqSel();
        selector.setMarket(getMarket());
        selector.setSymbol(getSymbol());
        selector.setSettlement(getSettlement());
        selector.setFeed(getFeed());
        selector.setSampling(getSampling());
        selector.setGrouping(getGrouping());
        return selector;
    }

}
