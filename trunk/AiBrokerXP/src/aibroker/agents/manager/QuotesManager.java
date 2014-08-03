package aibroker.agents.manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import aibroker.Context;
import aibroker.agents.manager.actions.AddSequenceAction;
import aibroker.agents.manager.actions.BackupDatabaseAction;
import aibroker.agents.manager.actions.BasicQuotesReportAction;
import aibroker.agents.manager.actions.CloseApplicationAction;
import aibroker.agents.manager.actions.DeleteQuotesAction;
import aibroker.agents.manager.actions.DropDatabaseContentAction;
import aibroker.agents.manager.actions.ImportQuotesAction;
import aibroker.agents.manager.actions.OpenDatabaseAction;
import aibroker.agents.manager.actions.SaveSequenceAction;
import aibroker.agents.manager.actions.UpdateAllQuotesAction;
import aibroker.agents.manager.actions.UpdateSequenceAction;
import aibroker.agents.manager.util.QuotesTreeCellRenderer;
import aibroker.agents.manager.util.QuotesTreeManager;
import aibroker.agents.manager.util.QuotesTreeNode;
import aibroker.agents.manager.util.SettlementMonth;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlDatabase;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.drivers.sql.VirtualSqlSequence;
import aibroker.util.BrokerException;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;

public class QuotesManager implements TreeSelectionListener {

