package aibroker.agents.monitor.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import aibroker.agents.monitor.Controller;
import aibroker.agents.monitor.SequenceSelectionDialog;
import aibroker.model.QuotesDb;
import aibroker.model.Sequence;

@SuppressWarnings("serial")
public class SelectSequence extends AbstractAction {

    private final Controller controller;

    public SelectSequence(final Controller controller) {
        this.controller = controller;
        putValue(NAME, null);
        putValue(SHORT_DESCRIPTION, "Select sequence");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final QuotesDb qDb = controller.getQDb();
        if (qDb == null) {
            JOptionPane.showMessageDialog(controller.getMainFrame(), "No quotes datadase was selected");
        } else {
            int i = 0;
            final String[] sequences = new String[qDb.getSequenceCount()];
            for (final Sequence sequence : qDb) {
                sequences[i++] = sequence.getName();
            }
            Arrays.sort(sequences);

            final SequenceSelectionDialog dlg = new SequenceSelectionDialog();
            dlg.setTitle(controller.getDbId().name + " - Sequences");
            dlg.setValues(sequences);
            dlg.setVisible(true);

            final String name = dlg.getSelectedName();
            if (name != null) {
                controller.setSequence(name);
            }
        }
    }

}