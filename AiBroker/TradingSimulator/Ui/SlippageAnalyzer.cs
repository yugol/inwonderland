using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using TradingCommon;
using TradingCommon.Storage;
using org.apache.commons.math.stat.descriptive;

namespace TradingSimulator.Ui
{
    public partial class SlippageAnalyzer : Form
    {
        IList<BidAsk> bidAsks;

        private Title title;
        public Title Title
        {
            get { return title; }
            set 
            {
                title = value;
                Text = "Trading Simulator - Slippage Analyzer - " + title.FullSymbol;
                bidAsks = DataUtil.ReadBidAsks(title.FullSymbol);
                DescriptiveStatistics bidStat = new DescriptiveStatistics();
                DescriptiveStatistics askStat = new DescriptiveStatistics();

                foreach (BidAsk bidAsk in bidAsks)
                {
                    bidStat.addValue(bidAsk.price - bidAsk.bid);
                    askStat.addValue(bidAsk.ask - bidAsk.price);
                }

                bidStatistics.SetStatistics(bidStat);
                askStatistics.SetStatistics(askStat);
            }
        }

        public SlippageAnalyzer()
        {
            InitializeComponent();
        }
    }
}
