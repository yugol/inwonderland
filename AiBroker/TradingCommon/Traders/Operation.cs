using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Traders
{
    public class Operation
    {
        public const int SELL = -1;
        public const int NONE = 0;
        public const int BUY = 1;

        public static readonly Operation sell = new Operation(-1);
        public static readonly Operation none = new Operation(0);
        public static readonly Operation buy = new Operation(1);

        public static implicit operator int(Operation operation)
        {
            return operation.op;
        }

        public static Operation operator ~(Operation operation)
        {
            return new Operation(-1 * operation.op);
        }

        int op = 0;

        public Operation(int operation)
        {
            this.op = Math.Sign(operation);
        }

        public Operation(Operation operation)
        {
            this.op = operation.op;
        }

        public override bool Equals(object obj)
        {
            if (obj == null) return false;
            return op == ((Operation)obj).op;
        }

        public override string ToString()
        {
            switch (op)
            {
                case -1: return "SELL";
                case 0: return "NONE";
                case 1: return "BUY";
                default: return null;
            }
        }

        public override int GetHashCode()
        {
            return op;
        }

        public static Operation Parse(string text)
        {
            text = text.ToUpper();

            if (text.Equals("SELL"))
                return sell;
            else if (text.Equals("NONE"))
                return none;
            else if (text.Equals("BUY"))
                return buy;

            throw new FormatException("Invalid operation string");
        }
    }
}
