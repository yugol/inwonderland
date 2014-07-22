package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.util.QuotesDatabaseFileFilter;
import aibroker.model.drivers.sql.SqlDatabase;
import aibroker.util.BrokerException;
import aibroker.util.FileUtil;

@SuppressWarnings("serial")
public class OpenDatabaseAction extends AbstractAction {

    private final QuotesManager view;

    public OpenDatabaseAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Open...");
        putValue(SHORT_DESCRIPTION, "Open a " + Context.APPLICATION_NAME + " quotes database");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final QuotesDatabaseFileFilter fileFilter = new QuotesDatabaseFileFilter();
        final JFileChooser dbChooser = new JFileChooser(Context.getQuotesFolder());
        dbChooser.setFileFilter(fileFilter);
        final int rVal = dbChooser.showOpenDialog(view.frmManager);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            final File dbFile = dbChooser.getSelectedFile();
            if (fileFilter.accept(dbFile)) {
                try {
                    final SqlDatabase qdb = new SqlDatabase(dbFile);
                    view.setDatabase(qdb);
                } catch (final SQLException e1) {
                    BrokerException.reportUi(e1);
                }
            } else {
                BrokerException.reportUi(new BrokerException("Database file must be of type *." + FileUtil.QDB_EXTENSION));
            }
        }
    }

}
