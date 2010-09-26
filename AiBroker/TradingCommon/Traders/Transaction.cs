using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Traders
{
    public class Transaction
    {
        public string symbol;
        public Operation operation;
        public int volume;
        public double price;
        public DateTime dateTime;

        public override bool Equals(object obj)
        {
            if (obj == null) return false;

            Transaction other = (Transaction)obj;
            
            if ((dateTime.ToOADate() == other.dateTime.ToOADate()) &&
                (symbol.Equals(other.symbol)) &&
                (operation.Equals(other.operation)) &&
                (price.Equals(other.price)) &&
                (volume.Equals(other.volume)))
            {
                return true;
            }

            return false;
        }

        public override int GetHashCode()
        {
            return (int)dateTime.ToOADate();
        }
    }
}
