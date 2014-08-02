package aibroker.model.drivers.sql;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.QuotesDatabase;
import aibroker.model.Sequence;
import aibroker.model.SequenceDescriptor;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.BrokerException;
import aibroker.util.MergeUtil;
import aibroker.util.Moment;
import static aibroker.util.StringUtil.isNullOrBlank;

public class SqlSequence extends Sequence {

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

    SqlSequence(final QuotesDatabase qDb, final SequenceDescriptor sb) {
        super(qDb, sb);

        tableId = isNullOrBlank(sb.tableId()) ? getNewQuotesTableName() : sb.tableId();

        if (sb.market() == null) { throw new BrokerException("Market cannot be empty"); }
        market = sb.market();

        if (sb.feed() == null) { throw new BrokerException("Feed cannot be empty"); }
        feed = sb.feed();

        if (sb.grouping() == null) { throw new BrokerException("Grouping cannot be empty"); }
        grouping = sb.grouping();

        if (sb.sampling() == null) { throw new BrokerException("Sampling cannot be empty"); }
        sampling = sb.sampling();

        favourite = sb.favourite();

        fee = sb.fee();

        firstDayOfTransaction = sb.firstDayOfTransaction();

        if (isRegular()) {
            support = getSymbol();
        } else {
            if (isNullOrBlank(sb.support())) { throw new BrokerException("Base Symbol cannot be empty"); }
            support = sb.support().trim().toUpperCase();

            if (getSettlement() == null) { throw new BrokerException("Settlement cannot be empty"); }

            margin = sb.margin();

            multiplier = sb.multiplier();
        }

        name = isNullOrBlank(sb.name()) ? super.getName() : sb.name();
    }

    @Override
    public void addQuotes(final List<Ohlc> quotes) throws SQLException {
        ((SqlDatabase) qDb).addQuotes(this, quotes);
    }

    public VirtualSqlSequence cloneVirtual() {
        final SequenceDescriptor sb = new SequenceDescriptor(getSymbol(), getSettlement());
        sb.tableId("N/A");
        sb.favourite(isFavourite());
        sb.fee(getFee());
        sb.feed(getFeed());
        sb.firstDayOfTransaction(getFirstDayOfTransaction());
        sb.grouping(getGrouping());
        sb.margin(getMargin());
        sb.market(getMarket());
        sb.multiplier(getMultiplier());
        sb.name(getName());
        sb.sampling(getSampling());
        sb.support(getSupport());
        return new VirtualSqlSequence(qDb, sb);
    }

    public void deleteQuotes() throws SQLException {
        ((SqlDatabase) qDb).deleteQuotesFor(this);
    }

    @Override
    public void deleteQuotes(final Moment date) throws SQLException {
        ((SqlDatabase) qDb).deleteQuotesFor(this, date);
    }

    @Override
    public SqlDatabase getDatabase() {
        return (SqlDatabase) super.getDatabase();
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

    public SequenceSelector toSelector() {
        final SequenceSelector selector = new SequenceSelector();
        selector.setFeed(getFeed());
        selector.setGrouping(getGrouping());
        selector.setMarket(getMarket());
        selector.setSampling(getSampling());
        selector.setSettlement(getSettlement());
        selector.setSymbol(getSymbol());
        return selector;
    }

    public void updateQuotes() throws SQLException {
        ((SqlDatabase) qDb).updateQuotes(this);
    }

    void setTableId(final String tableId) {
        this.tableId = tableId;
    }

}
