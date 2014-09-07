package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.BrokerException;

@SuppressWarnings("serial")
public class DeleteQuotesAction extends BasicAction {

    public DeleteQuotesAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete all quotes from sequence");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final SqlSeq sequence = view.getSequence();
        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(view.frmManager,
                "Are you sure you want to delete quotes for " + sequence.getName(),
                Context.APPLICATION_NAME + " - Confirn Delete Quotes",
                JOptionPane.OK_CANCEL_OPTION)) {
            try {
                sequence.deleteQuotes();
                view.setSequence(null, null);
            } catch (final SQLException e1) {
                BrokerException.reportUi(e1);
            }
        }
    }

}
