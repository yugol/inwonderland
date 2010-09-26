using System;
using System.Collections.Generic;
using System.Text;

namespace TradingSimulator
{
    class SearchSpace
    {
        IList<double[]> domains = new List<double[]>();

        internal double[] this[int i] { get { return domains[i]; } }
        internal int Dim { get { return domains.Count; } }

        internal void Add(double[] domain)
        {
            domains.Add(domain);
        }
    }
}
