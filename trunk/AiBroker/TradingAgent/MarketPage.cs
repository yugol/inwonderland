using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingAgent
{
    class MarketPage : WebPage
    {
        string MARKET_PAGE;

        internal event EventHandler DoneInitializing;

        internal MarketPage(Title title, int interval)
            : base(interval)
        {
            MARKET_PAGE = "https://www.ssifbroker.ro/php-template/afisare_derivate_online.php?ds=1&b_pag=&records_per_page=&simbol=";
            MARKET_PAGE += title.Symbol;
            MARKET_PAGE += "&scadenta=";
            MARKET_PAGE += title.Maturity;
        }

        public override void Init()
        {
            browser.Navigate(MARKET_PAGE);
        }

        protected override void browser_DocumentCompleted(object sender, System.Windows.Forms.WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (!isInitialized)
            {
                if (e.Url.ToString().Equals(MARKET_PAGE))
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
