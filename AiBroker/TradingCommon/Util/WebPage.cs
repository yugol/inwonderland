using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace TradingCommon.Util
{
    public abstract class WebPage
    {
        protected Timer timer = new Timer();

        protected WebBrowser browser = new WebBrowser();
        public WebBrowser Browser { get { return browser; } }

        protected bool isInitialized = false;

        public WebPage(int interval)
        {
            browser.ScriptErrorsSuppressed = true;
            browser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(browser_DocumentCompleted);
            timer.Interval = interval;
            timer.Tick += new EventHandler(BatchWork);
        }

        public abstract void Init();

        protected abstract void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e);

        protected void StartBatchWork()
        {
            timer.Start();
        }

        public void StopBatchWork()
        {
            timer.Stop();
        }

        virtual protected void BatchWork(object sender, EventArgs e) { }
    }
}
