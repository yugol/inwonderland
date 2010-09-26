using System;
using NUnit.Framework;
using TradingCommon.Util;

namespace TradingCommon
{
    [TestFixture]
    public class Tick_Test
    {
        [Test]
        public void EntryConversion_Simple()
        {
            Tick tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2007-08-01 09:59:50");
            tick.price = 1;
            tick.volume = 2;

            string entry = tick.ToEntry("_XYZ");
            Assert.AreEqual("_XYZ,2007-08-01,09:59:50,1,2", entry);

            tick = Tick.FromEntry(entry);
            Assert.AreEqual("2007-08-01 09:59:50", DTUtil.ToDateTimeString(tick.dateTime));
            Assert.AreEqual(1,tick.price);
            Assert.AreEqual(2,tick.volume);
            Assert.AreEqual(-1,tick.openInterest);
        }

        [Test]
        public void EntryConversion_OpenInterest()
        {
            Tick tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2007-08-01 09:59:50");
            tick.price = 1;
            tick.volume = 2;
            tick.openInterest = 3;

            string entry = tick.ToEntry("_XYZ");
            Assert.AreEqual("_XYZ,2007-08-01,09:59:50,1,2,3", entry);

            tick = Tick.FromEntry(entry);
            Assert.AreEqual("2007-08-01 09:59:50", DTUtil.ToDateTimeString(tick.dateTime));
            Assert.AreEqual(1, tick.price);
            Assert.AreEqual(2, tick.volume);
            Assert.AreEqual(3, tick.openInterest);
        }
    }
}
