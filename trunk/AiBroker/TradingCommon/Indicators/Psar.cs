using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Indicators
{
    public class Psar
    {
        private int enterIndex;
        private double[] highs = null;
        private bool positionIsLong;
        private double[] lows = null;
        private double maxHigh = -1;
        private double minLow = -1;
        private double nextSar = Double.NaN;
        private double[][] psarList = null;
        private double acceleration;
        private double maxAcceleration;
        private int dataLength;

        public double[][] SarData { get { CalculatePsarList(); return psarList; } }
        public double NextSar { get { return nextSar; } }

        public Psar(double[] highs, double[] lows, double acceleration, double maxAcceleration)
            : this(highs, lows, 0, true, acceleration, maxAcceleration)
        {
        }

        public Psar(double[] highs, double[] lows, int enterIndex, bool isPositionLong, double acceleration, double maxAcceleration)
        {
            this.highs = highs;
            this.lows = lows;
            this.acceleration = acceleration;
            this.maxAcceleration = maxAcceleration;
            this.positionIsLong = isPositionLong;
            this.enterIndex = enterIndex;
            this.dataLength = lows.Length;
        }

        void CalculatePsarList()
        {
            if (psarList == null)
            {
                psarList = new double[2][];
                psarList[0] = new double[dataLength];
                psarList[1] = new double[dataLength];
                for (int i = 0; i < dataLength; ++i)
                    psarList[0][i] = psarList[1][i] = Double.NaN;

                if (dataLength > 1)
                {
                    double currentSar = CalculateFirstStop();
                    psarList[0][enterIndex] = currentSar;
                    maxHigh = highs[enterIndex];
                    minLow = lows[enterIndex];

                    double accelerationFactor = acceleration;
                    double nextHigh;
                    double nextLow;

                    int nextEntryIndex = enterIndex + 1;
                    while (nextEntryIndex < dataLength)
                    {
                        nextSar = CalculateSarPoint(nextEntryIndex - 1, currentSar, accelerationFactor);

                        nextHigh = highs[nextEntryIndex];
                        nextLow = lows[nextEntryIndex];

                        if (positionIsLong)
                        {
                            if (nextLow <= nextSar)
                            {
                                psarList[1][nextEntryIndex] = nextSar;
                                nextSar = maxHigh;
                                positionIsLong = !positionIsLong;
                                accelerationFactor = acceleration;
                                minLow = nextLow;
                            }
                            else
                            {
                                if (nextHigh > maxHigh)
                                {
                                    accelerationFactor += acceleration;
                                    maxHigh = nextHigh;
                                }
                            }
                        }
                        else
                        {
                            if (nextHigh >= nextSar)
                            {
                                psarList[1][nextEntryIndex] = nextSar;
                                nextSar = minLow;
                                positionIsLong = !positionIsLong;
                                accelerationFactor = acceleration;
                                maxHigh = nextHigh;
                            }
                            else
                            {
                                if (nextLow < minLow)
                                {
                                    accelerationFactor += acceleration;
                                    minLow = nextLow;
                                }
                            }
                        }

                        if (accelerationFactor > maxAcceleration) accelerationFactor = maxAcceleration;
                        psarList[0][nextEntryIndex] = nextSar;
                        currentSar = nextSar;
                        ++nextEntryIndex;
                    }

                    nextSar = CalculateSarPoint(dataLength - 1, currentSar, accelerationFactor);
                }
            }
        }

        private double CalculateFirstStop()
        {
            double extremePoint;
            if (positionIsLong)
            {
                extremePoint = lows[enterIndex];
                for (int i = enterIndex - 1; i >= 0; --i)
                {
                    if (lows[i] <= extremePoint)
                        extremePoint = lows[i];
                    else break;
                }
            }
            else
            {
                extremePoint = highs[enterIndex];
                for (int i = enterIndex - 1; i >= 0; --i)
                {
                    if (highs[i] >= extremePoint)
                        extremePoint = highs[i];
                    else break;
                }
            }
            return extremePoint;
        }

        private double CalculateSarPoint(int currentEntryIndex, double currentSar, double accelerationFactor)
        {
            double extremePoint;

            if (positionIsLong) extremePoint = maxHigh;
            else extremePoint = minLow;

            double extremePointSar = extremePoint - currentSar;
            double sarDiff = accelerationFactor * extremePointSar;
            double nextSar = currentSar + sarDiff;

            if (positionIsLong)
            {
                if (lows[currentEntryIndex] < nextSar) nextSar = lows[currentEntryIndex];
                int prevEntryIndex = currentEntryIndex - 1;
                if (prevEntryIndex >= 0 && lows[prevEntryIndex] < nextSar)
                    nextSar = lows[prevEntryIndex];
                if (currentSar > nextSar)
                    nextSar = currentSar;
            }
            else
            {
                if (highs[currentEntryIndex] > nextSar) nextSar = highs[currentEntryIndex];
                int prevEntryIndex = currentEntryIndex - 1;
                if (prevEntryIndex >= 0 && highs[prevEntryIndex] > nextSar)
                    nextSar = highs[prevEntryIndex];
                if (currentSar < nextSar)
                    nextSar = currentSar;
            }

            return nextSar;
        }
    }
}
