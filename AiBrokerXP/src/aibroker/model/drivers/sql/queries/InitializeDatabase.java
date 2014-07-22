package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitializeDatabase extends AbstractQuery {

    private static String getCreateTableMSequencesSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS M_SEQUENCES (");
        sql.append(" MARKET TEXT NOT NULL,");
        sql.append(" SYMBOL TEXT NOT NULL,");
        sql.append(" SETTLEMENT TEXT NOT NULL,");
        sql.append(" FEED TEXT NOT NULL,");
        sql.append(" SAMPLING TEXT NOT NULL,");
        sql.append(" GROUPING TEXT NOT NULL,");
        sql.append(" NAME TEXT NOT NULL,");
        sql.append(" ID TEXT PRIMARY KEY NOT NULL,");
        sql.append(" UPDATER TEXT NOT NULL,");
        sql.append(" FAVOURITE INTEGER NOT NULL,");
        sql.append(" UNIQUE (MARKET, SYMBOL, SETTLEMENT, FEED, SAMPLING)");
        sql.append(");");
        return sql.toString();
    }

    private static String getCreateTableMSymbolsSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS M_SYMBOLS (");
        sql.append(" MARKET TEXT NOT NULL,");
        sql.append(" SYMBOL TEXT NOT NULL,");
        sql.append(" BASE TEXT NOT NULL,");
        sql.append(" FEE REAL NOT NULL,");
        sql.append(" MULTIPLIER REAL NOT NULL,");
        sql.append(" MARGIN REAL NOT NULL,");
        sql.append(" FIRST_TRANSACTED TEXT,");
        sql.append(" PRIMARY KEY (MARKET, SYMBOL)");
        sql.append(");");
        return sql.toString();
    }

    public InitializeDatabase(final Connection conn) {
        super(conn);
    }

    public void init() throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(getCreateTableMSymbolsSql());
            stmt.executeUpdate(getCreateTableMSequencesSql());
        } finally {
            closeStatement(stmt);
        }
    }

}
