#if TEST

using System;
using NUnit.Framework;

namespace TradingCommon.Traders
{

    [TestFixture]
    public class Operation_Test
    {
        [Test]
        public void LogicalEquality()
        {
            Operation x = new Operation(Operation.BUY);
            Operation y = new Operation(Operation.NONE);
            Operation z = new Operation(Operation.SELL);
            Assert.AreNotEqual(x, y);
            Assert.AreNotEqual(y, z);
            Assert.AreNotEqual(x, z);
            Assert.IsTrue(x.Equals(Operation.buy));
        }

        [Test]
        public void LogicalEqualityReflexivity()
        {
            Operation x = new Operation(Operation.BUY);
            Assert.IsTrue(x.Equals(x));
        }

        [Test]
        public void LogicalEqualitySymmetry()
        {
            Operation x = new Operation(Operation.BUY);
            Operation y = new Operation(Operation.NONE);
            Assert.AreEqual(x.Equals(y), y.Equals(x));
        }

        [Test]
        public void LogicalEqualityTransitivity()
        {
            Operation x = new Operation(Operation.BUY);
            Operation y = new Operation(Operation.BUY);
            Operation z = new Operation(Operation.BUY);
            Assert.AreNotSame(x, y);
            Assert.AreNotSame(y, z);
            Assert.AreNotSame(x, z);
            Assert.IsTrue(x.Equals(y));
            Assert.IsTrue(y.Equals(z));
            Assert.IsTrue(x.Equals(z));
        }

        [Test]
        public void LogicalEqualitySuccessiveClaas()
        {
            Operation x = new Operation(Operation.BUY);
            Operation y = new Operation(Operation.NONE);
            Assert.AreEqual(x.Equals(y), x.Equals(y));
        }

        [Test]
        public void LogicalEqualityNullComparison()
        {
            Operation x = new Operation(Operation.NONE);
            Assert.IsFalse(x.Equals(null));
        }

        [Test]
        public void LogicalIntEquality()
        {
            Operation x = new Operation(Operation.BUY);
            Assert.AreEqual(x, Operation.BUY);
            Assert.AreEqual(Operation.BUY, x);
        }

        [Test]
        public void ReferenceEquality()
        {
            Operation o1 = new Operation(Operation.BUY);
            Operation o2 = new Operation(Operation.BUY);
            Assert.IsFalse(o1 == o2);
        }

        [Test]
        public void TildaOperator()
        {
            Operation o = new Operation(Operation.BUY);
            Assert.AreEqual(Operation.sell, ~o);
            Assert.AreEqual(Operation.buy, ~~o);
            o = new Operation(Operation.NONE);
            Assert.AreEqual(Operation.none, ~o);
            Assert.AreEqual(Operation.none, ~~o);
        }
    }
}

#endif
