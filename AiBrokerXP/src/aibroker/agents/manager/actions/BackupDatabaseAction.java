package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.util.BackupFileFilter;
import aibroker.util.BrokerException;
import aibroker.util.FileUtil;
import aibroker.util.Moment;

@SuppressWarnings("serial")
public class BackupDatabaseAction extends AbstractAction {

    private final QuotesManager view;

    public BackupDatabaseAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Backup...");
        putValue(SHORT_DESCRIPTION, "Backup database in a zip file with current timestamp");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final File dbFile = view.getDatabase().getDbFile();
        final File dbFolder = dbFile.getParentFile();
        final StringBuilder zipFileName = new StringBuilder(FileUtil.getFileNameWithoutExtension(dbFile));
        zipFileName.append("=");
        zipFileName.append(Moment.getNow().toIsoDate());
        zipFileName.append("=");
        zipFileName.append(Moment.getNow().toCompactIsoTime());
        zipFileName.append(".");
        zipFileName.append(FileUtil.ZIP_EXTENSION);
        File backupFile = new File(dbFolder, zipFileName.toString());

        final BackupFileFilter fileFilter = new BackupFileFilter();
        final JFileChooser zipChooser = new JFileChooser(dbFolder);
        zipChooser.setSelectedFile(backupFile);
        zipChooser.setFileFilter(fileFilter);
        final int rVal = zipChooser.showSaveDialog(view.frmManager);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            backupFile = zipChooser.getSelectedFile();
            if (fileFilter.accept(backupFile)) {
                try {
                    FileUtil.zipFile(dbFile, backupFile);
                } catch (final IOException e1) {
                    BrokerException.reportUi(e1);
                }
            } else {
                BrokerException.reportUi(new BrokerException("Backup file must be of type *." + FileUtil.ZIP_EXTENSION));
            }
        }

    }

}
