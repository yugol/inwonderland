using System;
using System.Drawing;
using System.Windows.Forms;

using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Ui;
using TradingCommon.Util;

namespace TradingSimulator.Ui
{
    public partial class SearchSpaceExplorer : Form
    {
        static Pen[] PENS;
        static Brush[] BRUSHES;
        static SearchSpaceExplorer()
        {
            PENS = new Pen[256];
            BRUSHES = new Brush[PENS.Length];
            for (int i = 0; i < PENS.Length; ++i)
            {
                Color color = Color.FromArgb(i, 180, 255 - i);
                PENS[i] = new Pen(color);
                BRUSHES[i] = new SolidBrush(color);
            }
        }

        // TSTrader simulator = null;

        // int xParamIdx = 0;
        // int yParamIdx = 0;

        double absMinX = 0;
        double absMaxX = 0;
        double absMinY = 0;
        double absMaxY = 0;

        public double XMin
        {
            get { return double.Parse(xMin.Text); }
            set { xMin.Text = value.ToString("0.0000"); }
        }

        public double XMax
        {
            get { return double.Parse(xMax.Text); }
            set { xMax.Text = value.ToString("0.0000"); }
        }

        public double YMin
        {
            get { return double.Parse(yMin.Text); }
            set { yMin.Text = value.ToString("0.0000"); }
        }

        public double YMax
        {
            get { return double.Parse(yMax.Text); }
            set { yMax.Text = value.ToString("0.0000"); }
        }

        public double SelXValue
        {
            get { return double.Parse(selXValue.Text); }
            set { selXValue.Text = value.ToString("0.0000"); }
        }

        public double SelYValue
        {
            get { return double.Parse(selYValue.Text); }
            set { selYValue.Text = value.ToString("0.0000"); }
        }

        internal int XDivs { get { return (int)xDivs.Value; } }
        internal int YDivs { get { return (int)yDivs.Value; } }

        double[,] fitnessMap = null;
        LinearMapper mapColorMapper = null;

        float cellWidth = 0;
        float cellHeight = 0;

        double[] xDomain = null;
        double[] yDomain = null;

        XORDrawing rubberBand = new XORDrawing();
        Point mouseDownPoint = new Point(-1, 0);
        Point prevToRubberPoint = new Point(-1, 0);
        int timeZoneSpanDays = -1;

        // bool isInitialized = false;

        internal SearchSpaceExplorer(
            TSTrader simulator,
            int xParamIndex, int yParamIndex,
            SearchSpace searchSpace,
            DateTime from, DateTime to)
        {
//            InitializeComponent();
//            CenterToParent();
//
//            this.simulator = simulator;
//            this.xParamIdx = xParamIndex;
//            this.yParamIdx = yParamIndex;
//
//            Text = "Search Space Explorer - " + simulator.Name + " - " + simulator.Title.FullSymbol;
//            selXName.Text = xName.Text = (simulator.Parameters[xParamIndex].Name + ":");
//            selYName.Text = yName.Text = (simulator.Parameters[yParamIndex].Name + ":");
//
//            selXValue.Text = simulator.Parameters[xParamIndex].Value.ToString("0.0000");
//            selYValue.Text = simulator.Parameters[yParamIndex].Value.ToString("0.0000");
//
//            absMinX = simulator.Parameters[xParamIndex].MinValue;
//            absMaxX = simulator.Parameters[xParamIndex].MaxValue;
//            absMinY = simulator.Parameters[yParamIndex].MinValue;
//            absMaxY = simulator.Parameters[yParamIndex].MaxValue;
//
//            xDivs.Value = searchSpace[xParamIdx].Length;
//            yDivs.Value = searchSpace[yParamIdx].Length;
//
//            XMin = searchSpace[xParamIdx][0];
//            XMax = searchSpace[xParamIdx][XDivs - 1];
//            YMin = searchSpace[yParamIdx][0];
//            YMax = searchSpace[yParamIdx][YDivs - 1];
//
//            this.from.MinDate = this.to.MinDate = simulator.TimeSeries.FirstDate;
//            this.from.MaxDate = this.to.MaxDate = simulator.TimeSeries.LastDate;
//            this.from.Value = from;
//            this.to.Value = to;
//
//            SetTimeZone();
//
//            isInitialized = true;
//
//            UpdateFitnessMap();
        }

