package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import aibroker.agents.manager.EodQuotesUpdateDialog;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class UpdateAllQuotesAction extends AbstractAction {

    private final QuotesManager view;

    public UpdateAllQuotesAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Update All Sequences...");
        putValue(SHORT_DESCRIPTION, "Download all new quotes");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final EodQuotesUpdateDialog dialog = new EodQuotesUpdateDialog();
        dialog.setLocationRelativeTo(view.frmManager);
        dialog.setVisible(true);
        dialog.update(view.getDatabase());
        view.setSequence(null, null);
    }

}
