package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.util.BackupFileFilter;
import aibroker.util.BrokerException;
import aibroker.util.FileUtil;
import aibroker.util.Moment;

@SuppressWarnings("serial")
public class BackupDatabaseAction extends BasicAction {

    private boolean useGui = true;

    public BackupDatabaseAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Backup...");
        putValue(SHORT_DESCRIPTION, "Backup database in a zip file with current timestamp");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        File backupFile = getDefaultBackupFile();
        if (isUseGui()) {
            final BackupFileFilter fileFilter = new BackupFileFilter();
            final JFileChooser zipChooser = new JFileChooser(view.getDatabase().getDbFile().getParentFile());
            zipChooser.setSelectedFile(backupFile);
            zipChooser.setFileFilter(fileFilter);
            final int rVal = zipChooser.showSaveDialog(view.frmManager);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                backupFile = zipChooser.getSelectedFile();
                if (!fileFilter.accept(backupFile)) {
                    backupFile = null;
                    BrokerException.reportUi(new BrokerException("Backup file must be of type *." + FileUtil.ZIP_EXTENSION));
                }
            } else {
                backupFile = null;
            }
        }

        if (backupFile != null) {
            try {
                FileUtil.zipFile(view.getDatabase().getDbFile(), backupFile);
            } catch (final IOException e1) {
                BrokerException.reportUi(e1);
            }
        }
    }

    public File getDefaultBackupFile() {
        final File dbFile = view.getDatabase().getDbFile();
        final File dbFolder = dbFile.getParentFile();
        final StringBuilder zipFileName = new StringBuilder(FileUtil.getFileNameWithoutExtension(dbFile));
        zipFileName.append("=");
        zipFileName.append(Moment.getNow().toIsoDate());
        zipFileName.append("=");
        zipFileName.append(Moment.getNow().toCompactIsoTime());
        zipFileName.append(".");
        zipFileName.append(FileUtil.ZIP_EXTENSION);
        return new File(dbFolder, zipFileName.toString());
    }

    public boolean isUseGui() {
        return useGui;
    }

    public void setUseGui(final boolean useGui) {
        this.useGui = useGui;
    }

}
