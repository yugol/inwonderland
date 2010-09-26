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
    public class BvbEodRegsImporter : DayByDayUiImporter
    {
        IList<string> symbolSelection = null;
        
        internal override string GetTitle()
        {
            return "Import EOD original REGS";
        }

        internal override string GetUriString(DateTime date)
        {
            StringBuilder uri = new StringBuilder("http://www.tranzactiibursiere.ro/detalii/istoric_csv?");

            uri.Append("symbol=&market=REGS&sdate=");
            uri.Append(DTUtil.ToDateString(date));
            uri.Append("&edate=");
            uri.Append(DTUtil.ToDateString(date));
            uri.Append("&type=original&x=%2C");

            return uri.ToString();
        }

        internal override string GetTempDownloadedFilePath(DateTime date)
        {
            return Path.Combine(Configuration.TEMP_FOLDER, "temp.REGS.eod." + DTUtil.ToCDateString(date) + ".csv");
        }
        
        internal override void ProcessDownloadedFile(string fileName)
        {
            if (symbolSelection == null)
                symbolSelection = DataUtil.GetSpotSymbolsForDerivatives();
            
            IList<TransferEOD> eods = ReadEODs(fileName, symbolSelection);
            
            foreach (TransferEOD eod in eods)
                DataUtil.AppendEod(eod.symbol, eod);
        }

        internal static IList<TransferEOD> ReadEODs(string filePath, IList<string> symbolSelection)
        {
            IList<TransferEOD> eods = new List<TransferEOD>();

            CachedCsvReader csv = new CachedCsvReader(new StreamReader(filePath), true);
            foreach (string[] record in csv)
            {
                if (symbolSelection.Contains(record[1]))
                {
                    try
                    {
                        TransferEOD eod = new TransferEOD();
                        eod.date = DTUtil.ParseDate(record[0]);
                        eod.symbol = record[1];
                        eod.open = double.Parse(record[3]);
                        eod.high = double.Parse(record[4]);
                        eod.low = double.Parse(record[5]);
                        eod.close = double.Parse(record[6]);
                        eod.volume = long.Parse(record[8]);
                        eods.Add(eod);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error reading\n" + filePath + "\n" + ex.Message, "Trading Data Center");
                    }
                }
            }
            csv.Dispose();

            return eods;
        }
    }
}
