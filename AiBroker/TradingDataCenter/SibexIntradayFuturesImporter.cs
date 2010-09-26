/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 21/06/2008
 * Time: 09:13
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
    public class SibexIntradayFuturesImporter : DayByDayUiImporter
    {
        internal override string GetTitle()
        {
            return "Import Intraday Futures";
        }
        
        internal override string GetUriString(DateTime date)
        {
            StringBuilder uri = new StringBuilder("http://www.tranzactiibursiere.ro/detalii/tranzactii_futures_csv?lang=en&symbol=&settle_date=&date=");
            uri.Append(DTUtil.ToDateString(date));
            uri.Append("&x=%2C");
            return uri.ToString();
        }
        
        internal override string GetTempDownloadedFilePath(DateTime date)
        {
            return Path.Combine(Configuration.TEMP_FOLDER, "temp.FUTURES.intraday." + DTUtil.ToCDateString(date) + ".csv");
        }
        
        internal override void ProcessDownloadedFile(string fileName)
        {
            IList<TransferTick> ticks = ReadEODs(fileName);
            
            while (ticks.Count > 0)
            {
                string symbol = ticks[0].symbol;
                string date = DTUtil.ToDateTimeString(ticks[0].dateTime);
                System.Console.WriteLine(date + " -> " + symbol);
                
                IList<Tick> importedTicks = new List<Tick>();
                int i = 0;
                while (i < ticks.Count)
                {
                    if (ticks[i].symbol == symbol)
                    {
                        importedTicks.Add(ticks[i]);
                        ticks.RemoveAt(i);
                    }                    
                    else
                    {
                        ++i;    
                    }
                }
                IList<Tick> existingTicks = DataUtil.ReadTicks(symbol);
                TicksUtil.MergeAppendTicks(existingTicks, importedTicks);
                DataUtil.WriteTicks(symbol, existingTicks);
            }
        }
        
        internal static IList<TransferTick> ReadEODs(string fileName)
        {
            IList<TransferTick> ticks = new List<TransferTick>();

            CachedCsvReader csv = new CachedCsvReader(new StreamReader(fileName), true);
            foreach (string[] record in csv)
            {
                try
                {
                    TransferTick tick = new TransferTick();
                    string date = record[0].Substring(0, 10);
                    string time = record[1];
                    tick.dateTime = DTUtil.ParseDateTime(date + " " + time);
                    tick.symbol = record[2] + "-" + record[3];
                    tick.price = double.Parse(record[4]);
                    tick.volume = int.Parse(record[5]);
                    tick.openInterest = int.Parse(record[6]);
                    ticks.Add(tick);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error reading\n" + fileName + "\n" + ex.Message, "Trading Data Center");
                }
            }
            csv.Dispose();

            return ticks;
        }
    }
}
