using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon
{
    public class Title
    {
        string symbol;
        string maturity;
        private double margin = double.NaN;
        private int contractMagnitude = 100;

        public int ContractMagnitude
        {
            get { return contractMagnitude; }
            set { contractMagnitude = value; }
        }

        public double Margin
        {
            get { return margin; }
            set { margin = value; }
        }

        public string Symbol { get { return symbol; } }
        public string Maturity { get { return maturity; } }
        public string FullSymbol { get { return symbol + (string.IsNullOrEmpty(maturity) ? "" : "-" + maturity); } }

        public Title(string symbol, string maturity)
        {
            this.symbol = symbol;
            this.maturity = maturity;
        }

        public Title(string symbol)
            : this(symbol, "")
        {
        }
        
        public static Title Parse(string rep)
        {
            rep = rep.Trim();
            if (rep.IndexOf("-") < 0)
            {
                return new Title(rep);
            }
            else
            {
                string[] parts = rep.Split(new char[]{'-'}, 2);
                return new Title(parts[0].Trim(), parts[1].Trim());
            }
        }
    }
}
