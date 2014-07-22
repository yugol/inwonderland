package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.drivers.sql.queries.quotes.QuotesDerExtraOhlcMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesDerExtraTickMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesDerIntraOhlcMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesDerIntraTickMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesRegExtraOhlcMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesRegExtraTickMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesRegIntraOhlcMaintenance;
import aibroker.model.drivers.sql.queries.quotes.QuotesRegIntraTickMaintenance;
import aibroker.util.Moment;

public abstract class MaintainQuotes extends AbstractQuery {

    public static MaintainQuotes getInstance(final Connection conn, final SqlSequence sequence) {
        if (Market.REGS.equals(sequence.getMarket())) {
            if (sequence.getSampling().isIntraday()) {
                if (Grouping.TICK.equals(sequence.getGrouping())) {
                    return new QuotesRegIntraTickMaintenance(conn, sequence);
                } else {
                    return new QuotesRegIntraOhlcMaintenance(conn, sequence);
                }
            } else {
                if (Grouping.TICK.equals(sequence.getGrouping())) {
                    return new QuotesRegExtraTickMaintenance(conn, sequence);
                } else {
                    return new QuotesRegExtraOhlcMaintenance(conn, sequence);
                }
            }
        } else {
            if (sequence.getSampling().isIntraday()) {
                if (Grouping.TICK.equals(sequence.getGrouping())) {
                    return new QuotesDerIntraTickMaintenance(conn, sequence);
                } else {
                    return new QuotesDerIntraOhlcMaintenance(conn, sequence);
                }
            } else {
                if (Grouping.TICK.equals(sequence.getGrouping())) {
                    return new QuotesDerExtraTickMaintenance(conn, sequence);
                } else {
                    return new QuotesDerExtraOhlcMaintenance(conn, sequence);
                }
            }
        }
    }

    protected final SqlSequence sequence;

    protected MaintainQuotes(final Connection conn, final SqlSequence sequence) {
        super(conn);
        this.sequence = sequence;
    }

    public void createQuotesTable() throws SQLException {
        final String sql = getTableCreationQuery(sequence.getTableId().trim().toUpperCase());
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql.toString());
        } finally {
            closeStatement(stmt);
        }
    }

    public void deleteAllQuotes() throws SQLException {
        final String sql = "DELETE FROM " + sequence.getTableId();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } finally {
            closeStatement(stmt);
        }
    }

    public void deleteQuotesFor(final Moment date) throws SQLException {
        final StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(sequence.getTableId()).append(" ");
        sql.append("WHERE ");
        sql.append("      YY = ? ");
        sql.append("  AND MM = ? ");
        sql.append("  AND DD = ? ");
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql.toString());
            int paramIdx = 1;
            stmt.setInt(paramIdx++, date.get(Calendar.YEAR));
            stmt.setInt(paramIdx++, date.get(Calendar.MONTH));
            stmt.setInt(paramIdx++, date.get(Calendar.DAY_OF_MONTH));
            stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
    }

    public abstract void insertAllQuotes(List<Ohlc> quotes) throws SQLException;

    public Quotes readAllQuotes() throws SQLException {
        return readAllQuotesRaw();
    }

    protected abstract String getTableCreationQuery(String tableName);

    protected abstract Quotes readAllQuotesRaw() throws SQLException;

}
