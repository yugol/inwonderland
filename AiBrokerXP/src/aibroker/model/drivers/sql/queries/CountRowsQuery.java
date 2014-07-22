package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountRowsQuery extends AbstractQuery {

    public CountRowsQuery(final Connection conn) {
        super(conn);
    }

    public int callCount() throws SQLException {
        int rowCount = 0;
        PreparedStatement outerStmt = null;
        try {
            outerStmt = conn.prepareStatement("SELECT id FROM M_SEQUENCES");
            final ResultSet outerRs = outerStmt.executeQuery();
            while (outerRs.next()) {
                final String tableName = outerRs.getString(1);
                System.out.println(tableName);
                PreparedStatement innerStmt = null;
                try {
                    final String innerSql = "SELECT count(*) FROM " + tableName;
                    innerStmt = conn.prepareStatement(innerSql);
                    final ResultSet innerRs = innerStmt.executeQuery();
                    while (innerRs.next()) {
                        rowCount += innerRs.getInt(1);
                    }
                } finally {
                    closeStatement(innerStmt);
                }
            }
        } finally {
            closeStatement(outerStmt);
        }
        return rowCount;
    }

}
