using System;
using System.Collections.Generic;

namespace TradingCommon
{
    public class TimeSeries
    {
        public const int TICK_PERIOD = 0;
        public const int HOUR_PERIOD = 60;
        public const int DAY_FROM_TICK_PERIOD = 1439;
        public const int DAY_PERIOD = 1440;
        public const int WEEK_PERIOD = 10080;
        public const int MONTH_PERIOD = 43800;
        public const int YEAR_PERIOD = 525600;

        protected int period;
        protected DateTime[] dateTimes;
        protected DateTime[] dates = null;
        protected double[] opens;
        protected double[] highs;
        protected double[] lows;
        protected double[] closes;
        protected double[] volumes;

        public int Period { get { return period; } }
        public DateTime[] DateTimes { get { return dateTimes; } }
        public DateTime[] Dates { get { BuildDates(); return dates; } }
        public double[] Opens { get { return opens; } }
        public double[] Highs { get { return highs; } }
        public double[] Lows { get { return lows; } }
        public double[] Closes { get { return closes; } }
        public double[] Volumes { get { return volumes; } }
        public int Count { get { return dateTimes.Length; } }
        public DateTime FirstDate { get { return dateTimes[0]; } }
        public DateTime LastDate { get { return dateTimes[Count - 1]; } }
        public double LastPrice { get { return closes[Count - 1]; } }

        // public TimeSlice this[int i] { get { return new TimeSlice(dateTimes[i], opens[i], highs[i], lows[i], closes[i], volumes[i]); } }

        private int firstLastDayEntry = -1;
        public int FirstLastDayEntry { get { return firstLastDayEntry; } }

        public TimeSeries(IList<Tick> ticks, int period)
        {
            if (ticks.Count <= 0)
                throw new Exception("Cannot create " + typeof(TimeSeries).ToString() + " with 0 ticks");
            this.period = period;
            if (period == TICK_PERIOD)
            {
                FillSeries(ticks);
                FindFirstLastDayEntry();
            }
            else if (period < DAY_PERIOD)
            {
                IList<TimeSlice> packedSeries = PackSeries(ticks);
                FillSeries(packedSeries);
                FindFirstLastDayEntry();
            }
            else
            {
                IList<TimeSlice> packedSeries = PackSeries(ticks);
                FillSeries(packedSeries);
                firstLastDayEntry = Count - 1;
            }
        }

        public TimeSeries(IList<EOD> eods, int period)
        {
            if (eods.Count <= 0)
                throw new Exception("Cannot create " + typeof(TimeSeries).ToString() + " with 0 eods");
            if (period < DAY_PERIOD)
                period = DAY_PERIOD;
            this.period = period;
            if (period == DAY_PERIOD)
            {
                FillSeries(eods);
            }
            else
            {
                IList<TimeSlice> packedSeries = PackSeries(eods);
                FillSeries(packedSeries);
            }
            firstLastDayEntry = Count - 1;
        }

        public DateTime NormDateTime(int index)
        {
            return GetPreviousDateTime(dateTimes[index], period);
        }

        public int GetFirstBarIndexForDate(DateTime value)
        {
            return GetBarIndex(value.Date);
        }

        public int GetLastBarIndexForDate(DateTime value)
        {
            DateTime date = value.Date;
            int i = GetBarIndex(date);
            if (i >= 0)
            {
                for (; i < Count; ++i)
                {
                    if (dateTimes[i].Date.CompareTo(date) > 0)
                        break;
                }
                return i - 1;
            }
            else
            {
                return -1;
            }
        }

        private void FindFirstLastDayEntry()
        {
            firstLastDayEntry = Count - 1;
            int lastDay = dateTimes[firstLastDayEntry].Day;
            while (firstLastDayEntry >= 0)
            {
                if (dateTimes[firstLastDayEntry].Day != lastDay) break; ;
                --firstLastDayEntry;
            }
            ++firstLastDayEntry;
        }

        public void GetMinMax(int from, int to, out double min, out double max)
        {
            if (from > to)
            {
                min = double.NaN;
                max = double.NaN;
            }
            else
            {
                min = double.MaxValue;
                max = double.MinValue;
                for (int i = from; i <= to; i++)
                {
                    double high = highs[i];
                    double low = lows[i];
                    if (high > max) max = high;
                    if (low < min) min = low;
                }
            }
        }

