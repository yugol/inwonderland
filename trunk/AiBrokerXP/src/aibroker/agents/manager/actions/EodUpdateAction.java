package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.agents.manager.QuotesManager;

@SuppressWarnings("serial")
public class EodUpdateAction extends UpdateAllQuotesAction {

    private static final Logger consoleLogger = LoggerFactory.getLogger(EodUpdateAction.class);

    public EodUpdateAction(final QuotesManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        consoleLogger.info("Updating EOD quotes...");
        super.actionPerformed(e);

    }

    @Override
    public void onUpdateCompleted() {
        dialog.setVisible(false);
        final BackupDatabaseAction bkpAction = new BackupDatabaseAction(view);
        bkpAction.setUseGui(false);
        consoleLogger.info("Backing up quotes database to " + bkpAction.getDefaultBackupFile() + "...");
        bkpAction.actionPerformed(null);
        consoleLogger.info("EOD update completed");
        System.exit(0);
    }

}
