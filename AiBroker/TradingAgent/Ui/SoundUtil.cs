using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;
using TradingCommon.Traders;

namespace TradingAgent.Ui
{
    class SoundUtil
    {
        [DllImport("kernel32.dll")]
        public static extern bool Beep(uint dwFreq, uint dwDuration);

        [DllImport("WinMM.dll")]
        public static extern bool PlaySound(string fname, int Mod, int flag);

        // these are the SoundFlags we are using here, check mmsystem.h for more
        public const int SND_ASYNC = 0x0001; // play asynchronously
        public const int SND_FILENAME = 0x00020000; // use file name
        public const int SND_PURGE = 0x0040; // purge non-static events

        internal static void OrderPlacedSound(Operation op)
        {
            PlaySound("order_palced.wav", 0, SND_FILENAME | SND_ASYNC);
        }

        internal static void OrderExecutedSound(Operation op)
        {
            PlaySound("order_executed.wav", 0, SND_FILENAME | SND_ASYNC);
        }

        internal static void ApplicationStartSound()
        {
            PlaySound("ta_start.wav", 0, SND_FILENAME | SND_ASYNC);
        }
    }
}
