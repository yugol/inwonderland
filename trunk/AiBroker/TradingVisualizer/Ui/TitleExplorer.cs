using System;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;
using TradingCommon.Storage;
using TradingCommon;

namespace TradingVisualizer.Ui
{
    public delegate void TitleSelectedEventHandler(object sender, Title selectedTitle);

    public partial class TitleExplorer : DockContent
    {
        public event VisibleChanged2EventHandler VisibleChanged2;

        public TitleExplorer()
        {
            InitializeComponent();
        }

        public event TitleSelectedEventHandler TitleSelected;

        private void SymbolsExplorer_Load(object sender, EventArgs e)
        {
            LoadSymbols();
        }

        private void LoadSymbols()
        {
            titleTree.BeginUpdate();
            foreach (string symbol in DataUtil.GetSymbols(DataUtil.TICKS_SELECTION | DataUtil.EOD_SELECTION))
            {
                TreeNode symbolNode = new TreeNode(symbol, 0, 1);
                titleTree.Nodes.Add(symbolNode);
                foreach (string maturity in DataUtil.GetMaturities(symbol, DataUtil.TICKS_SELECTION | DataUtil.EOD_SELECTION))
                {
                    TreeNode maturityNode = new TreeNode(maturity, 0, 1);
                    symbolNode.Nodes.Add(maturityNode);
                }
                if (symbolNode.Nodes.Count > 0)
                {
                    symbolNode.ImageIndex = 8;
                    symbolNode.SelectedImageIndex = 9;
                }
            }
            titleTree.EndUpdate();
        }

        private void titleTree_AfterSelect(object sender, TreeViewEventArgs e)
        {
            Title title = null;

            if (e.Node.Nodes.Count == 0)
            {
                if (e.Node.Level == 0)
                    title = new Title(e.Node.Text);
                else
                    title = new Title(e.Node.Parent.Text, e.Node.Text);
            }

            if ((title != null) && (TitleSelected != null))
                TitleSelected(this, title);
        }

        private void TitleExplorer_VisibleChanged(object sender, EventArgs e)
        {
            if (VisibleChanged2 != null)
                VisibleChanged2(this, Visible);
        }
    }
}
