package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.export.XlsxAnalysis;
import aibroker.util.OsUtil;

@SuppressWarnings("serial")
public class ExportAction extends AbstractAction {

    private final QuotesManager view;

    public ExportAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Export...");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final SqlSequence seq = view.getSequence();
        File file = new File(Context.getExportFolder(), seq.getSymbol() + ".xlsx");
        final JFileChooser dbChooser = new JFileChooser(Context.getExportFolder());
        dbChooser.setSelectedFile(file);
        final int rVal = dbChooser.showSaveDialog(view.frmManager);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            try {
                final XlsxAnalysis exporter = new XlsxAnalysis(seq);
                file = dbChooser.getSelectedFile();
                exporter.save(file);
                OsUtil.openFile(file);
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
