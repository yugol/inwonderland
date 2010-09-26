using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using ZedGraph;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingAgent.Ui
{
    internal partial class AgentForm : Form
    {
        internal delegate void AddLogLineCallback(string text);

        internal static readonly Color BUY_COLOR = Color.FromArgb(((int)(((byte)(144)))), ((int)(((byte)(238)))), ((int)(((byte)(144)))));
        internal static readonly Color SELL_COLOR = Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(160)))), ((int)(((byte)(221)))));

        #region Minimize to tray

        private const int WM_QUERYENDSESSION = 0x11;
        private bool endSessionPending;
        FormWindowState prevWindowState;

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == WM_QUERYENDSESSION)
                endSessionPending = true;
            base.WndProc(ref m);
        }

        private void MinimizeToTray()
        {
            prevWindowState = WindowState;
            WindowState = FormWindowState.Minimized;
            Visible = false;
            ShowInTaskbar = false;
            notifyIcon.Visible = true;
        }

        private void RestoreFromTray()
        {
            notifyIcon.Visible = false;
            ShowInTaskbar = true;
            Visible = true;
            WindowState = prevWindowState;
        }

        protected override void OnClosing(CancelEventArgs e)
        {
            if (endSessionPending)
            {
                e.Cancel = false;
                AgentController.Instance.ExitApplication();
            }
            else
            {
                e.Cancel = true;
                MinimizeToTray();
            }

            base.OnClosing(e);
        }

        private void notifyIcon_MouseClick(object sender, MouseEventArgs e)
        {
            RestoreFromTray();
        }

        #endregion

        internal TraderControl TraderAControl { get { return traderAControl; } }

        internal TraderControl TraderBControl { get { return traderBControl; } }

        internal AgentForm()
        {
            InitializeComponent();
        }

        internal void _Navigate(WebBrowser browser, string url)
        {
            browser.Navigate(url);
        }

        internal void SetHomePageBrowser(WebBrowser webBrowser)
        {
            homePage.Controls.Add(webBrowser);
            webBrowser.Dock = DockStyle.Fill;
        }

        internal void SetPortofolioPageBrowser(WebBrowser webBrowser)
        {
            portofolioPage.Controls.Add(webBrowser);
            webBrowser.Dock = DockStyle.Fill;
        }

        internal void SetTransactionsPageBrowser(WebBrowser webBrowser)
        {
            transactionsPage.Controls.Add(webBrowser);
            webBrowser.Dock = DockStyle.Fill;
        }

        internal void SetPlaceOrderPageBrowsers(WebBrowser webBrowser, WebBrowser tempWebBrowser)
        {
            orderPlaceBrowserPanel.Controls.Add(webBrowser);
            webBrowser.Dock = DockStyle.Fill;
            tempBrowserPanel.Controls.Add(tempWebBrowser);
            tempWebBrowser.Dock = DockStyle.Fill;
        }

        private void formTimer_Tick(object sender, EventArgs e)
        {
            timeLabel.Text = DTUtil.ToDateTimeString(DateTime.Now);
        }

        internal void Log(string text)
        {
            logBox.Text += text;
            logBox.Text += "\r\n";
            logBox.Select(logBox.Text.Length, 0);
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            AgentController.Instance.ExitApplication();
        }

        private void TraderForm_Shown(object sender, EventArgs e)
        {
            Location = new Point(0, 0);

            if (Program.AccountName.Equals("") ||
                Program.AccountPassword.Equals("") ||
                Program.OrderPlacePassword.Equals(""))
            {
                LoginForm login = new LoginForm();
                if (login.ShowDialog() != DialogResult.OK)
                {
                    AgentController.Instance.ExitApplication();
                }
            }

            formTimer.Start();
            AgentController.Instance.Init(this);
            Text = "Trading Agent - " + traderAControl.Title.FullSymbol + " / " + traderBControl.Title.FullSymbol;

            drawOnlyToolStripMenuItem.Checked = Program.DrawOnly;
            drawOnlyToolStripMenuItem.Enabled = !Program.ManualControl;
            manualControlToolStripMenuItem.Checked = Program.ManualControl;

            UpdateStatusMode();
        }

        private void UpdateStatusMode()
        {
            if (Program.PaperTrade)
            {
                modeLabel.Text = "Paper trade simulation";
                modeLabel.ForeColor = Color.Black;
            }
            else
            {
                modeLabel.Text = "Real orders";
                modeLabel.ForeColor = Color.Red;
            }

            if (Program.ManualControl)
            {
                modeLabel.Text += " / Manual control";
            }
            else
            {
                if (Program.DrawOnly)
                {
                    modeLabel.Text = "Order placing disabled";
                    modeLabel.ForeColor = Color.Blue;
                }
                else
                {
                    modeLabel.Text += " / Automatic control";
                }
            }
        }

        private void manualControlToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Program.ManualControl = manualControlToolStripMenuItem.Checked;
            traderAControl.AddOrder.Enabled = traderBControl.AddOrder.Enabled = Program.ManualControl;
            drawOnlyToolStripMenuItem.Enabled = !Program.ManualControl;
            UpdateStatusMode();
        }

        private void drawOnlyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            traderAControl.Trader.DrawOnly = Program.DrawOnly = drawOnlyToolStripMenuItem.Checked;
            UpdateStatusMode();
        }
        
        internal string GetLog()
        {
            return logBox.Text;
        }
    }
}
