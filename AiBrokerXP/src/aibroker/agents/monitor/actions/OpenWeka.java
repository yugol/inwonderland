package aibroker.agents.monitor.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import weka.gui.GUIChooser;
import aibroker.Context;
import aibroker.agents.ExitTrap;

@SuppressWarnings("serial")
public class OpenWeka extends AbstractAction {

    public OpenWeka() {
        putValue(NAME, "Weka");
        putValue(SHORT_DESCRIPTION, "Open WEKA Environment");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        System.setProperty("WEKA_HOME", Context.getWekaFolder());
        ExitTrap.forbidSystemExitCall();
        GUIChooser.main(null);
    }

}