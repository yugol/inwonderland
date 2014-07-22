package aibroker.agents.monitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import aibroker.Context;
import aibroker.agents.monitor.actions.ExitApplication;
import aibroker.agents.monitor.actions.OpenWeka;
import aibroker.agents.monitor.actions.SelectDatabase;
import aibroker.agents.monitor.actions.SelectSequence;
import aibroker.util.BrokerException;

public class TradingMonitor {

    public static void main(final String[] args) {
        Context.setupContext("Trading Monitor");
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {

                    final TradingMonitor window = new TradingMonitor();
                    window.frmAibrokerXp.setBounds(Context.getMonitorWindowBounds());
                    window.frmAibrokerXp.setVisible(true);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private final Controller controller = new Controller(this);
    JFrame                   frmAibrokerXp;
    JButton                  tbtnDatabase;
    JButton                  tbtnSequence;
    JTabbedPane              mainTabs;
    private JPanel           tabHistory;
    private JButton          tbtnMarket;
    private JButton          tbtmTrader;
    private ChartPanel       chartHistory;

    private final Action     exitAction;
    private final Action     openWekaAction;
    private final Action     selectDatabaseAction;
    private final Action     selectSequenceAction;

    public TradingMonitor() {
        exitAction = new ExitApplication(controller);
        selectDatabaseAction = new SelectDatabase(controller);
        selectSequenceAction = new SelectSequence(controller);
        openWekaAction = new OpenWeka();

        initialize();

        try {
            controller.setDatabase(Context.getMonitorLastDatabase());
            try {
                controller.setSequence(Context.getMonitorLastSequence());
            } catch (final Exception ex) {
                BrokerException.reportUi(ex);
                Context.setMonitorLastName(null);
                controller.setSequence(Context.getMonitorLastSequence());
            }
        } catch (final Exception ex) {
            BrokerException.reportUi(ex);
            Context.setMonitorLastDatabase(null);
            controller.setDatabase(Context.getMonitorLastDatabase());
        }
    }

    private void initialize() {
        frmAibrokerXp = new JFrame();
        frmAibrokerXp.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                exitAction.actionPerformed(null);
            }

        });
        frmAibrokerXp.setTitle(Context.APPLICATION_NAME + " - Trading Monitor");
        frmAibrokerXp.setBounds(100, 100, 800, 600);
        frmAibrokerXp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JMenuBar menuBar = new JMenuBar();
        frmAibrokerXp.setJMenuBar(menuBar);

        final JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        final JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.setAction(exitAction);
        mnFile.add(mntmExit);

        final JMenu mnTools = new JMenu("Tools");
        menuBar.add(mnTools);

        mnTools.addSeparator();

        final JMenuItem mntmWeka = new JMenuItem("Weka");
        mntmWeka.setAction(openWekaAction);
        mnTools.add(mntmWeka);

        final JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        frmAibrokerXp.getContentPane().add(toolBar, BorderLayout.NORTH);

        tbtnDatabase = new JButton();
        tbtnDatabase.setAction(selectDatabaseAction);
        tbtnDatabase.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/database_error.png")));
        toolBar.add(tbtnDatabase);

        tbtnSequence = new JButton();
        tbtnSequence.setAction(selectSequenceAction);
        tbtnSequence.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/chart_curve_error.png")));
        toolBar.add(tbtnSequence);

        tbtnMarket = new JButton();
        tbtnMarket.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/cart_error.png")));
        toolBar.add(tbtnMarket);

        tbtmTrader = new JButton();
        tbtmTrader.setIcon(new ImageIcon(TradingMonitor.class.getResource("/com/famfamfam/silk/group_error.png")));
        toolBar.add(tbtmTrader);

        mainTabs = new JTabbedPane(SwingConstants.TOP);
        frmAibrokerXp.getContentPane().add(mainTabs, BorderLayout.CENTER);

        tabHistory = new JPanel();
        mainTabs.addTab("History", null, tabHistory, null);
        tabHistory.setLayout(new BorderLayout(0, 0));

    }

    void drawChartHistory() {
        final JFreeChart chart = ChartFactory.createCandlestickChart(null, null, null, controller.getSequenceChartData(), false);
        removeChartHistory();
        chartHistory = new ChartPanel(chart);
        chartHistory.setMouseWheelEnabled(true);
        tabHistory.add(chartHistory, BorderLayout.CENTER);
    }

    void removeChartHistory() {
        if (chartHistory != null) {
            tabHistory.remove(chartHistory);
        }
    }
}
