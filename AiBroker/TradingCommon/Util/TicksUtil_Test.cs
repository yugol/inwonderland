#if TEST

using System;
using System.Collections.Generic;
using NUnit.Framework;

namespace TradingCommon.Util
{
    [TestFixture]
    public class TicksUtil_Test
    {
        [Test]
        public void MergeAppendTicks_EmptyOldEmptyNew()
        {            
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 0);
        }

        [Test]
        public void MergeAppendTicks_OldEntriesEmptyNew()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:01");
            oldTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 2);
            Assert.AreEqual("2000-01-01 00:00:00", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-01 00:00:01", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
        }
    
        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesFirst()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 4);
            Assert.AreEqual("2000-01-01 00:00:02", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-01 00:00:03", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-02 00:00:00", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-02 00:00:01", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
        }
        

        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesLast()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 4);
            Assert.AreEqual("2000-01-01 00:00:00", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-01 00:00:01", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-02 00:00:02", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-02 00:00:03", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
        }

        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesBefore()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-01 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 6);
            Assert.AreEqual("2000-01-01 00:00:02", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-01 00:00:03", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-02 00:00:00", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-02 00:00:01", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
            Assert.AreEqual("2000-01-04 00:00:00", DTUtil.ToDateTimeString(oldTicks[4].dateTime));
            Assert.AreEqual("2000-01-04 00:00:01", DTUtil.ToDateTimeString(oldTicks[5].dateTime));
        }

        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesBetween()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-03 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-03 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 6);
            Assert.AreEqual("2000-01-02 00:00:00", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-02 00:00:01", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-03 00:00:02", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-03 00:00:03", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
            Assert.AreEqual("2000-01-04 00:00:00", DTUtil.ToDateTimeString(oldTicks[4].dateTime));
            Assert.AreEqual("2000-01-04 00:00:01", DTUtil.ToDateTimeString(oldTicks[5].dateTime));
        }

        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesAfter()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-05 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-05 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 6);
            Assert.AreEqual("2000-01-02 00:00:00", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-02 00:00:01", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-04 00:00:00", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-04 00:00:01", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
            Assert.AreEqual("2000-01-05 00:00:02", DTUtil.ToDateTimeString(oldTicks[4].dateTime));
            Assert.AreEqual("2000-01-05 00:00:03", DTUtil.ToDateTimeString(oldTicks[5].dateTime));
        }

        [Test]
        public void MergeAppendTicks_OldEntriesMergeNewEntriesAppendAndMerge()
        {
            Tick tick;
            IList<Tick> oldTicks = new List<Tick>();
            IList<Tick> newTicks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:01");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:00");
            oldTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            oldTicks.Add(tick);

            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-05 00:00:02");
            newTicks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-05 00:00:03");
            newTicks.Add(tick);

            TicksUtil.MergeAppendTicks(oldTicks, newTicks);
            Assert.IsTrue(oldTicks.Count == 6);
            Assert.AreEqual("2000-01-02 00:00:00", DTUtil.ToDateTimeString(oldTicks[0].dateTime));
            Assert.AreEqual("2000-01-02 00:00:01", DTUtil.ToDateTimeString(oldTicks[1].dateTime));
            Assert.AreEqual("2000-01-04 00:00:02", DTUtil.ToDateTimeString(oldTicks[2].dateTime));
            Assert.AreEqual("2000-01-04 00:00:03", DTUtil.ToDateTimeString(oldTicks[3].dateTime));
            Assert.AreEqual("2000-01-05 00:00:02", DTUtil.ToDateTimeString(oldTicks[4].dateTime));
            Assert.AreEqual("2000-01-05 00:00:03", DTUtil.ToDateTimeString(oldTicks[5].dateTime));
        }

        [Test]
        public void RemoveDate_NoEntries()
        {
            IList<Tick> ticks = new List<Tick>();
            DateTime date = DTUtil.ParseDate("2000-01-01").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(0, insertPosition);
            Assert.AreEqual(0, ticks.Count);
        }

        [Test]
        public void RemoveDate_BeforeEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-01").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(0, insertPosition);
            Assert.AreEqual(6, ticks.Count);
        }
        
        [Test]
        public void RemoveDate_BetweenEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-03").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(1, insertPosition);
            Assert.AreEqual(6, ticks.Count);

            date = DTUtil.ParseDate("2000-01-05").Date;
            
            insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(4, insertPosition);
            Assert.AreEqual(6, ticks.Count);
        }

        [Test]
        public void RemoveDate_AfterEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-07").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(6, insertPosition);
            Assert.AreEqual(6, ticks.Count);
        }

        [Test]
        public void RemoveDate_FirstEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-02").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(0, insertPosition);
            Assert.AreEqual(5, ticks.Count);
        }
        
        [Test]
        public void RemoveDate_MiddleEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-04").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(1, insertPosition);
            Assert.AreEqual(3, ticks.Count);
        }
        
        [Test]
        public void RemoveDate_LastEntries()
        {
            Tick tick;
            IList<Tick> ticks = new List<Tick>();
            
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-02 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:01");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:02");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-04 00:00:03");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:00");
            ticks.Add(tick);
            tick = new Tick();
            tick.dateTime = DTUtil.ParseDateTime("2000-01-06 00:00:01");
            ticks.Add(tick);
            
            DateTime date = DTUtil.ParseDate("2000-01-06").Date;
            
            int insertPosition = TicksUtil.RemoveOldDate(ticks, date);
            Assert.AreEqual(4, insertPosition);
            Assert.AreEqual(4, ticks.Count);
        }
        
        
        
    }
}

#endif
