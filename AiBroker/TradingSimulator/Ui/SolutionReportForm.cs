using System;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders.Optimization;
namespace TradingSimulator.Ui
{
    public partial class SolutionReportForm : Form
    {
        public Solution Solution
        {
            set
            {
                Text = (value == null) ? ("Null solution") : (value.Title.FullSymbol);
                solutionReport.Solution = value;
            }
        }
        
        public SolutionReportForm()
        {
            InitializeComponent();
        }
    }
}
