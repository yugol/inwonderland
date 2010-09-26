using System;
using System.Collections.Generic;
using System.IO;
using TradingCommon.Util;

namespace TradingCommon.Storage
{
    public class DataUtil
    {
        #region Symbols

        public const int TICKS_SELECTION = 0x01;
        public const int EOD_SELECTION = 0x02;

        public static string NormalizeSymbol(string symbol)
        {
            if (symbol.IndexOf('/') >= 0)
                return symbol.Replace("/", "");
            return symbol;
        }

        public static string GetFullSymbol(string fileName)
        {
            return fileName.Split('.')[0];
        }

        private static string GetSymbol(string fileName)
        {
            return GetFullSymbol(fileName).Split('-')[0];
        }

        public static IList<string> GetSymbols(int selectionType)
        {
            ICollection<string> symbolSet = new HashSet<string>();
            foreach (string filePath in Directory.GetFiles(Configuration.DATA_FOLDER))
            {
                string symbol = GetSymbol(Path.GetFileName(filePath));
                if (((selectionType & TICKS_SELECTION) != 0) && (filePath.IndexOf(".T.") >= 0))
                    symbolSet.Add(symbol);
                if (((selectionType & EOD_SELECTION) != 0) && (filePath.IndexOf(".EOD.") >= 0))
                    symbolSet.Add(symbol);
            }
            List<string> symbols = new List<string>(symbolSet);
            symbols.Sort();
            return symbols;
        }

        public static IList<string> GetMaturities(string symbol, int selection)
        {
            HashSet<string> maturitySet = new HashSet<string>();
            foreach (string filePath in Directory.GetFiles(Configuration.DATA_FOLDER))
            {
                string fullSymbol = GetFullSymbol(Path.GetFileName(filePath));
                string[] title = fullSymbol.Split('-');
                if ((title.Length == 2) && (title[0].Equals(symbol)))
                {
                    string maturity = title[1];
                    if (((selection & TICKS_SELECTION) != 0) && (filePath.IndexOf(".T.") >= 0))
                        maturitySet.Add(maturity);
                    if (((selection & EOD_SELECTION) != 0) && (filePath.IndexOf(".EOD.") >= 0))
                        maturitySet.Add(maturity);
                }
            }
            List<string> maturityList = new List<string>(maturitySet);
            maturityList.Sort(new MaturityConvenienceComparer());
            return maturityList;
        }
        
        public static IList<string> GetMaturitiesUpToNow(string symbol)
        {
            MaturityComparer maturityComparer = new MaturityComparer();
            string currentMaturity = DTUtil.CurrentMaturity();
            List<string> maturityList = new List<string>();
            foreach (string maturity in GetMaturities(symbol, TICKS_SELECTION))
            {
                if (maturityComparer.Compare(maturity, currentMaturity) <= 0)
                    maturityList.Add(maturity);
            }
            maturityList.Sort(maturityComparer);
            return maturityList;
        }

        public static IList<string> GetSpotSymbolsForDerivatives()
        {
            IList<string> spots = new List<string>();
            foreach (string symbol in GetSymbols(TICKS_SELECTION | EOD_SELECTION))
            {
                if ((symbol.IndexOf("DE") == 0) && (symbol.IndexOf("SBX") < 0))
                {
                    // Console.WriteLine(symbol);                    
                    string spot = symbol.Split('-')[0];
                    spot = spot.Substring(2, spot.Length - 2);
                    spots.Add(spot);
                }
            }
            return spots;
        }

        #endregion

        #region Bid /Ask

        private static string GetBidAskFileName(string symbol)
        {
            symbol = NormalizeSymbol(symbol);
            return Path.Combine(Configuration.DATA_FOLDER, symbol + ".BA.csv");
        }

        private static void CreateBidAskDataFile(string fileName)
        {
            StreamWriter writer = new StreamWriter(File.Create(fileName));
            writer.WriteLine("DateTime,Price,Bid,Ask");
            writer.Dispose();
        }

        public static void AppendBidAsk(string symbol, BidAsk bidAsk)
        {
            string fileName = GetBidAskFileName(symbol);
            if (!File.Exists(fileName)) 
                CreateBidAskDataFile(fileName);
            StreamWriter writer = new StreamWriter(File.Open(fileName, FileMode.Append, FileAccess.Write, FileShare.Read));
            writer.WriteLine(bidAsk.ToEntry());
            writer.Dispose();
        }

        public static IList<BidAsk> ReadBidAsks(string symbol)
        {
            IList<BidAsk> bidAsks = new List<BidAsk>();
            StreamReader reader = new StreamReader(File.Open(GetBidAskFileName(symbol), FileMode.Open, FileAccess.Read, FileShare.ReadWrite));
            string line = reader.ReadLine();
            while ((line = reader.ReadLine()) != null)
                bidAsks.Add(BidAsk.FromEntry(line));
            reader.Dispose();
            return bidAsks;
        }

        public static BidAsk GetLastBidAsk(string symbol)
        {
            string fileName = GetBidAskFileName(symbol);
            string[] lines = File.ReadAllLines(fileName);
            string line = lines[lines.Length - 1];
            return BidAsk.FromEntry(line);
        }

        #endregion

        #region Ticks

        public static string GetTickFileName(string symbol)
        {
            symbol = NormalizeSymbol(symbol);
            return Path.Combine(Configuration.DATA_FOLDER, symbol + ".T.csv");
        }

        private static void CreateTickDataFile(string fileName)
        {
            StreamWriter writer = new StreamWriter(File.Create(fileName));
            WriteTickDataFileHeaders(writer);
            writer.Dispose();
        }
        
