using System;
using System.Collections;
using System.Drawing;
using System.Windows.Forms;

using TradingCommon.Traders;
using TradingCommon.Traders.Optimization;
using TradingCommon.Util;

namespace TradingSimulator.Ui
{
    public partial class TradesListControl : UserControl
    {
        static Color WIN_COLOR = Color.FromArgb(210, 255, 210);
        static Color LOSE_COLOR = Color.FromArgb(255, 210, 210);

        Font defaultFont;

        public TradesListControl()
        {
            InitializeComponent();

            defaultFont = tradesList.Font;
            Font doubleFont = new Font(defaultFont.FontFamily, defaultFont.Size * 2.1F, defaultFont.Style);
            tradesList.Font = doubleFont;
        }

        internal void SetSolution(Solution solution)
        {
            tradesList.Items.Clear();
            tradesList.BeginUpdate();
            for (int i = 0; i < solution.Simulation.Status.Trades.Count; i++)
            {
                Trade trade = solution.Simulation.Status.Trades[i];
                if (trade.IsOpen) continue;

                Color tradeColor = Color.White;
                switch (Math.Sign(trade.Profit))
                {
                    case -1:
                        tradeColor = LOSE_COLOR;
                        break;
                    case 1:
                        tradeColor = WIN_COLOR;
                        break;
                }

                ListViewItem item = new ListViewItem((i + 1).ToString());
                item.SubItems.Add(trade.Enter.operation.ToString());
                item.SubItems.Add(DTUtil.ToDateTimeString(trade.Enter.dateTime) + "\n" + DTUtil.ToDateTimeString(trade.Exit.dateTime));
                item.SubItems.Add(trade.EnterBarIndex.ToString() + "\n" + trade.ExitBarIndex.ToString());
                item.SubItems.Add(trade.Enter.price.ToString("0.0000") + "\n" + trade.Exit.price.ToString("0.0000"));
                item.SubItems.Add(trade.Profit.ToString("0.0000"));
                item.SubItems.Add(trade.RunUp.ToString("0.0000"));
                item.SubItems.Add(trade.DrawDown.ToString("0.0000"));
                if (double.IsNaN(trade.Efficiency))
                    item.SubItems.Add("");
                else
                    item.SubItems.Add(trade.Efficiency.ToString("##0.00") + "%");
                item.BackColor = tradeColor;
                tradesList.Items.Add(item);
            }
            tradesList.EndUpdate();
        }

        private void tradesList_ColumnClick(object sender, ColumnClickEventArgs e)
        {
            tradesList.ListViewItemSorter = new ListViewItemComparer(e.Column);
        }

        private void tradesList_DrawColumnHeader(object sender, DrawListViewColumnHeaderEventArgs e)
        {
            e.DrawBackground();
            e.Graphics.DrawString(e.Header.Text, defaultFont, Brushes.Black, e.Bounds.Left + 2, 2);
        }

        private void tradesList_DrawItem(object sender, DrawListViewItemEventArgs e)
        {
            e.DrawBackground();
            int h = e.Bounds.Y + e.Bounds.Height - 1;
            e.Graphics.DrawLine(Pens.Gray, 0, h, e.Bounds.Width - 1, h);
        }

        private void tradesList_DrawSubItem(object sender, DrawListViewSubItemEventArgs e)
        {
            using (StringFormat sf = new StringFormat())
            {
                // Store the column text alignment, letting it default
                // to Left if it has not been set to Center or Right.

                if (e.ColumnIndex == 0)
                {
                    sf.Alignment = StringAlignment.Far;
                }
                else
                {
                    switch (e.Header.TextAlign)
                    {
                        case HorizontalAlignment.Center:
                            sf.Alignment = StringAlignment.Center;
                            break;
                        case HorizontalAlignment.Right:
                            sf.Alignment = StringAlignment.Far;
                            break;
                    }
                }

                Rectangle bounds = new Rectangle(e.Bounds.X, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height);
                e.Graphics.DrawString(e.SubItem.Text, defaultFont, Brushes.Black, bounds, sf);
            }
        }

        private void tradesList_MouseMove(object sender, MouseEventArgs e)
        {
            ListViewItem item = tradesList.GetItemAt(e.X, e.Y);
            if (item != null && item.Tag == null)
            {
                tradesList.Invalidate(item.Bounds);
                item.Tag = "tagged";
            }
        }
    }

    class ListViewItemComparer : IComparer
    {
        private int col;
        public ListViewItemComparer()
        {
            col = 0;
        }
        public ListViewItemComparer(int column)
        {
            col = column;
        }
        public int Compare(object x, object y)
        {
            if (col == 1 || col == 2 || col == 8)
            {
                return String.Compare(((ListViewItem)x).SubItems[col].Text, ((ListViewItem)y).SubItems[col].Text);
            }
            else
            {
                string sv1 = ((ListViewItem)x).SubItems[col].Text.Split('\n')[0];
                string sv2 = ((ListViewItem)y).SubItems[col].Text.Split('\n')[0];
                double v1 = double.Parse(sv1);
                double v2 = double.Parse(sv2);
                return v1.CompareTo(v2);
            }
        }
    }

}
