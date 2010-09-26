using System;
using System.Windows.Forms;
using System.Xml;
using TradingCommon;
using TradingCommon.Storage;
using TradingVisualizer.Charting;
using WeifenLuo.WinFormsUI.Docking;

namespace TradingVisualizer.Ui
{
    public partial class MultiChartFrame : DockContent
    {
        Title title = null;
        public Title Title
        {
            get { return title; }
            set
            {
                title = value;
                TabText = title.FullSymbol;
                chartLayout = new ChartLayout(title.FullSymbol);
                ApplyLayout();
                LoadData();
            }
        }

        ChartLayout chartLayout = null;

        TimeSeries timeSeries = null;
        public TimeSeries TimeSeries { get { return timeSeries; } }

        VisualizerData seriesMap = null;
        internal VisualizerData SeriesMap { get { return seriesMap; } }

        public MultiChartControl MultiChartControl { get { return multiChartControl; } }

        public MultiChartFrame()
        {
            InitializeComponent();
            multiChartControl.PeriodChanged += new PeriodChangedEventHandler(multiChartControl_PeriodChanged);
            multiChartControl.ReloadRefresh += new ReloadRefreshEventHandler(multiChartControl_ReloadRefresh);
        }

        private void LoadData()
        {
            timeSeries = DataUtil.ReadTimeSeries(title.FullSymbol, multiChartControl.Period);
            seriesMap = new VisualizerData(timeSeries);
            multiChartControl.Period = timeSeries.Period;
            multiChartControl.Zoom = chartLayout.Zoom;
            multiChartControl.LastDateTime = chartLayout.LastDateTime;
            multiChartControl.DeltaTerm = chartLayout.DeltaTerm;
            multiChartControl.CalibrateNavigator();
            DrawChart();
        }

        private void DrawChart()
        {
            multiChartControl.DrawChart();
        }

        private void ApplyLayout()
        {
            multiChartControl.Period = chartLayout.Period;
            multiChartControl.WindowSplits = chartLayout.WindowSplits;
            multiChartControl.YScaleWidth = MultiChartControl.Y_SCALE_WIDTH;

            for (int i = 0; i < multiChartControl.WindowCount; ++i)
            {
                XmlNodeList visualElementsNodes = chartLayout.GetVisualElements(i);
                foreach (XmlNode node in visualElementsNodes)
                {
                    Type veType = Type.GetType(node.Attributes[ChartLayout.TYPE].Value + ", TradingVisualizer", true);
                    VisualElement ve = (VisualElement)Activator.CreateInstance(veType, new object[] { int.Parse(node.Attributes[ChartLayout.ID].Value), 0, this });
                    multiChartControl.Charts[i].AddVisualElement(ve);
                }
            }
        }

        internal void SaveLayout()
        {
            chartLayout.Period = timeSeries.Period;
            chartLayout.LastDateTime = multiChartControl.LastDateTime;
            chartLayout.Zoom = multiChartControl.Zoom;
            chartLayout.DeltaTerm = multiChartControl.DeltaTerm;
            chartLayout.Save();
        }

        private void multiChartControl_PeriodChanged(int newPeriod)
        {
            multiChartControl.Period = newPeriod;
            if (multiChartControl.DeltaTerm != TradingCommon.Indicators.Delta.Term.NONE)
            {
                if (multiChartControl.Period < TimeSeries.DAY_FROM_TICK_PERIOD)
                    multiChartControl.DeltaTerm = TradingCommon.Indicators.Delta.Term.STD;
                else if (multiChartControl.Period == TimeSeries.DAY_FROM_TICK_PERIOD
                    || multiChartControl.Period == TimeSeries.DAY_PERIOD)
                    multiChartControl.DeltaTerm = TradingCommon.Indicators.Delta.Term.ITD;
                else if (multiChartControl.Period == TimeSeries.WEEK_PERIOD)
                    multiChartControl.DeltaTerm = TradingCommon.Indicators.Delta.Term.MTD;
            }
            SaveLayout();
            LoadData();
        }

        private void ChartFrame_FormClosing(object sender, FormClosingEventArgs e)
        {
            SaveLayout();
        }

        private void multiChartControl_ReloadRefresh()
        {
            SaveLayout();
            LoadData();
        }
    }
}
