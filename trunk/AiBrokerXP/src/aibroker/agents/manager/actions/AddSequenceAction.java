package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.tree.TreePath;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class AddSequenceAction extends AbstractAction {

    private final QuotesManager view;

    public AddSequenceAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Configure the UI for adding a new sequence");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final TreePath rootPath = new TreePath(view.quotesTree.getModel().getRoot());
        view.quotesTree.setSelectionPath(rootPath);
        view.setSequenceControlsEnabled(true);
    }

}
