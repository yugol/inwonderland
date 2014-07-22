package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.BrokerException;

@SuppressWarnings("serial")
public class DeleteQuotesAction extends AbstractAction {

    private final QuotesManager view;

    public DeleteQuotesAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete all quotes from sequence");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final SqlSequence sequence = view.getSequence();
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