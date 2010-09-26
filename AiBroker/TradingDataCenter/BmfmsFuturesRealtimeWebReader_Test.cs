#if TEST

using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

using NUnit.Framework;
using TradingCommon;
using TradingCommon.Util;

namespace TradingDataCenter
{
    [TestFixture]
    public class BmfmsFuturesRealtimeWebReader_Test
    {
        [Test]
        public void ParseDiffs()
        {
            string page = File.ReadAllText(@"..\..\..\TestData\www_sibex_ro-tabel_piata.html");
            
            IDictionary<string, Tick> ticks = null;
            
            string expectedDateTime = "2008-01-03 10:16:25";
            DateTime actualDateTime = BmfmsFuturesRealtimeWebReader._ParseDiffs(page, out ticks);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(actualDateTime));
            Assert.AreEqual(16, ticks.Count);
            
            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESIF2-MAR08"].dateTime));
            Assert.AreEqual("3.5706", ticks["DESIF2-MAR08"].price.ToString("0.0000"));
            Assert.AreEqual(293, ticks["DESIF2-MAR08"].volume);
            Assert.AreEqual(13948, ticks["DESIF2-MAR08"].openInterest);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESNP-MAR08"].dateTime));
            Assert.AreEqual("0.5361", ticks["DESNP-MAR08"].price.ToString("0.0000"));
            Assert.AreEqual(1, ticks["DESNP-MAR08"].volume);
            Assert.AreEqual(268, ticks["DESNP-MAR08"].openInterest);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESBX9-MAR08"].dateTime));
            Assert.AreEqual("4077.0000", ticks["DESBX9-MAR08"].price.ToString("0.0000"));
            Assert.AreEqual(0, ticks["DESBX9-MAR08"].volume);
            Assert.AreEqual(0, ticks["DESBX9-MAR08"].openInterest);
        }

