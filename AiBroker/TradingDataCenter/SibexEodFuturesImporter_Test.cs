/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 20/06/2008
 * Time: 07:10
 * 
 */
#if TEST

using System;
using System.Collections.Generic;
using System.IO;

using NUnit.Framework;
using TradingCommon;
using TradingCommon.Util;

namespace TradingDataCenter
{
    [TestFixture]
    public class SibexEodFuturesImporter_Test
    {
        [Test]
        public void GrtUrl()
        {
            string expected = "http://www.tranzactiibursiere.ro/detalii/istoric_futures_csv?lang=en&symbol=&settle_date=&date=2007-01-03&x=%2C";
            string actual = new SibexEodFuturesImporter().GetUriString(DTUtil.ParseDate("2007-01-03"));
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void GetTempDownloadedFilePath()
        {
            Assert.AreEqual(Path.Combine(Configuration.TEMP_FOLDER, "temp.FUTURES.eod.20070103.csv"),
                            new SibexEodFuturesImporter().GetTempDownloadedFilePath(DTUtil.ParseDate("2007-01-03")));
        }

        [Test]
        public void ReadEODs()
        {
            string filePath = @"..\..\..\TestData\FUTURES.EOD.csv";
            IList<TransferEOD> eods = SibexEodFuturesImporter.ReadEODs(filePath);
            Assert.AreEqual(16, eods.Count);

            Assert.AreEqual("DESIF2-IUN07", eods[1].symbol);
            Assert.AreEqual("2007-01-03", DTUtil.ToDateString(eods[1].date));
            Assert.AreEqual(3.66, eods[1].open);
            Assert.AreEqual(3.8929, eods[1].high);
            Assert.AreEqual(3.66, eods[1].low);
            Assert.AreEqual(3.85, eods[1].close);
            Assert.AreEqual(70, eods[1].volume);
            Assert.AreEqual(1446, eods[1].openInterest);
        }
    }
}
#endif
