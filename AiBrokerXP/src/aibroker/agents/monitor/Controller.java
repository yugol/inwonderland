package aibroker.agents.monitor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.QuotesDb;
import aibroker.model.Seq;
import aibroker.model.SeqSel;
import aibroker.util.convenience.Databases;

public class Controller {

    private static final Logger  logger = LoggerFactory.getLogger(Controller.class);

    private final TradingMonitor monitor;
    private Databases            dbId;
    private QuotesDb       qDb;
    private Seq             sequence;

    public Controller(final TradingMonitor monitor) {
        this.monitor = monitor;
    }

    public Databases getDbId() {
        return dbId;
    }

    public JFrame getMainFrame() {
        return monitor.frmAibrokerXp;
    }

    public QuotesDb getQDb() {
        return qDb;
    }

    public Seq getSequence() {
        return sequence;
    }

    public DefaultOHLCDataset getSequenceChartData() {
        final Quotes quotes = sequence.getQuotes();
        final OHLCDataItem[] data = new OHLCDataItem[quotes.size()];
        for (int i = 0; i < data.length; ++i) {
            final Ohlc ohlc = quotes.get(i);
            final OHLCDataItem item = new OHLCDataItem(ohlc.moment.getTime(), ohlc.open, ohlc.high, ohlc.low, ohlc.close, ohlc.volume);
            data[i] = item;
        }
        return new DefaultOHLCDataset("symbolOhlc", data);
    }

    public void setDatabase(final Databases dbId) {
        if (dbId == null) {
            monitor.tbtnDatabase.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/database_error.png")));
            monitor.tbtnDatabase.setText(null);
            monitor.tbtnSequence.setEnabled(false);
            setSequence(null);
            this.dbId = dbId;
        } else if (dbId != this.dbId) {
            qDb = dbId.getInstance();
            monitor.tbtnDatabase.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/database.png")));
            monitor.tbtnDatabase.setText(dbId.name);
            monitor.tbtnSequence.setEnabled(true);
            setSequence(null);
            this.dbId = dbId;
            logger.debug("Database changed to {}", dbId.toString());
        }
    }

    public void setSequence(final String name) {
        if (name == null) {
            sequence = null;
            monitor.tbtnSequence.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/chart_curve_error.png")));
            monitor.tbtnSequence.setText(null);
            monitor.mainTabs.setEnabled(false);
            monitor.removeChartHistory();
        } else {
            sequence = qDb.getSequence(SeqSel.fromName(name));
            monitor.tbtnSequence.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/chart_curve.png")));
            monitor.tbtnSequence.setText(sequence.getName());
            monitor.mainTabs.setEnabled(true);
            monitor.drawChartHistory();
            logger.debug("Sequence changed to {}", sequence.getName());
        }
    }

}
