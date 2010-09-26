using System;
using System.Collections.Generic;
using TradingCommon;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingSimulator
{
    public class MaturityJoiner
    {
        string symbol;
        
        public MaturityJoiner(string symbol)
        {
            this.symbol = symbol;
        }
        
        public string GetJoinSymbol()
        {
            return new Title(symbol, "DEC99").FullSymbol;
        }
        
        public void Join()
        {
            DataUtil.AppendTicks(GetJoinSymbol(), JoinTicks());
        }
        
        internal IList<Tick> JoinTicks()
        {
            
            IList<string> maturityList = DataUtil.GetMaturitiesUpToNow(symbol);
            if (maturityList.Count <= 0)
                return new List<Tick>();
            List<Tick> ticks = new List<Tick>(DataUtil.ReadTicks(new Title(symbol, maturityList[0]).FullSymbol));
            for (int i = 1; i < maturityList.Count; ++i)
            {
                double preTerminalPrice;
                IList<Tick> terminalTicks = GetTerminalTicks(maturityList[i-1], maturityList[i], out preTerminalPrice);
                double delta = preTerminalPrice - ticks[ticks.Count - 1].price;
                foreach (Tick tick in ticks)
                    tick.price += delta;
                ticks.AddRange(terminalTicks);
            }
            return ticks;
        }
        
        internal IList<Tick> GetTerminalTicks(string previousMaturity, string terminalMaturity, out double preTerminalPrice)
        {
            List<Tick> ticks = new List<Tick>(DataUtil.ReadTicks(new Title(symbol, terminalMaturity).FullSymbol));
            DateTime firstTerminalDate = DTUtil.Maturity2Date(previousMaturity).AddDays(1);
            int i = 0;
            while (ticks[i].dateTime.CompareTo(firstTerminalDate) < 0) {
                ++i;
            }
            preTerminalPrice = ticks[i-1].price;
            ticks.RemoveRange(0, i);
            return ticks;
        }
    }
}
