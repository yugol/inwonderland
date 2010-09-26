#if TEST

using System;
using System.Collections.Generic;
using System.IO;
using TradingCommon.Util;

using NUnit.Framework;

namespace TradingCommon.Storage
{
    [TestFixture]
    public class DataUtil_Test
    {
        [Test]
        public void ConcurrentFileAccess()
        {
            string filePath = Path.Combine(Configuration.TEMP_FOLDER, "concurrent_access_test.file");
            File.Create(filePath).Dispose();
            
            StreamReader reader1 = new StreamReader(File.Open(filePath, FileMode.Open, FileAccess.Read, FileShare.ReadWrite));
            StreamWriter writer1 = new StreamWriter(File.Open(filePath, FileMode.Append, FileAccess.Write, FileShare.Read));
            StreamReader reader2 = new StreamReader(File.Open(filePath, FileMode.Open, FileAccess.Read, FileShare.ReadWrite));
            // StreamWriter writer2 = new StreamWriter(File.Open(filePath, FileMode.Append, FileAccess.Write, FileShare.Read));
            
            writer1.Write("abc");
            
            // writer2.Dispose();
            writer1.Dispose();
            reader2.Dispose();
            reader1.Dispose();
            
            File.Delete(filePath);
        }
        
        [Test]
        public void ReadBidAsks()
        {
            IList<BidAsk> bidAsks = DataUtil.ReadBidAsks("DESIF2-SEP07");
            Assert.AreEqual(26527, bidAsks.Count);
            
            BidAsk bidAsk = bidAsks[0];
            Assert.AreEqual("2007-07-06 09:49:33", DTUtil.ToDateTimeString(bidAsk.dateTime));
            Assert.AreEqual("3.7150", bidAsk.price.ToString("0.0000"));
            Assert.AreEqual("3.7000", bidAsk.bid.ToString("0.0000"));
            Assert.AreEqual("3.7289", bidAsk.ask.ToString("0.0000"));

            bidAsk = bidAsks[26526];
            Assert.AreEqual("2007-09-28 16:04:42", DTUtil.ToDateTimeString(bidAsk.dateTime));
            Assert.AreEqual("3.4150", bidAsk.price.ToString("0.0000"));
            Assert.AreEqual("3.3900", bidAsk.bid.ToString("0.0000"));
            Assert.AreEqual("3.4150", bidAsk.ask.ToString("0.0000"));
        }
        
        [Test]
        public void ReadTicks()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-SEP07");
            Assert.AreEqual(21853, ticks.Count);
            
            Tick tick = ticks[0];
            Assert.AreEqual("2007-04-10 10:44:11", DTUtil.ToDateTimeString(tick.dateTime));
            Assert.AreEqual("3.2000", tick.price.ToString("0.0000"));
            Assert.AreEqual(2, tick.volume);
            Assert.AreEqual(868, tick.openInterest);

            tick = ticks[21844];
            Assert.AreEqual("2007-09-28 12:10:24", DTUtil.ToDateTimeString(tick.dateTime));
            Assert.AreEqual("3.3900", tick.price.ToString("0.0000"));
            Assert.AreEqual(2, tick.volume);
            Assert.AreEqual(-1, tick.openInterest);

            tick = ticks[21852];
            Assert.AreEqual("2007-09-28 12:44:50", DTUtil.ToDateTimeString(tick.dateTime));
            Assert.AreEqual("3.4150", tick.price.ToString("0.0000"));
            Assert.AreEqual(114, tick.volume);
            Assert.AreEqual(11692, tick.openInterest);
        }
        
        [Test]
        public void GetSymbols()
        {
            ICollection<string> symbols = DataUtil.GetSymbols(DataUtil.TICKS_SELECTION);
            Assert.IsTrue(symbols.Contains("DESIF2"));
            Assert.IsFalse(symbols.Contains("DEAMO"));

            symbols = DataUtil.GetSymbols(DataUtil.EOD_SELECTION);
            Assert.IsTrue(symbols.Contains("DEAMO"));
        }

        [Test]
        public void GetMaturities()
        {
            IList<string> maturities = DataUtil.GetMaturities("BUBOR3", DataUtil.TICKS_SELECTION);
            Assert.IsTrue(maturities.Contains("SEP07"));
            Assert.IsFalse(maturities.Contains("MAR06"));

            maturities = DataUtil.GetMaturities("BUBOR3", DataUtil.EOD_SELECTION);
            Assert.IsFalse(maturities.Contains("SEP07"));
            Assert.IsTrue(maturities.Contains("MAR06"));
        }

