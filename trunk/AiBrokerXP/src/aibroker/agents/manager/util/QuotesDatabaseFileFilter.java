package aibroker.agents.manager.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import aibroker.Context;
import aibroker.util.FileUtil;

public class QuotesDatabaseFileFilter extends FileFilter {

    @Override
    public boolean accept(final File f) {
        final String extension = FileUtil.getExtension(f.getAbsolutePath());
        return FileUtil.QDB_EXTENSION.equalsIgnoreCase(extension);
    }

    @Override
    public String getDescription() {
        return Context.APPLICATION_NAME + " quotes database files (*." + FileUtil.QDB_EXTENSION + ")";
    }

}
