#if TEST

using System;
using System.IO;
using NUnit.Framework;

namespace TradingCommon.Util
{
    [TestFixture]
    public class WebUtil_Test
    {
        [Test]
        public void GetWebPage()
        {
            string webPage = WebUtil.GetWebPage("http://www.google.com");
            Assert.IsTrue(webPage.IndexOf("Google") > 0);
        }
        
        [Test]
        public void DownloadFile()
        {
            string destinationPath = Path.Combine(Configuration.TEMP_FOLDER, "temp.test.file");
            string uri = "http://www.tranzactiibursiere.ro/detalii/istoric_futures_csv?lang=en&symbol=&settle_date=&date=2007-01-03&x=%2C";

            File.Delete(destinationPath);
            
            WebUtil.DownloadFile(uri, destinationPath);
            Assert.IsTrue(File.Exists(destinationPath));

            File.Delete(destinationPath);
        }        
    }
}

#endif
