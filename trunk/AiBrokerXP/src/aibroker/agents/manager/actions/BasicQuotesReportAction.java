package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import aibroker.Context;
import aibroker.agents.manager.QuotesManager;
import aibroker.analysis.BasicQuotesReport;
import aibroker.util.OsUtil;

@SuppressWarnings("serial")
public class BasicQuotesReportAction extends AbstractAction {

    private final QuotesManager view;

    public BasicQuotesReportAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Basic Quotes Report");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        try {
            final BasicQuotesReport report = new BasicQuotesReport(view.getDatabase(), view.getSequence());
            File file = new File(Context.getExportFolder(), report.getNameHint());
            final JFileChooser dbChooser = new JFileChooser(Context.getExportFolder());
            dbChooser.setSelectedFile(file);
            if (dbChooser.showSaveDialog(view.frmManager) == JFileChooser.APPROVE_OPTION) {
                report.fill();
                file = dbChooser.getSelectedFile();
                report.save(file);
                OsUtil.openFile(file);
            }
        } catch (final IOException e1) {
            e1.printStackTrace();
        }
    }

}
