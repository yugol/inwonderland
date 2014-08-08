package aibroker.model.drivers.sql.queries.quotes;

import java.sql.Connection;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.model.drivers.sql.queries.MaintainQuotes;

public class QuotesDerExtraOhlcMaintenance extends MaintainQuotes {

    public QuotesDerExtraOhlcMaintenance(final Connection conn, final SqlSeq sequence) {
        super(conn, sequence);
    }

    @Override
    public void insertAllQuotes(final List<Ohlc> quotes) {
        // XXX Auto-generated method stub
    }

    @Override
    protected String getTableCreationQuery(final String tableName) {
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" (");
        sql.append(" \"YY\" INTEGER NOT NULL,");
        sql.append(" \"MM\" INTEGER NOT NULL,");
        sql.append(" \"DD\" INTEGER NOT NULL,");
        sql.append(" \"C\" REAL NOT NULL,");
        sql.append(" \"O\" REAL NOT NULL,");
        sql.append(" \"H\" REAL NOT NULL,");
        sql.append(" \"L\" REAL NOT NULL,");
        sql.append(" \"V\" INTEGER NOT NULL,");
        sql.append(" \"OI\" INTEGER NOT NULL");
        sql.append(");");
        return sql.toString();
    }

    @Override
    protected Quotes readAllQuotesRaw() {
        // XXX Auto-generated method stub
        return null;
    }

}
