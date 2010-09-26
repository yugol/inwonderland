using System;
using System.Text;

using TradingCommon.Util;

namespace TradingCommon
{
    public class BidAsk
    {
        public DateTime dateTime;
        public double price;
        public double bid;
        public double ask;

        public string ToEntry()
        {
            StringBuilder sb = new StringBuilder(DTUtil.ToCDateTimeString(dateTime));
            sb.Append(',');
            sb.Append(price.ToString());
            sb.Append(',');
            sb.Append(bid.ToString());
            sb.Append(',');
            sb.Append(ask.ToString());
            return sb.ToString();
        }

        internal static BidAsk FromEntry(string line)
        {
            string[] chunks = line.Split(',');
            BidAsk bidAsk = new BidAsk();
            bidAsk.dateTime = DTUtil.ParseCDateTime(chunks[0]);
            bidAsk.price = double.Parse(chunks[1]);
            bidAsk.bid = double.Parse(chunks[2]);
            bidAsk.ask = double.Parse(chunks[3]);
            return bidAsk;
        }
    }
}
