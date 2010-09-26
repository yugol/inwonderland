using System;
using System.Collections.Generic;
using Tao.OpenGl;
using TradingVisualizer.Ui;

namespace TradingVisualizer.Charting
{
    public class VeVolume : VisualElement
    {
        public VeVolume(int id, int dataId, MultiChartFrame parentFrame)
            : base(id, dataId, parentFrame)
        {
        }

        internal override void GetMinMax(int first, int last, out double min, out double max)
        {
            double[] data = parentFrame.SeriesMap.Data[VisualizerData.VOLUMES_ID];

            min = 0;
            max = double.MinValue;

            for (int x = first; x <= last; ++x)
                if (data[x] > max)
                    max = data[x];
        }

        internal override void Paint(int first, int last, double min, double max)
        {
            base.Paint(first, last, min, max);

            double[] data = parentFrame.SeriesMap.Data[VisualizerData.VOLUMES_ID];

            Gl.glColor4d(0, 0, 1, 1);

            for (int x = first; x <= last; ++x)
            {
                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2d(x, 0);
                Gl.glVertex2d(x, data[x]);
                Gl.glEnd();
            }
        }

    }
}
