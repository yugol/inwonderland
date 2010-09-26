using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace TradingDataCenter.Ui
{
    public partial class TimeIntervalReader : Form
    {
        public MonthCalendar From { get { return from; } }
        public MonthCalendar To { get { return to; } }

        public TimeIntervalReader()
        {
            InitializeComponent();
            CenterToParent();
        }

        private void ok_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            Close();
        }
    }
}
