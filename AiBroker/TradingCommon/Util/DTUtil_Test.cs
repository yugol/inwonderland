#if TEST

using System;
using NUnit.Framework;

namespace TradingCommon.Util
{
    [TestFixture]
    public class DTUtil_Test
    {
        [Test]
        public void GetContractExpirationDateFor()
        {
            Assert.AreEqual(new DateTime(2007, 3, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 1, 1)));
            Assert.AreEqual(new DateTime(2007, 3, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 3, 30)));
            Assert.AreEqual(new DateTime(2007, 3, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 3, 31)));

            Assert.AreEqual(new DateTime(2007, 6, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 4, 1)));
            Assert.AreEqual(new DateTime(2007, 6, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 6, 29)));
            Assert.AreEqual(new DateTime(2007, 6, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 6, 30)));

            Assert.AreEqual(new DateTime(2007, 9, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 7, 1)));
            Assert.AreEqual(new DateTime(2007, 9, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 9, 29)));
            Assert.AreEqual(new DateTime(2007, 9, 30), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 9, 30)));

            Assert.AreEqual(new DateTime(2007, 12, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 10, 1)));
            Assert.AreEqual(new DateTime(2007, 12, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 12, 30)));
            Assert.AreEqual(new DateTime(2007, 12, 31), DTUtil.GetContractExpirationDateFor(new DateTime(2007, 12, 31)));
        }

        [Test]
        public void Date2Maturity()
        {
            Assert.AreEqual("MAR05", DTUtil.Date2Maturity(new DateTime(2005, 1, 30)));
            Assert.AreEqual("IUN06", DTUtil.Date2Maturity(new DateTime(2006, 5, 15)));
            Assert.AreEqual("SEP07", DTUtil.Date2Maturity(new DateTime(2007, 9, 10)));
            Assert.AreEqual("DEC08", DTUtil.Date2Maturity(new DateTime(2008, 11, 1)));
        }
        
        [Test]
        public void IsLeap()
        {
            // non divisible by 4
            Assert.IsFalse(DTUtil.IsLeap(1999));
            Assert.IsFalse(DTUtil.IsLeap(2001));
            Assert.IsFalse(DTUtil.IsLeap(2002));
            Assert.IsFalse(DTUtil.IsLeap(2003));

            // divisible by 4
            Assert.IsTrue(DTUtil.IsLeap(1996));
            Assert.IsTrue(DTUtil.IsLeap(2004));
            Assert.IsTrue(DTUtil.IsLeap(2008));

            // divisible by 100
            Assert.IsFalse(DTUtil.IsLeap(1900));
            Assert.IsFalse(DTUtil.IsLeap(2100));
            Assert.IsFalse(DTUtil.IsLeap(2200));
            Assert.IsFalse(DTUtil.IsLeap(2300));
            Assert.IsFalse(DTUtil.IsLeap(2500));
            Assert.IsFalse(DTUtil.IsLeap(2600));
            Assert.IsFalse(DTUtil.IsLeap(2700));
            
            // divisible by 400
            Assert.IsTrue(DTUtil.IsLeap(2000));
            Assert.IsTrue(DTUtil.IsLeap(2400));
            Assert.IsTrue(DTUtil.IsLeap(2800));
        }

        [Test]
        public void Maturity2Date()
        {
            Assert.AreEqual(new DateTime(2008, 1, 31, 0, 0, 0), DTUtil.Maturity2Date("IAN08"));
            Assert.AreEqual(new DateTime(2007, 2, 28, 0, 0, 0), DTUtil.Maturity2Date("FEB07"));
            Assert.AreEqual(new DateTime(2008, 2, 29, 0, 0, 0), DTUtil.Maturity2Date("FEB08"));
            Assert.AreEqual(new DateTime(2008, 3, 31, 0, 0, 0), DTUtil.Maturity2Date("MAR08"));
            Assert.AreEqual(new DateTime(2008, 4, 30, 0, 0, 0), DTUtil.Maturity2Date("APR08"));
            Assert.AreEqual(new DateTime(2008, 5, 31, 0, 0, 0), DTUtil.Maturity2Date("MAI08"));
            Assert.AreEqual(new DateTime(2008, 6, 30, 0, 0, 0), DTUtil.Maturity2Date("IUN08"));
            Assert.AreEqual(new DateTime(2008, 7, 31, 0, 0, 0), DTUtil.Maturity2Date("IUL08"));
            Assert.AreEqual(new DateTime(2008, 8, 31, 0, 0, 0), DTUtil.Maturity2Date("AUG08"));
            Assert.AreEqual(new DateTime(2008, 9, 30, 0, 0, 0), DTUtil.Maturity2Date("SEP08"));
            Assert.AreEqual(new DateTime(2008, 10, 31, 0, 0, 0), DTUtil.Maturity2Date("OCT08"));
            Assert.AreEqual(new DateTime(2008, 11, 30, 0, 0, 0), DTUtil.Maturity2Date("NOV08"));
            Assert.AreEqual(new DateTime(2008, 12, 31, 0, 0, 0), DTUtil.Maturity2Date("DEC08"));
        }        
    }
}

#endif