    public static void launch(final SqlDatabase sqlDb) {
        try {
            for (final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().toLowerCase().indexOf("nimbus") >= 0) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (final Exception e) {
        }

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    final QuotesManager window = new QuotesManager();
                    window.frmManager.setBounds(Context.getManagerWindowBounds());
                    window.frmManager.setVisible(true);
                    window.setDatabase(sqlDb);
                } catch (final Exception e) {
                    BrokerException.reportUi(e);
                }
            }

        });
    }

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        Context.setupContext("Quotes Manager");
        launch(Databases.DEFAULT());
    }

    // model attributes
    private SqlDatabase               sqlDb;
    private SqlSequence               sqlSequence;

    // frame attributes
    public JFrame                     frmManager;

    // menu attributes
    private JMenu                     mnReports;
    private JMenu                     mnSequence;
    private JMenuItem                 mntmBackup;
    private JMenuItem                 mntmBasicQuotesReport;
    private JMenuItem                 mntmDropContent;
    private JMenuItem                 mntmUpdateAll;

    // symbols tree attributes
    public JTree                      quotesTree;

    // sequence attributes
    private JButton                   btnDelete;
    private JButton                   btnExport;
    private JButton                   btnImport;
    private JButton                   btnSave;
    private JButton                   btnTableName;
    private JButton                   btnUpdate;
    private JLabel                    lblFee;
    private JLabel                    lblFeed;
    private JLabel                    lblMargin;
    private JLabel                    lblMarket;
    private JLabel                    lblMultiplier;
    private JLabel                    lblName;
    private JLabel                    lblSampling;
    private JLabel                    lblSettlementMonth;
    private JLabel                    lblSettlementYear;
    private JLabel                    lblSupport;
    private JLabel                    lblSymbol;
    private JLabel                    lblUpdater;
    public JCheckBox                  ckbFavourite;
    public JComboBox<Feed>            cbbFeed;
    public JComboBox<Integer>         cbbSettlementYear;
    public JComboBox<Market>          cbbMarket;
    public JComboBox<Sampling>        cbbSampling;
    public JComboBox<SettlementMonth> cbbSettlementMonth;
    public JComboBox<Updater>         cbbUpdater;
    public JTextField                 txtFee;
    public JTextField                 txtMargin;
    public JTextField                 txtMultiplier;
    public JTextField                 txtName;
    public JTextField                 txtSupport;
    public JTextField                 txtSymbol;

    // quotes attributes
    private QuotesViewPanel           quotesView;

    // status attributes
    private JLabel                    lblReady;

    // actions attributes
    private final Action              addSequenceAction;
    private final Action              backupDatabaseAction;
    private final Action              closeApplicationAction;
    private final Action              deleteQuotesAction;
    private final Action              dropDatabaseContentAction;
    private final Action              importQuotesAction;
    private final Action              openDatabaseAction;
    private final Action              saveSequenceAction;
    private final Action              updateAllQuotesAction;
    private final Action              updateSequenceAction;
    private final Action              basicQuotesReportAction;

    /**
     * Create the application.
     */
    public QuotesManager() {
        addSequenceAction = new AddSequenceAction(this);
        backupDatabaseAction = new BackupDatabaseAction(this);
        closeApplicationAction = new CloseApplicationAction(this);
        deleteQuotesAction = new DeleteQuotesAction(this);
        dropDatabaseContentAction = new DropDatabaseContentAction(this);
        importQuotesAction = new ImportQuotesAction(this);
        openDatabaseAction = new OpenDatabaseAction(this);
        saveSequenceAction = new SaveSequenceAction(this);
        updateAllQuotesAction = new UpdateAllQuotesAction(this);
        updateSequenceAction = new UpdateSequenceAction(this);
        basicQuotesReportAction = new BasicQuotesReportAction(this);

        initialize();

        quotesTree.setCellRenderer(new QuotesTreeCellRenderer());
        quotesTree.setModel(QuotesTreeManager.buildDefaultTreeModel());

        cbbMarket.addItem(Market.REGS);
        cbbMarket.addItem(Market.FUTURES);

        cbbFeed.addItem(Feed.ORIG);
        cbbFeed.addItem(Feed.CACHE);
        cbbFeed.addItem(Feed.LIVE);

        cbbSampling.addItem(Sampling.SECOND);
        cbbSampling.addItem(Sampling.DAILY);

        cbbUpdater.addItem(Updater.NONE);
        cbbUpdater.addItem(Updater.BVB_REG_DAILY_BASE);
        cbbUpdater.addItem(Updater.BVB_REG_DAILY_NORM);
        cbbUpdater.addItem(Updater.YAHOO_DAILY);
        cbbUpdater.addItem(Updater.CACHED_SIBEX_FUT_TICK);

        cbbSettlementMonth.addItem(new SettlementMonth("January", Calendar.JANUARY));
        cbbSettlementMonth.addItem(new SettlementMonth("February", Calendar.FEBRUARY));
        cbbSettlementMonth.addItem(new SettlementMonth("March", Calendar.MARCH));
        cbbSettlementMonth.addItem(new SettlementMonth("April", Calendar.APRIL));
        cbbSettlementMonth.addItem(new SettlementMonth("May", Calendar.MAY));
        cbbSettlementMonth.addItem(new SettlementMonth("June", Calendar.JUNE));
        cbbSettlementMonth.addItem(new SettlementMonth("July", Calendar.JULY));
        cbbSettlementMonth.addItem(new SettlementMonth("August", Calendar.AUGUST));
        cbbSettlementMonth.addItem(new SettlementMonth("September", Calendar.SEPTEMBER));
        cbbSettlementMonth.addItem(new SettlementMonth("October", Calendar.OCTOBER));
        cbbSettlementMonth.addItem(new SettlementMonth("November", Calendar.NOVEMBER));
        cbbSettlementMonth.addItem(new SettlementMonth("December", Calendar.DECEMBER));

        for (int i = Context.LAST_YEAR; i >= Context.FIRST_YEAR; --i) {
            cbbSettlementYear.addItem(i);
        }

        setDatabase(null);
        quotesTree.addTreeSelectionListener(this);
    }

    public SqlDatabase getDatabase() {
        return sqlDb;
    }

    public SqlSequence getSequence() {
        return sqlSequence;
    }

    public void setDatabase(final SqlDatabase database) {
        sqlDb = database;
        quotesTree.setModel(QuotesTreeManager.readSequences(database));
        setDatabaseControlsEnabled();
        setSequence(null, null);
    }

    public void setSequence(final SqlSequence sequence, final TreePath sequencePath) {
        sqlSequence = sequence;
        if (sqlSequence != null) {
            quotesTree.setSelectionPath(sequencePath);
        } else {
            if (sequencePath == null) {
                QuotesTreeManager.selectRoot(quotesTree);
            }
        }
        setSequenceControlsValues();
        setSequenceControlsEnabled(false);
        setStatusMessage();
    }

    public void setSequenceControlsEnabled(final boolean editable) {
        final boolean dbOpened = sqlDb != null;
        final boolean real = !(sqlSequence instanceof VirtualSqlSequence);
        final boolean existingSequence = sqlSequence != null;
        final boolean derivativeSequence = ((Market) cbbMarket.getSelectedItem()).isDerivative();
        final boolean updatable = existingSequence && !Updater.NONE.equals(sqlSequence.getUpdater());

        mnReports.setEnabled(dbOpened && existingSequence);

        lblMarket.setEnabled(dbOpened && real && editable);
        lblFeed.setEnabled(dbOpened && real && editable);
        lblSampling.setEnabled(dbOpened && real && editable);
        lblSymbol.setEnabled(dbOpened && real && editable);
        lblName.setEnabled(dbOpened && real && editable);
        lblUpdater.setEnabled(dbOpened && real && (existingSequence || editable));

        cbbMarket.setEnabled(dbOpened && real && editable);
        cbbFeed.setEnabled(dbOpened && real && editable);
        cbbSampling.setEnabled(dbOpened && real && editable);
        txtSymbol.setEnabled(dbOpened && real && editable);
        txtName.setEnabled(dbOpened && real && editable);
        cbbUpdater.setEnabled(dbOpened && real && (existingSequence || editable));

        lblSupport.setEnabled(dbOpened && real && derivativeSequence && editable);
        lblSettlementMonth.setEnabled(dbOpened && real && derivativeSequence && editable);
        lblSettlementYear.setEnabled(dbOpened && real && derivativeSequence && editable);
        lblMultiplier.setEnabled(dbOpened && real && derivativeSequence && (existingSequence || editable));
        lblMargin.setEnabled(dbOpened && real && derivativeSequence && (existingSequence || editable));
        lblFee.setEnabled(dbOpened && real && (existingSequence || editable));

        txtSupport.setEnabled(dbOpened && real && derivativeSequence && editable);
        cbbSettlementMonth.setEnabled(dbOpened && real && derivativeSequence && editable);
        cbbSettlementYear.setEnabled(dbOpened && real && derivativeSequence && editable);
        txtMultiplier.setEnabled(dbOpened && real && derivativeSequence && (existingSequence || editable));
        txtMargin.setEnabled(dbOpened && real && derivativeSequence && (existingSequence || editable));
        ckbFavourite.setEnabled(dbOpened && real && (existingSequence || editable));
        txtFee.setEnabled(dbOpened && real && (existingSequence || editable));

        btnSave.setEnabled(dbOpened && real && (existingSequence || editable));
        btnDelete.setEnabled(dbOpened && real && existingSequence);
        btnUpdate.setEnabled(dbOpened && real && updatable);
        btnImport.setEnabled(dbOpened && real && existingSequence);
        btnExport.setEnabled(dbOpened && existingSequence);

        quotesView.setEnabled(dbOpened);
    }

    @Override
    public void valueChanged(final TreeSelectionEvent e) {
        final TreePath treePath = e.getPath();
        final QuotesTreeNode leaf = (QuotesTreeNode) treePath.getLastPathComponent();
        final SqlSequence sequence = leaf.getSequence();
        setSequence(sequence, treePath);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmManager = new JFrame();
        frmManager.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                ((CloseApplicationAction) closeApplicationAction).saveBounds();
            }

        });
        frmManager.setTitle(Context.APPLICATION_NAME + " - Quotes Manager");
        frmManager.setBounds(100, 100, 1024, 768);
        frmManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JMenuBar menuBar = new JMenuBar();
        frmManager.setJMenuBar(menuBar);

        final JMenu mnDatabase = new JMenu("Database");
        menuBar.add(mnDatabase);

        final JMenuItem mntmOpen = new JMenuItem("Open...");
        mntmOpen.setAction(openDatabaseAction);
        mnDatabase.add(mntmOpen);

        mntmBackup = new JMenuItem("Backup...");
        mntmBackup.setAction(backupDatabaseAction);
        mnDatabase.add(mntmBackup);

        mnDatabase.addSeparator();

        mntmDropContent = new JMenuItem("Drop Content");
        mntmDropContent.setAction(dropDatabaseContentAction);
        mnDatabase.add(mntmDropContent);

        mnSequence = new JMenu("Sequence");
        menuBar.add(mnSequence);

        mntmUpdateAll = new JMenuItem("Update All...");
        mnSequence.add(mntmUpdateAll);
        mntmUpdateAll.setAction(updateAllQuotesAction);

        mnSequence.addSeparator();

        final JMenuItem mntmAddSequence = new JMenuItem();
        mntmAddSequence.setAction(addSequenceAction);
        mnSequence.add(mntmAddSequence);

        mnReports = new JMenu("Reports");
        menuBar.add(mnReports);

        mntmBasicQuotesReport = new JMenuItem("");
        mntmBasicQuotesReport.setAction(basicQuotesReportAction);
        mnReports.add(mntmBasicQuotesReport);

        final JToolBar statusBar = new JToolBar();
        statusBar.setFloatable(false);
        frmManager.getContentPane().add(statusBar, BorderLayout.SOUTH);

        lblReady = new JLabel("Ready");
        statusBar.add(lblReady);

        btnTableName = new JButton("");
        btnTableName.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(new StringSelection(btnTableName.getText()), null);
            }

        });
        btnTableName.setBorder(null);
        statusBar.add(btnTableName);

        final JSplitPane explorerSplitPane = new JSplitPane();
        frmManager.getContentPane().add(explorerSplitPane, BorderLayout.CENTER);

        final JScrollPane quotesScrollPane = new JScrollPane();
        explorerSplitPane.setLeftComponent(quotesScrollPane);

        quotesTree = new JTree();
        quotesTree.setFont(UIManager.getFont("List.font"));
        quotesScrollPane.setViewportView(quotesTree);

        final JSplitPane editorSplitPane = new JSplitPane();
        editorSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        explorerSplitPane.setDividerLocation(300);
        explorerSplitPane.setRightComponent(editorSplitPane);

        final JScrollPane metaScrollPane = new JScrollPane();
        editorSplitPane.setLeftComponent(metaScrollPane);

        final JPanel sequencePanel = new JPanel();
        metaScrollPane.setViewportView(sequencePanel);
        sequencePanel.setLayout(new BorderLayout(0, 0));

        final JPanel sequenceValuesPanel = new JPanel();
        sequenceValuesPanel.setBorder(new EmptyBorder(10, 15, 15, 0));
        sequencePanel.add(sequenceValuesPanel);
        final GridBagLayout gbl_sequenceValuesPanel = new GridBagLayout();
        gbl_sequenceValuesPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0 };
        sequenceValuesPanel.setLayout(gbl_sequenceValuesPanel);

        lblMarket = new JLabel("Market:");
        final GridBagConstraints gbc_lblMarket = new GridBagConstraints();
        gbc_lblMarket.anchor = GridBagConstraints.EAST;
        gbc_lblMarket.insets = new Insets(0, 0, 5, 5);
        gbc_lblMarket.gridx = 0;
        gbc_lblMarket.gridy = 0;
        sequenceValuesPanel.add(lblMarket, gbc_lblMarket);

        cbbMarket = new JComboBox<Market>();
        cbbMarket.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                setSequenceControlsEnabled(true);
            }

        });
        final GridBagConstraints gbc_cbbMarket = new GridBagConstraints();
        gbc_cbbMarket.insets = new Insets(0, 0, 5, 5);
        gbc_cbbMarket.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbMarket.gridx = 1;
        gbc_cbbMarket.gridy = 0;
        sequenceValuesPanel.add(cbbMarket, gbc_cbbMarket);

        final Component horizontalStrut = Box.createHorizontalStrut(15);
        final GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
        gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_horizontalStrut.gridx = 2;
        gbc_horizontalStrut.gridy = 0;
        sequenceValuesPanel.add(horizontalStrut, gbc_horizontalStrut);

        lblSupport = new JLabel("Support:");
        final GridBagConstraints gbc_lblSupport = new GridBagConstraints();
        gbc_lblSupport.insets = new Insets(0, 0, 5, 5);
        gbc_lblSupport.anchor = GridBagConstraints.EAST;
        gbc_lblSupport.gridx = 3;
        gbc_lblSupport.gridy = 0;
        sequenceValuesPanel.add(lblSupport, gbc_lblSupport);

        txtSupport = new JTextField();
        txtSupport.setEnabled(false);
        txtSupport.setColumns(10);
        final GridBagConstraints gbc_txtSupport = new GridBagConstraints();
        gbc_txtSupport.insets = new Insets(0, 0, 5, 0);
        gbc_txtSupport.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSupport.gridx = 4;
        gbc_txtSupport.gridy = 0;
        sequenceValuesPanel.add(txtSupport, gbc_txtSupport);

        lblFeed = new JLabel("Feed:");
        final GridBagConstraints gbc_lblFeed = new GridBagConstraints();
        gbc_lblFeed.anchor = GridBagConstraints.EAST;
        gbc_lblFeed.insets = new Insets(0, 0, 5, 5);
        gbc_lblFeed.gridx = 0;
        gbc_lblFeed.gridy = 1;
        sequenceValuesPanel.add(lblFeed, gbc_lblFeed);

        cbbFeed = new JComboBox<Feed>();
        final GridBagConstraints gbc_cbbFeed = new GridBagConstraints();
        gbc_cbbFeed.insets = new Insets(0, 0, 5, 5);
        gbc_cbbFeed.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbFeed.gridx = 1;
        gbc_cbbFeed.gridy = 1;
        sequenceValuesPanel.add(cbbFeed, gbc_cbbFeed);

        lblSettlementMonth = new JLabel("Set. Month:");
        final GridBagConstraints gbc_lblSettlementMonth = new GridBagConstraints();
        gbc_lblSettlementMonth.weighty = 1.0;
        gbc_lblSettlementMonth.anchor = GridBagConstraints.EAST;
        gbc_lblSettlementMonth.insets = new Insets(0, 0, 5, 5);
        gbc_lblSettlementMonth.gridx = 3;
        gbc_lblSettlementMonth.gridy = 1;
        sequenceValuesPanel.add(lblSettlementMonth, gbc_lblSettlementMonth);

        cbbSettlementMonth = new JComboBox<SettlementMonth>();
        final GridBagConstraints gbc_cbbSettlementMonth = new GridBagConstraints();
        gbc_cbbSettlementMonth.weighty = 1.0;
        gbc_cbbSettlementMonth.insets = new Insets(0, 0, 5, 0);
        gbc_cbbSettlementMonth.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbSettlementMonth.gridx = 4;
        gbc_cbbSettlementMonth.gridy = 1;
        sequenceValuesPanel.add(cbbSettlementMonth, gbc_cbbSettlementMonth);

        lblSampling = new JLabel("Sampling:");
        final GridBagConstraints gbc_lblSampling = new GridBagConstraints();
        gbc_lblSampling.anchor = GridBagConstraints.EAST;
        gbc_lblSampling.insets = new Insets(0, 0, 5, 5);
        gbc_lblSampling.gridx = 0;
        gbc_lblSampling.gridy = 2;
        sequenceValuesPanel.add(lblSampling, gbc_lblSampling);

        cbbSampling = new JComboBox<Sampling>();
        cbbSampling.setMaximumRowCount(10);
        final GridBagConstraints gbc_cbbSampling = new GridBagConstraints();
        gbc_cbbSampling.insets = new Insets(0, 0, 5, 5);
        gbc_cbbSampling.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbSampling.gridx = 1;
        gbc_cbbSampling.gridy = 2;
        sequenceValuesPanel.add(cbbSampling, gbc_cbbSampling);

        lblSettlementYear = new JLabel("Set. Year:");
        final GridBagConstraints gbc_lblSettlementYear = new GridBagConstraints();
        gbc_lblSettlementYear.insets = new Insets(0, 0, 5, 5);
        gbc_lblSettlementYear.anchor = GridBagConstraints.EAST;
        gbc_lblSettlementYear.gridx = 3;
        gbc_lblSettlementYear.gridy = 2;
        sequenceValuesPanel.add(lblSettlementYear, gbc_lblSettlementYear);

        cbbSettlementYear = new JComboBox<Integer>();
        final GridBagConstraints gbc_cbbSettlementYear = new GridBagConstraints();
        gbc_cbbSettlementYear.weighty = 1.0;
        gbc_cbbSettlementYear.insets = new Insets(0, 0, 5, 0);
        gbc_cbbSettlementYear.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbSettlementYear.gridx = 4;
        gbc_cbbSettlementYear.gridy = 2;
        sequenceValuesPanel.add(cbbSettlementYear, gbc_cbbSettlementYear);

        lblSymbol = new JLabel("Symbol:");
        final GridBagConstraints gbc_lblSymbol = new GridBagConstraints();
        gbc_lblSymbol.anchor = GridBagConstraints.EAST;
        gbc_lblSymbol.insets = new Insets(0, 0, 5, 5);
        gbc_lblSymbol.gridx = 0;
        gbc_lblSymbol.gridy = 3;
        sequenceValuesPanel.add(lblSymbol, gbc_lblSymbol);

        txtSymbol = new JTextField();
        final GridBagConstraints gbc_txtSymbol = new GridBagConstraints();
        gbc_txtSymbol.weighty = 1.0;
        gbc_txtSymbol.insets = new Insets(0, 0, 5, 5);
        gbc_txtSymbol.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSymbol.gridx = 1;
        gbc_txtSymbol.gridy = 3;
        sequenceValuesPanel.add(txtSymbol, gbc_txtSymbol);
        txtSymbol.setColumns(10);

        lblMultiplier = new JLabel("Multiplier:");
        final GridBagConstraints gbc_lblMultiplier = new GridBagConstraints();
        gbc_lblMultiplier.weighty = 1.0;
        gbc_lblMultiplier.anchor = GridBagConstraints.EAST;
        gbc_lblMultiplier.insets = new Insets(0, 0, 5, 5);
        gbc_lblMultiplier.gridx = 3;
        gbc_lblMultiplier.gridy = 3;
        sequenceValuesPanel.add(lblMultiplier, gbc_lblMultiplier);

        txtMultiplier = new JTextField();
        txtMultiplier.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_txtMultiplier = new GridBagConstraints();
        gbc_txtMultiplier.weighty = 1.0;
        gbc_txtMultiplier.insets = new Insets(0, 0, 5, 0);
        gbc_txtMultiplier.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMultiplier.gridx = 4;
        gbc_txtMultiplier.gridy = 3;
        sequenceValuesPanel.add(txtMultiplier, gbc_txtMultiplier);
        txtMultiplier.setColumns(10);

        lblName = new JLabel("Name:");
        final GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.weighty = 1.0;
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 4;
        sequenceValuesPanel.add(lblName, gbc_lblName);

        txtName = new JTextField();
        final GridBagConstraints gbc_txtName = new GridBagConstraints();
        gbc_txtName.insets = new Insets(0, 0, 5, 5);
        gbc_txtName.weighty = 1.0;
        gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtName.gridx = 1;
        gbc_txtName.gridy = 4;
        sequenceValuesPanel.add(txtName, gbc_txtName);
        txtName.setColumns(15);

        lblMargin = new JLabel("Margin:");
        final GridBagConstraints gbc_lblMargin = new GridBagConstraints();
        gbc_lblMargin.weighty = 1.0;
        gbc_lblMargin.anchor = GridBagConstraints.EAST;
        gbc_lblMargin.insets = new Insets(0, 0, 5, 5);
        gbc_lblMargin.gridx = 3;
        gbc_lblMargin.gridy = 4;
        sequenceValuesPanel.add(lblMargin, gbc_lblMargin);

        txtMargin = new JTextField();
        txtMargin.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_txtMargin = new GridBagConstraints();
        gbc_txtMargin.insets = new Insets(0, 0, 5, 0);
        gbc_txtMargin.weighty = 1.0;
        gbc_txtMargin.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMargin.gridx = 4;
        gbc_txtMargin.gridy = 4;
        sequenceValuesPanel.add(txtMargin, gbc_txtMargin);
        txtMargin.setColumns(10);

        lblUpdater = new JLabel("Updater:");
        lblUpdater.setEnabled(false);
        final GridBagConstraints gbc_lblUpdateClass = new GridBagConstraints();
        gbc_lblUpdateClass.insets = new Insets(0, 0, 5, 5);
        gbc_lblUpdateClass.anchor = GridBagConstraints.EAST;
        gbc_lblUpdateClass.gridx = 0;
        gbc_lblUpdateClass.gridy = 5;
        sequenceValuesPanel.add(lblUpdater, gbc_lblUpdateClass);

        cbbUpdater = new JComboBox<Updater>();
        cbbUpdater.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(final ItemEvent e) {
                final boolean existingSequence = sqlSequence != null;
                final boolean updatable = !Updater.NONE.equals(cbbUpdater.getSelectedItem());
                btnUpdate.setEnabled(existingSequence && updatable);
            }

        });
        cbbUpdater.setMaximumRowCount(10);
        cbbUpdater.setEnabled(false);
        final GridBagConstraints gbc_cbbUpdateCalss = new GridBagConstraints();
        gbc_cbbUpdateCalss.insets = new Insets(0, 0, 5, 5);
        gbc_cbbUpdateCalss.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbUpdateCalss.gridx = 1;
        gbc_cbbUpdateCalss.gridy = 5;
        sequenceValuesPanel.add(cbbUpdater, gbc_cbbUpdateCalss);

        lblFee = new JLabel("Fee:");
        final GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 3;
        gbc_lblNewLabel.gridy = 5;
        sequenceValuesPanel.add(lblFee, gbc_lblNewLabel);

        txtFee = new JTextField();
        txtFee.setHorizontalAlignment(SwingConstants.TRAILING);
        final GridBagConstraints gbc_txtFee = new GridBagConstraints();
        gbc_txtFee.insets = new Insets(0, 0, 5, 0);
        gbc_txtFee.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtFee.gridx = 4;
        gbc_txtFee.gridy = 5;
        sequenceValuesPanel.add(txtFee, gbc_txtFee);
        txtFee.setColumns(10);

        ckbFavourite = new JCheckBox("Favourite");
        final GridBagConstraints gbc_ckbFavourite = new GridBagConstraints();
        gbc_ckbFavourite.anchor = GridBagConstraints.WEST;
        gbc_ckbFavourite.gridx = 4;
        gbc_ckbFavourite.gridy = 6;
        sequenceValuesPanel.add(ckbFavourite, gbc_ckbFavourite);

        final JPanel sequenceButtonsPanel = new JPanel();
        sequenceButtonsPanel.setBorder(new EmptyBorder(15, 0, 10, 15));
        sequenceButtonsPanel.setLayout(new BorderLayout(0, 0));
        sequencePanel.add(sequenceButtonsPanel, BorderLayout.EAST);

        final JPanel sequenceOperationsPanel = new JPanel();
        sequenceOperationsPanel.setBorder(null);
        sequenceButtonsPanel.add(sequenceOperationsPanel, BorderLayout.CENTER);
        final GridBagLayout gbl_sequenceOperationsPanel = new GridBagLayout();
        sequenceOperationsPanel.setLayout(gbl_sequenceOperationsPanel);

        btnSave = new JButton("Save");
        btnSave.setAction(saveSequenceAction);
        final GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSave.insets = new Insets(0, 0, 5, 0);
        gbc_btnSave.gridx = 0;
        gbc_btnSave.gridy = 0;
        sequenceOperationsPanel.add(btnSave, gbc_btnSave);

        btnDelete = new JButton("Delete");
        btnDelete.setAction(deleteQuotesAction);
        final GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
        gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnDelete.gridx = 0;
        gbc_btnDelete.gridy = 5;
        sequenceOperationsPanel.add(btnDelete, gbc_btnDelete);

        final Component verticalStrut = Box.createVerticalStrut(15);
        final GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
        gbc_verticalStrut.gridx = 0;
        gbc_verticalStrut.gridy = 1;
        sequenceOperationsPanel.add(verticalStrut, gbc_verticalStrut);

        btnUpdate = new JButton("Update...");
        btnUpdate.setAction(updateSequenceAction);
        final GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
        gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnUpdate.insets = new Insets(0, 0, 5, 0);
        gbc_btnUpdate.gridx = 0;
        gbc_btnUpdate.gridy = 2;
        sequenceOperationsPanel.add(btnUpdate, gbc_btnUpdate);

        btnImport = new JButton("Import...");
        btnImport.setAction(importQuotesAction);
        final GridBagConstraints gbc_btnImport = new GridBagConstraints();
        gbc_btnImport.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnImport.insets = new Insets(0, 0, 5, 0);
        gbc_btnImport.gridx = 0;
        gbc_btnImport.gridy = 3;
        sequenceOperationsPanel.add(btnImport, gbc_btnImport);

        btnExport = new JButton("Export...");
        final GridBagConstraints gbc_btnExport = new GridBagConstraints();
        gbc_btnExport.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnExport.insets = new Insets(0, 0, 5, 0);
        gbc_btnExport.gridx = 0;
        gbc_btnExport.gridy = 4;
        sequenceOperationsPanel.add(btnExport, gbc_btnExport);

        final Component verticalGlue = Box.createVerticalGlue();
        final GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
        gbc_verticalGlue.weighty = 1.0;
        gbc_verticalGlue.fill = GridBagConstraints.VERTICAL;
        gbc_verticalGlue.gridx = 0;
        gbc_verticalGlue.gridy = 6;
        sequenceOperationsPanel.add(verticalGlue, gbc_verticalGlue);

        final JPanel sequenceSeparatorPanel = new JPanel();
        final FlowLayout flowLayout = (FlowLayout) sequenceSeparatorPanel.getLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        sequenceSeparatorPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 15, 0, 15), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        sequenceButtonsPanel.add(sequenceSeparatorPanel, BorderLayout.WEST);

        final JPanel quotesPanel = new JPanel();
        quotesPanel.setBorder(null);
        editorSplitPane.setRightComponent(quotesPanel);
        quotesPanel.setLayout(new BorderLayout(0, 0));

        quotesView = new QuotesViewPanel();
        quotesView.setControlsVisible(false);
        quotesPanel.add(quotesView, BorderLayout.CENTER);

    }

    private void resetSequenceControls() {
        cbbMarket.setSelectedItem(Market.REGS);
        cbbFeed.setSelectedItem(Feed.ORIG);
        cbbSampling.setSelectedItem(Sampling.SECOND);
        txtSymbol.setText("");
        txtName.setText("");
        cbbUpdater.setSelectedItem(Updater.NONE);
        ckbFavourite.setSelected(false);
        txtSupport.setText("");
        cbbSettlementYear.setSelectedItem(Moment.getCurrentYear());
        txtMargin.setText("0");
        txtMultiplier.setText("0");
        txtFee.setText("0");
        quotesView.setQuotes(null);
    }

    private void setDatabaseControlsEnabled() {
        final boolean dbOpened = sqlDb != null;
        quotesTree.setEnabled(dbOpened);
        mntmBackup.setEnabled(dbOpened);
        mntmDropContent.setEnabled(dbOpened);
        mnSequence.setEnabled(dbOpened);
    }

    private void setSequenceControlsValues() {
        resetSequenceControls();
        if (sqlSequence != null) {
            cbbMarket.setSelectedItem(sqlSequence.getMarket());
            cbbFeed.setSelectedItem(sqlSequence.getFeed());
            cbbSampling.setSelectedItem(sqlSequence.getSampling());
            txtSymbol.setText(sqlSequence.getSymbol());
            txtName.setText(sqlSequence.getName());
            cbbUpdater.setSelectedItem(sqlSequence.getUpdater());

            if (sqlSequence.getMarket().isDerivative()) {
                txtSupport.setText(sqlSequence.getSupport());
                final Moment settlement = sqlSequence.getSettlement();
                if (settlement != null) {
                    cbbSettlementMonth.setSelectedIndex(settlement.get(Calendar.MONTH));
                    cbbSettlementYear.setSelectedItem(settlement.get(Calendar.YEAR));
                }
                txtMargin.setText(String.valueOf(sqlSequence.getMargin()));
                txtMultiplier.setText(String.valueOf(sqlSequence.getMultiplier()));
            } else {
                txtMargin.setText("");
                txtMultiplier.setText("");
            }

            ckbFavourite.setSelected(sqlSequence.isFavourite());
            txtFee.setText(String.valueOf(sqlSequence.getFee()));

            frmManager.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            quotesView.setQuotes(sqlSequence.getQuotes());
            frmManager.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void setStatusMessage() {
        String message = "";
        btnTableName.setVisible(false);
        if (sqlDb == null) {
            message = "Open a database to start";
        } else {
            message = "Using: " + sqlDb.getDbFile().getAbsolutePath();
            if (sqlSequence != null) {
                message += "/";
                btnTableName.setVisible(true);
                btnTableName.setText(sqlSequence.getTableId());
            }
        }
        lblReady.setText(message);
    }

}
