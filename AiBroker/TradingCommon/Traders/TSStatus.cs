/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/25/2009
 * Time: 4:21 PM
 * 
 *
 * Copyright (c) 2008, 2009 Iulian GORIAC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Xsl;

using TradingCommon.Util;

using org.apache.commons.math.stat.descriptive;

namespace TradingCommon.Traders
{    
    public class TSStatus
    {
        int openPositions = 0;
        public int OpenPositions
        {
            get { return openPositions; }
        }
        
        Operation enterOperation = Operation.none;
        public Operation EnterOperation
        {
            get { return enterOperation; }
            set { enterOperation = value; }
        }
        
        double enterPrice = double.NaN;
        public double EnterPrice
        {
            get { return enterPrice; }
            set { enterPrice = value; }
        }
        
        DateTime enterTime = DateTime.MinValue;
        public DateTime EnterTime
        {
            get { return enterTime; }
            set { enterTime = value; }
        }
        
        int lastOperationBarIndex = -1;
        public int LastOperationBarIndex
        {
            get { return lastOperationBarIndex; }
            set { lastOperationBarIndex = value; }
        }
        
        IList<Order> orders = new List<Order>();
        public IList<Order> Orders { get { return orders; } }

        List<Trade> trades = new List<Trade>();
        public List<Trade> Trades { get { return trades; } }        
        
        
        #region Orders
        
        public Order GetOrderById(int orderId)
        {
            foreach (Order order in orders) {
                if (order.Id == orderId) {
                    return order;
                }
            }
            return null;
        }

        public void AddPlacedOrder(Order order)
        {
            for (int i = 0; i < order.Volume; ++i)
            {
                Order individualOrder = Order.CreateIndividualPlacedOrder(order);
                orders.Add(individualOrder);
            }
        }

        #endregion

        #region Transactions

        public void AddTransaction(Transaction transaction)
        {
            switch (transaction.operation)
            {
                case Operation.BUY:
                    openPositions += transaction.volume;
                    break;
                case Operation.SELL:
                    openPositions -= transaction.volume;
                    break;
            }

            for (int i = 0; i < transaction.volume; ++i) {
                AddIndividualTransaction(transaction);
            }
        }

        void AddIndividualTransaction(Transaction transaction)
        {
            // mark executed orders
            foreach (Order order in orders) {
                if (!order.IsExecuted) {
                    if (order.Symbol.Equals(transaction.symbol) && order.Operation.Equals(transaction.operation))
                    {
                        order.SetExecutionData(transaction.price, transaction.dateTime);
                        break;
                    }
                }
            }
            
            // fill trades
            for (int i = 0; i < trades.Count; ++i) 
            {
                Trade trade = trades[i];
                if (trade.IsOpen && (trade.Enter.operation != transaction.operation)) 
                {
                    Trade.CloseTrade(trade, transaction);
                    return;
                }
            }
            trades.Add(Trade.OpenTrade(transaction));
        }
        
        public void FillAllTradesData(TimeSeries series)
        {
            foreach (Trade trade in trades) {
                Trade.FillAllTradeData(trade, series);
            }
        }

        #endregion
        
        #region Trade statistics
        
        DescriptiveStatistics profitStatistics;
        DescriptiveStatistics winningStatistics;
        DescriptiveStatistics losingStatistics;
        DescriptiveStatistics consecutiveStatistics;
        DescriptiveStatistics barsStatistics;
        DescriptiveStatistics winningBarsStatistics;
        DescriptiveStatistics losingBarsStatistics;
        DescriptiveStatistics runUpStatistics;
        DescriptiveStatistics drawDownStatistics;

        public int CloseTradesCount { get { return (int)AllTradeProfitsStatistics.getN(); } }
        public int WinningTradesCount { get { return (int)WinningTradeProfitsStatistics.getN(); } }
        public int LosingTradesCount { get { return (int)LosingTradeProfitsStatistics.getN(); } }
        public int EvenTradesCount { get { return CloseTradesCount - WinningTradesCount - LosingTradesCount; } }

