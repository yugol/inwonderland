#if TEST

using System;
using NUnit.Framework;

namespace TradingCommon
{
    [TestFixture]
    public class Title_Test
    {
        [Test]
        public void Parse_Simple()
        {
            Title title = Title.Parse("ABC");
            Assert.AreEqual("ABC", title.Symbol);
            Assert.AreEqual("", title.Maturity);
            Assert.AreEqual("ABC", title.FullSymbol);
        }

        [Test]
        public void Parse_SimpleSpaces()
        {
            Title title = Title.Parse("\tABC ");
            Assert.AreEqual("ABC", title.Symbol);
            Assert.AreEqual("", title.Maturity);
            Assert.AreEqual("ABC", title.FullSymbol);
        }

        [Test]
        public void Parse_Deriv()
        {
            Title title = Title.Parse("ABC-DEF");
            Assert.AreEqual("ABC", title.Symbol);
            Assert.AreEqual("DEF", title.Maturity);
            Assert.AreEqual("ABC-DEF", title.FullSymbol);
        }

        [Test]
        public void Parse_DerivSpaces()
        {
            Title title = Title.Parse(" ABC\t\t\t - DEF ");
            Assert.AreEqual("ABC", title.Symbol);
            Assert.AreEqual("DEF", title.Maturity);
            Assert.AreEqual("ABC-DEF", title.FullSymbol);
        }

        [Test]
        public void Parse_DerivSpacesMulti()
        {
            Title title = Title.Parse(" ABC\t\t\t - DEF - GHI");
            Assert.AreEqual("ABC", title.Symbol);
            Assert.AreEqual("DEF - GHI", title.Maturity);
            Assert.AreEqual("ABC-DEF - GHI", title.FullSymbol);
        }
    }
}

#endif