        public double GetHighLowMean(int i)
        {
            return ((highs[i] + lows[i]) / 2);
        }

        public int GetBarIndex(DateTime dt)
        {
            DateTime firstDate = dateTimes[0].Date;
            if (dt.CompareTo(firstDate) < 0)
            {
                DateTime dtTemp = dt.Date;
                return ((dtTemp.CompareTo(firstDate) == 0) ? (0) : (-1));
            }

            int barIndex = Array.BinarySearch(dateTimes, dt);
            if (barIndex < 0)
                barIndex = -barIndex - 1;

            if (barIndex >= Count)
            {
                DateTime dtTemp = dt.Date;
                DateTime lastDate = dateTimes[dateTimes.Length - 1].Date;
                barIndex = ((dtTemp.CompareTo(lastDate) == 0) ? (dateTimes.Length) : (0)) - 1;
            }

            return barIndex;
        }

        public int IntradayBarIndex(int barIndex)
        {
            int intradayBarIndex = 0;
            DateTime barDate = dateTimes[barIndex].Date;

            --barIndex;
            while ((barIndex >= 0) && (dateTimes[barIndex].Date.CompareTo(barDate) == 0))
            {
                ++intradayBarIndex;
                --barIndex;
            }

            return intradayBarIndex;
        }

        private void BuildDates()
        {
            if (dates == null)
            {
                if (period >= DAY_PERIOD)
                {
                    dates = dateTimes;
                }
                else
                {
                    List<DateTime> dateList = new List<DateTime>();
                    foreach (DateTime dateTime in dateTimes)
                    {
                        DateTime date = dateTime.Date;
                        if (!dateList.Contains(date))
                            dateList.Add(date);
                    }
                    dates = dateList.ToArray();
                }
            }
        }

        public int GetIntradayVariation(DateTime date)
        {
            int firstBarIndex = GetFirstBarIndexForDate(date);
            int lastBarIndex = GetLastBarIndexForDate(date);

            //Console.WriteLine(firstBarIndex);
            //Console.WriteLine(lastBarIndex);

            double variation = 0;
            if (firstBarIndex == lastBarIndex)
                variation = closes[lastBarIndex] - opens[firstBarIndex];
            else
                variation = opens[lastBarIndex] - closes[firstBarIndex];
            return Math.Sign(variation);
        }

        #region PackSeries

        internal static DateTime GetNextDateTime(DateTime dt, int period)
        {
            DateTime nextDateTime = GetPreviousDateTime(dt, period);
            if (period > 0)
            {
                if (period < TimeSeries.DAY_PERIOD)
                    nextDateTime = nextDateTime.AddMinutes(period);
                else if ((period == TimeSeries.DAY_PERIOD) || (period == TimeSeries.DAY_FROM_TICK_PERIOD))
                    nextDateTime = nextDateTime.AddDays(1);
                else if (period == TimeSeries.WEEK_PERIOD)
                    nextDateTime = nextDateTime.AddDays(7);
                else if (period == TimeSeries.MONTH_PERIOD)
                    nextDateTime = nextDateTime.AddMonths(1);
                else if (period == TimeSeries.YEAR_PERIOD)
                    nextDateTime = nextDateTime.AddYears(1);
            }
            return nextDateTime;
        }

