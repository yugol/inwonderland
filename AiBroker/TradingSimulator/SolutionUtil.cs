using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using ZedGraph;
using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Util;
using TradingCommon.Traders.Optimization;

namespace TradingSimulator
{
    static class SolutionUtil
    {

        internal static string GetTextReport(Solution s)
        {
            int descLen = 27;
            int valLen = 11;
            string leftMarginSep = "  ";
            string colSep = "     ";

            int contractMagnitude = s.Title.ContractMagnitude;

            StringBuilder report = new StringBuilder();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Net Profit".PadRight(descLen));
            report.Append(s.Simulation.Status.NetProfit.ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Gross Profit".PadRight(descLen));
            report.Append(s.Simulation.Status.GrossProfit.ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Commissions".PadRight(descLen));
            report.Append(s.Simulation.Status.Commissions.ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Run-up Profit".PadRight(descLen));
            report.Append(s.Simulation.Status.MaxProfit.ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("".PadRight(descLen));
            report.Append("".PadLeft(valLen));
            report.Append(colSep);
            report.Append("Global Efficiency".PadRight(descLen));
            report.Append((s.Simulation.Status.GlobalEfficiency.ToString("0.00") + "%").PadLeft(valLen));

            report.AppendLine();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Total Number of Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.CloseTradesCount.ToString().PadLeft(valLen));
            report.Append(colSep);
            report.Append("Percent Profitable".PadRight(descLen));
            report.Append((s.Simulation.Status.ProfitableTradesPercent.ToString("0.00") + "%").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Winning Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.WinningTradesCount.ToString().PadLeft(valLen));
            report.Append(colSep);
            report.Append("Losing Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.LosingTradesCount.ToString().PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Even Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.EvenTradesCount.ToString().PadLeft(valLen));

            report.AppendLine();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Avg. Trade Gross Profit".PadRight(descLen));
            report.Append((s.Simulation.Status.AllTradeProfitsStatistics.getMean() * contractMagnitude).ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Ratio Avg. Win:Avg. Loss".PadRight(descLen));
            report.Append(Math.Abs(s.Simulation.Status.AvgWinAvgLossRatio).ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Avg. Winning Trade".PadRight(descLen));
            report.Append((s.Simulation.Status.WinningTradeProfitsStatistics.getMean() * contractMagnitude).ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Avg. Losing Trade".PadRight(descLen));
            report.Append((s.Simulation.Status.LosingTradeProfitsStatistics.getMean() * contractMagnitude).ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Largest Winning Trade".PadRight(descLen));
            report.Append((s.Simulation.Status.WinningTradeProfitsStatistics.getMax() * contractMagnitude).ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Largest Losing Trade".PadRight(descLen));
            report.Append((s.Simulation.Status.LosingTradeProfitsStatistics.getMin() * contractMagnitude).ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Max. Consec. Winning Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.ConsecutiveWinningsLossesStatistics.getMax().ToString().PadLeft(valLen));
            report.Append(colSep);
            report.Append("Max. Consec. Losing Trades".PadRight(descLen));
            report.Append(Math.Abs(s.Simulation.Status.ConsecutiveWinningsLossesStatistics.getMin()).ToString().PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Avg. Bars in Winning Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.WinningTradesBarCountStatistics.getMean().ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Avg. Bars in Losing Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.LosingTradesBarCountStatistics.getMean().ToString("0.00").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Avg. Bars in Total Trades".PadRight(descLen));
            report.Append(s.Simulation.Status.BarCountStatistics.getMean().ToString("0.00").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Market Orders Count".PadRight(descLen));
            report.Append(s.Simulation.Status.MarketOrderCount.ToString().PadLeft(valLen));

            report.AppendLine();

            report.AppendLine();

            string smb = "Symbol";
            report.Append(leftMarginSep);
            report.Append(smb);
            report.Append(s.Title.FullSymbol.PadLeft(descLen + valLen - smb.Length));
            report.Append(colSep);
            report.Append("Bar Period".PadRight(descLen));
            report.Append((s.Period.ToString() + " Min").PadLeft(valLen));

            if (s.Simulation.Status.Orders.Count > 0)
            {
                report.AppendLine();

                report.Append(leftMarginSep);
                report.Append("First Transaction".PadRight(descLen));
                report.Append(DTUtil.ToDateString(s.Simulation.Status.Orders[0].ExecutionDateTime).PadLeft(valLen));
                report.Append(colSep);
                report.Append("Last Transaction".PadRight(descLen));
                report.Append(DTUtil.ToDateString(s.Simulation.Status.Orders[s.Simulation.Status.Orders.Count - 1].ExecutionDateTime).PadLeft(valLen));

                report.AppendLine();

                report.Append(leftMarginSep);
                report.Append("Trading Period".PadRight(descLen));
                TimeSpan tradingPeriod = s.Simulation.Status.Orders[s.Simulation.Status.Orders.Count - 1].ExecutionDateTime - s.Simulation.Status.Orders[0].ExecutionDateTime;
                report.Append((Math.Ceiling(tradingPeriod.TotalDays).ToString() + " Days").PadLeft(valLen));
            }

            report.AppendLine();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Simulator parameters:");

            report.AppendLine();

            foreach (TSParameter parameter in s.TSys.Parameters.Values)
            {
                report.Append(leftMarginSep);
                report.Append("\t");
                report.Append(parameter.Name);
                report.Append(":  ");
                report.Append(parameter.Value.ToString("0.0000"));
                report.AppendLine();
            }

            
            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Simulator rule:");

            report.AppendLine();
            
            foreach (string line in s.TSys.RuleTree.ToString().Replace("\r", "").Split('\n')) {
                report.Append(leftMarginSep);
                report.Append("\t");
                report.AppendLine(line);
            }

            report.AppendLine();

            return report.ToString();
        }

        internal static void DrawEquityCurve(Solution solution, ZedGraphControl graph)
        {
            GraphPane pane = graph.GraphPane;
            pane.CurveList.Clear();

            pane.Title.Text = "";
            pane.XAxis.Title.Text = "Time";
            pane.YAxis.Title.Text = "Gross Profit";
            pane.XAxis.Scale.MinGrace = 0;
            pane.XAxis.Scale.MaxGrace = 0;

            pane.YAxis.MajorGrid.IsVisible = true;
            pane.YAxis.MajorGrid.Color = Color.Gray;

            double[] history = solution.Simulation.Status.EquityCurve;
            LineItem profit = pane.AddCurve("", null, history, Color.Black, SymbolType.None);
            profit.Line.Width = 2F;
            profit.Line.Fill = new Fill(Color.Blue);

            graph.AxisChange();
            graph.Invalidate();
        }

    }
}
