using System;
using System.Collections.Generic;
using System.Text;

namespace TradingAgent
{
    class PlacedOrder
    {
        internal string symbol;
        internal int volume;
        internal double price;
        // internal string brokerComment;
        internal int id;
        internal DateTime dateTime;

        public override bool Equals(object obj)
        {
            if (obj == null)
                return false;
            return id.Equals(((PlacedOrder)obj).id);
        }

        public override int GetHashCode()
        {
            return id;
        }
    }
}
