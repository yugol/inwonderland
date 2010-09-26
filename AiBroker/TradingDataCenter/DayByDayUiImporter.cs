/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 20/06/2008
 * Time: 08:00
 * 
 */

using System;
using System.ComponentModel;
using System.IO;
using System.Windows.Forms;

using TradingCommon;
using TradingDataCenter.Ui;
using TradingCommon.Util;

namespace TradingDataCenter
{
    internal delegate void ImportCompletedEventHandler();

    public abstract class DayByDayUiImporter
    {
        abstract internal string GetTitle();
        abstract internal string GetTempDownloadedFilePath(DateTime date);
        abstract internal string GetUriString(DateTime date);
        abstract internal void ProcessDownloadedFile(string fileName);
    
        internal event ImportCompletedEventHandler ImportCompleted;

        DateTime date;
        BackgroundWorker worker = null;
        ProgressReporter reporter = null;

        internal void Import(DateTime from, DateTime to)
        {
            CreateProgressReporter();
            CreateWorker();
            object[] parameters = new object[] { from, to };
            worker.RunWorkerAsync(parameters);
            reporter.ShowDialog();
        }

        private void CreateProgressReporter()
        {
            reporter = new ProgressReporter();
            reporter.Text = "Trading Data Center - " + GetTitle();
            reporter.Abort += new AbortEventHandler(reporter_Abort);
        }

        private void CreateWorker()
        {
            worker = new BackgroundWorker();
            worker.WorkerReportsProgress = true;
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(worker_DoWork);
            worker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
        }

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            date = (DateTime)((object[])(e.Argument))[0];
            DateTime to = (DateTime)((object[])(e.Argument))[1];

            if (date.CompareTo(to) > 0) return;

            TimeSpan unit = new TimeSpan(1, 0, 0, 0);
            int workAmmount = (int)((TimeSpan)(to - date)).TotalDays + 1;
            int work = 0;

            while (date.CompareTo(to) <= 0)
            {
                reporter.Message = ("Importing data for " + DTUtil.ToDateString(date));

                string tempDestFile = GetTempDownloadedFilePath(date);
                string uri = GetUriString(date);
                WebUtil.DownloadFile(uri, tempDestFile);  
                
                ProcessDownloadedFile(tempDestFile);
                
                File.Delete(tempDestFile);

                if (worker.CancellationPending)
                {
                    ShowInterruptionMessage();
                    break;
                }

                worker.ReportProgress((100 * ++work) / workAmmount);
                date = date.Add(unit);
            }
        }

        private void ShowInterruptionMessage()
        {
            MessageBox.Show(
                "Operation interrupted by user.",
                "Trading Data Center - " + GetTitle(),
                MessageBoxButtons.OK,
                MessageBoxIcon.Exclamation);
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            int val = e.ProgressPercentage;
            if (val > 100) val = 100;
            reporter.Progress = val;
        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            reporter.Close();
            if (ImportCompleted != null) ImportCompleted();
        }

        private void reporter_Abort()
        {
            worker.CancelAsync();
        }
    }
}