        [Test]
        public void GetMaturitiesUpToNow()
        {
            IList<string> maturities = DataUtil.GetMaturitiesUpToNow("DESIF2");
            // foreach (string maturity in  maturities) Console.WriteLine(maturity);
            
            Assert.IsTrue(maturities.Contains("IUN07"));
            Assert.IsTrue(maturities.Contains("SEP07"));
            Assert.IsTrue(maturities.Contains("DEC07"));
            Assert.IsTrue(maturities.Contains("MAR08"));
            
            Assert.AreEqual(0, DataUtil.GetMaturities("DESIF2", DataUtil.TICKS_SELECTION).Count - maturities.Count);
            
            MaturityComparer maturityComparer = new MaturityComparer();
            Assert.IsTrue(maturityComparer.Compare(maturities[0], maturities[maturities.Count - 1]) < 0);
            
        }

        [Test]
        public void GetSpotSymbolsForDerivatives()
        {
            IList<string> symbols = DataUtil.GetSpotSymbolsForDerivatives();
            Assert.IsTrue(symbols.Contains("BRK"));
            Assert.IsFalse(symbols.Contains("SBX9"));
        }

        [Test]
        public void ReadEod()
        {
            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            Assert.AreEqual(192, eods.Count);

            EOD eod = eods[0];
            Assert.AreEqual("2.7300", eod.close.ToString("0.0000"));
            Assert.AreEqual("2.7300", eod.high.ToString("0.0000"));
            Assert.AreEqual("2.7250", eod.low.ToString("0.0000"));
            Assert.AreEqual("2.7250", eod.open.ToString("0.0000"));
            Assert.AreEqual(11, eod.volume);
            Assert.AreEqual(22, eod.openInterest);
            Assert.AreEqual("2006-09-07", DTUtil.ToDateString(eod.date));
        }

        [Test]
        public void AppendTick()
        {
            Tick tick = new Tick();
            DataUtil.AppendTick("TEST", tick);
            Assert.IsFalse(File.Exists(DataUtil.GetTickFileName("TEST")));

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2007-08-01 10:00:00");
            DataUtil.AppendTick("TEST", tick);
            Assert.IsTrue(File.Exists(DataUtil.GetTickFileName("TEST")));

            File.Delete(DataUtil.GetTickFileName("TEST"));
        }

        [Test]
        public void WriteTicks_NoEntries()
        {
            File.Delete(DataUtil.GetTickFileName("TEST"));
            
            IList<Tick> ticks = new List<Tick>();
            DataUtil.WriteTicks("TEST", ticks);
            Assert.IsTrue(File.Exists(DataUtil.GetTickFileName("TEST")));
            
            ticks = DataUtil.ReadTicks("TEST");
            Assert.IsTrue(ticks.Count == 0);

            File.Delete(DataUtil.GetTickFileName("TEST"));
        }

        [Test]
        public void WriteTicks_WithEntries()
        {
            File.Delete(DataUtil.GetTickFileName("TEST"));
            
            IList<Tick> ticks = new List<Tick>();
            Tick tick = new Tick();
            tick.dateTime = DateTime.Now;
            ticks.Add(tick);
            DataUtil.WriteTicks("TEST", ticks);
            Assert.IsTrue(File.Exists(DataUtil.GetTickFileName("TEST")));
            
            ticks = DataUtil.ReadTicks("TEST");
            Assert.IsTrue(ticks.Count == 1);

            File.Delete(DataUtil.GetTickFileName("TEST"));
        }

        [Test]
        public void ReadTimeSeries()
        {
            TimeSeries ts;

            ts = DataUtil.ReadTimeSeries("UNKNOWN", TimeSeries.TICK_PERIOD);
            Assert.IsNull(ts);

            ts = DataUtil.ReadTimeSeries("DESNP-IUN07", TimeSeries.TICK_PERIOD);
            Assert.AreEqual(TimeSeries.TICK_PERIOD, ts.Period);
            Assert.AreEqual(403, ts.Count);

            ts = DataUtil.ReadTimeSeries("DESNP-IUN04", TimeSeries.TICK_PERIOD);
            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(22, ts.Count);

            ts = DataUtil.ReadTimeSeries("UNKNOWN", TimeSeries.DAY_PERIOD);
            Assert.IsNull(ts);

            ts = DataUtil.ReadTimeSeries("DESIF2-IUN07", TimeSeries.DAY_PERIOD);
            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(192, ts.Count);

            ts = DataUtil.ReadTimeSeries("DESIF2-IUN07", TimeSeries.DAY_FROM_TICK_PERIOD);
            Assert.AreEqual(TimeSeries.DAY_FROM_TICK_PERIOD, ts.Period);
            Assert.AreEqual(55, ts.Count);
            
            ts = DataUtil.ReadTimeSeries("DESIF1-DEC06", TimeSeries.DAY_PERIOD);
            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(49, ts.Count);

            ts = DataUtil.ReadTimeSeries("DESIF4-IUN07", TimeSeries.DAY_PERIOD);
            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(2, ts.Count);
        }
    }
}

#endif
