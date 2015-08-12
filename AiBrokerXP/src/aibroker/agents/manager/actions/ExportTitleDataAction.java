package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import aibroker.agents.manager.QuotesManager;
import aibroker.model.drivers.excel.ExportExcelTitleData;
import aibroker.model.drivers.sql.SqlSeq;

@SuppressWarnings("serial")
public class ExportTitleDataAction extends BasicAction {

    public ExportTitleDataAction(final QuotesManager view) {
        super(view);
        putValue(NAME, "Export...");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final SqlSeq seq = view.getSequence();

        final JFileChooser fileChooser = new JFileChooser();
        final File file = new File(fileChooser.getCurrentDirectory(), seq.getSymbol() + ".xlsx");
        fileChooser.setSelectedFile(file);

        final int result = fileChooser.showSaveDialog(view.frmManager);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                final File selection = fileChooser.getSelectedFile();
                final ExportExcelTitleData exporter = new ExportExcelTitleData(seq);
                exporter.build();
                exporter.saveTo(selection);
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
