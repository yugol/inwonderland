/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 2/22/2009
 * Time: 9:27 PM
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
using System.Collections;
using System.Xml;

using Antlr.Runtime;
using Antlr.Runtime.Tree;
using TradingCommon.Traders.BasicRules;
using TradingCommon.Traders.CompositionRules;
using TradingCommon.Traders.RuleParsing;

namespace TradingCommon.Traders
{
	public class TSParser
	{
		public static readonly string TRADINGSYSTEM_TAG = "ts";
		public static readonly string PARAMETERS_TAG = "parameters";
		public static readonly string PARAMETER_TAG = "parameter";
		public static readonly string PARAMETER_VAL_ATTR = "val";
		public static readonly string PARAMETER_MIN_ATTR = "min";
		public static readonly string PARAMETER_MAX_ATTR = "max";
		public static readonly string PARAMETER_TYPE_ATTR = "type";
		public static readonly string PARAMETER_TYPE_INT = "int";
		public static readonly string PARAMETER_TYPE_REAL = "real";
		public static readonly string PARAMETER_BITS = "bits";
		public static readonly string RULE_TAG = "rule";
		
		public static TradingSystem ParseTS(string filename)
		{
		    return ParseTS(filename, true);
		}

		public static TradingSystem ParseTS(string text, bool isFilename)
		{
			TradingSystem tSys = new TradingSystem();
			XmlDocument xmlDoc = new XmlDocument();
			if (isFilename) {
			    xmlDoc.Load(text);    
			} else {
			    xmlDoc.LoadXml(text);
			}
			ReadParameters(tSys, xmlDoc.GetElementsByTagName(PARAMETERS_TAG));
			tSys.RuleTree = ParseRule(xmlDoc.GetElementsByTagName(RULE_TAG)[0].InnerText);
			tSys.FetchParameters();
			return tSys;
		}
		
		public static void ReadParameters(TradingSystem ts, XmlNodeList nodes)
		{
			if (nodes.Count > 0) {
				foreach (XmlNode node in nodes[0].ChildNodes)
				{
					string name = node.InnerText;
					if (string.IsNullOrEmpty(name)) {
						continue;
					}

					TSParameter p = null;
					
					double val = double.NaN;
					double min = double.NaN;
					double max = double.NaN;
					
					try {
						val = double.Parse(node.Attributes[PARAMETER_VAL_ATTR].InnerText);
					} catch (Exception) { }
					try {
						min = double.Parse(node.Attributes[PARAMETER_MIN_ATTR].InnerText);
					} catch (Exception) { }
					try {
						max = double.Parse(node.Attributes[PARAMETER_MAX_ATTR].InnerText);
					} catch (Exception) { }
					
				    p = new TSParameterReal(name, val, min, max);
				    
				    try {
    				    p.BitLength = int.Parse(node.Attributes[PARAMETER_BITS].InnerText);
				    } catch (Exception) { }
				    
					ts.Parameters.Add(name, p);
				}
			}
		}
		
		public static RuleTree ParseRule(string desc) 
		{
			// creates the lexer
			RuleLexer lexer = new RuleLexer(new ANTLRStringStream(desc));
			 
			// creates the parser with a stream of tokens that will be extracted from the lexer to it
			CommonTokenStream tokStream = new CommonTokenStream(lexer);
			RuleParser parser = new RuleParser(tokStream);
			 
			// parse the input string and get the results
			RuleParser.sexpr_return expr = parser.sexpr();		
			
			RuleTree rootRule = null;
			
			IList children = ((CommonTree)(expr.Tree)).Children;
			for (int i = 0; i < children.Count - 1; ++i)
			{
				CommonTree node = (CommonTree) children[i];
				
				if (CompositionRule.IsValid(node.Text)) 
				{
					CompositionRuleNode rule = new CompositionRuleNode(node.Text);
					rootRule = rule;
				} 
				else if (BasicRule.IsValid(node.Text)) 
				{
					BasicRuleNode brNode = new BasicRuleNode(node.Text);
					while (true)
					{
						node = (CommonTree) children[++i];
						if (node.Text == ")") 
						{
							break;
						} 
						else 
						{
							brNode.ParameterNames.Add(node.Text);
						}
					}
					if (rootRule == null) 
					{
						rootRule = brNode;	
					} 
					else 
					{
						((CompositionRuleNode)rootRule).Children.Add(brNode);
					}
				} 
			}			
				        
			return rootRule;	
		}
	}
}
