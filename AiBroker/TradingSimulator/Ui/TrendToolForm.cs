/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 8/30/2009
 * Time: 9:32 PM
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
using System.IO;
using System.Windows.Forms;
using System.Text;

using weka.core;
using weka.clusterers;

using TradingCommon;
using TradingCommon.Analysis;
using TradingCommon.Util;

namespace TradingSimulator.Ui
{
    public partial class TrendToolForm : Form
    {
        double[] dates;
        int posCluster;
        int negCluster;

        Title title;
        public Title Title
        {
            get {
                return title;
            }
            set {
                title = value;
                Text = "Relative history tool - " + title.FullSymbol;
                RunTool();
            }
        }

        Instances dataset;
        public Instances Dataset
        {
            get {
                return dataset;
            }
            set {
                dataset = value;
                Clusterer = null;
            }
        }


        SimpleKMeans clusterer;
        public SimpleKMeans Clusterer
        {
            get {
                return clusterer;
            }
            set {
                clusterer = value;
                classifyTrendsButton.Enabled = (clusterer != null);
            }
        }

        public TrendToolForm()
        {
            InitializeComponent();
        }

        void HistoryNudValueChanged(object sender, EventArgs e)
        {
            RunTool();
        }

        void RunTool()
        {
            Dataset = RelativeTrendEvolution.GetTrendInstances(title.FullSymbol, (int) historyNud.Value);
            outputControl.FileName = Path.Combine(Configuration.ANALYSIS_FOLDER, title.FullSymbol + ".relative_trend.csv");
            outputControl.Content = RelativeTrendEvolution.GetTimedInstancesCsv(Dataset);
        }

        void FindClustersButtonClick(object sender, EventArgs e)
        {
            // prepare dateset
            dates = Dataset.attributeToDoubleArray(0);
            Dataset.deleteAttributeAt(0);

            // train clusters
            Clusterer = new SimpleKMeans();
            Clusterer.setNumClusters(3);
            Clusterer.setMaxIterations(500);
            Clusterer.buildClusterer(Dataset);

            // interpret clusters
            Instance positive = new Instance(dataset.numAttributes());
            Instance negative = new Instance(dataset.numAttributes());
            for (int i = 0; i < dataset.numAttributes(); ++i) {
                positive.setValue(i, 1);
                negative.setValue(i, -1);
            }

            posCluster = Clusterer.clusterInstance(positive);
            negCluster = Clusterer.clusterInstance(negative);

            // display results
            StringBuilder sb = new StringBuilder(
                RelativeTrendEvolution.GetInstancesCsv(
                    Clusterer.getClusterCentroids()));

            sb.AppendLine();
            sb.AppendLine();

            sb.Append(Clusterer.ToString().Replace("\n", "\r\n"));

            sb.AppendLine("Interpretation");
            sb.AppendLine("==============");
            sb.AppendLine();
            sb.AppendLine(" + cluster is " + posCluster);
            sb.AppendLine(" - cluster is " + negCluster);

            outputControl.FileName = Path.Combine(Configuration.ANALYSIS_FOLDER, title.FullSymbol + ".trend_centroids.csv");
            outputControl.Content = sb.ToString();
        }

        void ClassifyTrendsButtonClick(object sender, EventArgs e)
        {
            StringBuilder sb = new StringBuilder("DateTime, Trend");


            for (int i = 0; i < Dataset.numInstances(); ++i) {
                string dateTime = DTUtil.ToDateTimeString(DateTime.FromOADate(dates[i]));
                int cluster = Clusterer.clusterInstance(Dataset.instance(i));
                sb.AppendLine();
                sb.Append(dateTime);
                if (cluster == posCluster) {
                    sb.Append(", +");
                } else if (cluster == negCluster) {
                    sb.Append(", -");
                }
            }
            outputControl.FileName = Path.Combine(Configuration.ANALYSIS_FOLDER, title.FullSymbol + ".trends.csv");
            outputControl.Content = sb.ToString();
        }
    }
}
