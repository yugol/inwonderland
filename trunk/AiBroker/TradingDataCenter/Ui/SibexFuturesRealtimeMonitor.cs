using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter.Ui
{
    public partial class BmfmsFuturesRealtimeMonitor : UserControl
    {
        BmfmsFuturesRealtimeWebReader futuresReader = null;
        
        bool started = false;
        public bool IsStarted { get { return started; } }

        public BmfmsFuturesRealtimeMonitor()
        {
            InitializeComponent();
            updateTimer.Interval = Program.BMFMS_FUTURES_INTRADAY_UPDATE_INTERVAL;
            ShowStartUi();
        }

        void ShowStartUi()
        {
            btnAction.Text = "Start";
            lblLastUpdateTime.Text = "N/A";
            tbUpdatedSymbols.Text = "";
        }

        void btnAction_Click(object sender, EventArgs e)
        {
            if (futuresReader == null)
                StartMonitor();
            else
                StopMonitor();
        }

        public void StopMonitor()
        {
            try
            {
                if (futuresReader != null)
                {
                    updateTimer.Stop();
                    Log("Monitoring stopped at: " + DTUtil.ToDateTimeString(DateTime.Now));
                    ShowStartUi();
                    futuresReader = null;
                    started = false;
                }
            }
            catch (Exception ex)
            {
                TimeLog(ex.Message);
            }
        }

        public void StartMonitor()
        {
            try
            {
                if (futuresReader != null)
                    StopMonitor();
                futuresReader = new BmfmsFuturesRealtimeWebReader();
                updateTimer.Start();
                btnAction.Text = "Stop";
                Log("Monitoring started at: " + DTUtil.ToDateTimeString(DateTime.Now));
                started = true;
            }
            catch (Exception ex)
            {
                TimeLog(ex.Message);
            }
        }

        private void updateTimer_Tick(object sender, EventArgs e)
        {
            try
            {
                IDictionary<string, Tick> ticks = null;
                DateTime dateTime = futuresReader.GetDiffsUpdate(out ticks);

                tbUpdatedSymbols.Text = "";
                foreach (string key in ticks.Keys)
                {
                    DataUtil.AppendTick(key, ticks[key]);
                    tbUpdatedSymbols.Text += " ";
                    tbUpdatedSymbols.Text += key;
                }

                tbUpdatedSymbols.SelectAll();

                lblLastUpdateTime.Text = DTUtil.ToDateTimeString(dateTime);
            }
            catch (Exception ex)
            {
                TimeLog(ex.Message);
            }
        }

        private void Log(string msg)
        {
            tbLog.Text += msg;
            tbLog.Text += "\r\n";
        }

        private void TimeLog(string msg)
        {
            tbLog.Text += DTUtil.ToDateTimeString(DateTime.Now);
            tbLog.Text += "  ";
            tbLog.Text += msg;
            tbLog.Text += "\r\n";
        }
        
        internal string GetLog()
        {
            return tbLog.Text;
        }
    }
}
