using System;
using System.Collections.Generic;
using Tao.FreeGlut;
using TradingCommon;
using TradingVisualizer.Ui;
using TradingCommon.Util;

namespace TradingVisualizer.Charting
{
    class HorizontalGrid
    {
        public class GridPoint
        {
            public LabelFormat format;
            public String label;
            public double value;
            public int LabelLength { get { return Glut.glutBitmapLength(GLUT_Y_LABELS_FONT, label); } }
        }

        public enum LabelFormat { LABEL, MAJOR, MIDDLE, MINOR }

        public static readonly IntPtr GLUT_Y_LABELS_FONT = Glut.GLUT_BITMAP_HELVETICA_10;
        public const int LABEL_LOGIC_DISTANCE = 18;
        public const int MAJOR_LOGIC_LENGTH = 14;
        public const int MIDDLE_LOGIC_LENGTH = 8;
        public const int MINOR_LOGIC_LENGTH = 4;
        public const int TOTAL_LOGIC_WIDTH = MultiChartControl.Y_SCALE_WIDTH;
        public const int FONT_HEIGHT = 8;
        private const double MIN_MAJOR_DIVISION_HEIGHT = FONT_HEIGHT * 3;

        private double[] bottomTop;
        private LinearMapper logic2widget;
        private double magnitude;
        private double majorStep;
        private IList<GridPoint> points = new List<GridPoint>();
        private int widgetHeight;
        private double middleStep;
        private double minorStep;

        public HorizontalGrid(double min, double max, int widgetHeight)
        {
            this.bottomTop = new double[] { min, max };
            this.widgetHeight = widgetHeight;
            this.logic2widget = new LinearMapper(bottomTop[0], bottomTop[1], 0, widgetHeight - 1);

            CalculateMagnitude();
            CalculateIncrements();

            if (majorStep < 0) return;

            int y;
            double major = ((long)bottomTop[0] / majorStep) * majorStep - majorStep;
            while (true)
            {
                for (int j = 1; j <= minorCount; ++j)
                {
                    double minor = major + minorStep * j;
                    y = logic2widget.MapToInt(minor);
                    if (0 < y && y < widgetHeight) AddEntry(minor, LabelFormat.MINOR);
                }

                for (int i = 1; i <= middleCount; ++i)
                {
                    double middle = major + middleStep * i;
                    y = logic2widget.MapToInt(middle);
                    if (0 < y && y < widgetHeight) AddEntry(middle, LabelFormat.MIDDLE);

                    for (int j = 1; j <= minorCount; ++j)
                    {
                        double minor = middle + minorStep * j;
                        y = logic2widget.MapToInt(minor);
                        if (0 < y && y < widgetHeight) AddEntry(minor, LabelFormat.MINOR);
                    }
                }

                y = logic2widget.MapToInt(major);
                if (y < widgetHeight)
                {
                    if (y > 0)
                    {
                        if (y < widgetHeight - FONT_HEIGHT - 2) AddLabel(major);
                        else AddEntry(major, LabelFormat.MAJOR);
                    }
                    major += majorStep;
                }
                else break;
            }
        }

        public int Magnitude { get { return (int)magnitude; } }
        public IList<GridPoint> Points { get { return points; } }

        private void AddEntry(double logicalPos, LabelFormat labelFormat)
        {
            GridPoint entry = new GridPoint();
            entry.value = logicalPos;
            entry.format = labelFormat;
            points.Add(entry);
        }

        private void AddLabel(double major)
        {
            GridPoint entry = new GridPoint();
            entry.value = major;
            entry.format = LabelFormat.LABEL;

            String label;
            if (magnitude > 1)
            {
                label = (major / magnitude).ToString("#");
            }
            else
            {
                label = major.ToString("0.0000");
                if (label.IndexOf(".0000") >= 0)
                    label = major.ToString("0");
            }
            entry.label = label;
            points.Add(entry);
        }

        private int middleCount;

        private int minorCount;

        private void CalculateIncrements()
        {
            this.majorStep = 0.0001;

            double logicalHeight = bottomTop[1] - bottomTop[0];
            double majorIncrement;
            int majorDivisionHeight;

            while (true)
            {
                try
                {
                    majorIncrement = this.majorStep;
                    majorDivisionHeight = widgetHeight / (int)Math.Round(logicalHeight / majorIncrement);
                    middleCount = 1;
                    minorCount = (majorDivisionHeight > 32) ? 4 : 1;

                    if (majorDivisionHeight < MIN_MAJOR_DIVISION_HEIGHT)
                    {
                        majorIncrement *= 2.5;
                        majorDivisionHeight = widgetHeight / (int)Math.Round(logicalHeight / majorIncrement);
                        middleCount = 0;
                        minorCount = 4;
                        if (majorDivisionHeight < MIN_MAJOR_DIVISION_HEIGHT)
                        {
                            majorIncrement *= 2;
                            majorDivisionHeight = widgetHeight / (int)Math.Round(logicalHeight / majorIncrement);
                            middleCount = 4;
                            minorCount = 0;
                            if (majorDivisionHeight < MIN_MAJOR_DIVISION_HEIGHT)
                            {
                                this.majorStep *= 10;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                catch (ArithmeticException)
                {
                    majorIncrement = -1;
                    middleCount = 0;
                    minorCount = 0;
                    break;
                }
            }

            this.majorStep = majorIncrement;
            this.middleStep = (middleCount > 0) ? majorStep / (middleCount + 1) : this.majorStep;
            this.minorStep = (minorCount > 0) ? middleStep / (minorCount + 1) : 0;
        }

        private void CalculateMagnitude()
        {
            this.magnitude = Math.Pow(10, (long)Math.Log10(bottomTop[1])) / 1000;
            if (magnitude < 1) magnitude = 1;
        }
    }
}
