using System;
using System.Windows.Forms;
using TradingCommon.Storage;

namespace TradingCommon.Ui
{
    public partial class ComboTickTitleSelector : UserControl
    {
        public Title Title { get { return new Title(symbol.SelectedItem.ToString(), maturity.SelectedItem.ToString()); } }

        public ComboTickTitleSelector()
        {
            InitializeComponent();
            foreach (string s in DataUtil.GetSymbols(DataUtil.TICKS_SELECTION))
                symbol.Items.Add(s);
            symbol.SelectedIndex = 0;
        }

        private void symbol_SelectedIndexChanged(object sender, EventArgs e)
        {
            maturity.Items.Clear();
            foreach (string s in DataUtil.GetMaturities(symbol.SelectedItem.ToString(), DataUtil.TICKS_SELECTION))
                maturity.Items.Add(s);
            maturity.SelectedIndex = 0;
        }
    }
}
