using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ZedGraph;
using org.apache.commons.math.stat.descriptive;
using System.Diagnostics;

namespace TradingSimulator.Ui
{
    public partial class StatisticalReportControl : UserControl
    {
        DescriptiveStatistics stat;

        public StatisticalReportControl()
        {
            InitializeComponent();
        }

        internal void SetStatistics(DescriptiveStatistics stat)
        {
            this.stat = stat;
            report.Text = StatisticsUtil.GetTextReport(stat);
            StatisticsUtil.DrawDistribution(stat, (int)distClusterCount.Value, distGraph);
            distClusterCount.Enabled = true;
        }

        private void distClusterCount_ValueChanged(object sender, EventArgs e)
        {
            StatisticsUtil.DrawDistribution(stat, (int)distClusterCount.Value, distGraph);
        }
    }
}
