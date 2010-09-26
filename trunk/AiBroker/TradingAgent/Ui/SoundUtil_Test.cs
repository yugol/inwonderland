#if TEST

using System;
using NUnit.Framework;

namespace TradingAgent.Ui
{
    [TestFixture]
    public class SoundUtil_Test
    {
        [Test]
        public void WavSound()
        {
            // SoundUtil.OrderPlacedSound(null);
            // SoundUtil.OrderExecutedSound(null);
            SoundUtil.ApplicationStartSound();
        }
    }
}

#endif
