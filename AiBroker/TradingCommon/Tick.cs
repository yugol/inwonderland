using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using TradingCommon.Util;

namespace TradingCommon
{
    public class Tick
    {
        public DateTime dateTime;
        public double price;
        public int volume;
        public int openInterest = -1;

        public static Tick FromEntry(String data)
        {
            string[] chunks = data.Split(',');
            Tick tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime(chunks[1] + " " + chunks[2]);
            tick.price = double.Parse(chunks[3]);
            tick.volume = int.Parse(chunks[4]);
            if (chunks.Length > 5) 
                tick.openInterest = int.Parse(chunks[5]);
            return tick;
        }

        public string ToEntry(string symbol)
        {
            StringBuilder sb = new StringBuilder(symbol);
            sb.Append(',');
            sb.Append(DTUtil.ToDateString(dateTime));
            sb.Append(',');
            sb.Append(DTUtil.ToTimeString(dateTime));
            sb.Append(',');
            sb.Append(price);
            sb.Append(',');
            sb.Append(volume);
            if (openInterest >= 0)
            {
                sb.Append(',');
                sb.Append(openInterest);
            }
            return sb.ToString();
        }
    }
}
