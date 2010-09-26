using System;
using System.Collections.Generic;

namespace TradingCommon.Util
{
    /// <summary>
    /// Description of TicksUtil.
    /// </summary>
    public class TicksUtil
    {
        public static void MergeAppendTicks(IList<Tick> oldTicks, IList<Tick> newTicks)
        {
            if (newTicks.Count > 0)
            {            
                DateTime newDate = newTicks[0].dateTime.Date;
                int insertPosition = RemoveOldDate(oldTicks, newDate);;
                    
                foreach (Tick newTick in newTicks)
                {
                    if (newDate == newTick.dateTime.Date)
                    {
                        oldTicks.Insert(insertPosition, newTick);
                        ++insertPosition;
                    }
                    else
                    {
                        newDate = newTick.dateTime.Date;
                        insertPosition = RemoveOldDate(oldTicks, newDate);
                        oldTicks.Insert(insertPosition, newTick);
                        ++insertPosition;
                    }                    
                }
            }
        }
        
        public static int RemoveOldDate(IList<Tick> ticks, DateTime date)
        {
            int insertPosition = 0;
            int i = 0;
            while (i < ticks.Count)
            {
                Tick tick = ticks[i];
                int dateOrder = tick.dateTime.Date.CompareTo(date);
                if (dateOrder < 0)
                {
                    insertPosition = ++i;
                }
                else
                {
                    insertPosition = i;
                    if (dateOrder == 0)
                    {
                        ticks.RemoveAt(i);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            return insertPosition;
        }
        
        
    }
}
