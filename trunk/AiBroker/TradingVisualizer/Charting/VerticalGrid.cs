using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using Tao.FreeGlut;
using Tao.OpenGl;
using TradingCommon;

namespace TradingVisualizer.Charting
{
    class VerticalGrid
    {
        public static readonly IntPtr GLUT_X_LABELS_FONT = Glut.GLUT_BITMAP_HELVETICA_12;

        public enum GridPointFormat { EMPTY, DAY_SHORT, DAY_LONG, MONTH, YEAR_2, YEAR_4 }

        internal static void Paint(int first, int last, VerticalGrid verticalGrid)
        {
            Color fg = SystemColors.ControlText;

            Gl.glLoadIdentity();
            Glu.gluOrtho2D(first - VisualElement.ENTRY_HALF_WIDTH, last + VisualElement.ENTRY_HALF_WIDTH, 0, 1);
            Gl.glLineStipple(1, (short)0x0101);
            Gl.glEnable(Gl.GL_LINE_STIPPLE);
            Gl.glColor3d(fg.R / 255D, fg.G / 255D, fg.B / 255D);

            Gl.glBegin(Gl.GL_LINES);
            foreach (VerticalGrid.GridPoint gridPoint in verticalGrid.GridPoints)
            {
                double position = gridPoint.position;
                Gl.glVertex2d(position, 0);
                Gl.glVertex2d(position, 1);
            }
            Gl.glEnd();
        }

        internal static string Format(DateTime dateTime, GridPointFormat format)
        {
            StringBuilder rep = new StringBuilder(" ");

            switch (format)
            {
                case GridPointFormat.DAY_SHORT:
                    AppendDay(dateTime, rep);
                    break;
                case GridPointFormat.DAY_LONG:
                    rep = new StringBuilder(Format(dateTime, GridPointFormat.MONTH));
                    AppendDay(dateTime, rep);
                    break;
                case GridPointFormat.MONTH:
                    switch (dateTime.Month)
                    {
                        case 1: rep.Append("Jan"); break;
                        case 2: rep.Append("Feb"); break;
                        case 3: rep.Append("Mar"); break;
                        case 4: rep.Append("Apr"); break;
                        case 5: rep.Append("May"); break;
                        case 6: rep.Append("Jun"); break;
                        case 7: rep.Append("Jul"); break;
                        case 8: rep.Append("Aug"); break;
                        case 9: rep.Append("Sep"); break;
                        case 10: rep.Append("Oct"); break;
                        case 11: rep.Append("Nov"); break;
                        case 12: rep.Append("Dec"); break;
                    }
                    break;
                case GridPointFormat.YEAR_2:
                    int year = dateTime.Year % 100;
                    if (year < 10)
                        rep.Append("0");
                    rep.Append(year);
                    break;
                case GridPointFormat.YEAR_4:
                    rep.Append(dateTime.Year);
                    break;
                default:
                    throw new NotImplementedException();
            }

            rep.Append(" ");
            return rep.ToString();
        }

        private static void AppendDay(DateTime dateTime, StringBuilder rep)
        {
            int day = dateTime.Day;
            if (day < 10)
                rep.Append("0");
            rep.Append(day);
        }

        public class GridPoint
        {
            public int position;
            public string label = "";
            public DateTime dateTime;

            internal GridPoint(int position, DateTime dateTime)
            {
                this.position = position;
                this.dateTime = dateTime;
            }

            GridPointFormat format = GridPointFormat.EMPTY;
            public GridPointFormat Format
            {
                get { return format; }
                set
                {
                    format = value;
                    if (value == GridPointFormat.EMPTY)
                        label = "";
                    else
                        label = VerticalGrid.Format(dateTime, value);
                }
            }

            public int Length { get { return Glut.glutBitmapLength(GLUT_X_LABELS_FONT, label); } }
        }

        IList<GridPoint> gridPoints;
        public IList<GridPoint> GridPoints { get { return gridPoints; } }

        DateTime[] dateTimes;
        int first;
        int last;
        int period;
        double entryWidth;

        public VerticalGrid(DateTime[] dateTimes, int first, int last, int period, double entryWidth)
        {
            this.dateTimes = dateTimes;
            this.first = first;
            this.last = last;
            this.period = period;
            this.entryWidth = entryWidth;

            gridPoints = new List<GridPoint>();

            if (period == TimeSeries.YEAR_PERIOD)
                FindYearlyPoints();
            else if (period == TimeSeries.MONTH_PERIOD)
                FindMonthlyPoints();
            else if (period == TimeSeries.WEEK_PERIOD || period == TimeSeries.DAY_PERIOD || period == TimeSeries.DAY_FROM_TICK_PERIOD)
                FindDailyPoints();
            else
                FindIntradayPoints();
        }

