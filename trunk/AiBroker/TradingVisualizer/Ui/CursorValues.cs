using System;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;

namespace TradingVisualizer.Ui
{
    public partial class CursorValues : DockContent
    {
        public event VisibleChanged2EventHandler VisibleChanged2;

        public CursorValues()
        {
            InitializeComponent();
        }

        private void CursorValues_VisibleChanged(object sender, EventArgs e)
        {
            if (VisibleChanged2 != null)
                VisibleChanged2(this, Visible);
        }
    }
}
