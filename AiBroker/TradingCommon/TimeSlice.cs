using System;

namespace TradingCommon
{
    public class TimeSlice
    {
        public DateTime dateTime;
        public double open;
        public double high;
        public double low;
        public double close;
        public long volume;

        public TimeSlice(Tick tick)
        {
            dateTime = tick.dateTime;
            open = high = low = close = tick.price;
            volume = tick.volume;
        }

        public TimeSlice(EOD eod)
        {
            dateTime = eod.date;
            open = eod.open;
            high = eod.high;
            low = eod.low;
            close = eod.close;
            volume = eod.volume;

            // Console.WriteLine(DTUtil.ToDateTimeString(eod.date));
        }

        public TimeSlice(DateTime dt, double open, double high, double low, double close, int volume)
        {
            this.dateTime = dt;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.volume = volume;
        }

        public void Sum(Tick tick)
        {
            dateTime = tick.dateTime;
            if (tick.price > high) high = tick.price;
            if (tick.price < low) low = tick.price;
            close = tick.price;
            volume += tick.volume;
        }

        internal void Sum(EOD eod)
        {
            dateTime = eod.date;
            if (eod.high > high) high = eod.high;
            if (eod.low < low) low = eod.low;
            close = eod.close;
            volume += eod.volume;
        }
    }
}
