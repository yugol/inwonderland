using System;
using Tao.OpenGl;
using TradingVisualizer.Ui;

namespace TradingVisualizer.Charting
{
    public class VeOhlc : VisualElement
    {
        const double PRICE_SIDE_SIZE = 0.42;

        public VeOhlc(int id, int dataId, MultiChartFrame parentFrame)
            : base(id, dataId, parentFrame)
        {
        }

        internal override void Paint(int first, int last, double min, double max)
        {
            base.Paint(first, last, min, max);

            double[] opens = parentFrame.SeriesMap.Data[VisualizerData.OPENS_ID];
            double[] highs = parentFrame.SeriesMap.Data[VisualizerData.HIGHS_ID];
            double[] lows = parentFrame.SeriesMap.Data[VisualizerData.LOWS_ID];
            double[] closes = parentFrame.SeriesMap.Data[VisualizerData.CLOSES_ID];

            for (int x = first; x <= last; ++x)
            {
                double o = opens[x];
                double h = highs[x];
                double l = lows[x];
                double c = closes[x];

                DrawCandlestick(x, o, h, l, c);
            }
        }

        private void DrawCandlestick(int x, double o, double h, double l, double c)
        {
            bool up = true;
            if (o > c)
            {
                double temp = o;
                o = c;
                c = temp;
                up = false;
            }

            if (up)
                Gl.glColor3d(0, 0.6, 0);
            else
                Gl.glColor3d(0.98, 0, 0);
            if (o == c)
                Gl.glColor3d(0, 0, 0);

            Gl.glPolygonMode(Gl.GL_FRONT_AND_BACK, Gl.GL_FILL);

            Gl.glBegin(Gl.GL_QUADS);
            Gl.glVertex2d(x - PRICE_SIDE_SIZE, c);
            Gl.glVertex2d(x + PRICE_SIDE_SIZE, c);
            Gl.glVertex2d(x + PRICE_SIDE_SIZE, o);
            Gl.glVertex2d(x - PRICE_SIDE_SIZE, o);
            Gl.glEnd();

            Gl.glBegin(Gl.GL_LINES);
            Gl.glVertex2d(x, h);
            Gl.glVertex2d(x, c);
            Gl.glVertex2d(x - PRICE_SIDE_SIZE, c);
            Gl.glVertex2d(x + PRICE_SIDE_SIZE, c);
            Gl.glVertex2d(x - PRICE_SIDE_SIZE, o);
            Gl.glVertex2d(x + PRICE_SIDE_SIZE, o);
            Gl.glVertex2d(x, o);
            Gl.glVertex2d(x, l);
            Gl.glEnd();
        }

        internal override void GetMinMax(int first, int last, out double min, out double max)
        {
            double[] highs = parentFrame.SeriesMap.Data[VisualizerData.HIGHS_ID];
            double[] lows = parentFrame.SeriesMap.Data[VisualizerData.LOWS_ID];

            min = double.MaxValue;
            max = double.MinValue;

            for (int x = first; x <= last; ++x)
            {
                if (highs[x] > max)
                    max = highs[x];
                if (lows[x] < min)
                    min = lows[x];
            }
        }
    }
}
