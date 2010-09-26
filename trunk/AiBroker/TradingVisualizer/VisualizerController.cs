using System;
using TradingCommon;
using TradingVisualizer.Ui;

namespace TradingVisualizer
{
    class VisualizerController
    {
        private VisualizerController() { }
        private static VisualizerController instance = new VisualizerController();
        public static VisualizerController Instance { get { return instance; } }

        VisualizerForm mainForm;

        internal static void UseTitle(Title title)
        {
        }

        internal void Init(VisualizerForm mainForm)
        {
            this.mainForm = mainForm;
        }
    }
}
