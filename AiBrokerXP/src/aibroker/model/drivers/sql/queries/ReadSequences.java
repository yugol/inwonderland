package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import static aibroker.util.StringUtil.isNullOrBlank;

public class ReadSequences extends AbstractQuery {

    public ReadSequences(final Connection conn) {
        super(conn);
    }

    public List<SeqDesc> readSequences(final SeqSel selector) throws SQLException {
        final List<SeqDesc> builders = new ArrayList<SeqDesc>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT\n");
        sql.append("  M_SEQUENCES.ID             AS ID,\n");
        sql.append("  M_SEQUENCES.MARKET         AS MARKET,\n");
        sql.append("  M_SEQUENCES.FEED           AS FEED,\n");
        sql.append("  M_SEQUENCES.SAMPLING       AS SAMPLING,\n");
        sql.append("  M_SEQUENCES.GROUPING       AS GROUPING,\n");
        sql.append("  M_SEQUENCES.UPDATER        AS UPDATER,\n");
        sql.append("  M_SEQUENCES.SYMBOL         AS SYMBOL,\n");
        sql.append("  M_SEQUENCES.SETTLEMENT     AS SETTLEMENT,\n");
        sql.append("  M_SYMBOLS.BASE             AS BASE,\n");
        sql.append("  M_SEQUENCES.NAME           AS NAME,\n");
        sql.append("  M_SYMBOLS.FEE              AS FEE,\n");
        sql.append("  M_SYMBOLS.MULTIPLIER       AS MULTIPLIER,\n");
        sql.append("  M_SYMBOLS.MARGIN           AS MARGIN,\n");
        sql.append("  M_SEQUENCES.FAVOURITE      AS FAVOURITE,\n");
        sql.append("  M_SYMBOLS.FIRST_TRANSACTED AS FIRST_TRANSACTED\n");
        sql.append("FROM M_SEQUENCES LEFT JOIN M_SYMBOLS\n");
        sql.append("  ON M_SEQUENCES.MARKET = M_SYMBOLS.MARKET AND M_SEQUENCES.SYMBOL = M_SYMBOLS.SYMBOL\n");
        sql.append("WHERE\n");
        sql.append("      M_SEQUENCES.MARKET LIKE ?\n");
        sql.append("  AND M_SEQUENCES.SYMBOL LIKE ?\n");
        sql.append("  AND M_SEQUENCES.SETTLEMENT LIKE ?\n");
        sql.append("  AND M_SEQUENCES.FEED LIKE ?\n");
        sql.append("  AND M_SEQUENCES.SAMPLING LIKE ?\n");
        sql.append("  AND M_SEQUENCES.GROUPING LIKE ?\n");
        sql.append("ORDER BY MARKET, FEED, SAMPLING, SYMBOL ASC, SETTLEMENT DESC");

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql.toString());

            final String market = selector.getMarket() == null ? "%" : selector.getMarket().name();
            final String symbol = isNullOrBlank(selector.getSymbol()) ? "%" : selector.getSymbol().trim().toUpperCase();
            final String settlement = selector.getSettlement() == null ? "%" : selector.getSettlement().toIsoDate();
            final String feed = selector.getFeed() == null ? "%" : selector.getFeed().name();
            final String sampling = selector.getSampling() == null ? "%" : selector.getSampling().name();
            final String grouping = selector.getGrouping() == null ? "%" : selector.getGrouping().name();

            int paramIdx = 1;
            stmt.setString(paramIdx++, market);
            stmt.setString(paramIdx++, symbol);
            stmt.setString(paramIdx++, settlement);
            stmt.setString(paramIdx++, feed);
            stmt.setString(paramIdx++, sampling);
            stmt.setString(paramIdx++, grouping);

            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                final SeqDesc builder = new SeqDesc(rs.getString("SYMBOL"), rs.getString("SETTLEMENT"));
                builder.setTableId(rs.getString("ID"));
                builder.setMarket(Market.valueOf(rs.getString("MARKET")));
                builder.setFeed(Feed.valueOf(rs.getString("FEED")));
                builder.setSampling(Sampling.valueOf(rs.getString("SAMPLING")));
                builder.setGrouping(Grouping.valueOf(rs.getString("GROUPING")));
                builder.setUpdater(Updater.valueOf(rs.getString("UPDATER")));
                builder.setSupport(rs.getString("BASE"));
                builder.setName(rs.getString("NAME"));
                builder.setFee(rs.getDouble("FEE"));
                builder.setMultiplier(rs.getDouble("MULTIPLIER"));
                builder.setMargin(rs.getDouble("MARGIN"));
                builder.setFavourite(rs.getBoolean("FAVOURITE"));
                builder.setFirstDayOfTransaction(rs.getString("FIRST_TRANSACTED"));
                builders.add(builder);
            }
            rs.close();
        } finally {
            closeStatement(stmt);
        }

        return builders;
    }

}
