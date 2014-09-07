package aibroker.agents.manager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import aibroker.Context;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.cloud.SequenceUpdateListener;
import aibroker.model.cloud.SequenceUpdater;
import aibroker.model.domains.Feed;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.Moment;

@SuppressWarnings("serial")
public class EodQuotesUpdateDialog extends JDialog implements SequenceUpdateListener {

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        try {
            final EodQuotesUpdateDialog dialog = new EodQuotesUpdateDialog(null);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static interface EodQuotesUpdateListener {

        void onUpdateCompleted();

    }

    private final EodQuotesUpdateListener listener;
    private boolean                       cancel       = false;
    private boolean                       done         = false;

    private final JPanel                  contentPanel = new JPanel();
    private JLabel                        lblSequenceCount;
    private JProgressBar                  pbGlobal;
    private JLabel                        lblSequence;
    private JProgressBar                  pbSequence;
    private JTextArea                     txtLog;
    private JButton                       cancelButton;
    private JScrollPane                   scrollPane;

    /**
     * Create the dialog.
     */
    public EodQuotesUpdateDialog(final EodQuotesUpdateListener listener) {
        this.listener = listener;
        setType(Type.UTILITY);
        setResizable(false);
        setTitle(Context.APPLICATION_NAME + " - EOD Quotes Update Progress");
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        final GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);
        {
            lblSequenceCount = new JLabel("Updating N/A sequences...");
            final GridBagConstraints gbc_lblSequenceCount = new GridBagConstraints();
            gbc_lblSequenceCount.anchor = GridBagConstraints.WEST;
            gbc_lblSequenceCount.insets = new Insets(0, 0, 5, 0);
            gbc_lblSequenceCount.gridx = 0;
            gbc_lblSequenceCount.gridy = 0;
            contentPanel.add(lblSequenceCount, gbc_lblSequenceCount);
        }
        {
            pbGlobal = new JProgressBar();
            pbGlobal.setStringPainted(true);
            final GridBagConstraints gbc_bpGlobal = new GridBagConstraints();
            gbc_bpGlobal.fill = GridBagConstraints.HORIZONTAL;
            gbc_bpGlobal.insets = new Insets(0, 0, 5, 0);
            gbc_bpGlobal.gridx = 0;
            gbc_bpGlobal.gridy = 1;
            contentPanel.add(pbGlobal, gbc_bpGlobal);
        }
        {
            lblSequence = new JLabel("Updating N/A...");
            final GridBagConstraints gbc_lblSequence = new GridBagConstraints();
            gbc_lblSequence.insets = new Insets(0, 0, 5, 0);
            gbc_lblSequence.anchor = GridBagConstraints.WEST;
            gbc_lblSequence.gridx = 0;
            gbc_lblSequence.gridy = 2;
            contentPanel.add(lblSequence, gbc_lblSequence);
        }
        {
            pbSequence = new JProgressBar();
            pbSequence.setStringPainted(true);
            pbSequence.setEnabled(false);
            final GridBagConstraints gbc_bpSequence = new GridBagConstraints();
            gbc_bpSequence.insets = new Insets(0, 0, 5, 0);
            gbc_bpSequence.fill = GridBagConstraints.HORIZONTAL;
            gbc_bpSequence.gridx = 0;
            gbc_bpSequence.gridy = 3;
            contentPanel.add(pbSequence, gbc_bpSequence);
        }
        {
            final JLabel lblLog = new JLabel("Log:");
            final GridBagConstraints gbc_lblLog = new GridBagConstraints();
            gbc_lblLog.insets = new Insets(0, 0, 5, 0);
            gbc_lblLog.anchor = GridBagConstraints.WEST;
            gbc_lblLog.gridx = 0;
            gbc_lblLog.gridy = 4;
            contentPanel.add(lblLog, gbc_lblLog);
        }
        {
            scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 5;
            contentPanel.add(scrollPane, gbc_scrollPane);
            {
                txtLog = new JTextArea();
                scrollPane.setViewportView(txtLog);
                txtLog.setEditable(false);
            }
        }
        {
            final JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new EmptyBorder(0, 0, 5, 5));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (done) {
                            EodQuotesUpdateDialog.this.dispose();
                        }
                        cancel = true;
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    @Override
    public void onBeginDownloading(final String symbol, final String settlement, final Moment firstDate, final Moment lastDate) {
        final int workAmount = Moment.getDaysBetween(firstDate, lastDate) + 1;
        pbSequence.setMinimum(0);
        pbSequence.setMaximum(workAmount);
        pbSequence.setValue(0);
        final StringBuilder message = new StringBuilder("Downloading ");
        appendName(message, symbol, settlement);
        message.append(" [ ").append(firstDate.toIsoDate()).append(", ").append(lastDate.toIsoDate()).append(" ]\n");
        log(message.toString());
    }

    @Override
    public boolean onDownloaded(final String symbol, final String settlement) {
        pbSequence.setValue(pbSequence.getValue() + 1);
        return cancel;
    }

    @Override
    public void onDownloading(final String symbol, final String settlement, final Moment date) {
        final StringBuilder message = new StringBuilder("Downloading ");
        appendName(message, symbol, settlement);
        message.append(" quotes for ");
        message.append(date.toIsoDate());
        lblSequence.setText(message.toString());
    }

    @Override
    public void onEndDownloading(final String symbol, final String settlement) {
        pbGlobal.setValue(pbGlobal.getValue() + 1);
    }

    @Override
    public void onError(final String symbol, final String settlement, final Moment date, final Exception e) {
        final StringBuilder message = new StringBuilder(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(" ");
        appendName(message, symbol, settlement);
        message.append(" ").append(date.toIsoDate()).append("\n");
        log(message.toString());
    }

    public void update(final Iterable<Seq> sequences) {
        int updateCount = 0;
        final Iterator<Seq> it = sequences.iterator();
        while (it.hasNext()) {
            if (canUpdate((SqlSeq) it.next()) != null) {
                ++updateCount;
            }
        }
        if (updateCount > 0) {
            lblSequenceCount.setText("Updating " + updateCount + (updateCount == 1 ? " sequence" : " sequences"));
            pbGlobal.setMinimum(0);
            pbGlobal.setMaximum(updateCount);
            pbGlobal.setValue(0);

            new Thread() {

                @Override
                public void run() {
                    final Iterator<Seq> it = sequences.iterator();
                    while (it.hasNext()) {
                        final SqlSeq sequence = canUpdate((SqlSeq) it.next());
                        if (sequence != null) {
                            final SequenceUpdater updater = new SequenceUpdater(sequence);
                            updater.addUpdateListener(EodQuotesUpdateDialog.this);
                            updater.run();
                        }
                        if (cancel) {
                            break;
                        }
                    }
                    done = true;
                    cancelButton.setText("Done");
                    if (listener != null) {
                        listener.onUpdateCompleted();
                    }
                }

            }.start();
        }
    }

    public void update(final Seq sequence) {
        final Collection<Seq> sequences = new ArrayList<Seq>();
        sequences.add(sequence);
        update(sequences);
    }

    private void appendName(final StringBuilder message, final String symbol, final String settlement) {
        message.append(SeqDesc.getName(symbol, settlement));
    }

    private SqlSeq canUpdate(final SqlSeq sequence) {
        if (Feed.CACHE == sequence.getFeed()) { return sequence; }
        if (sequence instanceof SqlSeq) {
            final SqlSeq sqlSequence = sequence;
            if (sqlSequence.getUpdater() != null) {
                if (!sequence.isRegular()) {
                    final Moment settlement = sequence.getSettlement();
                    if (settlement.get(Calendar.YEAR) < Context.FUTURES_MASS_UPDATE_LAST_YEAR) { return null; }
                }
                return sqlSequence;
            }
        }
        return null;
    }

    private void log(final String message) {
        txtLog.append(message);
        final int length = txtLog.getText().length();
        txtLog.select(length, length);
    }

}
