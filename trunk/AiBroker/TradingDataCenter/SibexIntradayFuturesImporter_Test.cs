/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 21/06/2008
 * Time: 09:13
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
    public class SibexIntradayFuturesImporter_Test
    {
        [Test]
        public void GrtUrl()
        {
            string expected = "http://www.tranzactiibursiere.ro/detalii/tranzactii_futures_csv?lang=en&symbol=&settle_date=&date=2008-01-03&x=%2C";
            string actual = new SibexIntradayFuturesImporter().GetUriString(DTUtil.ParseDate("2008-01-03"));
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void GetTempDownloadedFilePath()
        {
            Assert.AreEqual(Path.Combine(Configuration.TEMP_FOLDER, "temp.FUTURES.intraday.20080103.csv"),
                            new SibexIntradayFuturesImporter().GetTempDownloadedFilePath(DTUtil.ParseDate("2008-01-03")));
        }

        [Test]
        public void ReadEODs()
        {
            string filePath = @"..\..\..\TestData\FUTURES.Intraday.csv";
            IList<TransferTick> ticks = SibexIntradayFuturesImporter.ReadEODs(filePath);
            Assert.AreEqual(1601, ticks.Count);

            Assert.AreEqual("DESIF5-MAR08", ticks[1].symbol);
            Assert.AreEqual("2008-01-03 10:00:45", DTUtil.ToDateTimeString(ticks[1].dateTime));
            Assert.AreEqual(4.5413, ticks[1].price);
            Assert.AreEqual(2, ticks[1].volume);
            Assert.AreEqual(17894, ticks[1].openInterest);
        }
    
        [Test]
        public void ProcessDownloadedFile()
        {
            string filePath = @"..\..\..\TestData\FUTURES.Intraday.csv";   
            new SibexIntradayFuturesImporter().ProcessDownloadedFile(filePath);
        }
    }
}
#endif
