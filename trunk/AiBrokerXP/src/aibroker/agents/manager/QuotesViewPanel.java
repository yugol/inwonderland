package aibroker.agents.manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import aibroker.model.Quotes;
import aibroker.model.drivers.csv.CsvWriter;

@SuppressWarnings("serial")
public class QuotesViewPanel extends JPanel {

    private final JTextArea            txtrHeader;
    private final JTextArea            txtrData;
    private final JPanel               pnlControls;
    private final JButton              btnMerge;

    private final List<ActionListener> mergeListeners = new ArrayList<ActionListener>();

    public QuotesViewPanel() {

        setBorder(null);
        setLayout(new BorderLayout(0, 0));

        final JScrollPane spHeader = new JScrollPane();
        spHeader.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spHeader.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(spHeader, BorderLayout.NORTH);

        txtrHeader = new JTextArea();
        txtrHeader.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtrHeader.setText(CsvWriter.getQuotesHeader());
        txtrHeader.setEditable(false);
        txtrHeader.setTabSize(0);
        txtrHeader.setRows(1);
        spHeader.setViewportView(txtrHeader);

        pnlControls = new JPanel();
        pnlControls.setBorder(new EmptyBorder(5, 0, 5, 0));
        add(pnlControls, BorderLayout.SOUTH);
        final GridBagLayout gbl_pnlControls = new GridBagLayout();
        gbl_pnlControls.columnWidths = new int[] { 0, 0, 0 };
        gbl_pnlControls.rowHeights = new int[] { 0, 0 };
        gbl_pnlControls.columnWeights = new double[] { 1.0, 0.0, 1.0 };
        gbl_pnlControls.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        pnlControls.setLayout(gbl_pnlControls);

        final Component horizontalGlue = Box.createHorizontalGlue();
        final GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
        gbc_horizontalGlue.insets = new Insets(0, 0, 0, 5);
        gbc_horizontalGlue.gridx = 0;
        gbc_horizontalGlue.gridy = 0;
        pnlControls.add(horizontalGlue, gbc_horizontalGlue);

        btnMerge = new JButton("Merge");
        btnMerge.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                for (final ActionListener listener : mergeListeners) {
                    listener.actionPerformed(e);
                }
            }

        });
        final GridBagConstraints gbc_btnMerge = new GridBagConstraints();
        gbc_btnMerge.insets = new Insets(0, 0, 0, 5);
        gbc_btnMerge.gridx = 1;
        gbc_btnMerge.gridy = 0;
        pnlControls.add(btnMerge, gbc_btnMerge);

        final JScrollPane spData = new JScrollPane();
        add(spData, BorderLayout.CENTER);

        txtrData = new JTextArea();
        txtrData.setEditable(false);
        txtrData.setFont(new Font("Courier New", Font.PLAIN, 12));
        spData.setViewportView(txtrData);

    }

    public void addMergeListener(final ActionListener listener) {
        mergeListeners.add(listener);
    }

    public String getQuotesCsv() {
        return txtrData.getText();
    }

    public void setControlsVisible(final boolean flag) {
        pnlControls.setVisible(flag);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        txtrHeader.setEnabled(enabled);
        txtrData.setEnabled(enabled);
        btnMerge.setEnabled(enabled);
    }

    public void setQuotes(final Quotes quotes) {
        if (quotes == null) {
            txtrData.setText("");
        } else {
            final StringBuffer buffer = new StringBuffer();
            CsvWriter.writeQuotesData(buffer, quotes);
            txtrData.setText(buffer.toString());
            txtrData.select(0, 0);
        }
    }

}
