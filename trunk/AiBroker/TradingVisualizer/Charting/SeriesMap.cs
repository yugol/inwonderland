using System;
using System.Collections.Generic;
using TradingCommon;

namespace TradingVisualizer
{
    internal class VisualizerData
    {
        public const int DATETIMES_ID = 0;
        public const int OPENS_ID = 1;
        public const int HIGHS_ID = 2;
        public const int LOWS_ID = 3;
        public const int CLOSES_ID = 4;
        public const int VOLUMES_ID = 5;

        private IDictionary<int, double[]> data = new Dictionary<int, double[]>();
        public IDictionary<int, double[]> Data { get { return data; } }

        public VisualizerData(TimeSeries timeSeries)
        {
            data.Clear();
            data[OPENS_ID] = timeSeries.Opens;
            data[HIGHS_ID] = timeSeries.Highs;
            data[LOWS_ID] = timeSeries.Lows;
            data[CLOSES_ID] = timeSeries.Closes;
            data[VOLUMES_ID] = timeSeries.Volumes;
        }
    }
}