        internal static DateTime GetPreviousDateTime(DateTime dt, int period)
        {
            DateTime previousDateTime = dt;
            if (period > 0)
            {
                if ((60 % period) == 0)
                {
                    int minute = (dt.Minute / period) * period;
                    previousDateTime = new DateTime(dt.Year, dt.Month, dt.Day, dt.Hour, minute, 0);
                }
                else if ((period == TimeSeries.DAY_PERIOD) || (period == TimeSeries.DAY_FROM_TICK_PERIOD))
                {
                    previousDateTime = new DateTime(dt.Year, dt.Month, dt.Day, 0, 0, 0);
                }
                else if (period == TimeSeries.WEEK_PERIOD)
                {
                    int delta = ((int)dt.DayOfWeek) - 1;
                    previousDateTime = new DateTime(dt.Year, dt.Month, dt.Day, 0, 0, 0);
                    if (delta > 0)
                        previousDateTime = previousDateTime.AddDays(-delta);
                }
                else if (period == TimeSeries.MONTH_PERIOD)
                {
                    previousDateTime = new DateTime(dt.Year, dt.Month, 1, 0, 0, 0);
                }
                else if (period == TimeSeries.YEAR_PERIOD)
                {
                    previousDateTime = new DateTime(dt.Year, 1, 1, 0, 0, 0);
                }
                else
                {
                    throw new NotImplementedException();
                }
            }
            return previousDateTime;
        }

        IList<TimeSlice> PackSeries(IList<Tick> ticks)
        {
            IList<TimeSlice> packedSeries = new List<TimeSlice>();
            TimeSlice slice = null;
            DateTime nextDateTime = GetNextDateTime(DateTime.Now, period);

            foreach (Tick tick in ticks)
            {
                if (slice != null)
                {
                    if (tick.dateTime.CompareTo(nextDateTime) < 0)
                    {
                        slice.Sum(tick);
                    }
                    else
                    {
                        packedSeries.Add(slice);
                        slice = null;
                    }
                }

                if (slice == null)
                {
                    slice = new TimeSlice(tick);
                    nextDateTime = GetNextDateTime(tick.dateTime, period);
                }
            }

            if (slice != null) packedSeries.Add(slice);

            return packedSeries;
        }

        IList<TimeSlice> PackSeries(IList<EOD> eods)
        {
            IList<TimeSlice> packedSeries = new List<TimeSlice>();
            TimeSlice slice = null;
            DateTime nextDateTime = GetNextDateTime(DateTime.Now, period);

            foreach (EOD eod in eods)
            {
                if (slice != null)
                {
                    if (eod.date.CompareTo(nextDateTime) < 0)
                    {
                        slice.Sum(eod);
                    }
                    else
                    {
                        packedSeries.Add(slice);
                        slice = null;
                    }
                }

                if (slice == null)
                {
                    slice = new TimeSlice(eod);
                    nextDateTime = GetNextDateTime(eod.date, period);
                }
            }

            if (slice != null) packedSeries.Add(slice);

            return packedSeries;
        }

        #endregion

        #region FillSeries

        private void FillSeries(IList<Tick> ticks)
        {
            int length = ticks.Count;

            dateTimes = new DateTime[length];
            opens = new double[length];
            highs = new double[length];
            lows = new double[length];
            closes = new double[length];
            volumes = new double[length];

            for (int i = 0; i < length; ++i)
            {
                Tick tick = ticks[i];
                dateTimes[i] = tick.dateTime;
                opens[i] = tick.price;
                highs[i] = tick.price;
                lows[i] = tick.price;
                closes[i] = tick.price;
                volumes[i] = tick.volume;
            }
        }

        void FillSeries(IList<TimeSlice> packedSeries)
        {
            int length = packedSeries.Count;

            dateTimes = new DateTime[length];
            opens = new double[length];
            highs = new double[length];
            lows = new double[length];
            closes = new double[length];
            volumes = new double[length];

            for (int i = 0; i < length; ++i)
            {
                TimeSlice slice = packedSeries[i];
                dateTimes[i] = slice.dateTime;
                opens[i] = slice.open;
                highs[i] = slice.high;
                lows[i] = slice.low;
                closes[i] = slice.close;
                volumes[i] = slice.volume;
            }
        }

        private void FillSeries(IList<EOD> eods)
        {
            int length = eods.Count;

            dateTimes = new DateTime[length];
            opens = new double[length];
            highs = new double[length];
            lows = new double[length];
            closes = new double[length];
            volumes = new double[length];

            for (int i = 0; i < length; ++i)
            {
                EOD eod = eods[i];
                dateTimes[i] = eod.date;
                opens[i] = eod.open;
                highs[i] = eod.high;
                lows[i] = eod.low;
                closes[i] = eod.close;
                volumes[i] = eod.volume;
            }
        }

        #endregion

    }
}
