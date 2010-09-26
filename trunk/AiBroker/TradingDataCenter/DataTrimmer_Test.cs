/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 19/06/2008
 * Time: 07:14
 */
#if TEST

using System;
using System.Collections.Generic;
using System.IO;

using NUnit.Framework;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter
{
    [TestFixture]
    public class DataTrimmer_Test
    {
        [Test]
        public void TrimEOD()
        {
            string symbol = "TEST";
            string fileName = DataUtil.GetEodFileName(symbol);
            
            File.Delete(fileName);            
            
            EOD eod = new EOD();            
            eod.date = DTUtil.ParseDate("2007-10-10");
            DataUtil.AppendEod(symbol, eod);
            eod.date = DTUtil.ParseDate("2008-10-10");
            DataUtil.AppendEod(symbol, eod);
            
            IList<EOD> eods = DataUtil.ReadEods(symbol);
            Assert.AreEqual(2, eods.Count);    
            
            DataTrimmer.Trim(fileName);
                
            eods = DataUtil.ReadEods(symbol);
            Assert.AreEqual(1, eods.Count);
            Assert.AreEqual("2007-10-10", DTUtil.ToDateString(eods[0].date));
            
            File.Delete(fileName);
        }
        
        // [Test]
        public void TrimAllFiles()
        {
            string[] files = Directory.GetFiles(Configuration.DATA_FOLDER, "*.EOD.csv");
            foreach (string file in files)
            {
                System.Console.WriteLine(file);
                // DataTrimmer.Trim(file);
            }
        }
    }
}

#endif
