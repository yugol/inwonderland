using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;
using TradingAgent.Ui;
using TradingCommon.Util;

namespace TradingAgent
{
    delegate void OrderPlacedEventHandler(Order order, PlacedOrder placedOrder);

    class PlaceOrderPage : WebPage
    {
        static string ORDER_LOGIN_PAGE = "https://www.ssifbroker.ro/php-template/auth_ordine_derivate.php";
        static string ORDER_PLACE_PAGE = "https://www.ssifbroker.ro/php-template/add_ordine_derivate.php";
        static string ORDER_DETAILS_PAGE = "https://www.ssifbroker.ro/included/detalii.php";

        private WebBrowser tempBrowser = new WebBrowser();
        public WebBrowser TempBrowser { get { return tempBrowser; } }

        IList<PlacedOrder> currentOrders;

        Order order;
        PlacedOrder placedOrder;

        internal event EventHandler DoneInitializing;
        internal event OrderPlacedEventHandler OrderPlaced;

        internal PlaceOrderPage()
            : base(int.MaxValue)
        {
            tempBrowser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(tempBrowser_DocumentCompleted);
            tempBrowser.ScriptErrorsSuppressed = true;
            tempBrowser.Visible = false;
        }

        public override void Init()
        {
            browser.Navigate(ORDER_LOGIN_PAGE);
            tempBrowser.Navigate(ORDER_LOGIN_PAGE);
        }

        protected override void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (!isInitialized)
            {
                if (e.Url.ToString().Equals(ORDER_LOGIN_PAGE))
                {
                    Login();
                }
                else if (e.Url.ToString().Equals(ORDER_PLACE_PAGE))
                {
                    currentOrders = ReadPlacedOrdersList();
                    isInitialized = true;
                    if (DoneInitializing != null) DoneInitializing(this, e);
                }
            }
            else
            {
                if (e.Url.ToString().Equals(ORDER_PLACE_PAGE))
                {
                    _OrderPlaced();
                }
            }
        }

        private IList<PlacedOrder> ReadPlacedOrdersList()
        {
            IList<PlacedOrder> pageOrders = new List<PlacedOrder>();

            if (browser.DocumentText.IndexOf("Ordine curente") >= 0)
            {
                HtmlElementCollection tables = browser.Document.GetElementsByTagName("table");
                HtmlElementCollection orders = tables[27].GetElementsByTagName("tr");
                for (int i = 1; i < orders.Count; ++i)
                {
                    HtmlElementCollection order = orders[i].GetElementsByTagName("td");
                    PlacedOrder placedOrder = new PlacedOrder();

                    placedOrder.symbol = order[0].InnerText;
                    
                    placedOrder.volume = int.Parse(order[1].InnerText);
                    if (order[2].InnerText.Equals(Configuration.MARKET_PRICE_TAG))
                        placedOrder.price = double.NaN;
                    else
                        placedOrder.price = double.Parse(order[2].InnerText);
                    
                    // placedOrder.brokerComment = order[4].InnerText;

                    placedOrder.id = 0;
                    string actions = order[5].InnerHtml;
                    int pos = actions.IndexOf("id_ordin");
                    pos = actions.IndexOf("=", pos) + 1;
                    char c;
                    while (char.IsNumber(c = actions[pos]))
                    {
                        placedOrder.id *= 10;
                        placedOrder.id += ((int)c - '0');
                        ++pos;
                    }

                    pageOrders.Add(placedOrder);
                }
            }

            return pageOrders;
        }

        private void Login()
        {
            browser.Document.All["passwd"].SetAttribute("value", Program.OrderPlacePassword);
            browser.Document.Forms[1].GetElementsByTagName("input")[5].InvokeMember("click");
        }

        internal void PlaceOrder(Order order)
        {
            SoundUtil.OrderPlacedSound(order.Operation);
            this.order = order;

            string priceString = Configuration.MARKET_PRICE_TAG;
            if (!double.IsNaN(order.PlacePrice)) priceString = order.PlacePrice.ToString("#.0000");

            browser.Document.All["fo1"].InvokeMember("click");
            if (order.Operation == Operation.SELL) browser.Document.All["vc1"].InvokeMember("click");
            else browser.Document.All["vc2"].InvokeMember("click");
            browser.Document.All["simbol"].SetAttribute("value", order.Symbol);
            browser.Document.All["cantitate"].SetAttribute("value", order.Volume.ToString());
            browser.Document.All["pret"].SetAttribute("value", priceString);
            browser.Document.All["termen"].SetAttribute("value", order.TimeLimit.ToString());
            browser.Document.All["go"].InvokeMember("click");
        }

        private void _OrderPlaced()
        {
            string pageSource = browser.DocumentText;
            if (pageSource.IndexOf("Termen*") >= 0)
            {
                IList<PlacedOrder> newOrders = ReadPlacedOrdersList();
                placedOrder = GetNewOrder(newOrders, currentOrders);
                currentOrders = newOrders;

                string url = ORDER_DETAILS_PAGE + "?id_ordin=" + placedOrder.id.ToString() + "&from=FUTURES";
                tempBrowser.Navigate(url);
            }
            else
            {
                browser.Navigate(ORDER_PLACE_PAGE);
            }
        }

        private PlacedOrder GetNewOrder(IList<PlacedOrder> newOrders, IList<PlacedOrder> currentOrders)
        {
            foreach (PlacedOrder order in newOrders)
                if (!currentOrders.Contains(order))
                    return order;
            return null;
        }

        private void _ReadOrderDetails()
        {
            ReadPlacedOrderDetails(placedOrder);
            if (OrderPlaced != null)
                OrderPlaced(order, placedOrder);
            placedOrder = null;
            order = null;
        }

        protected internal void tempBrowser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (e.Url.ToString().IndexOf(ORDER_DETAILS_PAGE) >= 0)
            {
                _ReadOrderDetails();
            }
        }

        void ReadPlacedOrderDetails(PlacedOrder order)
        {
            string pageSource = tempBrowser.DocumentText;
            char c;

            int pos = pageSource.IndexOf("Data");
            pos = pageSource.IndexOf("<b>", pos) + 3;
            string date = "";
            while ((c = pageSource[pos]) != '<')
            {
                if (!char.IsWhiteSpace(c)) date += c;
                ++pos;
            }

            pos = pageSource.IndexOf("Ora");
            pos = pageSource.IndexOf("<b>", pos) + 3;
            string time = "";
            while ((c = pageSource[pos]) != '<')
            {
                if (!char.IsWhiteSpace(c)) time += c;
                ++pos;
            }

            order.dateTime = DTUtil.ParseDateTime(date + " " + time);
        }

    }
}
