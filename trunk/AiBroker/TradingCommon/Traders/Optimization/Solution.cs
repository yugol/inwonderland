/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 9:07 AM
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
    public class Solution : SimulatorSpec, ISolution
    {
        double fitness;
        string tSysRep;
        TSTraderSimulation simulation;
        
        public double Fitness
        {
            get { return fitness; }
            set { fitness = value; }
        }
        
        new public TradingSystem TSys 
        { 
            get 
            {
                if (tSys == null) {
                    tSys = TSParser.ParseTS(tSysRep, false);
                }
                return tSys;
            }
        }
        
        public TSTraderSimulation Simulation
        {
            get 
            { 
                if (simulation == null) {
                    SimulationParameters simParam = new SimulationParameters(Title, Period, TSys);
                    simParam.FirstIndex = FirstIndex;
                    simParam.LastIndex = LastIndex;
                    simulation = new TSTraderSimulation(simParam);
                }
                return simulation;
            }
        }
        
        public Solution(Title title, int period, TradingSystem tSys, int firstIndex, int lastIndex, double fitness)
            : base(title, period, null)
        {
            tSysRep = tSys.ToXmlString();
            FirstIndex = firstIndex;
            LastIndex = lastIndex;
            this.fitness = fitness;
        }
        
    }
}