        private void UpdateFitnessMap()
        {
//            if (!isInitialized) return;
//
//            double minFitness = double.MaxValue;
//            double maxFitness = double.MinValue;
//            fitnessMap = new double[XDivs, YDivs];
//
//            int first = simulator.TimeSeries.GetFirstBarIndexForDate(from.Value);
//            int last = simulator.TimeSeries.GetLastBarIndexForDate(to.Value);
//
//            xDomain = TSParameter.BuildDomain(XMin, XMax, XDivs);
//            yDomain = TSParameter.BuildDomain(YMin, YMax, YDivs);
//
//            for (int x = 0; x < xDomain.Length; ++x)
//            {
//                for (int y = 0; y < yDomain.Length; ++y)
//                {
//                    simulator.Parameters[xParamIdx].Value = xDomain[x];
//                    simulator.Parameters[yParamIdx].Value = yDomain[y];
//                    double fitness = ((IQuicklyEvaluable)simulator).QuickFitness(first, last);
//                    if (fitness > maxFitness) maxFitness = fitness;
//                    if (fitness < minFitness) minFitness = fitness;
//                    fitnessMap[x, y] = fitness;
//                }
//            }
//
//            mapColorMapper = new LinearMapper(minFitness, maxFitness, 0, PENS.Length - 1);
//
//            this.maxFitness.Text = maxFitness.ToString("0.0000");
//            this.middleFitness.Text = ((maxFitness + minFitness) / 2).ToString("0.0000");
//            this.minFitness.Text = minFitness.ToString("0.0000");
//
//            cellWidth = (float)map.ClientRectangle.Width / XDivs;
//            cellHeight = (float)map.ClientRectangle.Height / YDivs;
//
//            map.Invalidate();
        }

        private void done_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            Close();
        }

        private void legend_Paint(object sender, PaintEventArgs e)
        {
            LinearMapper legendColorMapper = new LinearMapper(legend.Height, 0, 0, PENS.Length - 1);
            int y = legend.ClientRectangle.Width - 1;
            for (int i = 0; i < legend.ClientRectangle.Height; ++i)
                e.Graphics.DrawLine(PENS[legendColorMapper.MapToInt(i)], 0, i, y, i);
        }

        private void map_Paint(object sender, PaintEventArgs e)
        {
            for (int x = 0; x < xDomain.Length; ++x)
            {
                for (int y = 0; y < yDomain.Length; ++y)
                {
                    PaintCell(x, y, e.Graphics);
                }
            }
        }

        private void PaintCell(int x, int y, Graphics g)
        {
            Brush brush = BRUSHES[mapColorMapper.MapToInt(fitnessMap[x, y])];
            y = yDomain.Length - 1 - y;
            float px = x * cellWidth;
            float py = y * cellHeight;
            g.FillRectangle(brush, px, py, cellWidth, cellHeight);
        }

        private void yDivs_ValueChanged(object sender, EventArgs e)
        {
            UpdateFitnessMap();
        }

        private void xDivs_ValueChanged(object sender, EventArgs e)
        {
            UpdateFitnessMap();
        }

        private void map_MouseLeave(object sender, EventArgs e)
        {
            xValue.Text = "N/A";
            yValue.Text = "N/A";
            fitnessValue.Text = "N/A";
        }

        private void map_MouseMove(object sender, MouseEventArgs e)
        {
            if (map.ClientRectangle.Contains(e.X, e.Y))
            {
                int x, y;
                ScreenToMatrix(e.X, e.Y, out x, out y);

                xValue.Text = xDomain[x].ToString("0.0000");
                yValue.Text = yDomain[y].ToString("0.0000");
                fitnessValue.Text = fitnessMap[x, y].ToString("0.0000");
            }
            else
            {
                map_MouseLeave(sender, e);
            }

            if (mouseDownPoint.X >= 0)
            {
                if (map.ClientRectangle.Contains(e.X, e.Y))
                {
                    DeleteRubberBand();
                    rubberBand.DrawXORRectangle(map.CreateGraphics(), mouseDownPoint, e.Location);
                    prevToRubberPoint = e.Location;
                }
                else
                {
                    StopMoving();
                }
            }
        }

