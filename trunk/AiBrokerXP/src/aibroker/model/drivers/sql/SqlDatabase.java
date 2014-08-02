package aibroker.model.drivers.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.QuotesDatabase;
import aibroker.model.Sequence;
import aibroker.model.SequenceBuilder;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.queries.CountRowsQuery;
import aibroker.model.drivers.sql.queries.DropDatabaseContent;
import aibroker.model.drivers.sql.queries.InitializeDatabase;
import aibroker.model.drivers.sql.queries.MaintainQuotes;
import aibroker.model.drivers.sql.queries.ReadSequences;
import aibroker.model.drivers.sql.queries.ReadSettlements;
import aibroker.model.drivers.sql.queries.SaveSequence;
import aibroker.util.BrokerException;
import aibroker.util.MergeUtil;
import aibroker.util.Moment;
import aibroker.util.SamplingUtil;

public class SqlDatabase extends QuotesDatabase {

    private static final Logger logger = LoggerFactory.getLogger(SqlDatabase.class);

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Connection    conn;

    public SqlDatabase(final File dbFile) throws SQLException {
        super(dbFile);
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
        new InitializeDatabase(conn).init();
        readSequences();
    }

    @Override
    public SqlSequence add(final SequenceBuilder sb) {
        logger.debug(sb.toString());
        try {
            final SqlSequence sequence = new SqlSequence(this, sb);
            final String tableId = new SaveSequence(conn).add(sequence);
            sequence.setTableId(tableId);
            sequences.put(sequence.getTableId(), sequence);
            return sequence;
        } catch (final SQLException e) {
            throw new BrokerException(sb.toString(), e);
        }
    }

    public void close() throws SQLException {
        conn.close();
    }

    public int countRows() throws SQLException {
        return new CountRowsQuery(conn).callCount();
    }

    @Override
    public void drop() throws Exception {
        new DropDatabaseContent(conn).dropContent();
        sequences.clear();
        new InitializeDatabase(conn).init();
    }

    public Connection getConnection() {
        return conn;
    }

    public File getDbFile() {
        return dbLocation;
    }

    @Override
    public Sequence getSequence(final SequenceSelector selector) {
        if (selector.isJoinSettlements()) {
            final VirtualSqlSequence vSequence = new VirtualSqlSequence(this, selector.toBuilder());
            vSequence.setJoinSettlements(true);
            return vSequence;
        }
        return super.getSequence(selector);
    }

    private void readSequences() throws SQLException {
        for (final SequenceBuilder sb : new ReadSequences(conn).readSequences(new SequenceSelector())) {
            final SqlSequence sequence = new SqlSequence(this, sb);
            sequences.put(sequence.getTableId(), sequence);
        }
    }

    @Override
    protected List<? extends Sequence> getSequences(final SequenceSelector selector) {
        final List<Sequence> sequences = new ArrayList<Sequence>();
        try {
            final SequenceSelector selectorClone = selector.clone();
            if (selector.isJoinSettlements()) {
                selectorClone.setSampling(Sampling.SECOND);
                final List<Moment> settlements = new ReadSettlements(getConnection()).readSettlements(selectorClone);
                selectorClone.setJoinSettlements(false);
                final ReadSequences sequenceReader = new ReadSequences(getConnection());
                for (final Moment settlement : settlements) {
                    selectorClone.setSettlement(settlement);
                    final SequenceBuilder builder = sequenceReader.readSequences(selectorClone).get(0);
                    final SqlSequence tempSequence = (SqlSequence) getSequence(builder.toSelector());
                    sequences.add(tempSequence);
                }
            } else {
                selectorClone.setSampling(null);
                final List<SequenceBuilder> builders = new ReadSequences(conn).readSequences(selectorClone);
                Collections.sort(builders, new Comparator<SequenceBuilder>() {

                    @Override
                    public int compare(final SequenceBuilder o1, final SequenceBuilder o2) {
                        return o1.sampling().compareTo(o2.sampling());
                    }

                });
                if (selector.getSampling() == null) {
                    for (final SequenceBuilder sb : builders) {
                        final SqlSequence sequence = (SqlSequence) getSequence(sb.tableId());
                        sequences.add(sequence);
                    }
                } else {
                    for (final SequenceBuilder sb : builders) {
                        if (selector.getSampling() == sb.sampling()) {
                            final SqlSequence sequence = (SqlSequence) getSequence(sb.tableId());
                            sequences.add(sequence);
                        }
                    }
                    if (sequences.size() == 0 && builders.size() > 0) {
                        final SqlSequence sequence = (SqlSequence) getSequence(builders.get(0).tableId());
                        if (sequence.getSampling() == selector.getSampling()) {
                            sequences.add(sequence);
                        } else {
                            final VirtualSqlSequence vSequence = sequence.cloneVirtual();
                            vSequence.setSampling(selector.getSampling());
                            sequences.add(vSequence);
                        }
                    }
                }
            }
        } catch (final Exception e) {
            throw new BrokerException(e);
        }
        return sequences;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Quotes readQuotes(final Sequence sequence) {
        try {
            if (sequence instanceof VirtualSqlSequence) {
                final VirtualSqlSequence vSequence = (VirtualSqlSequence) sequence;
                final SequenceSelector selector = vSequence.toSelector();
                if (vSequence.isJoinSettlements()) {
                    final List<SqlSequence> baseSequences = (List<SqlSequence>) getSequences(selector);
                    Quotes joinedQuotes = new Quotes();
                    if (baseSequences.size() > 0) {
                        for (int i = 0; i < baseSequences.size(); ++i) {
                            final SqlSequence baseSequence = baseSequences.get(i);
                            System.out.print("Joining " + baseSequence.getName() + " ... ");
                            final Quotes tempQuotes = MaintainQuotes.getInstance(getConnection(), baseSequence).readAllQuotes();
                            joinedQuotes = MergeUtil.mergeQuotes(joinedQuotes, tempQuotes);
                            System.out.printf("%6.2f%% done\n", (i + 1.0) * 100 / baseSequences.size());
                        }
                        joinedQuotes = SamplingUtil.resample(joinedQuotes, baseSequences.get(0).getSampling(), vSequence.getSampling());
                    }
                    return joinedQuotes;
                } else {
                    final SqlSequence baseSequence = (SqlSequence) getSequence(selector);
                    final Quotes baseQuotes = baseSequence.getQuotes();
                    final Quotes vQuotes = SamplingUtil.resample(baseQuotes, baseSequence.getSampling(), vSequence.getSampling());
                    return vQuotes;
                }
            } else {
                return MaintainQuotes.getInstance(conn, (SqlSequence) sequence).readAllQuotes();
            }
        } catch (final SQLException e) {
            throw new BrokerException(e);
        }
    }

    void addQuotes(final SqlSequence sequence, final List<Ohlc> quotes) throws SQLException {
        MaintainQuotes.getInstance(conn, sequence).insertAllQuotes(quotes);
        sequence.setQuotes(null);
    }

    void deleteQuotesFor(final SqlSequence sequence) throws SQLException {
        MaintainQuotes.getInstance(conn, sequence).deleteAllQuotes();
        sequence.setQuotes(null);
    }

    void deleteQuotesFor(final SqlSequence sequence, final Moment date) throws SQLException {
        MaintainQuotes.getInstance(conn, sequence).deleteQuotesFor(date);
        sequence.setQuotes(null);
    }

    void updateQuotes(final SqlSequence sequence) throws SQLException {
        MaintainQuotes.getInstance(conn, sequence).deleteAllQuotes();
        MaintainQuotes.getInstance(conn, sequence).insertAllQuotes(sequence.getQuotes());
    }

}
