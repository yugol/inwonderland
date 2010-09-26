using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Traders;

namespace TradingAgent.Ui
{
    public partial class AddOrderControl : UserControl
    {
        public event EventHandler OrderPlaced;

        delegate void void_bool(bool b);

        new internal bool Enabled
        {
            get { return base.Enabled; }
            set
            {
                base.Enabled = value;
                volume.Enabled = value;
                price.Enabled = value;
                buy.Enabled = value;
                sell.Enabled = value;
            }
        }

        public string Symbol
        {
            get { return symbol.Text; }
            set { symbol.Text = value; }
        }


        public AddOrderControl()
        {
            InitializeComponent();
            buy.BackColor = AgentForm.BUY_COLOR;
            sell.BackColor = AgentForm.SELL_COLOR;
        }

        private void SetDefaultValues()
        {
            volume.SelectedIndex = 0;
            timeLimit.SelectedIndex = 0;
            price.Text = Configuration.MARKET_PRICE_TAG;
        }

        double GetPrice()
        {
            string priceInput = this.price.Text.ToUpper();
            try
            {
                if (priceInput.Equals(Configuration.MARKET_PRICE_TAG)) return double.NaN;
                if (priceInput.IndexOf(",") >= 0) throw new Exception();
                double price = double.Parse(priceInput);
                if (price <= 0) throw new Exception();
                return price;
            }
            catch (Exception)
            {
                MessageBox.Show("\"" + priceInput + "\"" + " is an invalid price", "Add new order");
                return -1;
            }
        }

        private void AddOrderControl_Load(object sender, EventArgs e)
        {
            for (int i = 1; i <= 10; ++i)
                volume.Items.Add(i.ToString());

            foreach (string name in Enum.GetNames(typeof(OrderTimeLimit)))
                timeLimit.Items.Add(name);

            SetDefaultValues();
        }

        private void buy_Click(object sender, EventArgs e)
        {
            int tmpVolume = int.Parse(volume.SelectedItem.ToString());
            double price = GetPrice();
            OrderTimeLimit tmpTimeLimit = (OrderTimeLimit)Enum.Parse(typeof(OrderTimeLimit), timeLimit.SelectedItem.ToString());
            Order order = new Order(symbol.Text, Operation.buy, tmpVolume, price, tmpTimeLimit);
            if (OrderPlaced != null)
                OrderPlaced(this, new OrderCreatedEventArgs(order));
            SetDefaultValues();
        }

        private void sell_Click(object sender, EventArgs e)
        {
            int tmpVolume = int.Parse(volume.SelectedItem.ToString());
            double price = GetPrice();
            OrderTimeLimit tmpTimeLimit = (OrderTimeLimit)Enum.Parse(typeof(OrderTimeLimit), timeLimit.SelectedItem.ToString());
            Order order = new Order(symbol.Text, Operation.sell, tmpVolume, price, tmpTimeLimit);
            if (OrderPlaced != null)
                OrderPlaced(this, new OrderCreatedEventArgs(order));
            SetDefaultValues();
        }
    }
}