        public double ProfitableTradesPercent { get { return (WinningTradesCount * 100d / trades.Count); } }

        public DescriptiveStatistics AllTradeProfitsStatistics { get { CalculateProfitStatistics(); return profitStatistics; } }
        public DescriptiveStatistics WinningTradeProfitsStatistics { get { CalculateWinningStatistics(); return winningStatistics; } }
        public DescriptiveStatistics LosingTradeProfitsStatistics { get { CalculateLosingStatistics(); return losingStatistics; } }
        public DescriptiveStatistics ConsecutiveWinningsLossesStatistics { get { CalculateConsecutiveStatistics(); return consecutiveStatistics; } }
        public DescriptiveStatistics BarCountStatistics { get { CalculateBarsStatistics(); return barsStatistics; } }
        public DescriptiveStatistics WinningTradesBarCountStatistics { get { CalculateWinningBarsStatistics(); return winningBarsStatistics; } }
        public DescriptiveStatistics LosingTradesBarCountStatistics { get { CalculateLosingBarsStatistics(); return losingBarsStatistics; } }
        public DescriptiveStatistics RunUpStatistics { get { CalculateRunUpStatistics(); return runUpStatistics; } }
        public DescriptiveStatistics DrawDownStatistics { get { CalculateDrawDownStatistics(); return drawDownStatistics; } }

        public double AvgWinAvgLossRatio { get { return (WinningTradeProfitsStatistics.getMean() / LosingTradeProfitsStatistics.getMean()); } }

        void CalculateWinningStatistics()
        {
            if (winningStatistics == null)
            {
                winningStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;

                    double profit = trade.Profit;
                    if (profit > 0)
                        winningStatistics.addValue(profit);
                }
            }
        }

