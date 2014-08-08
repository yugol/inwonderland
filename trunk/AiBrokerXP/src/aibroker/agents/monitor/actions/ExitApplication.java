package aibroker.agents.monitor.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import aibroker.Context;
import aibroker.agents.ExitTrap;
import aibroker.agents.monitor.Controller;
import aibroker.model.Seq;

@SuppressWarnings("serial")
public class ExitApplication extends AbstractAction {

    private final Controller controller;

    public ExitApplication(final Controller controller) {
        this.controller = controller;
        putValue(NAME, "Exit");
        putValue(SHORT_DESCRIPTION, "Quits the application");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ExitTrap.enableSystemExitCall();
        Context.setMonitorWindowBounds(controller.getMainFrame().getBounds());
        Context.setMonitorLastDatabase(controller.getDbId());
        if (controller.getDbId() != null) {
            final Seq sequence = controller.getSequence();
            if (sequence != null) {
                Context.setMonitorLastName(sequence.getName());
            } else {
                Context.setMonitorLastName(null);
            }
        }
        System.out.println("Exiting " + Context.APPLICATION_NAME);
        System.exit(0);
    }

}