using System;
using System.IO;
using System.Text;
using System.Xml;
using System.Windows.Forms;

namespace TradingCommon
{
    public static class Configuration
    {
        public static double CONTRACT_COMMISSION = 1.5;

        public const int CANDLE_TIME_FRAME = 10;
        public const int MARKET_OPEN_HOUR = 10;
        public const int MARKET_CLOSE_HOUR = 16;
        public const int TRADER_ACTION_DELAY = 5; // in minutes

        public static readonly string MARKET_PRICE_TAG = "PIATA";

        private static string installFolder = Path.Combine(Application.StartupPath, @"..\");
        private static readonly string CONFIGURATION_FILE = Path.Combine(Application.StartupPath, "AiBroker.config");

        private static bool taCloseWithMarket = false;
        private static bool taPaperTrade = true;
        private static bool taManualControl = false;
        private static bool taDrawOnly = false;

        public static string INSTALL_FOLDER { get { return installFolder; } }
        public static string BIN_FOLDER { get { return Path.Combine(installFolder, "bin"); } }
        public static string LOG_FOLDER { get { return Path.Combine(installFolder, "log"); } }
        public static string LAYOUT_FOLDER { get { return Path.Combine(installFolder, "layout"); } }
        public static string DATA_FOLDER { get { return Path.Combine(installFolder, "data"); } }
        public static string ORDERS_FOLDER { get { return Path.Combine(installFolder, "orders"); } }
        public static string TEMP_FOLDER { get { return Path.Combine(installFolder, "temp"); } }
        public static string TSYS_FOLDER { get { return Path.Combine(installFolder, "tsys"); } }
        public static string ANALYSIS_FOLDER { get { return Path.Combine(installFolder, "analysis"); } }
        
        public static bool TA_CLOSE_WITH_MARKET { get { return taCloseWithMarket; } }
        public static bool TA_PAPER_TRADE { get { return taPaperTrade; } }
        public static bool TA_MANUAL_CONTROL { get { return taManualControl; } }
        public static bool TA_DRAW_ONLY { get { return taDrawOnly; } }

        static Configuration()
        {
            if (File.Exists(CONFIGURATION_FILE)) {
                ReadConfiguration();
            }
            
#if TEST
            installFolder = @"..\..\..\TestData"; 
#endif

            // MessageBox.Show(installFolder);
            
            installFolder = Path.GetFullPath(installFolder);
            Directory.CreateDirectory(BIN_FOLDER);
            Directory.CreateDirectory(DATA_FOLDER);
            Directory.CreateDirectory(LOG_FOLDER);
            Directory.CreateDirectory(LAYOUT_FOLDER);
            Directory.CreateDirectory(ORDERS_FOLDER);
            Directory.CreateDirectory(TEMP_FOLDER);
            Directory.CreateDirectory(TSYS_FOLDER);
            Directory.CreateDirectory(ANALYSIS_FOLDER);
		}

        private static void ReadConfiguration()
        {
            XmlDocument config = new XmlDocument();
            config.Load(CONFIGURATION_FILE);
            
            XmlNodeList nodes = config.GetElementsByTagName("installFolder");
            if (nodes.Count == 1) 
                installFolder = nodes[0].InnerText;
            
            nodes = config.GetElementsByTagName("taCloseWithMarket");
            if (nodes.Count == 1) 
                taCloseWithMarket = string.Equals("true", nodes[0].InnerText, StringComparison.OrdinalIgnoreCase);
            nodes = config.GetElementsByTagName("taPaperTrade");
            if (nodes.Count == 1) 
                taPaperTrade = string.Equals("true", nodes[0].InnerText, StringComparison.OrdinalIgnoreCase);
            nodes = config.GetElementsByTagName("taManualControl");
            if (nodes.Count == 1) 
                taManualControl = string.Equals("true", nodes[0].InnerText, StringComparison.OrdinalIgnoreCase);
            nodes = config.GetElementsByTagName("taDrawOnly");
            if (nodes.Count == 1) 
                taDrawOnly = string.Equals("true", nodes[0].InnerText, StringComparison.OrdinalIgnoreCase);
        }
    }
}
