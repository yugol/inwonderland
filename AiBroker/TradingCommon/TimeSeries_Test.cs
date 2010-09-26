using System;
using System.Collections.Generic;
using TradingCommon.Storage;
using NUnit.Framework;

using TradingCommon.Util;

namespace TradingCommon
{
    [TestFixture]
    public class TimeSeries_Test
    {
        static IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-IUN07");

        [Test]
        public void NoTicksCreation()
        {
            try
            {
                TimeSeries ts = new TimeSeries(new List<Tick>(), 10);
            }
            catch (Exception ex)
            {
                Assert.IsTrue(ex.Message.IndexOf(typeof(TimeSeries).ToString()) >= 0);
            }
        }

        [Test]
        public void NoEodsCreation()
        {
            try
            {
                TimeSeries ts = new TimeSeries(new List<EOD>(), TimeSeries.DAY_PERIOD);
            }
            catch (Exception ex)
            {
                Assert.IsTrue(ex.Message.IndexOf(typeof(TimeSeries).ToString()) >= 0);
            }
        }

        [Test]
        public void GetBarIndex()
        {
            TimeSeries ts = new TimeSeries(ticks, 60);

            Assert.AreEqual(0, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 00:00:00")));

            Assert.AreEqual(0, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 10:59:23")));
            Assert.AreEqual(1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 11:56:11")));
            Assert.AreEqual(6, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-11 10:59:22")));

            Assert.AreEqual(0, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 10:59:00")));
            Assert.AreEqual(1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 11:56:00")));
            Assert.AreEqual(6, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-11 10:59:00")));

            Assert.AreEqual(-1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-09 23:59:59")));
            Assert.AreEqual(-1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-07-01 00:00:00")));

            ts = new TimeSeries(ticks, 30);
            Assert.AreEqual(3, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 11:56:11")));

            ts = new TimeSeries(ticks, 20);
            Assert.AreEqual(5, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 11:56:11")));

            ts = new TimeSeries(ticks, 10);
            Assert.AreEqual(1877, ts.Count);
            Assert.AreEqual(-1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-09 23:59:59")));
            Assert.AreEqual(0, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 10:00:00")));
            Assert.AreEqual(11, ts.GetBarIndex(DTUtil.ParseDateTime("2007-04-10 11:56:11")));
            Assert.AreEqual(1876, ts.GetBarIndex(DTUtil.ParseDateTime("2007-06-29 16:30:14")));
            Assert.AreEqual(-1, ts.GetBarIndex(DTUtil.ParseDateTime("2007-06-30 00:00:00")));
        }

        [Test]
        public void GetBarIndex_Iterative()
        {
            TimeSeries ts = new TimeSeries(ticks, 1);
            for (int i = 0; i < ts.Count; ++i)
                Assert.AreEqual(i, ts.GetBarIndex(ts.DateTimes[i]));
        }

        [Test]
        public void IntradayBarIndex()
        {
            TimeSeries ts = new TimeSeries(ticks, 60);

            Assert.AreEqual(0, ts.IntradayBarIndex(0));
            Assert.AreEqual(1, ts.IntradayBarIndex(1));
            Assert.AreEqual(2, ts.IntradayBarIndex(2));
            Assert.AreEqual(5, ts.IntradayBarIndex(5));
            Assert.AreEqual(0, ts.IntradayBarIndex(6));
            Assert.AreEqual(1, ts.IntradayBarIndex(7));
            Assert.AreEqual(2, ts.IntradayBarIndex(8));

            ts = new TimeSeries(ticks, 1);

            Assert.AreEqual(0, ts.IntradayBarIndex(0));
            Assert.AreEqual(1, ts.IntradayBarIndex(1));
            Assert.AreEqual(2, ts.IntradayBarIndex(2));
            Assert.AreEqual(204, ts.IntradayBarIndex(204));
            Assert.AreEqual(0, ts.IntradayBarIndex(205));
            Assert.AreEqual(1, ts.IntradayBarIndex(206));
            Assert.AreEqual(2, ts.IntradayBarIndex(207));
        }

        [Test]
        public void EodsConstructor()
        {
            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);

            Assert.AreEqual(192, ts.Count);
            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);

            Assert.AreEqual("2.7300", ts.Closes[0].ToString("0.0000"));
            Assert.AreEqual("2.7300", ts.Highs[0].ToString("0.0000"));
            Assert.AreEqual("2.7250", ts.Lows[0].ToString("0.0000"));
            Assert.AreEqual("2.7250", ts.Opens[0].ToString("0.0000"));
            Assert.AreEqual(11, ts.Volumes[0]);
            Assert.AreEqual("2006-09-07", DTUtil.ToDateString(ts.DateTimes[0]));
        }

        [Test]
        public void BuildDates()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-IUN07");
            TimeSeries ts = new TimeSeries(ticks, 10);
            Assert.AreEqual(55, ts.Dates.Length);

            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);
            Assert.AreEqual(192, ts.Dates.Length);
        }

        [Test]
        public void GetFirstBarIndexForDate()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-IUN07");
            TimeSeries ts = new TimeSeries(ticks, 10);
            Assert.AreEqual(0, ts.GetFirstBarIndexForDate(DTUtil.ParseDateTime("2007-04-10 12:14:32")));
            Assert.AreEqual(1863, ts.GetFirstBarIndexForDate(DTUtil.ParseDateTime("2007-06-29 20:10:32")));
            Assert.AreEqual(-1, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2007-04-09")));
            Assert.AreEqual(-1, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2007-06-30")));

            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);
            Assert.AreEqual(0, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2006-09-07")));
            Assert.AreEqual(191, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2007-06-29")));
            Assert.AreEqual(-1, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2006-09-06")));
            Assert.AreEqual(-1, ts.GetFirstBarIndexForDate(DTUtil.ParseDate("2007-06-30")));
        }

        [Test]
        public void GetLastBarIndexForDate()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-IUN07");
            TimeSeries ts = new TimeSeries(ticks, 10);
            Assert.AreEqual(32, ts.GetLastBarIndexForDate(DTUtil.ParseDateTime("2007-04-10 12:14:32")));
            Assert.AreEqual(1876, ts.GetLastBarIndexForDate(DTUtil.ParseDateTime("2007-06-29 10:14:32")));
            Assert.AreEqual(-1, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2007-04-09")));
            Assert.AreEqual(-1, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2007-06-30")));

            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);
            Assert.AreEqual(0, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2006-09-07")));
            Assert.AreEqual(191, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2007-06-29")));
            Assert.AreEqual(-1, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2006-09-06")));
            Assert.AreEqual(-1, ts.GetLastBarIndexForDate(DTUtil.ParseDate("2007-06-30")));
        }

        [Test]
        public void GetIntradayVariation()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DESIF2-IUN07");
            TimeSeries ts = new TimeSeries(ticks, 10);
            Assert.AreEqual(1, ts.GetIntradayVariation(DTUtil.ParseDate("2007-04-10")));
            Assert.AreEqual(-1, ts.GetIntradayVariation(DTUtil.ParseDate("2007-04-12")));

            IList<EOD> eods = DataUtil.ReadEods("DESIF2-IUN07");
            ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);
            Assert.AreEqual(1, ts.GetIntradayVariation(DTUtil.ParseDate("2007-04-10")));
            Assert.AreEqual(-1, ts.GetIntradayVariation(DTUtil.ParseDate("2007-04-12")));
        }

        [Test]
        public void FillSeries_T_T()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("RONEUR-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.TICK_PERIOD);

            Assert.AreEqual(TimeSeries.TICK_PERIOD, ts.Period);
            Assert.AreEqual(116, ts.Count);
            Assert.AreEqual(115, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-10 15:10:24", DTUtil.ToDateTimeString( ts.DateTimes[0]));
            Assert.AreEqual(3.351, ts.Opens[0]);
            Assert.AreEqual(3.351, ts.Highs[0]);
            Assert.AreEqual(3.351, ts.Lows[0]);
            Assert.AreEqual(3.351, ts.Closes[0]);
            Assert.AreEqual(1, ts.Volumes[0]);

            Assert.AreEqual("2007-06-29 16:07:18", DTUtil.ToDateTimeString(ts.DateTimes[115]));
            Assert.AreEqual(3.1481, ts.Opens[115]);
            Assert.AreEqual(3.1481, ts.Highs[115]);
            Assert.AreEqual(3.1481, ts.Lows[115]);
            Assert.AreEqual(3.1481, ts.Closes[115]);
            Assert.AreEqual(0, ts.Volumes[115]);
        }

        [Test]
        public void FillSeries_T_60()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.HOUR_PERIOD);

            Assert.AreEqual(TimeSeries.HOUR_PERIOD, ts.Period);
            Assert.AreEqual(180, ts.Count);
            Assert.AreEqual(178, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-10 10:49:19", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(2.02, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(2.02, ts.Closes[0]);
            Assert.AreEqual(1, ts.Volumes[0]);

            Assert.AreEqual("2007-04-10 13:39:51", DTUtil.ToDateTimeString(ts.DateTimes[1]));
            Assert.AreEqual(2.0453, ts.Opens[1]);
            Assert.AreEqual(2.046, ts.Highs[1]);
            Assert.AreEqual(2.0453, ts.Lows[1]);
            Assert.AreEqual(2.046, ts.Closes[1]);
            Assert.AreEqual(32, ts.Volumes[1]);

            Assert.AreEqual("2007-04-17 13:59:01", DTUtil.ToDateTimeString(ts.DateTimes[20]));
            Assert.AreEqual(2.101, ts.Opens[20]);
            Assert.AreEqual(2.116, ts.Highs[20]);
            Assert.AreEqual(2.101, ts.Lows[20]);
            Assert.AreEqual(2.11, ts.Closes[20]);
            Assert.AreEqual(15, ts.Volumes[20]);

            Assert.AreEqual("2007-05-04 10:48:33", DTUtil.ToDateTimeString(ts.DateTimes[50]));

            Assert.AreEqual("2007-05-24 10:16:08", DTUtil.ToDateTimeString(ts.DateTimes[100]));

            Assert.AreEqual("2007-06-13 13:39:00", DTUtil.ToDateTimeString(ts.DateTimes[150]));
            
            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[179]));
            Assert.AreEqual(3.04, ts.Opens[179]);
            Assert.AreEqual(3.04, ts.Highs[179]);
            Assert.AreEqual(3.04, ts.Lows[179]);
            Assert.AreEqual(3.04, ts.Closes[179]);
            Assert.AreEqual(2, ts.Volumes[179]);
        }

        [Test]
        public void FillSeries_T_DFT()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.DAY_FROM_TICK_PERIOD);

            Assert.AreEqual(TimeSeries.DAY_FROM_TICK_PERIOD, ts.Period);
            Assert.AreEqual(53, ts.Count);
            Assert.AreEqual(52, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-10 14:40:18", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(2.0494, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(2.0494, ts.Closes[0]);
            Assert.AreEqual(37, ts.Volumes[0]);

            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[52]));
            Assert.AreEqual(3.03, ts.Opens[52]);
            Assert.AreEqual(3.04, ts.Highs[52]);
            Assert.AreEqual(3.03, ts.Lows[52]);
            Assert.AreEqual(3.04, ts.Closes[52]);
            Assert.AreEqual(4, ts.Volumes[52]);
        }

        [Test]
        public void FillSeries_T_D()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.DAY_PERIOD);

            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(53, ts.Count);
            Assert.AreEqual(52, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-10 14:40:18", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(2.0494, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(2.0494, ts.Closes[0]);
            Assert.AreEqual(37, ts.Volumes[0]);

            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[52]));
            Assert.AreEqual(3.03, ts.Opens[52]);
            Assert.AreEqual(3.04, ts.Highs[52]);
            Assert.AreEqual(3.03, ts.Lows[52]);
            Assert.AreEqual(3.04, ts.Closes[52]);
            Assert.AreEqual(4, ts.Volumes[52]);
        }

        [Test]
        public void FillSeries_T_W()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.WEEK_PERIOD);

            Assert.AreEqual(TimeSeries.WEEK_PERIOD, ts.Period);
            Assert.AreEqual(12, ts.Count);
            Assert.AreEqual(11, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-13 16:08:54", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(2.0899, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(2.0697, ts.Closes[0]);
            Assert.AreEqual(101, ts.Volumes[0]);

            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[11]));
            Assert.AreEqual(2.89, ts.Opens[11]);
            Assert.AreEqual(3.04, ts.Highs[11]);
            Assert.AreEqual(2.89, ts.Lows[11]);
            Assert.AreEqual(3.04, ts.Closes[11]);
            Assert.AreEqual(15, ts.Volumes[11]);
        }

        [Test]
        public void FillSeries_T_M()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.MONTH_PERIOD);

            Assert.AreEqual(TimeSeries.MONTH_PERIOD, ts.Period);
            Assert.AreEqual(3, ts.Count);
            Assert.AreEqual(2, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-04-26 12:25:38", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(2.2275, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(2.185, ts.Closes[0]);
            Assert.AreEqual(650, ts.Volumes[0]);

            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[2]));
            Assert.AreEqual(2.555, ts.Opens[2]);
            Assert.AreEqual(3.04, ts.Highs[2]);
            Assert.AreEqual(2.5111, ts.Lows[2]);
            Assert.AreEqual(3.04, ts.Closes[2]);
            Assert.AreEqual(1095, ts.Volumes[2]);
        }

