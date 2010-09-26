using System;
using System.Collections.Generic;
using System.Text;
using TradingCommon.Util;

namespace TradingCommon
{
    public class EOD
    {
        public DateTime date;
        public double open;
        public double high;
        public double low;
        public double close;
        public long volume;
        public long openInterest = -1;

        internal string ToEntry(string symbol)
        {
            StringBuilder sb = new StringBuilder(symbol);
            sb.Append(',');
            sb.Append(DTUtil.ToDateString(date));
            sb.Append(',');
            sb.Append(open);
            sb.Append(',');
            sb.Append(high);
            sb.Append(',');
            sb.Append(low);
            sb.Append(',');
            sb.Append(close);
            sb.Append(',');
            sb.Append(volume);
            if (openInterest >= 0)
            {
                sb.Append(',');
                sb.Append(openInterest);
            }
            return sb.ToString();
        }

        internal static EOD FromEntry(string data)
        {
            string[] chunks = data.Split(',');
            EOD eod = new EOD();
            eod.date = DTUtil.ParseDate(chunks[1]);
            eod.open = double.Parse(chunks[2]);
            eod.high = double.Parse(chunks[3]);
            eod.low = double.Parse(chunks[4]);
            eod.close = double.Parse(chunks[5]);
            eod.volume = long.Parse(chunks[6]);
            if (chunks.Length > 7)
                eod.openInterest = long.Parse(chunks[7]);
            return eod;
        }
    }
}
