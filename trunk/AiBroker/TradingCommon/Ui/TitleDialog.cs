using System;
using System.Windows.Forms;
using TradingCommon;

namespace TradingCommon.Ui
{
    public partial class TitleDialog : Form
    {
        public Title Title { get { return titleSelector.Title; } }

        public TitleDialog()
        {
            InitializeComponent();
            CenterToScreen();
        }

        private void ok_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            Close();
        }

        private void cancel_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
