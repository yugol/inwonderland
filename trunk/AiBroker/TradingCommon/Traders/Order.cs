using System;
using System.Collections.Generic;
using System.Text;
using TradingCommon;
using System.Xml;
using TradingCommon.Util;

namespace TradingCommon.Traders
{
    public enum OrderTimeLimit { DAY, DESCHIS };

    public class Order
    {
        int id = -1;
        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        string symbol;
        public string Symbol { get { return symbol; } }

        int volume;
        public int Volume { get { return volume; } }

        double placePrice;
        public double PlacePrice { get { return placePrice; } }

        Operation operation;
        public Operation Operation { get { return operation; } }

        private OrderTimeLimit timeLimit;
        public OrderTimeLimit TimeLimit { get { return timeLimit; } }

        double executionPrice = -1;
        public double ExecutionPrice { get { return executionPrice; } }

        DateTime creationTime = DateTime.Now;

        DateTime placeTime = DateTime.MinValue;
        public DateTime PlaceTime { get { return placeTime; } }
        
        DateTime executionTime = DateTime.MinValue;
        public DateTime ExecutionDateTime { get { return executionTime; } }

        public bool IsExecuted { get { return (executionTime.CompareTo(DateTime.MinValue) != 0); } }
        public bool IsMarket { get { return double.IsNaN(placePrice); } }

        public Order(string symbol, Operation operation, int volume, double price, OrderTimeLimit timeLimit)
        {
            this.symbol = symbol;
            this.volume = volume;
            this.placePrice = price;
            this.operation = operation;
            this.timeLimit = timeLimit;
        }

        public void SetPlaceData(int id, DateTime placeTime)
        {
            this.id = id;
            this.placeTime = placeTime;
        }

        public void SetExecutionData(double executionPrice, DateTime executionTime)
        {
            this.executionPrice = executionPrice;
            this.executionTime = executionTime;
        }

        #region Marshall / Unmarshall

        internal void Marshall(XmlNode parent, XmlDocument document, int index)
        {
            XmlNode node = document.CreateNode(XmlNodeType.Element, "order", "");
            parent.AppendChild(node);
            parent = node;

            if (index > 0)
            {
                XmlNode attr = document.CreateNode(XmlNodeType.Attribute, "index", "");
                attr.Value = index.ToString();
                node.Attributes.Append((XmlAttribute)attr);
            }

            node = document.CreateNode(XmlNodeType.Element, "id", "");
            node.InnerText = id.ToString();
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "symbol", "");
            node.InnerText = symbol;
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "placePrice", "");
            node.InnerText = double.IsNaN(placePrice) ? Configuration.MARKET_PRICE_TAG : placePrice.ToString("0.0000");
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "volume", "");
            node.InnerText = volume.ToString();
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "operation", "");
            node.InnerText = operation.ToString();
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "timeLimit", "");
            node.InnerText = timeLimit.ToString();
            parent.AppendChild(node);

            node = document.CreateNode(XmlNodeType.Element, "created", "");
            node.InnerText = DTUtil.ToDateTimeString(creationTime);
            parent.AppendChild(node);

            if (placeTime.CompareTo(DateTime.MinValue) != 0)
            {

                node = document.CreateNode(XmlNodeType.Element, "placed", "");
                node.InnerText = DTUtil.ToDateTimeString(placeTime);
                parent.AppendChild(node);
            }

            if (executionTime.CompareTo(DateTime.MinValue) != 0)
            {
                node = document.CreateNode(XmlNodeType.Element, "executed", "");
                node.InnerText = DTUtil.ToDateTimeString(executionTime);
                parent.AppendChild(node);

                node = document.CreateNode(XmlNodeType.Element, "ePrice", "");
                node.InnerText = executionPrice.ToString("0.0000");
                parent.AppendChild(node);
            }
        }

        internal static Order Unmarshall(XmlNode xmlNode)
        {
            Order order = new Order(null, Operation.buy, 0, double.NaN, OrderTimeLimit.DAY);
            foreach (XmlNode node in xmlNode.ChildNodes)
            {
                if (node.Name.Equals("id"))
                    order.id = int.Parse(node.InnerText);
                else if (node.Name.Equals("symbol"))
                    order.symbol = node.InnerText;
                else if (node.Name.Equals("placePrice"))
                {
                    try { order.placePrice = double.Parse(node.InnerText); }
                    catch { order.placePrice = double.NaN; }
                }
                else if (node.Name.Equals("volume"))
                    order.volume = int.Parse(node.InnerText);
                else if (node.Name.Equals("operation"))
                    order.operation = Operation.Parse(node.InnerText);
                else if (node.Name.Equals("timeLimit"))
                    order.timeLimit = (OrderTimeLimit)Enum.Parse(typeof(OrderTimeLimit), node.InnerText, true);
                else if (node.Name.Equals("created"))
                    order.creationTime = DTUtil.ParseDateTime(node.InnerText);
                else if (node.Name.Equals("placed"))
                    order.placeTime = DTUtil.ParseDateTime(node.InnerText);
                else if (node.Name.Equals("executed"))
                    order.executionTime = DTUtil.ParseDateTime(node.InnerText);
                else if (node.Name.Equals("ePrice"))
                    order.executionPrice = double.Parse(node.InnerText);
            }
            return order;
        }

        #endregion

        #region Util

        internal static void SumOrders(Operation op1, int vol1, Operation op2, int vol2, out Operation op, out int vol)
        {
            int v1 = 0, v2 = 0;

            switch (op1)
            {
                case Operation.SELL:
                    v1 = -vol1;
                    break;
                case Operation.BUY:
                    v1 = vol1;
                    break;
            }

            switch (op2)
            {
                case Operation.SELL:
                    v2 = -vol2;
                    break;
                case Operation.BUY:
                    v2 = vol2;
                    break;
            }

            int v = v1 + v2;

            if (v > 0)
            {
                op = Operation.buy;
                vol = v;
            }
            else if (v < 0)
            {
                op = Operation.sell;
                vol = -v;
            }
            else
            {
                op = Operation.none;
                vol = 0;
            }
        }

        internal static Order CreateIndividualPlacedOrder(Order order)
        {
            Order individualOrder = new Order(order.Symbol, order.Operation, 1, order.PlacePrice, order.TimeLimit);
            individualOrder.creationTime = order.creationTime;
            individualOrder.SetPlaceData(order.Id, order.PlaceTime);
            return individualOrder;
        }

        #endregion

    }
}
