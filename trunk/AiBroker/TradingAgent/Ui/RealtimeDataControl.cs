using System;
using System.Drawing;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingAgent.Ui
{
    public partial class RealtimeDataControl : UserControl
    {
        internal BidAsk BidAsk 
        { 
            set 
            {
                if (value != null)
                {
                    SetLabelText(ask, value.ask);
                    SetLabelText(bid, value.bid);
                }
            } 
        }

        internal TradingStatus TradingStatus
        {
            set
            {
                openPositions.Text = value.OpenPositions.ToString();
                lastEnterOperation.Text = value.EnterOperation.ToString();
                if (value.EnterOperation.Equals(Operation.buy))
                    lastEnterOperation.BackColor = AgentForm.BUY_COLOR;
                else if (value.EnterOperation.Equals(Operation.sell))
                    lastEnterOperation.BackColor = AgentForm.SELL_COLOR;
            }
        }

        private void SetLabelText(Label label, double value)
        {
            double oldValue = double.Parse(label.Text);
            Color color = label.ForeColor;
            if (oldValue < value) color = Color.DarkGreen;
            else if (oldValue > value) color = Color.Red;
            label.ForeColor = color;
            label.Text = value.ToString("0.0000");
        }

        internal Tick Tick
        {
            set
            {
                SetLabelText(price, value.price);
                volume.Text = "(" + value.volume + ")";
                time.Text = DTUtil.ToTimeString(value.dateTime);
            }
        }

        public RealtimeDataControl()
        {
            InitializeComponent();
            ask.Text = "---";
            bid.Text = "---";
        }
    }
}
