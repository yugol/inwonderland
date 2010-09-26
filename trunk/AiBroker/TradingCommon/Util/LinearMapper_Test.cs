using System;
using NUnit.Framework;

namespace TradingCommon.Util
{
    [TestFixture]
    public class LinearMapper_Test
    {
        [Test]
        public void testMapShift()
        {
            LinearMapper linearMapper = new LinearMapper(2, 4, 1, 3);

            Assert.AreEqual(0.0, linearMapper.Map(1));
            Assert.AreEqual(1.0, linearMapper.Map(2));
            Assert.AreEqual(2.0, linearMapper.Map(3));
            Assert.AreEqual(3.0, linearMapper.Map(4));
            Assert.AreEqual(4.0, linearMapper.Map(5));

            Assert.AreEqual(1.0, linearMapper.Unmap(0.0));
            Assert.AreEqual(2.0, linearMapper.Unmap(1.0));
            Assert.AreEqual(3.0, linearMapper.Unmap(2.0));
            Assert.AreEqual(4.0, linearMapper.Unmap(3.0));
            Assert.AreEqual(5.0, linearMapper.Unmap(4.0));
        }

        [Test]
        public void testMapScale()
        {
            LinearMapper linearMapper = new LinearMapper(2, 4, 1, 2);

            Assert.AreEqual(0.5, linearMapper.Map(1));
            Assert.AreEqual(1.0, linearMapper.Map(2));
            Assert.AreEqual(1.5, linearMapper.Map(3));
            Assert.AreEqual(2.0, linearMapper.Map(4));
            Assert.AreEqual(2.5, linearMapper.Map(5));

            Assert.AreEqual(1.0, linearMapper.Unmap(.5));
            Assert.AreEqual(2.0, linearMapper.Unmap(1.0));
            Assert.AreEqual(3.0, linearMapper.Unmap(1.5));
            Assert.AreEqual(4.0, linearMapper.Unmap(2.0));
            Assert.AreEqual(5.0, linearMapper.Unmap(2.5));
        }

        [Test]
        public void testMapReverseScale()
        {
            LinearMapper linearMapper = new LinearMapper(0, 100, 20, 0);

            Assert.AreEqual(20.0, linearMapper.Map(0));
            Assert.AreEqual(0.0, linearMapper.Map(100));
            Assert.AreEqual(10.0, linearMapper.Map(50));

            Assert.AreEqual(0.0, linearMapper.Unmap(20));
            Assert.AreEqual(100.0, linearMapper.Unmap(0));
            Assert.AreEqual(50.0, linearMapper.Unmap(10));
        }

        [Test]
        public void testDx0()
        {
            LinearMapper linearMapper = new LinearMapper(1, 1, 2, 3);
            Assert.AreEqual(2.0, linearMapper.Map(1));
            Assert.AreEqual(1.0, linearMapper.Unmap(3));
        }

        [Test]
        public void testDy0()
        {
            LinearMapper linearMapper = new LinearMapper(1, 3, 2, 2);
            Assert.AreEqual(2.0, linearMapper.Map(1));
            Assert.AreEqual(1.0, linearMapper.Unmap(2));
        }

        [Test]
        public void testDxDy0()
        {
            LinearMapper linearMapper = new LinearMapper(1, 1, 2, 2);
            Assert.AreEqual(2.0, linearMapper.Map(1));
            Assert.AreEqual(1.0, linearMapper.Unmap(2));
        }

        [Test]
        public void testStaticMap()
        {
            Assert.AreEqual(0.0, LinearMapper.Map(1, 2, 4, 1, 3));
            Assert.AreEqual(1.0, LinearMapper.Map(2, 2, 4, 1, 3));
            Assert.AreEqual(2.0, LinearMapper.Map(3, 2, 4, 1, 3));
            Assert.AreEqual(3.0, LinearMapper.Map(4, 2, 4, 1, 3));
            Assert.AreEqual(4.0, LinearMapper.Map(5, 2, 4, 1, 3));

            Assert.AreEqual(0.5, LinearMapper.Map(1, 2, 4, 1, 2));
            Assert.AreEqual(1.0, LinearMapper.Map(2, 2, 4, 1, 2));
            Assert.AreEqual(1.5, LinearMapper.Map(3, 2, 4, 1, 2));
            Assert.AreEqual(2.0, LinearMapper.Map(4, 2, 4, 1, 2));
            Assert.AreEqual(2.5, LinearMapper.Map(5, 2, 4, 1, 2));

            Assert.AreEqual(20.0, LinearMapper.Map(0, 0, 100, 20, 0));
            Assert.AreEqual(0.0, LinearMapper.Map(100, 0, 100, 20, 0));
            Assert.AreEqual(10.0, LinearMapper.Map(50, 0, 100, 20, 0));
        }

    }
}
