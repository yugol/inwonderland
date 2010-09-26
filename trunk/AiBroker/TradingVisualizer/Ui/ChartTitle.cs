using System;
using System.Windows.Forms;

namespace TradingVisualizer.Ui
{
    public delegate void TitleResizeEventHandler(ChartTitle title, int distance);
    public delegate void TitleMoveDownEventHandler(ChartTitle title);
    public delegate void TitleMoveUpEventHandler(ChartTitle title);
    public delegate void TitleSplitEventHandler(ChartTitle title);
    public delegate void TitleCloseEventHandler(ChartTitle title);

    public partial class ChartTitle : Panel
    {
        public event TitleResizeEventHandler TitleResize;
        public event TitleMoveDownEventHandler TitleMoveDown;
        public event TitleMoveUpEventHandler TitleMoveUp;
        public event TitleSplitEventHandler TitleSplit;
        public event TitleCloseEventHandler TitleClose;

        int yResizingBase = -1;

        new public string Text
        {
            get { return titleLabel.Text; }
            set { titleLabel.Text = value; }
        }

        public bool CloseEnabled
        {
            get { return close.Enabled; }
            set { close.Enabled = value; }
        }

        public ChartTitle()
        {
            InitializeComponent();
            DoubleBuffered = true;
        }

        private void Title_MouseDown(object sender, MouseEventArgs e)
        {
            yResizingBase = e.Y;
            Cursor = Cursors.HSplit;
        }

        private void Title_MouseMove(object sender, MouseEventArgs e)
        {
            if (yResizingBase >= 0)
            {
                int distance = e.Y - yResizingBase;
                if (TitleResize != null) TitleResize(this, distance);
            }
        }

        private void Title_MouseUp(object sender, MouseEventArgs e)
        {
            yResizingBase = -1;
            Cursor = Cursors.Default;
        }

        private void moveDown_Click(object sender, EventArgs e)
        {
            if (TitleMoveDown != null)
                TitleMoveDown(this);
        }

        private void moveUp_Click(object sender, EventArgs e)
        {
            if (TitleMoveUp != null)
                TitleMoveUp(this);
        }

        private void split_Click(object sender, EventArgs e)
        {
            if (TitleSplit != null)
                TitleSplit(this);
        }

        private void close_Click(object sender, EventArgs e)
        {
            if (TitleClose != null)
                TitleClose(this);
        }
    }
}