        [Test]
        public void ParseDiffsNoInfo()
        {
            string page = File.ReadAllText(@"..\..\..\TestData\www_sibex_ro-tabel_piata-no_info.html");
            
            IDictionary<string, Tick> ticks = null;
            
            string expectedDateTime = "2008-01-03 13:07:42";
            DateTime actualDateTime = BmfmsFuturesRealtimeWebReader._ParseDiffs(page, out ticks);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(actualDateTime));
            Assert.AreEqual(0, ticks.Count);
        }

        [Test]
        public void ParseDiffsBadInfo()
        {
            string page = File.ReadAllText(@"..\..\..\TestData\www_sibex_ro-tabel_piata-bad_info.html");
            
            IDictionary<string, Tick> ticks = null;
            
            string expectedDateTime = "2008-01-03 14:00:13";
            DateTime actualDateTime = BmfmsFuturesRealtimeWebReader._ParseDiffs(page, out ticks);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(actualDateTime));
            Assert.AreEqual(0, ticks.Count);
        }
        
        [Test]
        public void ParseDiffsBadInfo2()
        {
            string page = File.ReadAllText(@"..\..\..\TestData\www_sibex_ro-tabel_piata-bad_info_2.html");
            
            IDictionary<string, Tick> ticks = null;
            
            string expectedDateTime = "2008-01-03 16:03:13";
            DateTime actualDateTime = BmfmsFuturesRealtimeWebReader._ParseDiffs(page, out ticks);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(actualDateTime));
            Assert.AreEqual(16, ticks.Count);
            
            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESIF2-MAR08"].dateTime));
            Assert.AreEqual("3.4600", ticks["DESIF2-MAR08"].price.ToString("0.0000"));
            Assert.AreEqual(3639, ticks["DESIF2-MAR08"].volume);
            Assert.AreEqual(13796, ticks["DESIF2-MAR08"].openInterest);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESNP-MAR08"].dateTime));
            Assert.AreEqual("0.5351", ticks["DESNP-MAR08"].price.ToString("0.0000"));
            Assert.AreEqual(7, ticks["DESNP-MAR08"].volume);
            Assert.AreEqual(280, ticks["DESNP-MAR08"].openInterest);

            Assert.AreEqual(expectedDateTime, DTUtil.ToDateTimeString(ticks["DESIF5-SEP08"].dateTime));
            Assert.AreEqual("4.9900", ticks["DESIF5-SEP08"].price.ToString("0.0000"));
            Assert.AreEqual(0, ticks["DESIF5-SEP08"].volume);
            Assert.AreEqual(2, ticks["DESIF5-SEP08"].openInterest);
        }

        [Test]
        public void ProcessDiffSequences()
        {
            IDictionary<string, Tick> normalTicks;
            IDictionary<string, Tick> noTicks;
            IDictionary<string, Tick> diffTicks;

            string page = File.ReadAllText(@"..\..\..\TestData\www_sibex_ro-tabel_piata.html");
            BmfmsFuturesRealtimeWebReader._ParseDiffs(page, out normalTicks);
            
            noTicks = new Dictionary<string, Tick>();
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(normalTicks, noTicks, out diffTicks);
            Assert.AreEqual(16, normalTicks.Count);
            Assert.AreEqual(16, noTicks.Count);
            Assert.AreEqual(0, diffTicks.Count);

            noTicks = new Dictionary<string, Tick>();
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(noTicks, normalTicks, out diffTicks);
            Assert.AreEqual(16, normalTicks.Count);
            Assert.AreEqual(0, noTicks.Count);
            Assert.AreEqual(16, diffTicks.Count);

            noTicks = new Dictionary<string, Tick>();
            noTicks["A"] = new Tick();
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(normalTicks, noTicks, out diffTicks);
            Assert.AreEqual(16, normalTicks.Count);
            Assert.AreEqual(17, noTicks.Count);
            Assert.AreEqual(1, diffTicks.Count);
        }
       
        [Test]
        public void ProcessDiff_Tick_New()
        {
            Tick tick;
                
            IDictionary<string, Tick> oldTicks = new Dictionary<string, Tick>();
            
            IDictionary<string, Tick> newTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.volume = 10;
            newTicks["A"] = tick;
            
            IDictionary<string, Tick> ticks = new Dictionary<string, Tick>();
            
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(oldTicks, newTicks, out ticks);
            
            Assert.AreEqual(1, ticks.Count);
            Assert.AreEqual(10, ticks["A"].volume);
        }

        [Test]
        public void ProcessDiff_Tick_SameVolume()
        {
            Tick tick;
                
            IDictionary<string, Tick> oldTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.volume = 10;
            oldTicks["A"] = tick;
            
            IDictionary<string, Tick> newTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.volume = 10;
            newTicks["A"] = tick;
            
            IDictionary<string, Tick> ticks = new Dictionary<string, Tick>();
            
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(oldTicks, newTicks, out ticks);
            
            Assert.AreEqual(0, ticks.Count);
        }

        [Test]
        public void ProcessDiff_Tick_GreaterVolume()
        {
            Tick tick;
                
            IDictionary<string, Tick> oldTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2010-10-10 10:10:10");
            tick.price = 1;
            tick.volume = 1;
            tick.openInterest = 1;
            oldTicks["A"] = tick;
            
            IDictionary<string, Tick> newTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2010-10-10 10:10:12");
            tick.price = 2;
            tick.volume = 2;
            tick.openInterest = 2;
            newTicks["A"] = tick;
            
            IDictionary<string, Tick> ticks = new Dictionary<string, Tick>();
            
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(oldTicks, newTicks, out ticks);
            
            Assert.AreEqual(1, ticks.Count);
            Assert.AreEqual("2010-10-10 10:10:12", DTUtil.ToDateTimeString(ticks["A"].dateTime));
            Assert.AreEqual(2, ticks["A"].price);
            Assert.AreEqual(1, ticks["A"].volume);
            Assert.AreEqual(2, ticks["A"].openInterest);
        }

        [Test]
        public void ProcessDiff_Tick_LesserVolume()
        {
            Tick tick;
                
            IDictionary<string, Tick> oldTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.volume = 10;
            oldTicks["A"] = tick;
            
            IDictionary<string, Tick> newTicks = new Dictionary<string, Tick>();
            tick = new Tick();
            tick.volume = 1;
            newTicks["A"] = tick;
            
            IDictionary<string, Tick> ticks = new Dictionary<string, Tick>();
            
            BmfmsFuturesRealtimeWebReader._ProcessDiffs(oldTicks, newTicks, out ticks);
            
            Assert.AreEqual(0, ticks.Count);
            Assert.AreEqual(10, newTicks["A"].volume);
        }
        
        [Test]
        public void RegEx()
        {
            string table = "><a href=\"../../../?sibex=tranzactionare/grafice&symbol=DESIF2&scadenta=MAR08\" target=\"_parent\" ><img title=\"Grafic\"  src=\"../../../img/chenar_tabel/graphic.png\" class=\"imageFlag\" /></a></a><";
            Regex rex = new Regex("<a href.*?</a>");
            table = rex.Replace(table, "");
            Assert.AreEqual("></a><", table);
        }
    }
}

#endif
