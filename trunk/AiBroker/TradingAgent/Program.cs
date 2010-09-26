using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TradingAgent.Ui;
using TradingCommon;

namespace TradingAgent
{
    static class Program
    {
        public static string AccountName = "";
        public static string AccountPassword = "";
        public static string OrderPlacePassword = "";
        public static bool PaperTrade = Configuration.TA_PAPER_TRADE;
        public static bool ManualControl = Configuration.TA_MANUAL_CONTROL;
        public static bool DrawOnly = Configuration.TA_DRAW_ONLY;
        public static bool CloseWithMarket = Configuration.TA_CLOSE_WITH_MARKET;
    
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            try
            {
                AccountName = args[0];
                AccountPassword = args[1];
                OrderPlacePassword = args[2];
                PaperTrade = bool.Parse(args[3]);
                ManualControl = bool.Parse(args[4]);
                DrawOnly = bool.Parse(args[5]);
            }
            catch (Exception)
            {
            }
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new AgentForm());
        }
    }
}
