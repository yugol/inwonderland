using System;
using System.Drawing;
using System.Windows.Forms;

using TradingCommon.Storage;

namespace TradingSimulator.Ui
{
    public partial class SymbolSelector : Form
    {
        string symbol = null;
        
        public string Symbol { get { return symbol; } }
        
        public SymbolSelector()
        {
            InitializeComponent();            
            foreach (string s in DataUtil.GetSymbols(DataUtil.TICKS_SELECTION))
                cbSymbols.Items.Add(s);
        }
        
        void BtnOkClick(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            Close();
        }
        
        void BtnCancelClick(object sender, EventArgs e)
        {
            Close();
        }
        
        void CbSymbolsSelectedIndexChanged(object sender, EventArgs e)
        {
            btnOk.Enabled = true;
            symbol = cbSymbols.SelectedItem.ToString();
        }
    }
}
