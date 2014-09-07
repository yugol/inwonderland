package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.util.BrokerException;

@SuppressWarnings("serial")
public class DropDatabaseContentAction extends BasicAction {

    public DropDatabaseContentAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Drop Content");
        putValue(SHORT_DESCRIPTION, "Deletes all tables in the database");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(view.frmManager, "Are you sure you want to delete the content of the database?", Context.APPLICATION_NAME, JOptionPane.YES_NO_OPTION)) {
            try {
                view.getDatabase().drop();
                view.setDatabase(view.getDatabase());
            } catch (final Exception e1) {
                BrokerException.reportUi(e1);
            }
        }
    }

}
