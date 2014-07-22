package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractQuery {

    protected final Connection conn;

    public AbstractQuery(final Connection conn) {
        this.conn = conn;
    }

    protected void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    protected void closeStatement(final Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    protected void commitTransaction() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    protected void rollbackTransaction() throws SQLException {
        conn.rollback();
        conn.setAutoCommit(true);
    }

}
