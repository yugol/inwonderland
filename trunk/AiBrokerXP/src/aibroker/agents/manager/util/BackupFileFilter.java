package aibroker.agents.manager.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import aibroker.Context;
import aibroker.util.FileUtil;

public class BackupFileFilter extends FileFilter {

    @Override
    public boolean accept(final File f) {
        final String extension = FileUtil.getExtension(f.getAbsolutePath());
        return FileUtil.ZIP_EXTENSION.equalsIgnoreCase(extension);
    }

    @Override
    public String getDescription() {
        return Context.APPLICATION_NAME + " quotes database backup files (*." + FileUtil.ZIP_EXTENSION + ")";
    }

}
