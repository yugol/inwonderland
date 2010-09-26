using System;
using TradingCommon;
using TradingCommon.Traders.Optimization;

namespace TradingSimulator
{
    public delegate void PeriodChangedHandler(int period); 
    public delegate void SimulationsChangedHandler(); 
    
    sealed class SimulatorData
    {
        public event PeriodChangedHandler PeriodChanged;       
        public event SimulationsChangedHandler SimulationsChanged;       
        
        static SimulatorData instance = new SimulatorData();
        public static SimulatorData Instance { get { return instance; } }

        int period = TimeSeries.DAY_PERIOD;
        DataSet testData = null;
        DataSet trainData = null;
        SolutionBuffer solutions = new SolutionBuffer();
        
        public SimulatorData()
        {
            solutions.MaxSize = 30;
        }
        
        public int Period
        {
            get { return period; }
            set
            {
                period = value;
                if (PeriodChanged != null) {
                    PeriodChanged(period);
                }
            }
        }
        
        public DataSet TestData
        {
            get { return testData; }
            set { testData = value; }
        }
        
        public DataSet TrainData
        {
            get { return trainData; }
            set { trainData = value; }
        }
        
        public SolutionBuffer Solutions
        {
            get { return solutions; }
            set
            {
                if (value != null) {
                    solutions = value;    
                    if (SimulationsChanged != null) {
                        SimulationsChanged();
                    }
                }
            }
        }
        
        public void AddSolution(ISolution solution)
        {
            solutions.Add(solution);
            if (SimulationsChanged != null) {
                SimulationsChanged();
            }
        }
        
        public void SetMaxSolutionsCount(int count)
        {
            int tmp = solutions.MaxSize;
            solutions.MaxSize = count;
            if (tmp > count) {
                if (SimulationsChanged != null) {
                    SimulationsChanged();
                }
            }
        }
        
        public void ClearSolutions()
        {
            solutions.Clear();
            if (SimulationsChanged != null) {
                SimulationsChanged();
            }
        }
    }
}
