/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 20/06/2008
 * Time: 07:10
 * 
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

using LumenWorks.Framework.IO.Csv;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter
{
    public class SibexEodFuturesImporter : DayByDayUiImporter
    {
        internal override string GetTitle()
        {
            return "Import EOD FUTURES";
        }
        
        internal override string GetUriString(DateTime date)
        {
            StringBuilder uri = new StringBuilder("http://www.tranzactiibursiere.ro/detalii/istoric_futures_csv?lang=en&symbol=&settle_date=&date=");
            uri.Append(DTUtil.ToDateString(date));
            uri.Append("&x=%2C");
            return uri.ToString();
        }   
        
        internal override string GetTempDownloadedFilePath(DateTime date)
        {
            return Path.Combine(Configuration.TEMP_FOLDER, "temp.FUTURES.eod." + DTUtil.ToCDateString(date) + ".csv");
        }
        
        internal override void ProcessDownloadedFile(string fileName)
        {
            IList<TransferEOD> eods = ReadEODs(fileName);
            
            foreach (TransferEOD eod in eods)
                DataUtil.AppendEod(eod.symbol, eod);
        }
        
        internal static IList<TransferEOD> ReadEODs(string filePath)
        {
            IList<TransferEOD> eods = new List<TransferEOD>();

            CachedCsvReader csv = new CachedCsvReader(new StreamReader(filePath), true);
            foreach (string[] record in csv)
            {
                try
                {
                    long volume = long.Parse(record[7]);
                    if (volume > 0)
                    {
                        TransferEOD eod = new TransferEOD();
                        eod.date = DTUtil.ParseDate(record[0]);
                        eod.symbol = record[1] + "-" + record[2];
                        eod.open = double.Parse(record[3]);
                        eod.high = double.Parse(record[4]);
                        eod.low = double.Parse(record[5]);
                        eod.close = double.Parse(record[6]);
                        eod.volume = volume;
                        eod.openInterest = long.Parse(record[8]);
                        eods.Add(eod);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error reading\n" + filePath + "\n" + ex.Message, "Trading Data Center");
                }
            }
            csv.Dispose();

            return eods;
        }        
    }
}
