/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 8/30/2009
 * Time: 9:49 PM
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
using System.Collections.Generic;
using System.Text;

using weka.core;

using TradingCommon.Util;
using TradingCommon.Storage;

namespace TradingCommon.Analysis
{
    public class RelativeTrendEvolution
    {

        public static Instances GetTrendInstances(string title, int historyLength)
        {
            TimeSeries series = DataUtil.ReadTimeSeries(title, TimeSeries.DAY_PERIOD);

            FastVector headers = new FastVector(1 + 4 * historyLength);
            headers.addElement(new weka.core.Attribute("DateTime", ""));
            for (int i = 1; i <= historyLength; ++i) {
                headers.addElement(new weka.core.Attribute("O" + i.ToString()));
                headers.addElement(new weka.core.Attribute("H" + i.ToString()));
                headers.addElement(new weka.core.Attribute("L" + i.ToString()));
                headers.addElement(new weka.core.Attribute("C" + i.ToString()));
            }

            Instances dataset = new Instances("RelativeHistory", headers, series.Count - historyLength);

            for (int i = 0; i < series.Count - historyLength; ++i) {
                Instance inst = new Instance(headers.capacity());
                inst.setDataset(dataset);
                inst.setValue(0, series.Dates[i].ToOADate());
                double referenceValue = series.Closes[i];
                int attrIndex = 0;
                for (int j = i + 1; j <= i + historyLength; ++j) {
                    inst.setValue(++attrIndex, series.Opens[j] - referenceValue);
                    inst.setValue(++attrIndex, series.Highs[j] - referenceValue);
                    inst.setValue(++attrIndex, series.Lows[j] - referenceValue);
                    inst.setValue(++attrIndex, series.Closes[j] - referenceValue);
                }
                dataset.add(inst);
            }

            return dataset;
        }

        internal static string GetHeadersCsv(Instances dataset)
        {
            StringBuilder sb = new StringBuilder(dataset.attribute(0).name());
            for (int i = 1; i < dataset.numAttributes(); ++i) {
                sb.Append(", ");
                sb.Append(dataset.attribute(i).name());
            }
            return sb.ToString();
        }

        internal static string GetTimedInstanceCsv(Instance inst)
        {
            StringBuilder sb = new StringBuilder(DTUtil.ToDateTimeString(DateTime.FromOADate(inst.value(0))));
            for (int i = 1; i < inst.numValues(); ++i) {
                sb.Append(", ");
                sb.Append(inst.value(i).ToString("0.0000"));
            }
            return sb.ToString();
        }

        internal static string GetInstanceCsv(Instance inst)
        {
        	StringBuilder sb = new StringBuilder(DTUtil.ToDateTimeString(DateTime.FromOADate(inst.value(0))));
            for (int i = 1; i < inst.numValues(); ++i) {
                sb.Append(", ");
                sb.Append(inst.value(i).ToString("0.0000"));
            }
            return sb.ToString();
        }

        public static string GetTimedInstancesCsv(Instances dataset)
        {
            StringBuilder sb = new StringBuilder(GetHeadersCsv(dataset));
            for (int i = 0; i < dataset.numInstances(); ++i) {
                sb.AppendLine();
                sb.Append(GetTimedInstanceCsv(dataset.instance(i)));
            }
            return sb.ToString();
        }

        public static string GetInstancesCsv(Instances dataset)
        {
            StringBuilder sb = new StringBuilder(GetHeadersCsv(dataset));
            for (int i = 0; i < dataset.numInstances(); ++i) {
                sb.AppendLine();
                sb.Append(GetInstanceCsv(dataset.instance(i)));
            }
            return sb.ToString();
        }
        
        
    }
}
