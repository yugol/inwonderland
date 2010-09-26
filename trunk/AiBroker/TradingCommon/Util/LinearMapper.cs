using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Util
{
    public class LinearMapper
    {
        private double xMin;
        private double yMin;
        private double coef;
        private double dx;
        private double dy;

        public LinearMapper(
            double xMin, double xMax,
            double yMin, double yMax)
        {
            this.xMin = xMin;
            this.yMin = yMin;
            dx = xMax - xMin;
            dy = yMax - yMin;
            if (dx != 0)
                coef = dy / dx;
        }

        public double Map(double x)
        {
            return (coef * (x - xMin) + yMin);
        }

        public int MapToInt(double x)
        {
            double y = Map(x);
            return (int)Math.Round(y);
        }

        public double Unmap(double y)
        {
            if ((dx == 0) || (dy == 0)) { return xMin; }
            return ((y - yMin) / coef + xMin);
        }

        public static double Map(
            double value,
            double fromMin, double fromMax,
            double toMin, double toMax)
        {
            double dx = fromMax - fromMin;
            double dy = toMax - toMin;
            double coef = 0;
            if (dx != 0) coef = dy / dx;
            return (coef * (value - fromMin) + toMin);
        }
    }
}
