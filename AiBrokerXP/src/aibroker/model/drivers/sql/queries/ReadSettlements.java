package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aibroker.model.SequenceSelector;
import aibroker.util.Moment;

public class ReadSettlements extends AbstractQuery {

    public ReadSettlements(final Connection conn) {
        super(conn);
    }

    public List<Moment> readSettlements(final SequenceSelector selector) throws SQLException {
        final List<Moment> settlements = new ArrayList<Moment>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT SETTLEMENT FROM M_SEQUENCES ");
        sql.append("WHERE ");
        sql.append("      FEED = ? ");
        sql.append("  AND MARKET = ? ");
        sql.append("  AND SYMBOL = ? ");
        sql.append("ORDER BY SETTLEMENT ASC");

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql.toString());
            int paramIdx = 1;
            stmt.setString(paramIdx++, selector.getFeed().name());
            stmt.setString(paramIdx++, selector.getMarket().name());
            stmt.setString(paramIdx++, selector.getSymbol());
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                final Moment settlement = Moment.fromIso(rs.getString(1));
                settlements.add(settlement);
            }
            rs.close();
        } finally {
            closeStatement(stmt);
        }
        return settlements;
    }

}