        [Test]
        public void FillSeries_T_Y()
        {
            IList<Tick> ticks = DataUtil.ReadTicks("DEBRK-IUN07");
            TimeSeries ts = new TimeSeries(ticks, TimeSeries.YEAR_PERIOD);

            Assert.AreEqual(TimeSeries.YEAR_PERIOD, ts.Period);
            Assert.AreEqual(1, ts.Count);
            Assert.AreEqual(0, ts.FirstLastDayEntry);

            Assert.AreEqual("2007-06-28 15:27:57", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(2.02, ts.Opens[0]);
            Assert.AreEqual(3.04, ts.Highs[0]);
            Assert.AreEqual(2.02, ts.Lows[0]);
            Assert.AreEqual(3.04, ts.Closes[0]);
            Assert.AreEqual(3346, ts.Volumes[0]);
        }

        [Test]
        public void FillSeries_EOD_D()
        {
            IList<EOD> eods = DataUtil.ReadEods("DERRC-SEP06");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);

            Assert.AreEqual(TimeSeries.DAY_PERIOD, ts.Period);
            Assert.AreEqual(120, ts.Count);
            Assert.AreEqual(119, ts.FirstLastDayEntry);

            Assert.AreEqual("2006-04-06 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(0.105, ts.Opens[0]);
            Assert.AreEqual(0.105, ts.Highs[0]);
            Assert.AreEqual(0.105, ts.Lows[0]);
            Assert.AreEqual(0.105, ts.Closes[0]);
            Assert.AreEqual(5, ts.Volumes[0]);

            Assert.AreEqual("2006-09-25 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[115]));
            Assert.AreEqual(0.0902, ts.Opens[115]);
            Assert.AreEqual(0.0914, ts.Highs[115]);
            Assert.AreEqual(0.0895, ts.Lows[115]);
            Assert.AreEqual(0.0912, ts.Closes[115]);
            Assert.AreEqual(248, ts.Volumes[115]);

            Assert.AreEqual("2006-09-29 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[119]));
            Assert.AreEqual(0.0915, ts.Opens[119]);
            Assert.AreEqual(0.0916, ts.Highs[119]);
            Assert.AreEqual(0.0906, ts.Lows[119]);
            Assert.AreEqual(0.0906, ts.Closes[119]);
            Assert.AreEqual(36, ts.Volumes[119]);
        }

        [Test]
        public void FillSeries_EOD_W()
        {
            IList<EOD> eods = DataUtil.ReadEods("DERRC-SEP06");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.WEEK_PERIOD);

            Assert.AreEqual(TimeSeries.WEEK_PERIOD, ts.Period);
            Assert.AreEqual(26, ts.Count);
            Assert.AreEqual(25, ts.FirstLastDayEntry);

            Assert.AreEqual("2006-04-07 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(0.105, ts.Opens[0]);
            Assert.AreEqual(0.105, ts.Highs[0]);
            Assert.AreEqual(0.1034, ts.Lows[0]);
            Assert.AreEqual(0.1034, ts.Closes[0]);
            Assert.AreEqual(47, ts.Volumes[0]);

            Assert.AreEqual("2006-04-15 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[1]));
            Assert.AreEqual(0.115, ts.Opens[1]);
            Assert.AreEqual(0.115, ts.Highs[1]);
            Assert.AreEqual(0.098, ts.Lows[1]);
            Assert.AreEqual(0.1, ts.Closes[1]);
            Assert.AreEqual(452, ts.Volumes[1]);

            Assert.AreEqual("2006-09-29 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[25]));
            Assert.AreEqual(0.0902, ts.Opens[25]);
            Assert.AreEqual(0.0917, ts.Highs[25]);
            Assert.AreEqual(0.0895, ts.Lows[25]);
            Assert.AreEqual(0.0906, ts.Closes[25]);
            Assert.AreEqual(772, ts.Volumes[25]);
        }

        [Test]
        public void FillSeries_EOD_M()
        {
            IList<EOD> eods = DataUtil.ReadEods("DERRC-SEP06");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.MONTH_PERIOD);

            Assert.AreEqual(TimeSeries.MONTH_PERIOD, ts.Period);
            Assert.AreEqual(6, ts.Count);
            Assert.AreEqual(5, ts.FirstLastDayEntry);

            Assert.AreEqual("2006-04-26 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(0.105, ts.Opens[0]);
            Assert.AreEqual(0.115, ts.Highs[0]);
            Assert.AreEqual(0.098, ts.Lows[0]);
            Assert.AreEqual(0.1015, ts.Closes[0]);
            Assert.AreEqual(704, ts.Volumes[0]);

            Assert.AreEqual("2006-09-29 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[5]));
            Assert.AreEqual(0.087, ts.Opens[5]);
            Assert.AreEqual(0.0917, ts.Highs[5]);
            Assert.AreEqual(0.084, ts.Lows[5]);
            Assert.AreEqual(0.0906, ts.Closes[5]);
            Assert.AreEqual(3004, ts.Volumes[5]);
        }

        [Test]
        public void FillSeries_EOD_Y()
        {
            IList<EOD> eods = DataUtil.ReadEods("DERRC-SEP06");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.YEAR_PERIOD);

            Assert.AreEqual(TimeSeries.YEAR_PERIOD, ts.Period);
            Assert.AreEqual(1, ts.Count);
            Assert.AreEqual(0, ts.FirstLastDayEntry);

            Assert.AreEqual("2006-09-29 00:00:00", DTUtil.ToDateTimeString(ts.DateTimes[0]));
            Assert.AreEqual(0.105, ts.Opens[0]);
            Assert.AreEqual(0.115, ts.Highs[0]);
            Assert.AreEqual(0.0628, ts.Lows[0]);
            Assert.AreEqual(0.0906, ts.Closes[0]);
            Assert.AreEqual(38765, ts.Volumes[0]);
        }

        [Test]
        public void GetPreviousDateTime()
        {
            DateTime date = DTUtil.ParseDateTime("2007-08-08 12:07:32");

            Assert.AreEqual("2007-08-08 12:07:32", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 0)));

            Assert.AreEqual("2007-08-08 12:07:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 1)));
            Assert.AreEqual("2007-08-08 12:05:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 5)));
            Assert.AreEqual("2007-08-08 12:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 10)));
            Assert.AreEqual("2007-08-08 12:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 15)));
            Assert.AreEqual("2007-08-08 12:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 30)));
            Assert.AreEqual("2007-08-08 12:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, 60)));

            Assert.AreEqual("2007-08-08 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, TimeSeries.DAY_PERIOD)));
            Assert.AreEqual("2007-08-06 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, TimeSeries.WEEK_PERIOD)));
            Assert.AreEqual("2007-08-01 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, TimeSeries.MONTH_PERIOD)));
            Assert.AreEqual("2007-01-01 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetPreviousDateTime(date, TimeSeries.YEAR_PERIOD)));
        }

        [Test]
        [ExpectedException("System.NotImplementedException")]
        public void GetPreviousDateTime_Exception()
        {
            DateTime date = DTUtil.ParseDateTime("2007-08-08 12:07:32");
            TimeSeries.GetPreviousDateTime(date, 120);
        }

        [Test]
        public void GetNextDateTime()
        {
            DateTime date = DTUtil.ParseDateTime("2007-08-08 12:07:32");

            Assert.AreEqual("2007-08-08 12:07:32", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 0)));

            Assert.AreEqual("2007-08-08 12:08:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 1)));
            Assert.AreEqual("2007-08-08 12:10:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 5)));
            Assert.AreEqual("2007-08-08 12:10:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 10)));
            Assert.AreEqual("2007-08-08 12:15:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 15)));
            Assert.AreEqual("2007-08-08 12:30:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 30)));
            Assert.AreEqual("2007-08-08 13:00:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, 60)));

            Assert.AreEqual("2007-08-09 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, TimeSeries.DAY_PERIOD)));
            Assert.AreEqual("2007-08-13 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, TimeSeries.WEEK_PERIOD)));
            Assert.AreEqual("2007-09-01 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, TimeSeries.MONTH_PERIOD)));
            Assert.AreEqual("2008-01-01 00:00:00", DTUtil.ToDateTimeString(TimeSeries.GetNextDateTime(date, TimeSeries.YEAR_PERIOD)));
        }

    }
}
