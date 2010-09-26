#if TEST

using System;
using NUnit.Framework;
using TradingCommon.Util;

namespace TradingCommon.Indicators
{
    [TestFixture]
    public class Delta_Test
    {
        [Test]
        public void testGetItdColor()
        {
            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2006-01-14")));
            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2006-02-13")));
            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2006-03-15")));
            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2006-04-13")));
            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2006-05-13")));
            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2006-06-11")));
            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2006-07-11")));
            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2006-08-09")));
            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2006-09-07")));
            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2006-10-07")));
            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2006-11-05")));
            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2006-12-05")));

            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2007-01-03")));
            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2007-01-04")));
            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2007-02-01")));

            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2007-02-02")));
            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2007-02-03")));
            Assert.AreEqual(Delta.ORANGE, Delta.ItdColor(DTUtil.ParseDate("2007-03-02")));

            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2007-03-04")));
            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2007-03-05")));
            Assert.AreEqual(Delta.GREEN, Delta.ItdColor(DTUtil.ParseDate("2007-04-01")));

            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2007-04-02")));
            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2007-04-03")));
            Assert.AreEqual(Delta.RED, Delta.ItdColor(DTUtil.ParseDate("2007-05-01")));

            Assert.AreEqual(Delta.BLUE, Delta.ItdColor(DTUtil.ParseDate("2007-05-02")));
        }

        [Test]
        public void testGetLtdColor()
        {
            Assert.AreEqual(Delta.RED, Delta.LtdColor(DTUtil.ParseDate("2000-01-01")));
            Assert.AreEqual(Delta.BLUE, Delta.LtdColor(DTUtil.ParseDate("2001-01-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.LtdColor(DTUtil.ParseDate("2002-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.LtdColor(DTUtil.ParseDate("2003-01-01")));
            Assert.AreEqual(Delta.RED, Delta.LtdColor(DTUtil.ParseDate("2004-01-01")));
        }

        [Test]
        public void testGetMtdColor()
        {
            Assert.AreEqual(Delta.BLUE, Delta.MtdColor(DTUtil.ParseDate("2007-01-03")));

            Assert.AreEqual(Delta.ORANGE, Delta.MtdColor(DTUtil.ParseDate("2007-02-02")));
            Assert.AreEqual(Delta.ORANGE, Delta.MtdColor(DTUtil.ParseDate("2007-03-04")));
            Assert.AreEqual(Delta.ORANGE, Delta.MtdColor(DTUtil.ParseDate("2007-04-02")));

            Assert.AreEqual(Delta.GREEN, Delta.MtdColor(DTUtil.ParseDate("2007-05-02")));
            Assert.AreEqual(Delta.GREEN, Delta.MtdColor(DTUtil.ParseDate("2007-06-01")));
            Assert.AreEqual(Delta.GREEN, Delta.MtdColor(DTUtil.ParseDate("2007-06-30")));

            Assert.AreEqual(Delta.RED, Delta.MtdColor(DTUtil.ParseDate("2007-07-30")));
            Assert.AreEqual(Delta.RED, Delta.MtdColor(DTUtil.ParseDate("2007-08-28")));
            Assert.AreEqual(Delta.RED, Delta.MtdColor(DTUtil.ParseDate("2007-09-26")));

            Assert.AreEqual(Delta.BLUE, Delta.MtdColor(DTUtil.ParseDate("2007-10-26")));
            Assert.AreEqual(Delta.BLUE, Delta.MtdColor(DTUtil.ParseDate("2007-11-24")));
            Assert.AreEqual(Delta.BLUE, Delta.MtdColor(DTUtil.ParseDate("2007-12-24")));

            Assert.AreEqual(Delta.ORANGE, Delta.MtdColor(DTUtil.ParseDate("2008-01-22")));
        }

        [Test]
        public void testGetSltdColor()
        {
            Assert.AreEqual(Delta.GREEN, Delta.SltdColor(DTUtil.ParseDate("1999-12-31")));
            Assert.AreEqual(Delta.RED, Delta.SltdColor(DTUtil.ParseDate("2000-01-01")));
            Assert.AreEqual(Delta.RED, Delta.SltdColor(DTUtil.ParseDate("2000-01-02")));

            Assert.AreEqual(Delta.RED, Delta.SltdColor(DTUtil.ParseDate("2004-09-30")));
            Assert.AreEqual(Delta.BLUE, Delta.SltdColor(DTUtil.ParseDate("2004-10-01")));
            Assert.AreEqual(Delta.BLUE, Delta.SltdColor(DTUtil.ParseDate("2004-10-02")));

            Assert.AreEqual(Delta.BLUE, Delta.SltdColor(DTUtil.ParseDate("2009-06-30")));
            Assert.AreEqual(Delta.ORANGE, Delta.SltdColor(DTUtil.ParseDate("2009-07-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.SltdColor(DTUtil.ParseDate("2009-07-02")));

            Assert.AreEqual(Delta.ORANGE, Delta.SltdColor(DTUtil.ParseDate("2014-03-31")));
            Assert.AreEqual(Delta.GREEN, Delta.SltdColor(DTUtil.ParseDate("2014-04-01")));
            Assert.AreEqual(Delta.GREEN, Delta.SltdColor(DTUtil.ParseDate("2014-04-02")));

            Assert.AreEqual(Delta.GREEN, Delta.SltdColor(DTUtil.ParseDate("2018-12-31")));
            Assert.AreEqual(Delta.RED, Delta.SltdColor(DTUtil.ParseDate("2019-01-01")));
            Assert.AreEqual(Delta.RED, Delta.SltdColor(DTUtil.ParseDate("2019-01-02")));
        }

        [Test]
        public void testGetStdColor()
        {
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDateTime("1991-01-01 00:01:00")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("1991-02-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("1991-03-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("1991-04-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("1991-05-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("1991-06-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("1991-07-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("1991-08-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("1991-09-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("1991-10-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("1991-11-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("1991-12-01")));

            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("1992-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("1993-01-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("1994-01-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("1995-01-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("1996-01-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("1997-01-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("1998-01-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("1999-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("2000-01-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("2001-01-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("2002-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("2003-01-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("2004-01-01")));
            Assert.AreEqual(Delta.ORANGE, Delta.StdColor(DTUtil.ParseDate("2005-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("2006-01-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("2007-01-01")));
            Assert.AreEqual(Delta.BLUE, Delta.StdColor(DTUtil.ParseDate("2008-01-01")));
            Assert.AreEqual(Delta.GREEN, Delta.StdColor(DTUtil.ParseDate("2009-01-01")));
            Assert.AreEqual(Delta.RED, Delta.StdColor(DTUtil.ParseDate("2010-01-01")));
        }
    }
}

#endif
