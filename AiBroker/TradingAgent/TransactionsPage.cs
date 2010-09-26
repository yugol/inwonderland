using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingAgent
{
    class TransactionsPage : WebPage
    {
        static string TRANSACTIONS_PAGE = "https://www.ssifbroker.ro/php-template/afisare_tranzactii.php";

        internal event EventHandler DoneInitializing;
        internal event EventHandler NewTransactionsCompleted;

        IList<Transaction> previousTransactions;

        internal TransactionsPage(int interval)
            : base(interval)
        {
        }

        public override void Init()
        {
            browser.Navigate(TRANSACTIONS_PAGE);
        }

        protected override void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // MessageBox.Show(e.Url.ToString());
            if (e.Url.ToString().Equals(TRANSACTIONS_PAGE))
            {
                if (!isInitialized)
                {
                    isInitialized = true;
                    if (DoneInitializing != null) DoneInitializing(this, e);
                    previousTransactions = ReadTransactions();
                    StartBatchWork();
                }
                else
                {
                    ProcessPage();
                }
            }
        }

        private void ProcessPage()
        {
            lock (previousTransactions)
            {
                IList<Transaction> currentTransactions = ReadTransactions();
                IList<Transaction> newTransactions = FindNewTransactions(currentTransactions, previousTransactions);
                if (newTransactions.Count > 0)
                {
                    if (NewTransactionsCompleted != null)
                        NewTransactionsCompleted(this, new TransactionsCompletedEventArgs(newTransactions));
                 
                    previousTransactions.Clear();
                    foreach (Transaction t in currentTransactions)
                        previousTransactions.Add(t);
                }
            }
        }

        private IList<Transaction> FindNewTransactions(IList<Transaction> currentTransactions, IList<Transaction> previousTransactions)
        {
            IList<Transaction> newTransactions = new List<Transaction>();
            foreach (Transaction t in currentTransactions)
            {
                if (!previousTransactions.Contains(t))
                    newTransactions.Add(t);
            }
            return newTransactions;
        }

        protected override void BatchWork(object sender, EventArgs args)
        {
            browser.Navigate(TRANSACTIONS_PAGE);
        }

        internal IList<Transaction> ReadTransactions()
        {
            IList<Transaction> transactions = new List<Transaction>();

            try
            {
                HtmlElementCollection tables = browser.Document.GetElementsByTagName("table");
                HtmlElementCollection rows = tables[18].GetElementsByTagName("tr");
                for (int i = 1; i < rows.Count; ++i)
                {
                    Transaction transaction = new Transaction();
                    ReadTransactions(rows[i], transaction);
                    transactions.Add(transaction);
                }
            }
            catch (Exception)
            {
                // ignore...
            }

            return transactions;
        }

        private void ReadTransactions(HtmlElement tr, Transaction transaction)
        {
            HtmlElementCollection tds = tr.GetElementsByTagName("td");

            transaction.symbol = tds[0].InnerText;
            transaction.dateTime = DTUtil.ParseDateTime(DTUtil.ToDateString(DateTime.Now) + " " + tds[1].InnerText);
            transaction.volume = int.Parse(tds[2].InnerText);
            transaction.price = double.Parse(tds[3].InnerText);
            if (tds[4].InnerText.IndexOf("Cump") >= 0)
                transaction.operation = Operation.buy;
            else
                transaction.operation = Operation.sell;
        }
    }
}