        private static void WriteTickDataFileHeaders(StreamWriter writer)
        {
            writer.WriteLine("Symbol,Date,Time,Price,Volume,OpenInterest");   
        }

        public static void AppendTick(string symbol, Tick tick)
        {
            if (tick.dateTime.Hour >= Configuration.MARKET_OPEN_HOUR)
            {
                string fileName = GetTickFileName(symbol);
                if (!File.Exists(fileName)) 
                    CreateTickDataFile(fileName);
                StreamWriter writer = new StreamWriter(File.Open(fileName, FileMode.Append, FileAccess.Write, FileShare.Read));
                writer.WriteLine(tick.ToEntry(symbol));
                writer.Dispose();
            }
        }

        public static void AppendTicks(string symbol, IList<Tick> ticks)
        {
            string fileName = GetTickFileName(symbol);
            if (!File.Exists(fileName)) 
                CreateTickDataFile(fileName);
            
            StreamWriter writer = new StreamWriter(File.Open(fileName, FileMode.Append, FileAccess.Write, FileShare.Read));

            foreach (Tick tick in ticks)
                if (tick.dateTime.Hour >= Configuration.MARKET_OPEN_HOUR)
                    writer.WriteLine(tick.ToEntry(symbol));
            
            writer.Dispose();
        }

        public static IList<Tick> ReadTicks(string symbol)
        {
            IList<Tick> ticks = new List<Tick>();
            try
            {
                StreamReader reader = new StreamReader(File.Open(GetTickFileName(symbol), FileMode.Open, FileAccess.Read, FileShare.ReadWrite));
                string line = reader.ReadLine(); // ignore the first line
                while ((line = reader.ReadLine()) != null)
                    ticks.Add(Tick.FromEntry(line));
                reader.Dispose();
            }
            catch (FileNotFoundException) { }
            return ticks;
        }

        public static void WriteTicks(string symbol, IList<Tick> ticks)
        {
            string fileName = GetTickFileName(symbol);            
            StreamWriter writer = new StreamWriter(File.Open(fileName, FileMode.Create, FileAccess.Write, FileShare.Read));
            WriteTickDataFileHeaders(writer);
            foreach (Tick tick in ticks)
                writer.WriteLine(tick.ToEntry(symbol));            
            writer.Dispose();
        }

        #endregion

        #region EOD

        private static void CreateEodDataFile(string fileName)
        {
            StreamWriter writer = new StreamWriter(File.Create(fileName));
            writer.WriteLine("Symbol,Date,Open,High,Low,Close,Volume,OpenInterest");
            writer.Dispose();
        }

        public static string GetEodFileName(string symbol)
        {
            symbol = NormalizeSymbol(symbol);
            return Path.Combine(Configuration.DATA_FOLDER, symbol + ".EOD.csv");
        }

        public static void AppendEod(string symbol, EOD ohlcvi)
        {
            string fileName = GetEodFileName(symbol);
            if (!File.Exists(fileName)) CreateEodDataFile(fileName);
            StreamWriter writer = File.AppendText(fileName);
            writer.WriteLine(ohlcvi.ToEntry(symbol));
            writer.Dispose();
        }

        public static IList<EOD> ReadEods(string symbol)
        {
            IList<EOD> eods = new List<EOD>();
            try
            {
                string fileName = GetEodFileName(symbol);
                string[] lines = File.ReadAllLines(fileName);
                for (int i = 1; i < lines.Length; i++)
                    eods.Add(EOD.FromEntry(lines[i]));
            }
            catch (FileNotFoundException) { }
            return eods;
        }

        #endregion

        #region TimeSeries

        public static TimeSeries ReadTimeSeries(string symbol, int period)
        {
            TimeSeries timeSeries = null;

            if (period < TimeSeries.DAY_PERIOD)
            {
                IList<Tick> ticks = ReadTicks(symbol);
                if (ticks.Count > 0)
                    timeSeries = new TimeSeries(ticks, period);
                else
                    period = TimeSeries.DAY_PERIOD;
            }

            if (timeSeries == null)
            {
                IList<EOD> eods = ReadEods(symbol);
                if (eods.Count > 0)
                    timeSeries = new TimeSeries(eods, period);
                else
                {
                    IList<Tick> ticks = ReadTicks(symbol);
                    if (ticks.Count > 0)
                        timeSeries = new TimeSeries(ticks, period);
                }
            }

            return timeSeries;
        }

        #endregion
    }

    class MaturityComparer : IComparer<string>
    {
        
        public int Compare(string m1, string m2)
        {
            DateTime dt1 = DTUtil.Maturity2Date(m1);
            DateTime dt2 = DTUtil.Maturity2Date(m2);
            return dt1.CompareTo(dt2);
        }
    }

    class MaturityConvenienceComparer : IComparer<string>
    {
        public int Compare(string m1, string m2)
        {
            DateTime currentMaturity = DTUtil.GetContractExpirationDateFor(DateTime.Now);
            DateTime dt1 = DTUtil.Maturity2Date(m1);
            DateTime dt2 = DTUtil.Maturity2Date(m2);

            int c = dt1.CompareTo(dt2);

            if (c == 0)
                return 0;

            int c1 = currentMaturity.CompareTo(dt1);

            if (c1 == 0)
                return -1;

            int c2 = currentMaturity.CompareTo(dt2);

            if (c2 == 0)
                return 1;

            if (c1 == c2)
                if (c1 > 0)
                    return -c;
                else
                    return c;

            return c1;
        }
    }
}
