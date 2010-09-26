using System;
using System.Windows.Forms;
using TradingCommon.Indicators;

namespace TradingVisualizer.Ui
{
    public partial class DeltaSelectionForm : Form
    {
        Delta.Term deltaTerm = Delta.Term.NONE;
        public Delta.Term DeltaTerm
        {
            get { return deltaTerm; }
            set
            {
                deltaGridEnabled.Checked = (value != Delta.Term.NONE);
                deltaTerm = value;
                UpdateUi();
            }
        }

        private void UpdateUi()
        {
            std.Checked = (deltaTerm == Delta.Term.STD);
            itd.Checked = (deltaTerm == Delta.Term.ITD);
            mtd.Checked = (deltaTerm == Delta.Term.MTD);
            ltd.Checked = (deltaTerm == Delta.Term.LTD);
            sltd.Checked = (deltaTerm == Delta.Term.SLTD);

            std.Enabled = deltaGridEnabled.Checked;
            itd.Enabled = deltaGridEnabled.Checked;
            mtd.Enabled = deltaGridEnabled.Checked;
            ltd.Enabled = deltaGridEnabled.Checked;
            sltd.Enabled = deltaGridEnabled.Checked;
        }

        public DeltaSelectionForm()
        {
            InitializeComponent();
        }

        private void deltaGridEnabled_CheckedChanged(object sender, EventArgs e)
        {
            if (!deltaGridEnabled.Checked)
                deltaTerm = Delta.Term.NONE;
            UpdateUi();
        }

        private void std_CheckedChanged(object sender, EventArgs e)
        {
            if (std.Checked)
                deltaTerm = Delta.Term.STD;
        }

        private void itd_CheckedChanged(object sender, EventArgs e)
        {
            if (itd.Checked)
                deltaTerm = Delta.Term.ITD;
        }

        private void mtd_CheckedChanged(object sender, EventArgs e)
        {
            if (mtd.Checked)
                deltaTerm = Delta.Term.MTD;
        }

        private void ltd_CheckedChanged(object sender, EventArgs e)
        {
            if (ltd.Checked)
                deltaTerm = Delta.Term.LTD;
        }

        private void sltd_CheckedChanged(object sender, EventArgs e)
        {
            if (sltd.Checked)
                deltaTerm = Delta.Term.SLTD;
        }

        private void close_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
