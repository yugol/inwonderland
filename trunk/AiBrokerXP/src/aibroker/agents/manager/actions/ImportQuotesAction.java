package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.QuotesViewDialog;
import aibroker.model.drivers.csv.CsvReader;
import aibroker.util.FileUtil;

@SuppressWarnings("serial")
public class ImportQuotesAction extends AbstractAction {

    private final QuotesManager view;

    public ImportQuotesAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Import...");
        putValue(SHORT_DESCRIPTION, "Import quotes from CSV file");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JFileChooser dbChooser = new JFileChooser(Context.getQuotesFolder());
        final int rVal = dbChooser.showOpenDialog(view.frmManager);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            final File importFile = dbChooser.getSelectedFile();
            if (FileUtil.CSV_EXTENSION.equalsIgnoreCase(FileUtil.getExtension(importFile))) {
                final QuotesViewDialog dialog = new QuotesViewDialog();
                dialog.setFile(importFile);
                dialog.setSequence(view.getSequence());
                dialog.setQuotes(CsvReader.readQuotes(importFile));
                dialog.setVisible(true);
                view.setSequence(null, null);
            }
        }
    }

}