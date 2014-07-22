package aibroker.agents.manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import aibroker.Context;
import aibroker.model.Quotes;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.BrokerException;

@SuppressWarnings("serial")
public class QuotesViewDialog extends JDialog {

    public static void main(final String[] args) {
        try {
            final QuotesViewDialog dialog = new QuotesViewDialog();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private QuotesViewPanel quotesView;
    private SqlSequence     sequence;
    private Quotes          quotes;

    public QuotesViewDialog() {
        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle(Context.APPLICATION_NAME);
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new BorderLayout());
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            quotesView = new QuotesViewPanel();
            quotesView.addMergeListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    try {
                        sequence.mergeQuotes(quotes);
                        QuotesViewDialog.this.dispose();
                    } catch (final SQLException e1) {
                        BrokerException.reportUi(e1);
                    }
                }

            });

            contentPanel.add(quotesView, BorderLayout.CENTER);
        }
    }

    public void setFile(final File file) {
        setTitle(Context.APPLICATION_NAME + " - Import Quotes - " + file.getName());
    }

    public void setQuotes(final Quotes quotes) {
        this.quotes = quotes;
        quotesView.setQuotes(quotes);
    }

    public void setSequence(final SqlSequence sequence) {
        this.sequence = sequence;
    }

}
