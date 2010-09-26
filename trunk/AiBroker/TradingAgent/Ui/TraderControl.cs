using System;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;

namespace TradingAgent.Ui
{
    public partial class TraderControl : UserControl
    {
        Title title = null;
        public Title Title
        {
            get { return title; }
            set
            {
                title = value;
                if (title == null)
                    return;

                chartTab.Text = title.FullSymbol;
                addOrder.Symbol = title.FullSymbol;

                marketPage = new MarketPage(title, 20000);
                marketTab.Controls.Clear();
                marketTab.Controls.Add(marketPage.Browser);
                marketPage.Browser.Dock = DockStyle.Fill;
                marketPage.DoneInitializing += new EventHandler(AgentController.Instance.webPage_DoneInitializing);
            }
        }

        Trader trader = null;
        public Trader Trader { get { return trader; } set { trader = value; } }

        MarketPage marketPage;
        internal MarketPage MarketPage { get { return marketPage; } }

        public AddOrderControl AddOrder { get { return addOrder; } }

        public TraderControl()
        {
            InitializeComponent();
        }

        public void UpdateChart()
        {
            trader.Draw(chart);
        }

        public void UpdateStatus()
        {
            trader.SaveTradingStatus(Program.PaperTrade);
            statusBrowser.DocumentText = trader.TradingStatus.GetHTMLReport();
        }

        private void addOrder_OrderPlaced(object sender, EventArgs e)
        {
            Order order = ((OrderCreatedEventArgs)e).Order;
            AgentController.Instance.TyrPlaceOrder(sender, order);
        }

        internal void SetRealtimeData(Tick tick, BidAsk bidAsk)
        {
            realtimeData.BidAsk = bidAsk;
            realtimeData.Tick = tick;
            realtimeData.TradingStatus = trader.TradingStatus;
        }
    }
}
