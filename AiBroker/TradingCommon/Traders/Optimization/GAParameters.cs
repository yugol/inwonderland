/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/2/2009
 * Time: 10:31 PM
 * 
 *
 * Copyright (c) 2008, 2009 Iulian GORIAC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
using System;

namespace TradingCommon.Traders.Optimization
{
    public class GAParameters
    {
        double mutationRate = 0.2;
        double crossoverRate = 0.80;
		int populationSize = 100;
		int generationSize = 10;
		double rouletteStep = 1.01;

        public double MutationRate
        {
            get { return mutationRate; }
            set { mutationRate = value; }
        }

        public double CrossoverRate
        {
            get { return crossoverRate; }
            set { crossoverRate = value; }
        }
		
        public int PopulationSize
        {
            get { return populationSize; }
            set 
            { 
                if (value < 2 || value % 2 == 1) {
            		value = (value / 2) * 2;
            		if (value < 2) {
            			value = 2;
            		}
                }
                populationSize = value;
            }
        }
		
        public int GenerationSize
        {
            get { return generationSize; }
            set { generationSize = value; }
        }
        
        public double RouletteStep
        {
            get { return rouletteStep; }
            set 
            { 
                if (value < 1) {
                    throw new ArgumentException("Roulette step must be at least 1");
                }
                rouletteStep = value;
            }
        }
    }
}
