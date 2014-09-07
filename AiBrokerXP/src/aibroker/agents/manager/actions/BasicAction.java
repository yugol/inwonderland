package aibroker.agents.manager.actions;

import javax.swing.AbstractAction;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public abstract class BasicAction extends AbstractAction {

    protected final QuotesManager view;

    public BasicAction(final QuotesManager view) {
        this.view = view;
    }

}
