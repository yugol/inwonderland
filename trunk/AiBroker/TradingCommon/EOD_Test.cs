#if TEST

using System;
using NUnit.Framework;
using TradingCommon.Util;

namespace TradingCommon
{
    [TestFixture]
    public class EOD_Test
    {
        [Test]
        public void EntryConversion_Simple()
        {
            EOD eod = new EOD();
            eod.close = 1;
            eod.date = DTUtil.ParseDate("2007-08-01");
            eod.high = 2;
            eod.low = 3;
            eod.open = 4;
            eod.volume = 5;

            string entry = eod.ToEntry("_XYZ");
            Assert.AreEqual("_XYZ,2007-08-01,4,2,3,1,5", entry);

            eod = EOD.FromEntry(entry);
            Assert.AreEqual(1, eod.close);
            Assert.AreEqual(2, eod.high);
            Assert.AreEqual(3, eod.low);
            Assert.AreEqual(4, eod.open);
            Assert.AreEqual(5, eod.volume);
            Assert.AreEqual(-1, eod.openInterest);
            Assert.AreEqual("2007-08-01", DTUtil.ToDateString(eod.date));
        }

        [Test]
        public void EntryConversion_OpenInterest()
        {
            EOD eod = new EOD();
            eod.close = 1;
            eod.date = DTUtil.ParseDate("2007-08-01");
            eod.high = 2;
            eod.low = 3;
            eod.open = 4;
            eod.volume = 5;
            eod.openInterest = 6;

            string entry = eod.ToEntry("_XYZ");
            Assert.AreEqual("_XYZ,2007-08-01,4,2,3,1,5,6", entry);

            eod = EOD.FromEntry(entry);
            Assert.AreEqual(1, eod.close);
            Assert.AreEqual(2, eod.high);
            Assert.AreEqual(3, eod.low);
            Assert.AreEqual(4, eod.open);
            Assert.AreEqual(5, eod.volume);
            Assert.AreEqual(6, eod.openInterest);
            Assert.AreEqual("2007-08-01", DTUtil.ToDateString(eod.date));
        }
    }
}

#endif