        private void FindIntradayPoints()
        {
            DateTime prev = DateTime.MinValue;
            int pointIndex = first;
            while (pointIndex <= last)
            {
                DateTime curr = dateTimes[pointIndex];
                int skipable = DistToNext(TimeSeries.DAY_PERIOD, pointIndex);
                if (skipable == 0)
                    skipable = last - pointIndex + 1;
                GridPoint entry = new GridPoint(pointIndex, curr);
                int extra = FormatAsDay(entry, skipable);
                if (entry.Format != GridPointFormat.EMPTY)
                    gridPoints.Add(entry);
                int skipCount = Math.Max(extra, skipable - 1);
                pointIndex += (skipCount + 1);
                prev = curr;
                if (skipable == 0)
                    break;
            }
        }

        private void FindDailyPoints()
        {
            DateTime prev = DateTime.MinValue;
            int pointIndex = first;
            while (pointIndex <= last)
            {
                DateTime curr = dateTimes[pointIndex];
                bool formatAsYear = (curr.Month == 1) || (prev.Equals(DateTime.MinValue) && (prev.Year != curr.Year));

                int skipable = DistToNext(TimeSeries.MONTH_PERIOD, pointIndex);
                if (skipable == 0)
                    skipable = last - pointIndex + 1;

                GridPoint entry = new GridPoint(pointIndex, curr);
                int extra = formatAsYear ?
                    FormatAsYear(entry, DistToNext(TimeSeries.YEAR_PERIOD, pointIndex))
                    : FormatAsMonth(entry, skipable);
                if (entry.Format != GridPointFormat.EMPTY)
                    gridPoints.Add(entry);
                int skipCount = Math.Max(extra, skipable - 1);
                pointIndex += (skipCount + 1);
                prev = curr;
                if (skipable == 0)
                    break;
            }
        }

        private void FindMonthlyPoints()
        {
            DateTime prev = DateTime.MinValue;
            int pointIndex = first;
            while (pointIndex <= last)
            {
                DateTime curr = dateTimes[pointIndex];
                bool formatAsYear = (curr.Month == 1) || (prev.Equals(DateTime.MinValue) && (prev.Year != curr.Year));
                GridPoint gridPoint = new GridPoint(pointIndex, curr);
                int extra = formatAsYear ? FormatAsYear(gridPoint, 1) : FormatAsMonth(gridPoint, 1);
                if (gridPoint.Format != GridPointFormat.EMPTY)
                    gridPoints.Add(gridPoint);
                pointIndex += (extra + 1);
                prev = curr;
            }
        }

        private void FindYearlyPoints()
        {
            DateTime prevDateTime = dateTimes[0];
            int pointIndex = first;
            while (pointIndex <= last)
            {
                GridPoint gridPoint = new GridPoint(pointIndex, dateTimes[pointIndex]);
                gridPoints.Add(gridPoint);
                int extra = FormatAsYear(gridPoint, 1);
                pointIndex += (extra + 1);
            }
        }

        private int FormatAsDay(GridPoint gridPoint, int availableEntries)
        {
            double pixelWidth = availableEntries * entryWidth;
            gridPoint.Format = GridPointFormat.DAY_LONG;
            if (gridPoint.Length > pixelWidth)
                gridPoint.Format = GridPointFormat.DAY_SHORT;
            int required = (int)Math.Ceiling(gridPoint.Length / entryWidth);
            return required - 1;
        }

        private int FormatAsMonth(GridPoint gridPoint, int availableEntries)
        {
            gridPoint.Format = GridPointFormat.MONTH;
            int required = (int)Math.Ceiling(gridPoint.Length / entryWidth);
            int entryIndex = gridPoint.position;
            int checkTo = Math.Min(entryIndex + required - 1, last);
            for (int i = entryIndex + 1; i <= checkTo; ++i)
            {
                if (gridPoint.dateTime.Year != dateTimes[i].Year)
                {
                    gridPoint.Format = GridPointFormat.EMPTY;
                    required = 1;
                    break;
                }
            }
            return required - 1;
        }

        private int FormatAsYear(GridPoint gridPoint, int availableEntries)
        {
            double pixelWidth = availableEntries * entryWidth;
            gridPoint.Format = GridPointFormat.YEAR_4;
            if (gridPoint.Length > pixelWidth)
            {
                gridPoint.Format = GridPointFormat.YEAR_2;
                if (gridPoint.Length > pixelWidth)
                {
                    gridPoint.Format = GridPointFormat.YEAR_4;
                }
            }
            int required = (int)Math.Ceiling(gridPoint.Length / entryWidth);
            return required - 1;
        }

        private int DistToNext(int period, int pointIndex)
        {
            int dist = 0;
            DateTime prev = dateTimes[pointIndex];
            for (int i = pointIndex + 1; i <= last; ++i)
            {
                ++dist;
                DateTime curr = dateTimes[i];
                if (curr.Year != prev.Year)
                    return dist;
                switch (period)
                {
                    case TimeSeries.MONTH_PERIOD:
                        if (curr.Month != prev.Month)
                            return dist;
                        break;
                    case TimeSeries.DAY_PERIOD:
                        if (curr.Month != prev.Month || curr.Day != prev.Day)
                            return dist;
                        break;
                }
            }
            return 0;
        }
    }
}
