package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class CloseApplicationAction extends AbstractAction {

    private final QuotesManager view;

    public CloseApplicationAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Close");
        putValue(SHORT_DESCRIPTION, "Close application window");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        saveBounds();
        view.frmManager.dispose();
    }

    public void saveBounds() {
        Context.setManagerWindowBounds(view.frmManager.getBounds());
    }

}
