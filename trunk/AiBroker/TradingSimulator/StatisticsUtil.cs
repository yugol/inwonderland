using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using ZedGraph;
using org.apache.commons.math.stat.descriptive;

namespace TradingSimulator
{
    static class StatisticsUtil
    {
        internal static DescriptiveStatistics GetBarProfitStatistics()
        {
            
            DescriptiveStatistics stat = new DescriptiveStatistics();
//            for (int i = SimulatorData.Instance.First + 1; i <= SimulatorData.Instance.Last; i++)
//            {
//                double value = SimulatorData.Instance.TimeSeries.Closes[i] - SimulatorData.Instance.TimeSeries.Closes[i - 1];
//                stat.addValue(value);
//            }
            return stat;
        }

        internal static string GetTextReport(DescriptiveStatistics s)
        {
            string leftMarginSep = "  ";
            int col1Len = 20;
            int col2Len = 10;
            int col3Len = 10;
            int valLen = 10;
            string colSep = "     ";

            StringBuilder report = new StringBuilder();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Sample Size".PadRight(col1Len));
            report.Append(s.getN().ToString().PadLeft(valLen));
            report.Append(colSep);
            report.Append("Variance".PadRight(col2Len));
            report.Append(s.getVariance().ToString("0.0000").PadLeft(valLen));
            report.Append(colSep);
            report.Append("".PadRight(col3Len));
            report.Append("".PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Mean".PadRight(col1Len));
            report.Append(s.getMean().ToString("0.0000").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Skewness".PadRight(col2Len));
            report.Append(s.getSkewness().ToString("0.0000").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Min Value".PadRight(col3Len));
            report.Append(s.getMin().ToString("0.0000").PadLeft(valLen));

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Standard Deviation".PadRight(col1Len));
            report.Append(s.getStandardDeviation().ToString("0.0000").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Kurtosis".PadRight(col2Len));
            report.Append(s.getKurtosis().ToString("0.0000").PadLeft(valLen));
            report.Append(colSep);
            report.Append("Max Value".PadRight(col3Len));
            report.Append(s.getMax().ToString("0.0000").PadLeft(valLen));

            report.AppendLine();

            report.AppendLine();

            report.Append(leftMarginSep);
            report.Append("Percentiles");

            report.AppendLine();

            AppendPercentiles(s, leftMarginSep, report, new double[] { 1, 5, 10, 20, 25, 30, 40, 50 });
            AppendPercentiles(s, leftMarginSep, report, new double[] { 60, 70, 75, 80, 90, 95, 99 });

            return report.ToString();
        }

        private static void AppendPercentiles(DescriptiveStatistics s, string leftMarginSep, StringBuilder report, double[] percentiles)
        {
            int colLen = 10;
            string colSep = " ";

            report.AppendLine();

            report.Append(leftMarginSep);
            foreach (double p in percentiles)
            {
                report.Append((p.ToString() + "%").PadLeft(colLen));
                report.Append(colSep);
            }

            report.AppendLine();

            report.Append(leftMarginSep);
            foreach (double p in percentiles)
            {
                report.Append("".PadLeft(colLen, '-'));
                report.Append(colSep);
            }

            report.AppendLine();

            report.Append(leftMarginSep);
            foreach (double p in percentiles)
            {
                report.Append(s.getPercentile(p).ToString("0.0000").PadLeft(colLen));
                report.Append(colSep);
            }

            report.AppendLine();
        }

        internal static double[][] GetDistributionClusters(DescriptiveStatistics stat, int clusterCount)
        {
            double[][] dist = new double[2][];

            if (stat.getN() <= 0)
            {
                dist[0] = new double[0];
                dist[1] = new double[0];
            }
            else
            {
                double min = stat.getMin();
                double max = stat.getMax();

                if (min == max)
                {
                    dist[0] = new double[] { min };
                    dist[1] = new double[] { stat.getN() };
                }
                else
                {
                    dist[0] = new double[clusterCount];
                    dist[1] = new double[clusterCount];

                    double[] intervals = new double[clusterCount];
                    double step = (max - min) / clusterCount;

                    intervals[0] = min + step;
                    for (int i = 1; i < clusterCount - 1; i++)
                    {
                        intervals[i] = intervals[i - 1] + step;
                    }
                    intervals[clusterCount - 1] = max;

                    step /= 2;
                    for (int i = 0; i < clusterCount; i++)
                    {
                        dist[0][i] = intervals[i] - step;
                    }

                    for (int i = 0; i < stat.getN(); i++)
                    {
                        double value = stat.getElement(i);
                        int pos = Array.BinarySearch(intervals, value);
                        if (pos < 0) pos = -pos - 1;
                        dist[1][pos] += 1;
                    }
                }
            }
            return dist;
        }

        internal static void DrawDistribution(DescriptiveStatistics stat, int clusterCount, ZedGraphControl graph)
        {
            GraphPane pane = graph.GraphPane;
            pane.CurveList.Clear();

            pane.Title.Text = "Distribution";
            pane.XAxis.Title.Text = "Cluster center";
            pane.YAxis.Title.Text = "Count";

            pane.YAxis.MajorGrid.IsVisible = true;
            pane.YAxis.MajorGrid.Color = Color.Gray;

            double[][] dist = StatisticsUtil.GetDistributionClusters(stat, clusterCount);
            BarItem bar = pane.AddBar("", dist[0], dist[1], Color.Blue);
            bar.Bar.Fill = new Fill(Color.Blue, Color.DeepSkyBlue, Color.Blue);

            graph.RestoreScale(pane);
            graph.Invalidate();
        }
    }
}
