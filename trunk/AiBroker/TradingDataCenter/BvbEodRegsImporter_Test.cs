#if TEST

using System.Collections.Generic;
using System.IO;
using NUnit.Framework;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter
{
    [TestFixture]
    public class BvbEodRegsImporter_Test
    {
        [Test]
        public void GrtUrl()
        {
            string expected = "http://www.tranzactiibursiere.ro/detalii/istoric_csv?symbol=&market=REGS&sdate=2007-04-10&edate=2007-04-10&type=original&x=%2C";
            string actual = new BvbEodRegsImporter().GetUriString(DTUtil.ParseDate("2007-04-10"));
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void CombinePath()
        {
            Assert.AreEqual("c:\\a\\b.c", Path.Combine("c:\\a", "b.c"));
        }

        [Test]
        public void GetTempDownloadedFilePath()
        {
            Assert.AreEqual(Path.Combine(Configuration.TEMP_FOLDER, "temp.REGS.eod.20070410.csv"),
                            new BvbEodRegsImporter().GetTempDownloadedFilePath(DTUtil.ParseDate("2007-04-10")));
        }

        [Test]
        public void ReadEODs()
        {
            string filePath = @"..\..\..\TestData\REGS.EOD.csv";
            IList<string> selection = DataUtil.GetSpotSymbolsForDerivatives();
            IList<TransferEOD> eods = BvbEodRegsImporter.ReadEODs(filePath, selection);
            Assert.AreEqual(11, eods.Count);

            Assert.AreEqual("RRC", eods[2].symbol);
            Assert.AreEqual("2007-04-10", DTUtil.ToDateString(eods[1].date));
            Assert.AreEqual(0.103, eods[2].open);
            Assert.AreEqual(0.105, eods[2].high);
            Assert.AreEqual(0.103, eods[2].low);
            Assert.AreEqual(0.103, eods[2].close);
            Assert.AreEqual(3829300, eods[2].volume);
            Assert.AreEqual(-1, eods[2].openInterest);
        }
    }
}

#endif
