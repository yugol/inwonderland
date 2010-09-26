using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;

namespace TradingDataCenter.Ui
{
    public delegate void AbortEventHandler();

    public partial class ProgressReporter : Form
    {
        public event AbortEventHandler Abort;
        delegate void void_string(string value);

        public string Message
        {
            set
            {
                if (InvokeRequired)
                {
                    void_string d = new void_string(_SetMessage);
                    Invoke(d, new object[] { value });
                }
                else
                {
                    _SetMessage(value);
                }
            }
        }

        private void _SetMessage(string value)
        {
            message.Text = value;
        }

        public int WorkAmmount { set { progress.Maximum = value; } }
        public int Progress
        {
            get { return progress.Value; }
            set { progress.Value = value; }
        }

        public ProgressReporter()
        {
            InitializeComponent();
            CenterToScreen();
        }

        private void abort_Click(object sender, EventArgs e)
        {
            if (Abort != null) Abort();
        }
    }
}
