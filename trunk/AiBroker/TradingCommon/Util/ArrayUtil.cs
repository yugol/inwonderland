using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Util
{
    public static class ArrayUtil
    {
        public static void MoveDataToEnd(double[] container, int begin, int length)
        {
            int pos = container.Length - 1;
            for (int i = 0; i < length; ++i)
            {
                container[pos] = container[pos - begin];
                --pos;
            }
            for (int i = 0; i < begin; ++i) container[i] = double.NaN;
        }
    }
}