        void CalculateLosingStatistics()
        {
            if (losingStatistics == null)
            {
                losingStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;

                    double profit = trade.Profit;
                    if (profit < 0)
                        losingStatistics.addValue(profit);
                }
            }
        }

        void CalculateProfitStatistics()
        {
            if (profitStatistics == null)
            {
                profitStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;

                    profitStatistics.addValue(trade.Profit);
                }
            }
        }

        void CalculateConsecutiveStatistics()
        {
            if (consecutiveStatistics == null)
            {
                consecutiveStatistics = new DescriptiveStatistics();

                int prevProfit = 0;
                int len = 0;

                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;

                    int currProfit = Math.Sign(trade.Profit);
                    if (currProfit != prevProfit)
                    {
                        if (len != 0)
                        {
                            switch (prevProfit)
                            {
                                case 1:
                                    consecutiveStatistics.addValue(len);
                                    break;
                                case -1:
                                    consecutiveStatistics.addValue(-len);
                                    break;
                            }
                        }
                        len = 1;
                        prevProfit = currProfit;
                    }
                    else
                    {
                        ++len;
                    }
                }
                if (len != 0)
                {
                    switch (prevProfit)
                    {
                        case 1:
                            consecutiveStatistics.addValue(len);
                            break;
                        case -1:
                            consecutiveStatistics.addValue(-len);
                            break;
                    }
                }
            }
        }

        void CalculateBarsStatistics()
        {
            if (barsStatistics == null)
            {
                barsStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;
                    barsStatistics.addValue(trade.BarCount);
                }
            }
        }

        void CalculateWinningBarsStatistics()
        {
            if (winningBarsStatistics == null)
            {
                winningBarsStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;
                    if (trade.Profit > 0)
                        winningBarsStatistics.addValue(trade.BarCount);
                }
            }
        }

        void CalculateLosingBarsStatistics()
        {
            if (losingBarsStatistics == null)
            {
                losingBarsStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) continue;
                    if (trade.Profit < 0)
                        losingBarsStatistics.addValue(trade.BarCount);
                }
            }
        }

        void CalculateRunUpStatistics()
        {
            if (runUpStatistics == null)
            {
                runUpStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) 
                        continue;
                    runUpStatistics.addValue(trade.RunUp);
                }
            }
        }

        void CalculateDrawDownStatistics()
        {
            if (drawDownStatistics == null)
            {
                drawDownStatistics = new DescriptiveStatistics();
                foreach (Trade trade in trades)
                {
                    if (trade.IsOpen) 
                        continue;
                    drawDownStatistics.addValue(trade.DrawDown);
                }
            }
        }
        
        #endregion
        
        #region Evaluation
        
        double grossProfit = double.NaN;
        int tradedVolume = 0;
        int marketOrderCount = 0;
        double[] equityCurve = null;
        
        public double GrossProfit { get { if (double.IsNaN(grossProfit)) { QuickEvaluation(); } return grossProfit; } }
        public double Commissions { get { return TradedVolume * Configuration.CONTRACT_COMMISSION; } }
        public double NetProfit { get { return GrossProfit - Commissions; } }
        public double MaxProfit { get { return RunUpStatistics.getSum(); } }
        public double GlobalEfficiency { get { return (GrossProfit * 100 / MaxProfit); } }
        public double[] EquityCurve { get { if (equityCurve == null) { BuildEquityCurve(); } return equityCurve; } }
        public int TradedVolume { get { if (double.IsNaN(grossProfit)) { QuickEvaluation(); } return tradedVolume; } }
        public int MarketOrderCount { get { if (double.IsNaN(grossProfit)) { QuickEvaluation(); } return marketOrderCount; } }
        
        void QuickEvaluation()
        {
            grossProfit = 0;
            marketOrderCount = 0;
            tradedVolume = 0;
            
            for (int i = 0; i < orders.Count; i++)
            {
                Order order = orders[i];
                if (order.IsExecuted)
                {
                    switch (order.Operation)
                    {
                        case Operation.BUY:
                            grossProfit -= (order.ExecutionPrice * order.Volume);
                            break;
                        case Operation.SELL:
                            grossProfit += (order.ExecutionPrice * order.Volume);
                            break;
                    }

                    tradedVolume += order.Volume;

                    if (order.IsMarket) {
                        marketOrderCount += order.Volume;
                    }
                }
            }
        }
        
        void BuildEquityCurve()
        {
            List<double> eqCurve = new List<double>();
            eqCurve.Add(0);
            for (int i = 0; i < Trades.Count; ++i)
            {
                Trade trade = Trades[i];
                if (trade.IsOpen) continue;
                eqCurve.Add(trade.Profit + eqCurve[i]);
            }
            equityCurve = eqCurve.ToArray();
        }
        
        
        #endregion
            
        #region Marshall / Unmarshall

        private static XslCompiledTransform ordersReportTransform;
        public static XslCompiledTransform OrdersReportTransform { get { return ordersReportTransform; } }

        DateTime exitTime = DateTime.MinValue;
        public DateTime ExitTime
        {
            get { return exitTime; }
            set { exitTime = value; }
        }
        
        double exitPrice = double.NaN;
        public double ExitPrice 
        {
            get { return exitPrice; }
            set { exitPrice = value; }
        }

        static TSStatus()
        {
            try
            {
                ordersReportTransform = new XslCompiledTransform();
                ordersReportTransform.Load(Path.Combine(Configuration.BIN_FOLDER, "tradingStatusReport.xsl"));
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void Marshall(XmlNode parent, XmlDocument document)
        {
            XmlNode node = document.CreateNode(XmlNodeType.Element, "orders", "");
            parent.AppendChild(node);

            parent = node;

            node = document.CreateNode(XmlNodeType.Element, "openPositions", "");
            node.InnerText = openPositions.ToString();
            parent.AppendChild(node);

            if (!enterTime.Equals(DateTime.MinValue))
            {
                node = document.CreateNode(XmlNodeType.Element, "enterTime", "");
                node.InnerText = DTUtil.ToDateTimeString(enterTime);
                parent.AppendChild(node);

                node = document.CreateNode(XmlNodeType.Element, "enterPrice", "");
                node.InnerText = enterPrice.ToString("0.0000");
                parent.AppendChild(node);

                node = document.CreateNode(XmlNodeType.Element, "enterOperation", "");
                node.InnerText = enterOperation.ToString();
                parent.AppendChild(node);
            }

            if (!exitTime.Equals(DateTime.MinValue))
            {
                node = document.CreateNode(XmlNodeType.Element, "exitTime", "");
                node.InnerText = DTUtil.ToDateTimeString(exitTime);
                parent.AppendChild(node);
                
                node = document.CreateNode(XmlNodeType.Element, "exitPrice", "");
                node.InnerText = exitPrice.ToString("0.0000");
                parent.AppendChild(node);
            }

            for (int i = 0; i < orders.Count; ++i)
            {
                Order order = orders[i];
                order.Marshall(parent, document, i + 1);
            }
        }

        internal static TSStatus Unmarshall(XmlNode xmlNode)
        {
            TSStatus tradingStatus = new TSStatus();

            foreach (XmlNode node in xmlNode.ChildNodes)
            {
                if (node.Name.Equals("order"))
                {
                    Order order = Order.Unmarshall(node);
                    tradingStatus.orders.Add(order);
                }
                else if (node.Name.Equals("openPositions"))
                    tradingStatus.openPositions = int.Parse(node.InnerText);
                else if (node.Name.Equals("enterTime"))
                    tradingStatus.enterTime = DTUtil.ParseDateTime(node.InnerText);
                else if (node.Name.Equals("enterOperation"))
                    tradingStatus.enterOperation = Operation.Parse(node.InnerText);
                else if (node.Name.Equals("enterPrice"))
                    tradingStatus.enterPrice = double.Parse(node.InnerText);
                else if (node.Name.Equals("exitTime"))
                    tradingStatus.exitTime = DTUtil.ParseDateTime(node.InnerText);
                else if (node.Name.Equals("exitPrice"))
                    tradingStatus.exitPrice = double.Parse(node.InnerText);
            }
            return tradingStatus;
        }

        public string GetHTMLReport()
        {
            XmlDocument xmlOrders = new XmlDocument();
            Marshall(xmlOrders, xmlOrders);

            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.OmitXmlDeclaration = true;

            StringBuilder report = new StringBuilder();
            XmlWriter xmlWriter = XmlWriter.Create(new StringWriter(report), settings);
            OrdersReportTransform.Transform(xmlOrders.CreateNavigator(), xmlWriter);
            xmlWriter.Close();

            return report.ToString();
        }

        public void Save(string filePath)
        {
            Object lockThis = new Object();
            lock (lockThis)
            {
                XmlDocument xmlOrders = new XmlDocument();
                Marshall(xmlOrders, xmlOrders);

                XmlWriterSettings settings = new XmlWriterSettings();
                settings.Indent = true;
                settings.OmitXmlDeclaration = true;

                StreamWriter writer = new StreamWriter(filePath);
                XmlWriter xmlWriter = XmlWriter.Create(writer, settings);
                xmlOrders.WriteTo(xmlWriter);
                xmlWriter.Close();
                writer.Dispose();
            }
        }

        public void ListOrdersInConsole()
        {
            XmlDocument xmlOrders = new XmlDocument();
            Marshall(xmlOrders, xmlOrders);

            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.OmitXmlDeclaration = true;

            XmlWriter xmlWriter = XmlWriter.Create(Console.Out, settings);
            xmlOrders.WriteTo(xmlWriter);
            xmlWriter.Close();
        }

        public static TSStatus Load(string filePath)
        {
            if (!File.Exists(filePath))
                return new TSStatus();

            XmlDocument xmlOrders = new XmlDocument();
            xmlOrders.Load(filePath);
            return TSStatus.Unmarshall(xmlOrders.FirstChild);
        }

        public static string BuildPersistenceFilePath(string fileName)
        {
            fileName = Configuration.ORDERS_FOLDER + "\\" + fileName;
            return fileName;
        }

        #endregion
        
    }
}
