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
import aibroker.model.QuotesDb;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
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

public class SqlDb extends QuotesDb {

    private static final Logger logger = LoggerFactory.getLogger(SqlDb.class);

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Connection    conn;

    public SqlDb(final File dbFile) throws SQLException {
        super(dbFile);
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
        new InitializeDatabase(conn).init();
        readSequences();
    }

    @Override
    public SqlSeq add(final SeqDesc sb) {
        logger.debug(sb.toString());
        try {
            final SqlSeq sequence = new SqlSeq(this, sb);
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
    public SqlSeq getSequence(final SeqSel selector) {
        SqlSeq sequence = null;
        if (selector.isJoinSettlements()) {
            final VirtualSqlSeq vSequence = new VirtualSqlSeq(this, selector.toDescriptor());
            vSequence.setJoinSettlements(true);
            sequence = vSequence;
        } else {
            final List<? extends Seq> sqlSequences = getSequences(selector);
            if (!sqlSequences.isEmpty()) {
                final SqlSeq sqlSequence = (SqlSeq) sqlSequences.get(0);
                if (sqlSequence.getSampling() == selector.getSampling()) {
                    sequence = sqlSequence;
                }
                else {
                    final VirtualSqlSeq vSequence = sqlSequence.cloneVirtual();
                    vSequence.setSampling(selector.getSampling());
                    vSequence.add(sqlSequence);
                    sequence = vSequence;
                }
            }
        }
        return sequence;
    }

    private void readSequences() throws SQLException {
        for (final SeqDesc sb : new ReadSequences(conn).readSequences(new SeqSel())) {
            final SqlSeq sequence = new SqlSeq(this, sb);
            sequences.put(sequence.getTableId(), sequence);
        }
    }

    @Override
    protected List<? extends Seq> getSequences(final SeqSel selector) {
        final List<Seq> sequences = new ArrayList<Seq>();
        try {
            final SeqSel selectorClone = selector.clone();
            if (selector.isJoinSettlements()) {
                selectorClone.setSampling(Sampling.SECOND);
                final List<Moment> settlements = new ReadSettlements(getConnection()).readSettlements(selectorClone);
                selectorClone.setJoinSettlements(false);
                final ReadSequences sequenceReader = new ReadSequences(getConnection());
                for (final Moment settlement : settlements) {
                    selectorClone.setSettlement(settlement);
                    final SeqDesc builder = sequenceReader.readSequences(selectorClone).get(0);
                    final SqlSeq tempSequence = getSequence(builder.toSelector());
                    sequences.add(tempSequence);
                }
            } else {
                selectorClone.setSampling(null);
                final List<SeqDesc> builders = new ReadSequences(conn).readSequences(selectorClone);
                Collections.sort(builders, new Comparator<SeqDesc>() {

                    @Override
                    public int compare(final SeqDesc o1, final SeqDesc o2) {
                        return o1.getSampling().compareTo(o2.getSampling());
                    }

                });
                if (selector.getSampling() == null) {
                    for (final SeqDesc sb : builders) {
                        final SqlSeq sequence = (SqlSeq) getSequence(sb.getTableId());
                        sequences.add(sequence);
                    }
                } else {
                    for (final SeqDesc sb : builders) {
                        if (selector.getSampling() == sb.getSampling()) {
                            final SqlSeq sequence = (SqlSeq) getSequence(sb.getTableId());
                            sequences.add(sequence);
                        }
                    }
                    if (sequences.size() == 0 && builders.size() > 0) {
                        final SqlSeq sequence = (SqlSeq) getSequence(builders.get(0).getTableId());
                        sequences.add(sequence);
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
    protected Quotes readQuotes(final Seq sequence) {
        try {
            if (sequence instanceof VirtualSqlSeq) {
                final VirtualSqlSeq vSequence = (VirtualSqlSeq) sequence;
                if (vSequence.getBaseSequences() == null) {
                    final SeqSel selector = vSequence.toSelector();
                    final List<SqlSeq> baseSequences = (List<SqlSeq>) getSequences(selector);
                    vSequence.setBaseSequences(baseSequences);
                }
                Quotes vQuotes = new Quotes();
                if (vSequence.getBaseSequences().size() > 0) {
                    if (vSequence.isJoinSettlements()) {
                        for (int i = 0; i < vSequence.getBaseSequences().size(); ++i) {
                            final SqlSeq baseSequence = vSequence.getBaseSequences().get(i);
                            System.out.print("Joining " + baseSequence.getName() + " ... ");
                            final Quotes tempQuotes = MaintainQuotes.getInstance(getConnection(), baseSequence).readAllQuotes();
                            vQuotes = MergeUtil.mergeQuotes(vQuotes, tempQuotes);
                            System.out.printf("%6.2f%% done\n", (i + 1.0) * 100 / vSequence.getBaseSequences().size());
                        }
                        vQuotes = SamplingUtil.resample(vQuotes, vSequence.getBaseSequences().get(0).getSampling(), vSequence.getSampling());
                    } else {
                        final Quotes tempQuotes = vSequence.getBaseSequences().get(0).getQuotes();
                        vQuotes = SamplingUtil.resample(tempQuotes, vSequence.getBaseSequences().get(0).getSampling(), vSequence.getSampling());
                    }
                }
                return vQuotes;
            } else {
                return MaintainQuotes.getInstance(conn, (SqlSeq) sequence).readAllQuotes();
            }
        } catch (final SQLException e) {
            throw new BrokerException(e);
        }
    }

    void addQuotes(final SqlSeq sequence, final List<Ohlc> quotes) throws SQLException {
        if (sequence instanceof VirtualSqlSeq) { throw new UnsupportedOperationException("quotes for virtual sequences are readonly"); }
        MaintainQuotes.getInstance(conn, sequence).insertAllQuotes(quotes);
        sequence.setQuotes(null);
    }

    void deleteQuotesFor(final SqlSeq sequence) throws SQLException {
        if (sequence instanceof VirtualSqlSeq) { throw new UnsupportedOperationException("quotes for virtual sequences are readonly"); }
        MaintainQuotes.getInstance(conn, sequence).deleteAllQuotes();
        sequence.setQuotes(null);
    }

    void deleteQuotesFor(final SqlSeq sequence, final Moment date) throws SQLException {
        if (sequence instanceof VirtualSqlSeq) { throw new UnsupportedOperationException("quotes for virtual sequences are readonly"); }
        MaintainQuotes.getInstance(conn, sequence).deleteQuotesFor(date);
        sequence.setQuotes(null);
    }

    void updateQuotes(final SqlSeq sequence) throws SQLException {
        if (sequence instanceof VirtualSqlSeq) { throw new UnsupportedOperationException("quotes for virtual sequences are readonly"); }
        MaintainQuotes.getInstance(conn, sequence).deleteAllQuotes();
        MaintainQuotes.getInstance(conn, sequence).insertAllQuotes(sequence.getQuotes());
    }

}
