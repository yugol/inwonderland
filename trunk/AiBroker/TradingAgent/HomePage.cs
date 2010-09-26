using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Util;

namespace TradingAgent
{
    class HomePage : WebPage
    {
        static string HOME_PAGE = "http://www.ssifbroker.ro/";
        static string LOG_IN_PAGE = "https://www.ssifbroker.ro/index.php";

        internal event EventHandler DoneInitializing;

        internal HomePage()
            : base(int.MaxValue)
        {
        }

        public override void Init()
        {
            browser.Navigate(HOME_PAGE);
        }

        protected override void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (!isInitialized)
            {
                if (e.Url.ToString().Equals(HOME_PAGE))
                {
                    UserLogin();
                }
                else if (e.Url.ToString().IndexOf(LOG_IN_PAGE) >= 0)
                {
                    isInitialized = true;
                    if (DoneInitializing != null) DoneInitializing(this, e);
                }
            }
        }

        private void UserLogin()
        {
            browser.Document.All["user"].SetAttribute("value", Program.AccountName);
            browser.Document.All["passwd"].SetAttribute("value", Program.AccountPassword);
            browser.Document.Forms[1].GetElementsByTagName("input")[6].InvokeMember("click");
        }
    }
}
