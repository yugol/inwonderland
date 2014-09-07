package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import aibroker.agents.manager.EodQuotesUpdateDialog;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class UpdateAllQuotesAction extends BasicAction {

    public UpdateAllQuotesAction(final QuotesManager view) {
        super(view);
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
