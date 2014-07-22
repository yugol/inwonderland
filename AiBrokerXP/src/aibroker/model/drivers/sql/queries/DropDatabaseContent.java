package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DropDatabaseContent extends AbstractQuery {

    public DropDatabaseContent(final Connection conn) {
        super(conn);
    }

    public void dropContent() throws SQLException {
        Statement stmt = null;
        try {
            beginTransaction();
            stmt = conn.createStatement();
            for (final String tableName : readTableNames()) {
                final String sql = "DROP TABLE " + tableName;
                stmt.executeUpdate(sql);
            }
            commitTransaction();
        } catch (final Exception ex) {
            rollbackTransaction();
        } finally {
            closeStatement(stmt);
        }
    }

    private List<String> readTableNames() throws SQLException {
        final List<String> tableNames = new ArrayList<String>();
        final String sql = "SELECT name FROM sqlite_master WHERE type='table';";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableNames.add(rs.getString("name"));
            }
            rs.close();
        } finally {
            closeStatement(stmt);
        }
        return tableNames;
    }

}
