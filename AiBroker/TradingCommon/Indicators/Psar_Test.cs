#if TEST

using System;
using NUnit.Framework;

namespace TradingCommon.Indicators
{
    [TestFixture]
    public class Psar_Test
    {
        [Test]
        public void FirstEpSarLocalMinima()
        {
            double[] highs = { 10f, 6f, 7f, 5f };
            double[] lows = { 1f, 4f, 3f, 5f };

            Psar psarGen = new Psar(highs, lows, 3, true, .02, .2);
            Assert.AreEqual(3, psarGen.SarData[0][3]);

            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);

            psarGen = new Psar(highs, lows, 3, false, .02f, .2f);
            Assert.AreEqual(7, psarGen.SarData[0][3]);

            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);
        }

        [Test]
        public void EmptySeries()
        {
            double[] highs = { };
            double[] lows = { };

            Psar psarGen = new Psar(highs, lows, 0, false, .02f, .2f);
            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);
        }

        [Test]
        public void TurnPoints()
        {
            double[] highs = { 10f, 7f, 12f, 5f, 14f };
            double[] lows = { 9f, 6f, 11f, 4f, 13f };

            Psar psarGen = new Psar(highs, lows, 0, false, .02f, .2f);

            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);

            Assert.AreEqual(10.0, psarGen.SarData[0][0]);

            Assert.AreEqual(10.0, psarGen.SarData[0][1]);

            Assert.AreEqual(6.0, psarGen.SarData[0][2]);
            Assert.AreEqual(10.0, psarGen.SarData[1][2]);

            Assert.AreEqual(12.0, psarGen.SarData[0][3]);
            Assert.AreEqual(6.0, psarGen.SarData[1][3]);

            Assert.AreEqual(4.0, psarGen.SarData[0][4]);
            Assert.AreEqual(12.0, psarGen.SarData[1][4]);
        }

        [Test]
        public void SimpleWilderData()
        {
            double[] highs = { 51.5f, 51.00, 51.5f, 52.00f, 52.50f, 53.00f, 53.50f, 54.00f, 54.50f, 55.00f, 55.50f, 56.00f,
                56.50f, 57.00f, 57.50f, 57.50f, 58.00f, 58.50f };
            double[] lows = { 50.5f, 50.00f, 50.5f, 51.00f, 51.50f, 52.00f, 52.50f, 53.00f, 53.50f, 54.00f, 54.50f, 55.00f,
                55.50f, 56.00f, 56.50f, 56.50f, 57.00f, 57.50f };

            Psar psarGen = new Psar(highs, lows, 4, true, .02f, .2f);

            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);

            Assert.AreEqual(double.NaN, psarGen.SarData[0][0]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][1]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][2]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][3]);

            Assert.AreEqual(50.00f, (float)psarGen.SarData[0][4]);
            Assert.AreEqual(50.05f, (float)psarGen.SarData[0][5]);
            Assert.AreEqual(50.168f, (float)psarGen.SarData[0][6]);
            Assert.AreEqual(50.36792f, (float)psarGen.SarData[0][7]);
            Assert.AreEqual(50.658485f, (float)psarGen.SarData[0][8]);
            Assert.AreEqual(51.042637f, (float)psarGen.SarData[0][9]);
            Assert.AreEqual(51.51752f, (float)psarGen.SarData[0][10]);
            Assert.AreEqual(52.07507f, (float)psarGen.SarData[0][11]);
            Assert.AreEqual(52.703056f, (float)psarGen.SarData[0][12]);

            Assert.AreEqual(53.386505f, (float)psarGen.SarData[0][13]);
            Assert.AreEqual(54.109207f, (float)psarGen.SarData[0][14]);
            Assert.AreEqual(54.787365f, (float)psarGen.SarData[0][15]);
            Assert.AreEqual(55.32989f, (float)psarGen.SarData[0][16]);
            Assert.AreEqual(55.863914f, (float)psarGen.SarData[0][17]);
        }

        [Test]
        public void WilderData()
        {
            double[] highs = { 52f, 51.5f, 51.2f, 51.5f, 52f, 52.35f, 52.10f, 51.80f, 52.10f, 52.50f, 52.80f, 52.50f,
                53.50f, 53.50f, 53.80f, 54.20f, 53.40f, 53.50f, 54.40f, 55.20f, 55.70f, 57.00f, 57.50f, 58.00f, 57.70f,
                58.00f, 57.50f, 57.00f, 56.70f, 57.50f, 56.70f, 56.00f, 56.20f, 54.80f, 55.50f, 54.70f, 54.00f, 52.50f,
                51.00f, 51.50f, 51.70f };
            double[] lows = { 51.5f, 50.5f, 50f, 50.7f, 51f, 51.50f, 51.00f, 50.50f, 51.25f, 51.70f, 51.85f, 51.50f,
                52.30f, 52.50f, 53.00f, 53.50f, 52.50f, 52.10f, 53.00f, 54.00f, 55.00f, 56.00f, 56.50f, 57.00f, 56.50f,
                57.30f, 56.70f, 56.30f, 56.20f, 56.00f, 55.50f, 55.00f, 54.90f, 54.00f, 54.50f, 53.80f, 53.00f, 51.50f,
                50.00f, 50.50f, 50.20f };

            Psar psarGen = new Psar(highs, lows, 5, true, .02f, .2f);

            Assert.AreEqual(highs.Length, psarGen.SarData[0].Length);

            Assert.AreEqual(double.NaN, psarGen.SarData[0][0]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][1]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][2]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][3]);
            Assert.AreEqual(double.NaN, psarGen.SarData[0][4]);

            Assert.AreEqual(50.00f, (float)psarGen.SarData[0][5]);
            Assert.AreEqual(50.047f, (float)psarGen.SarData[0][6]);
            Assert.AreEqual(50.09306f, (float)psarGen.SarData[0][7]);
            Assert.AreEqual(50.1382f, (float)psarGen.SarData[0][8]);
            Assert.AreEqual(50.182434f, (float)psarGen.SarData[0][9]);
            Assert.AreEqual(50.27514f, (float)psarGen.SarData[0][10]);
            Assert.AreEqual(50.42663f, (float)psarGen.SarData[0][11]);
            Assert.AreEqual(50.56903f, (float)psarGen.SarData[0][12]);
            Assert.AreEqual(50.80351f, (float)psarGen.SarData[0][13]);
            Assert.AreEqual(51.01923f, (float)psarGen.SarData[0][14]);
            Assert.AreEqual(51.297306f, (float)psarGen.SarData[0][15]);
            Assert.AreEqual(51.64563f, (float)psarGen.SarData[0][16]);
            Assert.AreEqual(51.952152f, (float)psarGen.SarData[0][17]);
            Assert.AreEqual(52.10f, (float)psarGen.SarData[0][18]);
            Assert.AreEqual(52.10f, (float)psarGen.SarData[0][19]);
            Assert.AreEqual(52.596f, (float)psarGen.SarData[0][20]);
            Assert.AreEqual(53.15472f, (float)psarGen.SarData[0][21]);
            Assert.AreEqual(53.923775f, (float)psarGen.SarData[0][22]);
            Assert.AreEqual(54.63902f, (float)psarGen.SarData[0][23]);
            Assert.AreEqual(55.311214f, (float)psarGen.SarData[0][24]);
            Assert.AreEqual(55.848972f, (float)psarGen.SarData[0][25]);
            Assert.AreEqual(56.27918f, (float)psarGen.SarData[0][26]);

            Assert.AreEqual(56.623344f, (float)psarGen.SarData[1][27]);
            Assert.AreEqual(58.00f, (float)psarGen.SarData[0][27]);

            Assert.AreEqual(57.966f, (float)psarGen.SarData[0][28]);
            Assert.AreEqual(57.89536f, (float)psarGen.SarData[0][29]);
            Assert.AreEqual(57.78164f, (float)psarGen.SarData[0][30]);
            Assert.AreEqual(57.599106f, (float)psarGen.SarData[0][31]);
            Assert.AreEqual(57.339195f, (float)psarGen.SarData[0][32]);
            Assert.AreEqual(57.046494f, (float)psarGen.SarData[0][33]);
            Assert.AreEqual(56.619984f, (float)psarGen.SarData[0][34]);
            Assert.AreEqual(56.253185f, (float)psarGen.SarData[0][35]);
            Assert.AreEqual(55.860676f, (float)psarGen.SarData[0][36]);
            Assert.AreEqual(55.345757f, (float)psarGen.SarData[0][37]);
            Assert.AreEqual(54.576603f, (float)psarGen.SarData[0][38]);
            Assert.AreEqual(53.66128f, (float)psarGen.SarData[0][39]);
            Assert.AreEqual(52.929028f, (float)psarGen.SarData[0][40]);
        }
    }
}

#endif
