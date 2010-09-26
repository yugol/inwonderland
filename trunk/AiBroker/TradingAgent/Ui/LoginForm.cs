using System;
using System.Windows.Forms;

namespace TradingAgent.Ui
{
    public partial class LoginForm : Form
    {
        public LoginForm()
        {
            InitializeComponent();
            CenterToScreen();

            accountName.Text = Program.AccountName;
            accountPassword.Text = Program.AccountPassword;
            orderPlacePassword.Text = Program.OrderPlacePassword;
            paperTrade.Checked = Program.PaperTrade;
            chkCloseWithMarket.Checked = Program.CloseWithMarket;

            accountName.Select();
        }

        private void ok_Click(object sender, EventArgs e)
        {
            Program.AccountName = accountName.Text;
            Program.AccountPassword = accountPassword.Text;
            Program.OrderPlacePassword = orderPlacePassword.Text;
            Program.PaperTrade = paperTrade.Checked;
            Program.CloseWithMarket = chkCloseWithMarket.Checked;
        }

        private void LoginForm_Shown(object sender, EventArgs e)
        {
            // SoundUtil.ApplicationStartSound();
        }
    }
}
