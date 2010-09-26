#if TEST

using System;
using NUnit.Framework;

namespace TradingAgent
{
    [TestFixture]    
    public class PlacedOrder_Test
    {
        [Test]
        public void LogicalEqualityNullComparison()
        {
            PlacedOrder order = new PlacedOrder();
            Assert.IsFalse(order.Equals(null));
        }

        [Test]
        public void LogicalEquality()
        {
            PlacedOrder order1 = new PlacedOrder();
            order1.id = 10;
            PlacedOrder order2 = new PlacedOrder();
            order2.id = 10;
            Assert.AreEqual(order1, order2);
        }

        [Test]
        public void LogicalInEquality()
        {
            PlacedOrder order1 = new PlacedOrder();
            order1.id = 10;
            PlacedOrder order2 = new PlacedOrder();
            order2.id = 20;
            Assert.AreNotEqual(order1, order2);
        }
    }
}

#endif
