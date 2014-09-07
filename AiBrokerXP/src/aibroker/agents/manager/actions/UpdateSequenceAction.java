package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import aibroker.agents.manager.EodQuotesUpdateDialog;
import aibroker.agents.manager.EodQuotesUpdateDialog.EodQuotesUpdateListener;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class UpdateSequenceAction extends BasicAction implements EodQuotesUpdateListener {

    protected final EodQuotesUpdateDialog dialog = new EodQuotesUpdateDialog(this);

    public UpdateSequenceAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Update...");
        putValue(SHORT_DESCRIPTION, "Download quotes up to a day before today");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        dialog.setLocationRelativeTo(view.frmManager);
        dialog.setVisible(true);
        dialog.update(view.getSequence());
    }

    @Override
    public void onUpdateCompleted() {
        view.setSequence(null, null);
    }

}
