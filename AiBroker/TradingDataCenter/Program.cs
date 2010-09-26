using System;
using System.Windows.Forms;
using TradingDataCenter.Ui;

namespace TradingDataCenter
{
    static class Program
    {
    	public static bool MonitorBmfmsFuturesIntraday = false;
    	public static bool ImportEodData = false;
    	public static bool MinimizeToTray = false;
    	
    	public static int BMFMS_FUTURES_INTRADAY_UPDATE_INTERVAL = 2000;
    	
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            foreach (string arg in args)
            {
                if (arg.Equals("-INTRADAY-BMFMS-FUTURES"))
                    MonitorBmfmsFuturesIntraday = true;
                else if (arg.Equals("-IMPORT-EOD-DATA"))
                    ImportEodData = true;
            }

            if (args.Length > 0)
                MinimizeToTray = true;

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new DataCenterForm());
        }
    }
}
