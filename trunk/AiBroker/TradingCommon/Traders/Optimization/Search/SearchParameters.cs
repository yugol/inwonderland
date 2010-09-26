/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/2/2009
 * Time: 10:24 PM
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

namespace TradingCommon.Traders.Optimization.Search
{
    public class SearchParameters
    {
        BoundedDataSpec trainData;
        BoundedDataSpec testData;
        GAParameters innerGAParam = new GAParameters();
        FitnessParameters innerFitnessParam = new FitnessParameters();
        GAParameters outerGAParam = new GAParameters();
        FitnessParameters outerFitnessParam = new FitnessParameters();
        GenotypeParameters genotypeParam = new GenotypeParameters();
        double minFitness = -1000000;
        
        public BoundedDataSpec TrainData
        {
            get { return trainData; }
        }
        
        public BoundedDataSpec TestData
        {
            get { return testData; }
        }
        
        public GAParameters InnerGAParam
        {
            get { return innerGAParam; }
            set { innerGAParam = value; }
        }
        
        public FitnessParameters InnerFitnessParam
        {
            get { return innerFitnessParam; }
            set { innerFitnessParam = value; }
        }
        
        public GAParameters OuterGAParam
        {
            get { return outerGAParam; }
            set { outerGAParam = value; }
        }
        
        public FitnessParameters OuterFitnessParam
        {
            get { return outerFitnessParam; }
            set { outerFitnessParam = value; }
        }
        
        public GenotypeParameters GenotypeParam
        {
            get { return genotypeParam; }
            set { genotypeParam = value; }
        }
        
        public double MinFitness
        {
            get { return minFitness; }
            set { minFitness = value; }
        }

        public SearchParameters(Title trainTitle, Title testTitle, int period)
        {
            trainData = new BoundedDataSpec(trainTitle, period);
            testData = new BoundedDataSpec(testTitle, period);
            
            outerGAParam.PopulationSize = 30;
            outerGAParam.RouletteStep = 1;
        }
    }
}
