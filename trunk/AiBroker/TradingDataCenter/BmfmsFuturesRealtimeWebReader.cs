using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml;

using TradingCommon;
using TradingCommon.Util;

namespace TradingDataCenter
{
    public class BmfmsFuturesRealtimeWebReader
    {
        static string BMFMS_INTRADAY_FUTURES_DIFFS_PAGE = "http://www.sibex.ro/ro/home/tables/tabel_piata.php?refresh=Actualizare";
        
        IDictionary<string, Tick> tickMemory = new Dictionary<string, Tick>();

        public BmfmsFuturesRealtimeWebReader()
        {
            try 
            {
                _ParseDiffs(_GetDiffsWebPage(), out tickMemory);
            }
            catch (Exception)
            {
            }
        }
        
        public DateTime GetDiffsUpdate(out IDictionary<string, Tick> ticks)
        {
            IDictionary<string, Tick> newTicks;
            
            DateTime dateTime = _ParseDiffs(_GetDiffsWebPage(), out newTicks);
            
            _ProcessDiffs(tickMemory, newTicks, out ticks);
            tickMemory = newTicks;

            return dateTime;
        }
        
        internal static string _GetDiffsWebPage()
        {
            return WebUtil.GetWebPage(BMFMS_INTRADAY_FUTURES_DIFFS_PAGE);
        }

        internal static DateTime _ParseDiffs(string page, out IDictionary<string, Tick> ticks)
        {
            string table = "";
            try
            {
                // set time
                int pos = page.IndexOf("&nbsp;&nbsp;&nbsp;&nbsp;") + 24;
                string dateTimeString = page.Substring(pos, 19);
                DateTime dateTime = DTUtil.ParseDmyTime(dateTimeString);
                
                // read quotes
                ticks = new Dictionary<string, Tick>();
                
                do
                {
                    if (page.IndexOf("Momentan") >= 0) break;// Momentan nu sunt disponibile informatii
                    
                    int from = page.IndexOf("<table", pos);
                    if (from < 0) break;
                    
                    int to = page.IndexOf("</table>", from) + 8;
                    if (to < 0) break;
                    
                    table = page.Substring(from, to - from);
                    
                    Regex rex = new Regex("<a href.*?</a>");
                    table = rex.Replace(table, "");
                    rex = new Regex("bgcolor=#......");
                    table = rex.Replace(table, "");
                    table = table.Replace("N/A", "td");
                    table = table.Replace("td td", "td");
                    // table = table.Replace("bgcolor=#F0F2F4", "");
                    
                    XmlDocument quoteTable = new XmlDocument();
                    quoteTable.Load(new StringReader(table));
                    XmlNodeList quotes = quoteTable.GetElementsByTagName("tr");
                    
                    for (int i = 0; i < quotes.Count; ++i)
                    {
                        try 
                        {
                            XmlNodeList quote = quotes[i].ChildNodes;
                            string symbol = quote[0].InnerText;
                            
                            Tick tick = new Tick();
                            tick.dateTime = dateTime;
                            tick.price = double.Parse(quote[1].InnerText);
                            tick.volume = int.Parse(quote[3].InnerText);
                            tick.openInterest = int.Parse(quote[4].InnerText);
                            ticks[symbol] = tick;
                        }
                        catch (Exception)
                        {
                        }
                    }
                }while (false);
                
                return dateTime;
            }
            catch (Exception ex)
            {
                try
                {
                    string fileName = "TDC-SIBEX-INTRADAY-ERROR-PAGE-" + DTUtil.ToCDateTimeString(DateTime.Now) + ".html";
                    string filePath = Path.Combine(Configuration.LOG_FOLDER, fileName);
                    File.WriteAllText(filePath, page);
                    
                    fileName = "TDC-SIBEX-INTRADAY-ERROR-TABLE-" + DTUtil.ToCDateTimeString(DateTime.Now) + ".html";
                    filePath = Path.Combine(Configuration.LOG_FOLDER, fileName);
                    File.WriteAllText(filePath, table);
                }
                catch (Exception)
                {
                }
                throw ex;
            }
        }
        
        internal static void _ProcessDiffs(IDictionary<string, Tick> oldTicks, IDictionary<string, Tick> newTicks, out IDictionary<string, Tick> diffTicks)
        {
            diffTicks = new Dictionary<string, Tick>();
            
            foreach (string key in newTicks.Keys)
            {
                Tick t2 = newTicks[key];
                try
                {
                    Tick t1 = oldTicks[key];
                    int deltaVolume = t2.volume - t1.volume;
                    if (deltaVolume > 0)
                    {
                        Tick tick = new Tick();
                        tick.dateTime = t2.dateTime;
                        tick.price = t2.price;
                        tick.volume = deltaVolume;
                        tick.openInterest = t2.openInterest;
                        diffTicks[key] = tick;
                    }
                    else if (deltaVolume < 0)
                    {
                        t2.volume = t1.volume;
                    }
                }
                catch (KeyNotFoundException)
                {
                    diffTicks[key] = t2;
                }
            }
            
            foreach (string key in oldTicks.Keys)
            {
                if (!newTicks.Keys.Contains(key))
                {
                    newTicks[key] = oldTicks[key];
                }
            }
        }
    }
}
