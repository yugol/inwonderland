package aibroker.model.drivers.sql.queries.quotes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.model.drivers.sql.queries.MaintainQuotes;
import aibroker.util.Moment;

public class QuotesDerIntraTickMaintenance extends MaintainQuotes {

    public QuotesDerIntraTickMaintenance(final Connection conn, final SqlSeq sequence) {
        super(conn, sequence);
    }

    @Override
    public void insertAllQuotes(final List<Ohlc> quotes) throws SQLException {
        final StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(sequence.getTableId()).append(" ");
        sql.append(" ( YY, MM, DD, HO, MI, SE, C, V, OI ) ");
        sql.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        PreparedStatement stmt = null;
        try {
            beginTransaction();
            stmt = conn.prepareStatement(sql.toString());
            for (final Ohlc ohlc : quotes) {
                int paramIdx = 1;
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.YEAR));
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.MONTH));
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.DAY_OF_MONTH));
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.HOUR_OF_DAY));
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.MINUTE));
                stmt.setInt(paramIdx++, ohlc.moment.get(Calendar.SECOND));
                stmt.setFloat(paramIdx++, ohlc.close);
                stmt.setInt(paramIdx++, ohlc.volume);
                stmt.setInt(paramIdx++, ohlc.openInt);
                stmt.executeUpdate();
            }
            commitTransaction();
        } catch (final Exception ex) {
            rollbackTransaction();
            throw ex;
        } finally {
            closeStatement(stmt);
        }
    }

    @Override
    protected String getTableCreationQuery(final String tableName) {
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" (");
        sql.append(" YY INTEGER NOT NULL,");
        sql.append(" MM INTEGER NOT NULL,");
        sql.append(" DD INTEGER NOT NULL,");
        sql.append(" HO INTEGER NOT NULL,");
        sql.append(" MI INTEGER NOT NULL,");
        sql.append(" SE INTEGER NOT NULL,");
        sql.append(" C REAL NOT NULL,");
        sql.append(" V INTEGER NOT NULL,");
        sql.append(" OI INTEGER NOT NULL");
        sql.append(");");
        return sql.toString();
    }

    @Override
    protected Quotes readAllQuotesRaw() throws SQLException {
        final Quotes quotes = new Quotes();
        final String sql = "SELECT YY, MM, DD, HO, MI, SE, C, V, OI FROM " + sequence.getTableId() + " ORDER BY YY, MM, DD, HO, MI, SE";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                final Moment moment = new Moment(rs.getInt("YY"), rs.getInt("MM"), rs.getInt("DD"), rs.getInt("HO"), rs.getInt("MI"), rs.getInt("SE"));
                final Ohlc ohlc = new Ohlc(moment, rs.getFloat("C"), rs.getInt("V"), rs.getInt("OI"));
                quotes.add(ohlc);
            }
            rs.close();
        } finally {
            closeStatement(stmt);
        }
        return quotes;
    }

}