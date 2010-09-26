using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingVisualizer.Ui
{
    public partial class VisualizerForm : Form
    {
        TitleExplorer titleExplorer = new TitleExplorer();
        CursorValues cursorValues = new CursorValues();

        public override string Text { set { base.Text = ("Trading Visualizer - " + value); } }

        internal DateTime CursorDateTime
        {
            set
            {
                if (value.Equals(DateTime.MinValue))
                {
                    toolStripDate.Text = "";
                    toolStripTime.Text = "";
                }
                else
                {
                    toolStripDate.Text = DTUtil.ToDateString(value);
                    string timeString = DTUtil.ToTimeString(value);
                    if (timeString.Equals("00:00:00"))
                        toolStripTime.Text = "";
                    else
                        toolStripTime.Text = timeString;
                }
            }
        }

        internal double CursorValue
        {
            set
            {
                if (double.IsNaN(value))
                    toolStripValue.Text = "";
                else
                    toolStripValue.Text = value.ToString("0.0000");
            }
        }

        public VisualizerForm()
        {
            InitializeComponent();
            CenterToScreen();

            cursorValues.Show(dockPanel);
            titleExplorer.Show(dockPanel);

            titleExplorer.TitleSelected += new TitleSelectedEventHandler(titleExplorer_TitleSelected);
            cursorValues.VisibleChanged2 += new VisibleChanged2EventHandler(cursorValues_VisibleChanged);
            titleExplorer.VisibleChanged2 += new VisibleChanged2EventHandler(titleExplorer_VisibleChanged);

            CursorDateTime = DateTime.MinValue;
            CursorValue = double.NaN;
        }

        private void ExitApplication()
        {
            foreach (Form form in MdiChildren)
                form.Close();
            Application.Exit();
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ExitApplication();
        }

        private void VisualizerForm_Shown(object sender, EventArgs e)
        {
            VisualizerController.Instance.Init(this);
            cursorValuesToolStripMenuItem.Checked = false;
            cursorValues.Hide();
        }

        private void cursorValuesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (cursorValuesToolStripMenuItem.Checked)
                cursorValues.Show(dockPanel);
            else
                cursorValues.Hide();
        }

        private void cursorValues_VisibleChanged(object sender, bool val)
        {
            cursorValuesToolStripMenuItem.Checked = val;
        }

        private void symbolsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (symbolsToolStripMenuItem.Checked)
                titleExplorer.Show(dockPanel);
            else
                titleExplorer.Hide();
        }

        private void titleExplorer_VisibleChanged(object sender, bool val)
        {
            symbolsToolStripMenuItem.Checked = val;
        }

        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            base.OnClosing(e);
            ExitApplication();
        }
    }
}
