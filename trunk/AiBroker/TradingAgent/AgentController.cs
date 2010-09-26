// #define SHOW_MARKET_PAGES // uncomment this to show futures market levels

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows.Forms;

using TradingAgent.Ui;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingAgent
{
    class AgentController
    {
        static AgentController instance = new AgentController();
        internal static AgentController Instance { get { return instance; } }

        internal AgentForm agentForm = null;

        HomePage homePage;
        PortofolioPage portofolioPage;
        TransactionsPage transactionsPage;
        PlaceOrderPage placeOrderPage;
        Timer periodicTickReader;

        PaperTrading paperTradingA;
        PaperTrading paperTradingB;

        int remainingToInitialize = 0;
        int prevTickCount = -1;

        Timer applicationRestartTimer;

        void InitTraderA()
        {
            Title title = agentForm.TraderAControl.Title;
            Log("Trading:\t\t" + title.FullSymbol);

            int timeFrame = Configuration.CANDLE_TIME_FRAME;
            Log("Time frame:\t\t" + timeFrame + " minute(s)");

            int baseVolume = 2;
            Log("Base volume:\t\t" + baseVolume + " contract(s)");

            Trader trader = new FilteredSarTrader(title, timeFrame, baseVolume);
            Log("Trader A:\t\t" + trader.Name);

            trader.Parameters[FilteredSarTrader.PSAR_ACC_PI].Value = 0.04;
            trader.Parameters[FilteredSarTrader.PSAR_MAX_PI].Value = 0.06;
            trader.Parameters[FilteredSarTrader.RSI_PERIOD_PI].Value = 3;
            trader.Parameters[FilteredSarTrader.RSI_FILTER_LONG_PI].Value = 80;
            trader.Parameters[FilteredSarTrader.RSI_FILTER_SHORT_PI].Value = 70;

            Log("Parameters:");
            foreach (TSParameter param in trader.Parameters)
            {
                Log("\t" + param.Name + ":\t\t" + param.Value);
            }

            trader.PlaceOrder = TyrPlaceOrder;

            trader.DrawOnly = Program.DrawOnly;

            IList<Tick> ticks = DataUtil.ReadTicks(trader.Title.FullSymbol);
            trader.UpdateTicks(ticks);
            prevTickCount = ticks.Count;

            trader.LoadTradingStatus(Program.PaperTrade);

            agentForm.TraderAControl.Trader = trader;
            agentForm.TraderAControl.UpdateChart();

            Log("");
        }

        void InitTraderB()
        {
            Title title = agentForm.TraderBControl.Title;
            Log("Trading:\t\t" + title.FullSymbol);

            int timeFrame = Configuration.CANDLE_TIME_FRAME;
            Log("Time frame:\t\t" + timeFrame + " minute(s)");

            int baseVolume = 2;
            Log("Base volume:\t\t" + baseVolume + " contract(s)");

            Trader trader = new FilteredSarTrader(title, timeFrame, baseVolume);
            Log("Trader B:\t\t" + trader.Name);

            trader.Parameters[FilteredSarTrader.PSAR_ACC_PI].Value = 0.015;
            trader.Parameters[FilteredSarTrader.PSAR_MAX_PI].Value = 0.020;
            trader.Parameters[FilteredSarTrader.RSI_PERIOD_PI].Value = 5;
            trader.Parameters[FilteredSarTrader.RSI_FILTER_LONG_PI].Value = 40;
            trader.Parameters[FilteredSarTrader.RSI_FILTER_SHORT_PI].Value = 40;

            Log("Parameters:");
            foreach (TSParameter param in trader.Parameters)
            {
                Log("\t" + param.Name + ":\t\t" + param.Value);
            }

            trader.PlaceOrder = TyrPlaceOrder;

            trader.DrawOnly = Program.DrawOnly;

            IList<Tick> ticks = DataUtil.ReadTicks(trader.Title.FullSymbol);
            trader.UpdateTicks(ticks);
            prevTickCount = ticks.Count;

            trader.LoadTradingStatus(Program.PaperTrade);

            agentForm.TraderBControl.Trader = trader;
            agentForm.TraderBControl.UpdateChart();

            Log("");
        }

        internal void Init(AgentForm mainForm)
        {
            AgentController.Instance.agentForm = mainForm;

            agentForm.TraderAControl.Title = new Title("DESIF2", "MAR08");
            agentForm.TraderBControl.Title = new Title("DESIF5", "MAR08");

            InitTraderA();
            InitTraderB();

            remainingToInitialize = 0;

#if SHOW_MARKET_PAGES
            remainingToInitialize += 2; // market pages for TraderAControl and TraderBControl
#endif

            homePage = new HomePage();
            mainForm.SetHomePageBrowser(homePage.Browser);
            homePage.DoneInitializing += new EventHandler(homePage_DoneInitializing);

            portofolioPage = new PortofolioPage(30000);
            mainForm.SetPortofolioPageBrowser(portofolioPage.Browser);
            portofolioPage.DoneInitializing += new EventHandler(webPage_DoneInitializing);
            ++remainingToInitialize;

            placeOrderPage = new PlaceOrderPage();
            mainForm.SetPlaceOrderPageBrowsers(placeOrderPage.Browser, placeOrderPage.TempBrowser);
            placeOrderPage.DoneInitializing += new EventHandler(webPage_DoneInitializing);
            placeOrderPage.OrderPlaced += new OrderPlacedEventHandler(placeOrderPage_OrderPlaced);
            ++remainingToInitialize;

            transactionsPage = new TransactionsPage(20000);
            mainForm.SetTransactionsPageBrowser(transactionsPage.Browser);
            transactionsPage.DoneInitializing += new EventHandler(webPage_DoneInitializing);
            transactionsPage.NewTransactionsCompleted += new EventHandler(NewTransactionsCompleted);
            ++remainingToInitialize;

            periodicTickReader = new Timer();
            periodicTickReader.Interval = 7000;
            periodicTickReader.Tick += new EventHandler(ReadRealtimeData);

            periodicTickReader.Start();

            if (Program.PaperTrade)
            {
                paperTradingA = new PaperTrading(agentForm.TraderAControl.Trader.TradingStatus);
                paperTradingA.TradesCompleted += new EventHandler(NewTransactionsCompleted);
                paperTradingB = new PaperTrading(agentForm.TraderBControl.Trader.TradingStatus);
                paperTradingB.TradesCompleted += new EventHandler(NewTransactionsCompleted);
            }

            applicationRestartTimer = new Timer();
            applicationRestartTimer.Interval = 100000;
            applicationRestartTimer.Tick += new EventHandler(restartProcessTimer_Tick);
            applicationRestartTimer.Start();
            Log("Application restart countdown initialized for " + (applicationRestartTimer.Interval / 1000) + " seconds");

            homePage.Init();
        }

        void restartProcessTimer_Tick(object sender, EventArgs e)
        {
            agentForm.Log("Init time expired");

            Process tradingAgent = new Process();
            tradingAgent.StartInfo.FileName = Application.ExecutablePath;
            StringBuilder args = new StringBuilder(Program.AccountName);
            args.Append(" ");
            args.Append(Program.AccountPassword);
            args.Append(" ");
            args.Append(Program.OrderPlacePassword);
            args.Append(" ");
            args.Append(Program.PaperTrade);
            args.Append(" ");
            args.Append(Program.ManualControl);
            args.Append(" ");
            args.Append(Program.DrawOnly);
            tradingAgent.StartInfo.Arguments = args.ToString();
            tradingAgent.Start();
            
            ExitApplication();
        }

        void homePage_DoneInitializing(object sender, EventArgs e)
        {
            Log(sender.ToString() + " initialized");

#if SHOW_MARKET_PAGES
            agentForm.TraderAControl.MarketPage.Init();
            agentForm.TraderBControl.MarketPage.Init();
#endif

            portofolioPage.Init();
            transactionsPage.Init();
            placeOrderPage.Init();
        }

        internal void webPage_DoneInitializing(object sender, EventArgs e)
        {
            Log(sender.ToString() + " initialized");
            --remainingToInitialize;

            if (remainingToInitialize == 0)
            {
                applicationRestartTimer.Stop();

                agentForm.TraderAControl.UpdateChart();
                agentForm.TraderAControl.UpdateStatus();
                agentForm.TraderAControl.AddOrder.Enabled = Program.ManualControl;

                agentForm.TraderBControl.UpdateChart();
                agentForm.TraderBControl.UpdateStatus();
                agentForm.TraderBControl.AddOrder.Enabled = Program.ManualControl;

                Log("Application restart countdown cancelled");
                Log("Trading started at: " + DTUtil.ToDateTimeString(DateTime.Now));
            }
        }

        internal void ReadRealtimeData(object sender, EventArgs args)
        {
            try
            {
                if (Program.CloseWithMarket)
                {
                    DateTime now = DateTime.Now;
                    if ((now.Hour >= Configuration.MARKET_CLOSE_HOUR) && (now.Minute >= 10))
                    {
                        ExitApplication();
                    }
                }
                
                IList<Tick> ticks = DataUtil.ReadTicks(agentForm.TraderAControl.Title.FullSymbol);
                if (ticks.Count > agentForm.TraderAControl.Trader.TickCount)
                {
                    agentForm.TraderAControl.Trader.UpdateTicks(ticks);
                    agentForm.TraderAControl.Trader.Decide();
                    agentForm.TraderAControl.UpdateChart();
                }
                if (Program.PaperTrade) paperTradingA.UpdateTicks(ticks);
                agentForm.TraderAControl.SetRealtimeData(ticks[ticks.Count - 1], null);

                ticks = DataUtil.ReadTicks(agentForm.TraderBControl.Title.FullSymbol);
                if (ticks.Count > agentForm.TraderBControl.Trader.TickCount)
                {
                    agentForm.TraderBControl.Trader.UpdateTicks(ticks);
                    agentForm.TraderBControl.Trader.Decide();
                    agentForm.TraderBControl.UpdateChart();
                }
                if (Program.PaperTrade)
                {
                    paperTradingB.UpdateTicks(ticks);
                }
                agentForm.TraderBControl.SetRealtimeData(ticks[ticks.Count - 1], null);
            }
            catch (Exception ex)
            {
                Log("Error reading realtime data: " + ex.ToString());
            }
        }

        internal bool IsTimeToPlaceAutomaticOrder()
        {
            DateTime now = DateTime.Now;
            return ((now.Hour > Configuration.MARKET_OPEN_HOUR) ||
                    ((now.Hour == Configuration.MARKET_OPEN_HOUR) && (now.Minute >= Configuration.TRADER_ACTION_DELAY)));
        }

        internal bool TyrPlaceOrder(object sender, Order order)
        {
            agentForm.TraderAControl.AddOrder.Enabled = false;
            agentForm.TraderBControl.AddOrder.Enabled = false;

            if (Program.PaperTrade)
            {
                PlacedOrder placedOrder = new PlacedOrder();
                placedOrder.id = -1;
                placedOrder.dateTime = DateTime.Now;
                placeOrderPage_OrderPlaced(order, placedOrder);
                return true;
            }
            else
            {
                if ((Program.ManualControl) && (sender is AddOrderControl))
                {
                    placeOrderPage.PlaceOrder(order);
                    return true;
                }
                else if ((!Program.ManualControl) && (sender is Trader))
                {
                    if (IsTimeToPlaceAutomaticOrder())
                    {
                        placeOrderPage.PlaceOrder(order);
                        return true;
                    }
                }
            }

            return false;
        }

        internal void placeOrderPage_OrderPlaced(Order order, PlacedOrder placedOrder)
        {
            order.SetPlaceData(placedOrder.id, placedOrder.dateTime);

            if (order.Symbol.Equals(agentForm.TraderAControl.Title.FullSymbol))
            {
                agentForm.TraderAControl.Trader.TradingStatus.AddPlacedOrder(order);
                agentForm.TraderAControl.UpdateStatus();
            }
            else if (order.Symbol.Equals(agentForm.TraderBControl.Title.FullSymbol))
            {
                agentForm.TraderBControl.Trader.TradingStatus.AddPlacedOrder(order);
                agentForm.TraderBControl.UpdateStatus();
            }

            agentForm.TraderAControl.AddOrder.Enabled = Program.ManualControl;
            agentForm.TraderBControl.AddOrder.Enabled = Program.ManualControl;
        }

        internal void NewTransactionsCompleted(object sender, EventArgs args)
        {
            IList<Transaction> trades = ((TransactionsCompletedEventArgs)args).Trades;

            foreach (Transaction trade in trades)
            {
                if (trade.symbol.Equals(agentForm.TraderAControl.Title.FullSymbol))
                {
                    agentForm.TraderAControl.Trader.TradingStatus.AddTransaction(trade);
                    agentForm.TraderAControl.UpdateStatus();
                }
                else if (trade.symbol.Equals(agentForm.TraderBControl.Title.FullSymbol))
                {
                    agentForm.TraderBControl.Trader.TradingStatus.AddTransaction(trade);
                    agentForm.TraderBControl.UpdateStatus();
                }

                SoundUtil.OrderExecutedSound(trade.operation);
            }

            agentForm.TraderAControl.AddOrder.Enabled = Program.ManualControl;
            agentForm.TraderBControl.AddOrder.Enabled = Program.ManualControl;
        }

        internal void Log(string line)
        {
            agentForm.Log(line);
        }
        
        internal void ExitApplication()
        {
            SaveLog();
            Application.Exit();
        }
        
        private void SaveLog()
        {
            string fileName = Path.Combine(Configuration.LOG_FOLDER, "TA-" + DTUtil.ToCDateTimeString(DateTime.Now) + ".log");
            try
            {
                File.WriteAllText(fileName, agentForm.GetLog());
            }
            catch(Exception)
            {
                File.WriteAllText(fileName, "");
            }
        }
    }
}
