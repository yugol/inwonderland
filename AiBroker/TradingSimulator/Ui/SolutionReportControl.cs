using System;
using System.Windows.Forms;

using TradingCommon.Traders.Optimization;

namespace TradingSimulator.Ui
{
    public partial class SolutionReportControl : UserControl
    {
        private Solution solution;
        public Solution Solution
        {
            set
            {
                solution = value;
                solution.Simulation.Status.FillAllTradesData(solution.Simulation.Series);
                solutionTabs.Enabled = (value != null);
                solutionSummary.Text = SolutionUtil.GetTextReport(solution);
                SolutionUtil.DrawEquityCurve(solution, accountEquity);
                accountEquity.Visible = true;
                statisticsSectionSelector_SelectedIndexChanged(null, null);
                tradesList.SetSolution(solution);
                orderBrowser.DocumentText = solution.Simulation.Status.GetHTMLReport();
            }
        }

        public SolutionReportControl()
        {
            InitializeComponent();
            solutionTabs.Enabled = false;
        }

        private void statisticsSectionSelector_SelectedIndexChanged(object sender, EventArgs e)
        {
            statisticsSectionControl.Visible = false;
            switch (statisticsSectionSelector.SelectedIndex)
            {
                case 0:
                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.AllTradeProfitsStatistics);
                    statisticsSectionControl.Visible = true;
                    break;
                    
                case 1:
                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.WinningTradeProfitsStatistics);
                    statisticsSectionControl.Visible = true;
                    break;
                    
                case 2:
                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.LosingTradeProfitsStatistics);
                    statisticsSectionControl.Visible = true;
                    break;
                    
//                case 3:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.ConsecutiveWinningsLossesStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;
//                case 4:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.RunUpStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;
//                case 5:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.DrawDownStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;
//                case 6:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.BarCountStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;
//                case 7:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.WinningTradesBarCountStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;
//                case 8:
//                    statisticsSectionControl.SetStatistics(solution.Simulation.Status.LosingTradesBarCountStatistics);
//                    statisticsSectionControl.Visible = true;
//                    break;

//Consecutive winnings/losses trades statistics
//Run-up statistics
//Draw-down statistics
//Bar count statistics
//Winnins bar count statistics
//Losses bar count statistics

            }
        }
    }
}
