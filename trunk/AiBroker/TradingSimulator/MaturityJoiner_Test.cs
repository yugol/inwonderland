#if TEST

using System;
using System.Collections.Generic;
using System.IO;
using TradingCommon;
using TradingCommon.Storage;
using NUnit.Framework;
using TradingCommon.Util;


namespace TradingSimulator
{
    [TestFixture]
    public class MaturityJoiner_Test
    {
        [Test]
        public void Join()
        {
            MaturityJoiner joiner = new MaturityJoiner("DESIF2");
            string joinFilePath = Path.Combine(Configuration.DATA_FOLDER, DataUtil.GetTickFileName(joiner.GetJoinSymbol()));
            File.Delete(joinFilePath);
            joiner.Join();
            Assert.IsTrue(File.Exists(joinFilePath));
            File.Delete(joinFilePath);
        }
        
        [Test]
        public void GetJoinSymbol()
        {
             MaturityJoiner joiner = new MaturityJoiner("DESIF2");
             Assert.AreEqual("DESIF2-DEC99", joiner.GetJoinSymbol());
        }

        [Test]
        public void JoinTicks()
        {
            MaturityJoiner joiner = new MaturityJoiner("DESIF2");
            IList<Tick> ticks = joiner.JoinTicks();
            
            Assert.AreEqual("2007-04-10 10:00:15", DTUtil.ToDateTimeString(ticks[0].dateTime));
            
            Assert.AreEqual("2007-06-29 12:40:09", DTUtil.ToDateTimeString(ticks[25176].dateTime));
            Assert.AreEqual("2007-07-02 10:01:49", DTUtil.ToDateTimeString(ticks[25177].dateTime));
            Assert.AreEqual("-0.0100", (ticks[25177].price - ticks[25176].price).ToString("0.0000"));
            
            Assert.AreEqual("2007-09-28 12:44:50", DTUtil.ToDateTimeString(ticks[43943].dateTime));
            Assert.AreEqual("2007-10-01 10:00:54", DTUtil.ToDateTimeString(ticks[43944].dateTime));
            Assert.AreEqual("-0.0100", (ticks[43944].price - ticks[43943].price).ToString("0.0000"));
            
            Assert.AreEqual("2007-12-21 12:44:06", DTUtil.ToDateTimeString(ticks[63968].dateTime));
            Assert.AreEqual("2008-01-03 10:00:21", DTUtil.ToDateTimeString(ticks[63969].dateTime));
            Assert.AreEqual("-0.1404", (ticks[63969].price - ticks[63968].price).ToString("0.0000"));
        }

        [Test]
        public void GetTerminalTicks()
        {
            MaturityJoiner joiner = new MaturityJoiner("DESIF2");
            double preTerminalPrice;
            
            IList<Tick> ticks = joiner.GetTerminalTicks("IUN07", "SEP07", out preTerminalPrice);
            Assert.AreEqual(3.68, preTerminalPrice);
            Assert.AreEqual("2007-07-02 10:01:49", DTUtil.ToDateTimeString(ticks[0].dateTime));
            Assert.AreEqual(18767, ticks.Count);
            
            ticks = joiner.GetTerminalTicks("SEP07", "DEC07", out preTerminalPrice);
            Assert.AreEqual(3.57, preTerminalPrice);
            Assert.AreEqual("2007-10-01 10:00:54", DTUtil.ToDateTimeString(ticks[0].dateTime));
            Assert.AreEqual(20025, ticks.Count);
        }
    }
}
#endif
