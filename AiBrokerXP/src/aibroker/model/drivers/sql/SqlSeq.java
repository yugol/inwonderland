package aibroker.model.drivers.sql;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.QuotesDb;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.BrokerException;
import aibroker.util.MergeUtil;
import aibroker.util.Moment;
import static aibroker.util.StringUtil.isNullOrBlank;

public class SqlSeq extends Seq {

    public static String getNewQuotesTableName() {
        return "S_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    protected String   name;
    protected String   tableId;

    protected Market   market;
    protected Feed     feed;
    protected Grouping grouping;
    protected Sampling sampling;

    protected Double   multiplier;
    protected Double   margin;
    protected Double   fee;

    protected String   support;

    protected Moment   firstDayOfTransaction;
    protected boolean  favourite;

    SqlSeq(final QuotesDb qDb, final SeqDesc sb) {
        super(qDb, sb);

        tableId = isNullOrBlank(sb.getTableId()) ? getNewQuotesTableName() : sb.getTableId();

        if (sb.getMarket() == null) { throw new BrokerException("Market cannot be empty"); }
        market = sb.getMarket();

        if (sb.getFeed() == null) { throw new BrokerException("Feed cannot be empty"); }
        feed = sb.getFeed();

        if (sb.getGrouping() == null) { throw new BrokerException("Grouping cannot be empty"); }
        grouping = sb.getGrouping();

        if (sb.getSampling() == null) { throw new BrokerException("Sampling cannot be empty"); }
        sampling = sb.getSampling();

        favourite = sb.isFavourite();

        fee = sb.getFee();

        firstDayOfTransaction = sb.getFirstDayOfTransaction();

        if (isRegular()) {
            support = getSymbol();
        } else {
            if (isNullOrBlank(sb.getSupport())) { throw new BrokerException("Base Symbol cannot be empty"); }
            support = sb.getSupport().trim().toUpperCase();

            if (getSettlement() == null) { throw new BrokerException("Settlement cannot be empty"); }

            margin = sb.getMargin();

            multiplier = sb.getMultiplier();
        }

        name = isNullOrBlank(sb.getName()) ? super.getName() : sb.getName();
    }

    @Override
    public void addQuotes(final List<Ohlc> quotes) throws SQLException {
        ((SqlDb) qDb).addQuotes(this, quotes);
    }

    public VirtualSqlSeq cloneVirtual() {
        final SeqDesc sb = new SeqDesc(getSymbol(), getSettlement());
        sb.setTableId("N/A");
        sb.setFavourite(isFavourite());
        sb.setFee(getFee());
        sb.setFeed(getFeed());
        sb.setFirstDayOfTransaction(getFirstDayOfTransaction());
        sb.setGrouping(getGrouping());
        sb.setMargin(getMargin());
        sb.setMarket(getMarket());
        sb.setMultiplier(getMultiplier());
        sb.setName(getName());
        sb.setSampling(getSampling());
        sb.setSupport(getSupport());
        return new VirtualSqlSeq(qDb, sb);
    }

    public void deleteQuotes() throws SQLException {
        ((SqlDb) qDb).deleteQuotesFor(this);
    }

    @Override
    public void deleteQuotes(final Moment date) throws SQLException {
        ((SqlDb) qDb).deleteQuotesFor(this, date);
    }

    @Override
    public SqlDb getDatabase() {
        return (SqlDb) super.getDatabase();
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

    public Double getMargin() {
        return margin;
    }

    public Market getMarket() {
        return market;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    @Override
    public String getName() {
        return name;
    }

    public Sampling getSampling() {
        return sampling;
    }

    public String getSupport() {
        return support;
    }

    public String getTableId() {
        return tableId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void mergeQuotes(final Quotes newQuotes) throws SQLException {
        final Quotes oldQuotes = getQuotes();
        final Quotes quotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        deleteQuotes();
        addQuotes(quotes);
    }

    public void setFee(final Double fee) {
        this.fee = fee;
    }

    public void setFirstDayOfTransaction(final Moment firstDayOfTransaction) {
        this.firstDayOfTransaction = firstDayOfTransaction;
    }

    public void setSampling(final Sampling sampling) {
        this.sampling = sampling;

    }

    public SeqSel toSelector() {
        final SeqSel selector = new SeqSel();
        selector.setFeed(getFeed());
        selector.setGrouping(getGrouping());
        selector.setMarket(getMarket());
        selector.setSampling(getSampling());
        selector.setSettlement(getSettlement());
        selector.setSymbol(getSymbol());
        return selector;
    }

    public void updateQuotes() throws SQLException {
        ((SqlDb) qDb).updateQuotes(this);
    }

    void setTableId(final String tableId) {
        this.tableId = tableId;
    }

}
