using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Threading;
using System.Windows.Forms;

using TradingCommon;
using TradingCommon.Ui;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter.Ui
{
    public partial class DataCenterForm : Form
    {
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
                ExitApplication();
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

        static string BMFMS_EOD_FUTURES_TAB_NAME = "BMFMS Daily Futures";

        //bool eodBmfmsFuturesUpdateCompleted;

        public DataCenterForm()
        {
            InitializeComponent();
            Width = 280;
            Height = 335;
        }

        private void DataCenterForm_Shown(object sender, EventArgs e)
        {
            formTimer.Start();

            if (Program.MinimizeToTray)
                MinimizeToTray();

            if (Program.MonitorBmfmsFuturesIntraday)
            {
                bmfmsFuturesRealtimeImporter.StartMonitor();
            }
            else
            {
                bool exitApp = false;
                DateTime now = DateTime.Now;
                if (Program.ImportEodData)
                {
                    ImportEodData(now, now);
                    exitApp = true;
                }
                if (exitApp)
                    ExitApplication();
            }
        }

        private void formTimer_Tick(object sender, EventArgs e)
        {
            DateTime now = DateTime.Now;
            timeLabel.Text = DTUtil.ToDateTimeString(now);
            if (bmfmsFuturesRealtimeImporter.IsStarted)
            {
                if ((now.Hour >= Configuration.MARKET_CLOSE_HOUR) && (now.Minute >= 15))
                {
                    ExitApplication();
                }
            }
        }

        private void ExitApplication()
        {
            bmfmsFuturesRealtimeImporter.StopMonitor();
            formTimer.Stop();
            SaveLog();
            Application.Exit();
        }

        private void SaveLog()
        {
            string fileName = Path.Combine(Configuration.LOG_FOLDER, "TDC-" + DTUtil.ToCDateTimeString(DateTime.Now) + ".log");
            try
            {
                File.WriteAllText(fileName, bmfmsFuturesRealtimeImporter.GetLog());
            }
            catch(Exception)
            {
                File.WriteAllText(fileName, "");
            }
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ExitApplication();
        }

        void bmfmsEodFuturesImporter_ImportCompleted()
        {
            allTabs.TabPages.RemoveByKey(BMFMS_EOD_FUTURES_TAB_NAME);
            //if (Properties.Settings.Default.UpdateBvbRegsEod)
            //    eodBmfmsFuturesUpdateCompleted = true;
        }

        private void futuresToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            TimeIntervalReader dlg = new TimeIntervalReader();
            dlg.Text = "Trading Data Center - " + new SibexEodFuturesImporter().GetTitle();
            dlg.From.MinDate = dlg.To.MinDate = DTUtil.ParseDate("2003-11-01");

            if (dlg.ShowDialog() == DialogResult.OK)
                ImportEodFutures(dlg.From.SelectionStart, dlg.To.SelectionStart);
        }

        void eODREGSToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TimeIntervalReader dlg = new TimeIntervalReader();
            dlg.Text = "Trading Data Center - " + new BvbEodRegsImporter().GetTitle();
            dlg.From.MinDate = dlg.To.MinDate = DTUtil.ParseDate("1996-11-01");

            if (dlg.ShowDialog() == DialogResult.OK)
                ImportEodRegs(dlg.From.SelectionStart, dlg.To.SelectionStart);
        }
        
        TimeInterval UiGetTimeInterval()
        {
            TimeIntervalReader timeDialog = new TimeIntervalReader();
            if (timeDialog.ShowDialog() == DialogResult.OK)
            {
                TimeInterval timeInterval = new TimeInterval();
                timeInterval.from = timeDialog.From.SelectionStart;
                timeInterval.to = timeDialog.To.SelectionStart;
                return timeInterval;
            }
            return null;
        }
        
        void intradayFuturesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TimeIntervalReader dlg = new TimeIntervalReader();
            dlg.Text = "Trading Data Center - " + new SibexIntradayFuturesImporter().GetTitle();
            dlg.From.MinDate = dlg.To.MinDate = DTUtil.ParseDate("2007-01-03");

            if (dlg.ShowDialog() == DialogResult.OK)
                ImportIntradayFutures(dlg.From.SelectionStart, dlg.To.SelectionStart);
        }

        static void ImportEodRegs(DateTime from, DateTime to)
        {
            BvbEodRegsImporter importer = new BvbEodRegsImporter();
            importer.Import(from, to);
        }
        
        static void ImportEodFutures(DateTime from, DateTime to)
        {
            SibexEodFuturesImporter importer = new SibexEodFuturesImporter();
            importer.Import(from, to);
        }

        static void ImportIntradayFutures(DateTime from, DateTime to)
        {
            SibexIntradayFuturesImporter importer = new SibexIntradayFuturesImporter();
            importer.Import(from, to);
        }
        
        static void ImportEodData(DateTime from, DateTime to)
        {
            ImportEodRegs(from, to);
            ImportEodFutures(from, to);
            ImportIntradayFutures(from, to);            
        }
    }
}
