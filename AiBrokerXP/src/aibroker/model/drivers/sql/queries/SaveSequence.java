package aibroker.model.drivers.sql.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.BrokerException;

public class SaveSequence extends AbstractQuery {

    public SaveSequence(final Connection conn) {
        super(conn);
    }

    public String add(final SqlSequence sequence) throws SQLException {
        final SeqSel selector = sequence.toSelector();
        final List<SeqDesc> builders = new ReadSequences(conn).readSequences(selector);
        if (builders.size() == 0) {
            try {
                beginTransaction();
                try {
                    addSymbol(sequence);
                } catch (final SQLException sqlEx) {
                    if (!sqlEx.getMessage().contains("columns MARKET, SYMBOL are not unique")) { throw sqlEx; }
                }
                addSequence(sequence);
                commitTransaction();
            } catch (final Exception ex) {
                rollbackTransaction();
                throw ex;
            }
            return sequence.getTableId();
        } else if (builders.size() == 1) {
            final String tableId = builders.get(0).tableId();
            try {
                beginTransaction();
                updateSymbol(sequence);
                updateSequence(tableId, sequence);
                commitTransaction();
            } catch (final Exception ex) {
                rollbackTransaction();
                throw ex;
            }
            return tableId;
        } else {
            throw new BrokerException("More than o record matches the sequence - this should not happen!\n" + selector.toString());
        }
    }

    private void addSequence(final SqlSequence sequence) throws SQLException {
        final String sql = "INSERT INTO M_SEQUENCES (ID, MARKET, SYMBOL, SETTLEMENT, NAME, FEED, SAMPLING, GROUPING, UPDATER, FAVOURITE) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int paramIdx = 1;
            stmt.setString(paramIdx++, sequence.getTableId().trim().toUpperCase());
            stmt.setString(paramIdx++, sequence.getMarket().name());
            stmt.setString(paramIdx++, sequence.getSymbol().trim().toUpperCase());
            stmt.setString(paramIdx++, sequence.getSettlement() == null ? "" : sequence.getSettlement().toIsoDate());
            stmt.setString(paramIdx++, sequence.getName().trim().toUpperCase());
            stmt.setString(paramIdx++, sequence.getFeed().name());
            stmt.setString(paramIdx++, sequence.getSampling().name());
            stmt.setString(paramIdx++, sequence.getGrouping().name());
            stmt.setString(paramIdx++, sequence.getUpdater().name());
            stmt.setBoolean(paramIdx++, sequence.isFavourite());
            stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
        MaintainQuotes.getInstance(conn, sequence).createQuotesTable();
    }

    private void addSymbol(final SqlSequence sequence) throws SQLException {
        final String sql = "INSERT INTO M_SYMBOLS (MARKET, SYMBOL, BASE, FEE, MULTIPLIER, MARGIN, FIRST_TRANSACTED) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int paramIdx = 1;
            stmt.setString(paramIdx++, sequence.getMarket().name());
            stmt.setString(paramIdx++, sequence.getSymbol().trim().toUpperCase());
            stmt.setString(paramIdx++, sequence.getSupport().trim().toUpperCase());
            stmt.setDouble(paramIdx++, sequence.getFee() == null ? 0 : sequence.getFee());
            stmt.setDouble(paramIdx++, sequence.getMultiplier() == null ? 0 : sequence.getMultiplier());
            stmt.setDouble(paramIdx++, sequence.getMargin() == null ? 0 : sequence.getMargin());
            stmt.setString(paramIdx++, sequence.getFirstDayOfTransaction() == null ? "" : sequence.getFirstDayOfTransaction().toIsoDate());
            stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
    }

    private void updateSequence(final String tableId, final SqlSequence sequence) throws SQLException {
        final String sql = "UPDATE M_SEQUENCES SET UPDATER = ?, FAVOURITE = ? WHERE ID = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int paramIdx = 1;
            stmt.setString(paramIdx++, sequence.getUpdater().name());
            stmt.setBoolean(paramIdx++, sequence.isFavourite());
            stmt.setString(paramIdx++, tableId);
            stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
    }

    private void updateSymbol(final SqlSequence sequence) throws SQLException {
        final String sql = "UPDATE M_SYMBOLS SET FEE = ?, MULTIPLIER = ?, MARGIN = ? WHERE MARKET = ? AND SYMBOL = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int paramIdx = 1;
            stmt.setDouble(paramIdx++, sequence.getFee() == null ? 0 : sequence.getFee());
            stmt.setDouble(paramIdx++, sequence.getMultiplier() == null ? 0 : sequence.getMultiplier());
            stmt.setDouble(paramIdx++, sequence.getMargin() == null ? 0 : sequence.getMargin());
            stmt.setString(paramIdx++, sequence.getMarket().name());
            stmt.setString(paramIdx++, sequence.getSymbol());
            stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
    }

}
