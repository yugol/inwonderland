using System;
using System.Windows.Forms;
using TradingCommon;

namespace TradingVisualizer.Ui
{
    public partial class VisualizerForm
    {
        private void titleExplorer_TitleSelected(object sender, Title title)
        {
            Text = title.FullSymbol;
            MultiChartFrame chartFrame = new MultiChartFrame();
            chartFrame.Title = title;
            chartFrame.Show(dockPanel);
        }
    }
}