        private void ScreenToMatrix(int xIn, int yIn, out int xOut, out int yOut)
        {
            xOut = (int)(xIn / cellWidth);
            yOut = (int)(yIn / cellHeight);
            yOut = yDomain.Length - 1 - yOut;
        }

        private void map_MouseDown(object sender, MouseEventArgs e)
        {
            mouseDownPoint = e.Location;
        }

        private void map_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Location.Equals(mouseDownPoint))
            {
                int x, y;
                ScreenToMatrix(e.X, e.Y, out x, out y);
                SelXValue = xDomain[x];
                SelYValue = yDomain[y];
            }
            else
            {
                if (mouseDownPoint.X >= 0)
                {
                    int x1, y1, x2, y2;
                    ScreenToMatrix(mouseDownPoint.X, mouseDownPoint.Y, out x1, out y1);
                    ScreenToMatrix(e.X, e.Y, out x2, out y2);
                    ResizeDomains(x1, y1, x2, y2);
                }
            }
            StopMoving();
        }

        private void StopMoving()
        {
            DeleteRubberBand();
            mouseDownPoint.X = -1;
            prevToRubberPoint.X = -1;
        }

        private void DeleteRubberBand()
        {
            Graphics g = map.CreateGraphics();
            if (prevToRubberPoint.X >= 0)
                rubberBand.DrawXORRectangle(g, mouseDownPoint, prevToRubberPoint);
        }

        private void ResizeDomains(int x1, int y1, int x2, int y2)
        {
            if (x1 > x2) { int temp = x1; x1 = x2; x2 = temp; }
            if (y1 > y2) { int temp = y1; y1 = y2; y2 = temp; }

            double dx = xDomain[x2] - xDomain[x1];
            double dy = xDomain[y2] - xDomain[y1];

            if ((dx < 0.001) || (dy < 0.001))
                return;

            XMin = xDomain[x1];
            XMax = xDomain[x2];
            YMin = yDomain[y1];
            YMax = yDomain[y2];

            UpdateFitnessMap();
        }

        private void zoomOut_Click(object sender, EventArgs e)
        {
            double dx = (XMax - XMin) / 2;
            double dy = (YMax - YMin) / 2;

            XMin -= dx;
            XMax += dx;
            YMin -= dy;
            YMax += dy;

            if (XMin < absMinX) XMin = absMinX;
            if (XMax > absMaxX) XMax = absMaxX;
            if (YMin < absMinX) YMin = absMinY;
            if (YMax > absMaxY) YMax = absMaxY;

            UpdateFitnessMap();
        }

        private void timeZone_Scroll(object sender, EventArgs e)
        {
            TimeSpan span = new TimeSpan(timeZone.Value, 0, 0, 0);
            if (!lockFrom.Checked)
            {
                from.Value = from.MinDate.Add(span);
                span = new TimeSpan(timeZoneSpanDays, 0, 0, 0);
            }
            to.Value = from.Value.Add(span);

            UpdateFitnessMap();
        }

        private void lockFrom_CheckedChanged(object sender, EventArgs e)
        {
            SetTimeZone();
        }

        private void SetTimeZone()
        {
            TimeSpan span = to.MaxDate - from.MinDate;
            int totalDays = (int)Math.Round(span.TotalDays);

            span = from.Value - from.MinDate;
            int skippedLeft = (int)Math.Round(span.TotalDays);

            span = to.Value - from.Value;
            timeZoneSpanDays = (int)Math.Round(span.TotalDays);

            if (lockFrom.Checked)
            {
                timeZone.Maximum = totalDays - skippedLeft;
                timeZone.Value = timeZoneSpanDays;
            }
            else
            {
                timeZone.Maximum = totalDays - timeZoneSpanDays;
                timeZone.Value = skippedLeft;
            }
        }

    }
}
