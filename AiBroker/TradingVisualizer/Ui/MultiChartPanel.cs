using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace TradingVisualizer.Ui
{
    public partial class MultiChartPanel : TableLayoutPanel
    {
        const float minPanelHeight = 3F;
        const float totalPanelsPercent = 100F;

        private static void Swap<T>(IList<T> list, int pos1, int pos2)
        {
            T tmp = list[pos1];
            list[pos1] = list[pos2];
            list[pos2] = tmp;
        }

        IList<ChartTitle> titles = new List<ChartTitle>();
        IList<ChartContent> chartControls = new List<ChartContent>();
        List<float> splitPecents = new List<float>();

        public float[] WindowSplits
        {
            get { return splitPecents.ToArray(); }
            set
            {
                titles.Clear();
                chartControls.Clear();
                splitPecents.Clear();

                for (int i = 0; i < value.Length; ++i)
                {
                    titles.Add(new ChartTitle());
                    chartControls.Add(new ChartContent());
                    splitPecents.Add(value[i]);
                }

                NormalizeSplits();
                PlaceComponents();
            }
        }

        public int WindowCount { get { return titles.Count; } }
        public IList<ChartTitle> Titles { get { return titles; } }
        public IList<ChartContent> Charts { get { return chartControls; } }
        public int YScaleWidth
        {
            get { return chartControls[0].YScaleWidth; }
            set
            {
                foreach (ChartContent chartPanel in chartControls)
                    chartPanel.YScaleWidth = value;
            }
        }

        private int titleHeight = 18;
        public int TitleHeight
        {
            get { return titleHeight; }
            set { titleHeight = value; LayoutComponents(); }
        }

        float TotalTitlesHeight { get { return (WindowCount * titleHeight); } }
        float TotalPanelHeight { get { return (Height - TotalTitlesHeight); } }

        TitleResizeEventHandler titleResizeEventHandler;
        TitleMoveDownEventHandler titleMoveDownEventHandler;
        TitleMoveUpEventHandler titleMoveUpEventHandler;
        TitleSplitEventHandler titleSplitEventHandler;
        TitleCloseEventHandler titleCloseEventHandler;

        public MultiChartPanel()
        {
            InitializeComponent();

            titleResizeEventHandler = new TitleResizeEventHandler(title_TitleResize);
            titleMoveDownEventHandler = new TitleMoveDownEventHandler(title_TitleMoveDown);
            titleMoveUpEventHandler = new TitleMoveUpEventHandler(title_TitleMoveUp);
            titleSplitEventHandler = new TitleSplitEventHandler(title_TitleSplit);
            titleCloseEventHandler = new TitleCloseEventHandler(title_TitleClose);

            titles.Add(new ChartTitle());
            chartControls.Add(new ChartContent());
            splitPecents.Add(totalPanelsPercent);

            PlaceComponents();
        }

        private void LayoutComponents()
        {
            Margin = new Padding(0);
            for (int i = 0; i < WindowCount; ++i)
            {
                RowStyles[2 * i].Height = titleHeight;
                RowStyles[2 * i + 1].Height = CalculatePanelHeight(i);
                chartControls[i].Invalidate();
            }
            Refresh();
        }

        private float CalculatePanelHeight(int panelIndex)
        {
            float panelHeight;
            if (panelIndex < WindowCount - 1)
            {
                panelHeight = TotalPanelHeight * splitPecents[panelIndex] / totalPanelsPercent;
            }
            else
            {
                float usedHeight = TotalTitlesHeight;
                for (int i = 0; i < WindowCount - 1; ++i)
                    usedHeight += CalculatePanelHeight(i);
                panelHeight = Height - usedHeight - 1;
            }
            if (panelHeight < minPanelHeight)
                panelHeight = minPanelHeight;
            return panelHeight;
        }

        private void PlaceComponents()
        {
            Controls.Clear();
            RowCount = 2 * WindowCount;
            RowStyles.Clear();
            for (int i = 0; i < WindowCount; ++i)
            {
                ChartTitle title = titles[i];

                Controls.Add(title, 0, 2 * i);
                title.Dock = DockStyle.Fill;
                title.Margin = new Padding(0);
                title.CloseEnabled = true;

                title.TitleResize -= titleResizeEventHandler;
                title.TitleMoveDown -= titleMoveDownEventHandler;
                title.TitleMoveUp -= titleMoveUpEventHandler;
                title.TitleSplit -= titleSplitEventHandler;
                title.TitleClose -= titleCloseEventHandler;

                if (i > 0) title.TitleResize += titleResizeEventHandler;
                title.TitleMoveDown += titleMoveDownEventHandler;
                title.TitleMoveUp += titleMoveUpEventHandler;
                title.TitleSplit += titleSplitEventHandler;
                title.TitleClose += titleCloseEventHandler;

                RowStyles.Add(new RowStyle(SizeType.Absolute, titleHeight));

                Controls.Add(chartControls[i], 0, 2 * i + 1);
                chartControls[i].Dock = DockStyle.Fill;
                chartControls[i].Margin = new Padding(0);
                RowStyles.Add(new RowStyle(SizeType.Absolute, CalculatePanelHeight(i)));
            }

            if (WindowCount == 1)
                titles[0].CloseEnabled = false;
        }

        public void Split(int row)
        {
            float splitPercent = splitPecents[row] / 2;
            float splitHeight = splitPercent * TotalPanelHeight / totalPanelsPercent;
            if (splitHeight >= minPanelHeight)
            {
                ChartTitle title = new ChartTitle();
                title.Text = ("Title #" + WindowCount);
                titles.Insert(row + 1, title);

                ChartContent chartPanel = new ChartContent();
                chartPanel.YScaleWidth = YScaleWidth;
                chartControls.Insert(row + 1, chartPanel);

                splitPecents[row] = splitPercent;
                splitPecents.Insert(row + 1, splitPercent);

                PlaceComponents();
            }
        }

        protected override void OnSizeChanged(EventArgs e)
        {
            base.OnSizeChanged(e);
            LayoutComponents();
        }

        void title_TitleResize(ChartTitle title, int distance)
        {
            int titleIndex = titles.IndexOf(title);
            float ammount = (Math.Abs(distance) * totalPanelsPercent) / TotalPanelHeight;
            float abovePercent = 0;
            float belowPercent = 0;

            if (distance > 0)
            {
                abovePercent = splitPecents[titleIndex - 1] + ammount;
                belowPercent = splitPecents[titleIndex] - ammount;
            }
            else if (distance < 0)
            {
                abovePercent = splitPecents[titleIndex - 1] - ammount;
                belowPercent = splitPecents[titleIndex] + ammount;
            }
            else
            {
                return;
            }

            float aboveHeight = abovePercent * TotalPanelHeight / totalPanelsPercent;
            if (aboveHeight >= minPanelHeight)
            {
                float belowHeight = belowPercent * TotalPanelHeight / totalPanelsPercent;
                if (belowPercent >= minPanelHeight)
                {
                    splitPecents[titleIndex - 1] = abovePercent;
                    splitPecents[titleIndex] = belowPercent;
                    LayoutComponents();
                }
            }
        }

        void title_TitleMoveDown(ChartTitle title)
        {
            int titleIndex = titles.IndexOf(title);
            if (titleIndex < WindowCount - 1)
            {
                Swap<ChartTitle>(titles, titleIndex, titleIndex + 1);
                Swap<ChartContent>(chartControls, titleIndex, titleIndex + 1);
                Swap<float>(splitPecents, titleIndex, titleIndex + 1);
                PlaceComponents();
            }
        }

        void title_TitleMoveUp(ChartTitle title)
        {
            int titleIndex = titles.IndexOf(title);
            if (titleIndex > 0)
            {
                Swap<ChartTitle>(titles, titleIndex, titleIndex - 1);
                Swap<ChartContent>(chartControls, titleIndex, titleIndex - 1);
                Swap<float>(splitPecents, titleIndex, titleIndex - 1);
                PlaceComponents();
            }
        }

        void title_TitleSplit(ChartTitle title)
        {
            int titleIndex = titles.IndexOf(title);
            Split(titleIndex);
        }

        void title_TitleClose(ChartTitle title)
        {
            int titleIndex = titles.IndexOf(title);
            titles.RemoveAt(titleIndex);
            chartControls.RemoveAt(titleIndex);
            splitPecents.RemoveAt(titleIndex);
            NormalizeSplits();
            PlaceComponents();
        }

        private void NormalizeSplits()
        {
            float sum = 0;
            foreach (float sp in splitPecents)
                sum += sp;

            for (int i = 0; i < splitPecents.Count; ++i)
                splitPecents[i] = splitPecents[i] * totalPanelsPercent / sum;
        }
    }
}
