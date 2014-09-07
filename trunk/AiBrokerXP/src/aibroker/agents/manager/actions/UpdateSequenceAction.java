package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import aibroker.agents.manager.EodQuotesUpdateDialog;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class UpdateSequenceAction extends BasicAction {

    public UpdateSequenceAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Update...");
        putValue(SHORT_DESCRIPTION, "Download quotes up to a day before today");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final EodQuotesUpdateDialog dialog = new EodQuotesUpdateDialog();
        dialog.setVisible(true);
        dialog.update(view.getSequence());
        view.setSequence(null, null);
    }

}
