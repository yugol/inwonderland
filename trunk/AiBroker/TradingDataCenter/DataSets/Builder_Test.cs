#if TEST

using System;
using System.Collections.Generic;
using System.Text;

using NUnit.Framework;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingDataCenter.DataSets
{
    // [TestFixture]
    public class Builder_Test
    {
        // [Test]
        public void SIF2_3OHLC_O()
        {
            IList<EOD> eods = DataUtil.ReadEods("SIF2");
            TimeSeries ts = new TimeSeries(eods, TimeSeries.DAY_PERIOD);
            int first = ts.GetBarIndex(DTUtil.ParseDate("2005-01-02"));
            int last = ts.GetBarIndex(DTUtil.ParseDate("2007-07-31"));

            // Console.WriteLine(first);
            // Console.WriteLine(last);

            IList<RelativeEOD> reods = new List<RelativeEOD>();
            for (int i = first + 1; i <= last; ++i)
            {
                RelativeEOD reod = new RelativeEOD();
                reod.date = ts.DateTimes[i];
                reod.open = ts.Opens[i] - ts.Closes[i - 1];
                reod.high = ts.Highs[i] - ts.Highs[i - 1];
                reod.low = ts.Lows[i] - ts.Lows[i - 1];
                reod.close = ts.Closes[i] - ts.Opens[i];
                reod.MultiplyBy(100);
                //Console.WriteLine(reod);
                reods.Add(reod);
            }

            for (int i = 1; i < reods.Count; ++i)
            {
                StringBuilder patt = new StringBuilder();
                patt.Append(reods[i - 1]);
                patt.Append(",");
                patt.Append(Math.Sign(reods[i].open));
                patt.Append(",");
                int c0 = Math.Sign(ts.Closes[i] - ts.Opens[i]);
                if (c0 == 0)
                    continue;
                patt.Append(c0);

                Console.WriteLine(patt);
            }
        }
    }

    public class RelativeEOD
    {
        public DateTime date;
        public double open;
        public double high;
        public double low;
        public double close;

        public void MultiplyBy(double val)
        {
            open *= val;
            high *= val;
            low *= val;
            close *= val;
        }

        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            //sb.Append(DTUtil.ToDateString(date));
            //sb.Append(",");
            sb.Append(Math.Sign(open));
            sb.Append(",");
            sb.Append(Math.Sign(high));
            sb.Append(",");
            sb.Append(Math.Sign(low));
            sb.Append(",");
            sb.Append(Math.Sign(close));
            return sb.ToString();
        }
    }
}

#endif
