#if TEST

using System;
using NUnit.Framework;
using TradingCommon.Util;

namespace TradingCommon.Traders
{
    [TestFixture]
    public class Transaction_Test
    {
        [Test]
        public void LogicalEqualityNullComparison()
        {
            Transaction x = new Transaction();
            Assert.IsFalse(x.Equals(null));
        }


        [Test]
        public void LogicalEquality()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            x.operation = Operation.buy;
            x.price = 10;
            x.symbol = "ABC";
            x.volume = 7;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreEqual(x, y);
        }

        [Test]
        public void LogicalInequality_dateTime()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:13");
            x.operation = Operation.buy;
            x.price = 10;
            x.symbol = "ABC";
            x.volume = 7;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreNotEqual(x, y);
        }

        [Test]
        public void LogicalInequality_operation()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            x.operation = Operation.sell;
            x.price = 10;
            x.symbol = "ABC";
            x.volume = 7;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreNotEqual(x, y);
        }

        [Test]
        public void LogicalInequality_price()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            x.operation = Operation.buy;
            x.price = 12;
            x.symbol = "ABC";
            x.volume = 7;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreNotEqual(x, y);
        }

        [Test]
        public void LogicalInequality_symbol()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            x.operation = Operation.buy;
            x.price = 10;
            x.symbol = "ABCD";
            x.volume = 7;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreNotEqual(x, y);
        }

        [Test]
        public void LogicalInequality_volume()
        {
            Transaction x = new Transaction();
            x.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            x.operation = Operation.buy;
            x.price = 10;
            x.symbol = "ABC";
            x.volume = 6;

            Transaction y = new Transaction();
            y.dateTime = DTUtil.ParseDateTime("2007-10-10 12:13:14");
            y.operation = Operation.buy;
            y.price = 10;
            y.symbol = "ABC";
            y.volume = 7;

            Assert.AreNotEqual(x, y);
        }
    }
}

#endif
