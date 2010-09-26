using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Util;

namespace TradingAgent
{
    class PortofolioPage : WebPage
    {
        static string PORTOFOLIO_PAGE = "https://www.ssifbroker.ro/php-template/portofoliu_realtime.php";

        internal event EventHandler DoneInitializing;

        internal PortofolioPage(int interval)
            : base(interval)
        {
        }

        public override void Init()
        {
            browser.Navigate(PORTOFOLIO_PAGE);
        }

        protected override void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (!isInitialized)
            {
                if (e.Url.ToString().Equals(PORTOFOLIO_PAGE))
                {
                    isInitialized = true;
                    if (DoneInitializing != null) DoneInitializing(this, e);
                    StartBatchWork();
                }
            }
        }

        protected override void BatchWork(object sender, EventArgs args)
        {
            browser.Refresh();
        }
    }
}
