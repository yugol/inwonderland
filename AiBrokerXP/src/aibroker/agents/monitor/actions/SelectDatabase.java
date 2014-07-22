package aibroker.agents.monitor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import aibroker.agents.monitor.Controller;
import aibroker.util.convenience.Databases;

@SuppressWarnings("serial")
public class SelectDatabase extends AbstractAction {

    private final JPopupMenu popupMenu;

    public SelectDatabase(final Controller controller) {
        popupMenu = new JPopupMenu();
        for (final Databases db : Databases.values()) {
            final JMenuItem menuItem = new JMenuItem(db.name);
            menuItem.putClientProperty("tag", db);
            menuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    final Object tag = ((JMenuItem) e.getSource()).getClientProperty("tag");
                    controller.setDatabase((Databases) tag);
                }

            });
            popupMenu.add(menuItem);
        }
        putValue(NAME, null);
        putValue(SHORT_DESCRIPTION, "Select database");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JButton button = (JButton) e.getSource();
        popupMenu.show(button, button.getLocation().x, button.getLocation().y + button.getSize().height);
    }

}