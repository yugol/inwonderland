using System;
using System.Drawing;
using System.Windows.Forms;

using TradingCommon;
using TradingCommon.Traders.Optimization;

namespace TradingSimulator.Ui
{
    public partial class MainForm : Form
    {
        TradingSystemOptimizationHandler optimizationHandler;

        public MainForm()
        {
            InitializeComponent();
            optimizationHandler = new TradingSystemOptimizationHandler(StartOptimization);
            SimulatorData.Instance.SimulationsChanged += new SimulationsChangedHandler(UpdateSolutions);
            tsTuner.TradingSystemTest += new TradingSystemTestHandler(TestTradingSystem);

            EnableOptimizationEvents();
            CenterToScreen();

            // display period from SimulatorData
            periodCombo.FindString(SimulatorData.Instance.Period.ToString());
            if (periodCombo.SelectedIndex < 0) {
                periodCombo.SelectedIndex = periodCombo.Items.Count - 1;
            }
        }

        void MainForm_Load(object sender, EventArgs e)
        {
            maxSolutionCount.Value = SimulatorData.Instance.Solutions.MaxSize;
            splitContainer.Panel2MinSize = 300;
        }


        void UpdateSolutions()
        {
            solutionList.BeginUpdate();

            int i = 0;
            while (i < solutionList.Items.Count) {
                if (!solutionList.Items[i].Checked) {
                    solutionList.Items.RemoveAt(i);
                } else {
                    ++i;
                }
            }

            foreach (ISolution solution in SimulatorData.Instance.Solutions.Items) {
                ListViewItem item = new ListViewItem(solution.Fitness.ToString("0.00"));
                item.Tag = solution;
                if (solution.Fitness < 0) {
                    item.ForeColor = Color.Red;
                }
                solutionList.Items.Add(item);
            }

            solutionList.EndUpdate();

            viewTabs.SelectedTab = simulationsPage;
        }

        void SolutionListSelectedIndexChanged(object sender, EventArgs e)
        {
            if (solutionList.SelectedItems.Count > 0) {
                Solution solution = (Solution)(solutionList.SelectedItems[0].Tag);
                solutionReport.Solution = solution;
                tsTuner.TSys = solution.TSys;
            }
        }

        void MaxSolutionCountValueChanged(object sender, EventArgs e)
        {
            SimulatorData.Instance.SetMaxSolutionsCount((int) maxSolutionCount.Value);
        }

        void ClearSolutionListClick(object sender, EventArgs e)
        {
            SimulatorData.Instance.ClearSolutions();
        }

        void ExitToolStripMenuItemClick(object sender, EventArgs e)
        {
            Application.Exit();
        }

        internal static bool CheckTestData()
        {
            if (SimulatorData.Instance.TestData == null) {
                ShowMessage("No test data set provided.\nPlease select a TEST DATA SET before continuing with optimization.");
                return false;
            }
            return true;
        }

        internal static bool CheckTrainData()
        {
            if (SimulatorData.Instance.TrainData == null) {
                ShowMessage("No train data set provided.\nPlease select a TRAIN DATA SET before continuing with optimization.");
                return false;
            }
            return true;
        }

        internal static void ShowMessage(string message)
        {
            MessageBox.Show(message, "Simulation", MessageBoxButtons.OK, MessageBoxIcon.Stop);
        }

        void TrendRecognitionToolStripMenuItemClick(object sender, EventArgs e)
        {
            TitleSelectionForm selector = new TitleSelectionForm();
            if (selector.ShowDialog() == DialogResult.OK) {
                TrendToolForm tool = new TrendToolForm();
                tool.Title = selector.Title;
                tool.ShowDialog();
            }
        }
    }
}
