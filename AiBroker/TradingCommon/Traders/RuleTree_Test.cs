/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 3:23 AM
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
#if TEST

using System;
using System.Collections.Generic;
using NUnit.Framework;

namespace TradingCommon.Traders
{
    [TestFixture]
    public class RuleTree_Test
    {
        [Test]
        public void PreOrderNodes()
        {
            RuleTree tree = new CompositionRuleNode("And");
            ((CompositionRuleNode) tree).Children.Add(new BasicRuleNode("MomentumZeroline"));
            CompositionRuleNode orNode = new CompositionRuleNode("Or");
            ((CompositionRuleNode) tree).Children.Add(orNode);
            ((CompositionRuleNode) tree).Children.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            orNode.Children.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            orNode.Children.Add(new BasicRuleNode("MomentumZeroline"));
            
            List<RuleTree> nodes = new List<RuleTree>();
            tree.PreOrderNodes(nodes);
            
            Assert.AreEqual(6, nodes.Count);
        }
    }
}
#endif
