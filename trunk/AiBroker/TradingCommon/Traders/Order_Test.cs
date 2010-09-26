#if TEST

using System;
using NUnit.Framework;

namespace TradingCommon.Traders
{
    [TestFixture]
    public class Order_Test
    {
        [Test]
        public void SumOrdersNone()
        {
            Operation op;
            int vol;

            Order.SumOrders(Operation.none, 0, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 0, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 0, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.none, 0, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 0, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 0, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            
            Order.SumOrders(Operation.none, 0, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 0, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 0, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.none, 1, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 1, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 1, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.none, 1, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 1, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 1, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.none, 1, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 1, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 1, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.none, 2, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 2, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 2, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.none, 2, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 2, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 2, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.none, 2, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.none, 2, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.none, 2, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);
        }

        [Test]
        public void SumOrdersSell()
        {
            Operation op;
            int vol;

            Order.SumOrders(Operation.sell, 0, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.sell, 0, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.sell, 0, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.sell, 0, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.sell, 0, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 0, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.sell, 0, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.sell, 0, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 0, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.sell, 1, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 1, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 1, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);


            Order.SumOrders(Operation.sell, 1, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 1, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.sell, 1, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(3, vol);


            Order.SumOrders(Operation.sell, 1, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 1, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.sell, 1, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);


            Order.SumOrders(Operation.sell, 2, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.sell, 2, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.sell, 2, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.sell, 2, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.sell, 2, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(3, vol);

            Order.SumOrders(Operation.sell, 2, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(4, vol);


            Order.SumOrders(Operation.sell, 2, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.sell, 2, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.sell, 2, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);
        }

        [Test]
        public void SumOrdersBuy()
        {
            Operation op;
            int vol;

            Order.SumOrders(Operation.buy, 0, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.buy, 0, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.buy, 0, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.buy, 0, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.buy, 0, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 0, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.buy, 0, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.buy, 0, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 0, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.buy, 1, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 1, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 1, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);


            Order.SumOrders(Operation.buy, 1, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 1, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);

            Order.SumOrders(Operation.buy, 1, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.sell, op);
            Assert.AreEqual(1, vol);


            Order.SumOrders(Operation.buy, 1, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 1, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.buy, 1, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(3, vol);


            Order.SumOrders(Operation.buy, 2, Operation.none, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.buy, 2, Operation.none, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.buy, 2, Operation.none, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);


            Order.SumOrders(Operation.buy, 2, Operation.sell, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.buy, 2, Operation.sell, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(1, vol);

            Order.SumOrders(Operation.buy, 2, Operation.sell, 2, out op, out vol);
            Assert.AreEqual(Operation.none, op);
            Assert.AreEqual(0, vol);


            Order.SumOrders(Operation.buy, 2, Operation.buy, 0, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(2, vol);

            Order.SumOrders(Operation.buy, 2, Operation.buy, 1, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(3, vol);

            Order.SumOrders(Operation.buy, 2, Operation.buy, 2, out op, out vol);
            Assert.AreEqual(Operation.buy, op);
            Assert.AreEqual(4, vol);
        }
    }
}

#endif
