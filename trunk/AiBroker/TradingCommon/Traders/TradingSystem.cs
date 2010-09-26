/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 11:43 AM
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
using System.IO;
using System.Xml;

using TradingCommon.Traders.BasicRules;

namespace TradingCommon.Traders
{
	public class TradingSystem
	{
		Dictionary<string, TSParameter> parameters = new Dictionary<string, TSParameter>();
		RuleTree ruleTree;
		
		public Dictionary<string, TSParameter> Parameters
		{
			get { return parameters; }
		}
		
		public RuleTree RuleTree
		{
			get { return ruleTree; }
			set { ruleTree = value; }
		}
		
		public void ResetParameters()
		{
		    List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
		    ruleTree.GetBasicRuleNodes(brNodes);
		    foreach (BasicRuleNode brNode in brNodes)
		    {
		        brNode.ParameterNames.Clear();
		    }
		    parameters.Clear();
		    FetchParameters();
		}
				
		public void FetchParameters()
		{
		    if (ruleTree == null) {
		        return;
		    }
		    
		    List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
		    ruleTree.GetBasicRuleNodes(brNodes);
			foreach (BasicRuleNode brNode in brNodes)
			{
			    BasicRule br = brNode.Rule;
				for (int i = 0; i < br.DefaultParameters.Count; ++i) 
				{
					TSParameter defaultParameter = br.DefaultParameters[i];
					
					string usedParameterName = null;
					if (i < brNode.ParameterNames.Count) {
						usedParameterName = brNode.ParameterNames[i];
					} else {
					    usedParameterName = defaultParameter.Name +	"_" + Guid.NewGuid().ToString().Replace("-", "");
						brNode.ParameterNames.Add(usedParameterName);
					}
					
					TSParameter usedParameter = null;
					if (parameters.ContainsKey(usedParameterName)) {
						usedParameter = parameters[usedParameterName];
					}
					
					double usedValue;
					double usedMin;
					double usedMax;
					if (usedParameter == null) {
						usedValue = defaultParameter.Value;
						usedMin = defaultParameter.MinValue;
						usedMax = defaultParameter.MaxValue;
					} else {
					    usedValue = double.IsNaN(usedParameter.Value) ? defaultParameter.Value : usedParameter.Value;
					    usedMin = double.IsNaN(usedParameter.MinValue) ? defaultParameter.MinValue : usedParameter.MinValue;
					    usedMax = double.IsNaN(usedParameter.MaxValue) ? defaultParameter.MaxValue : usedParameter.MaxValue;
					}
					
					if (defaultParameter is TSParameterInt) {
						usedParameter = new TSParameterInt(usedParameterName, usedValue, usedMin, usedMax);
					} else if (defaultParameter is TSParameterReal) {
						usedParameter = new TSParameterReal(usedParameterName, usedValue, usedMin, usedMax);
					}
					
					parameters[usedParameterName] = usedParameter;
				}
				br.SetActualParameters(brNode.ParameterNames, parameters);
			}
		}
		
		public string ToXmlString() 
		{
		    XmlDocument xmlDoc = new XmlDocument();
		    XmlNode tsNode = xmlDoc.CreateNode(XmlNodeType.Element, TSParser.TRADINGSYSTEM_TAG, "");
		    xmlDoc.AppendChild(tsNode);
		    
		    XmlNode psNode = xmlDoc.CreateNode(XmlNodeType.Element, TSParser.PARAMETERS_TAG, "");
		    tsNode.AppendChild(psNode);
		    
		    foreach (string pName in parameters.Keys) 
		    {
		        TSParameter p = parameters[pName];
		        
    		    XmlNode pNode = xmlDoc.CreateNode(XmlNodeType.Element, TSParser.PARAMETER_TAG, "");
    		    
    		    XmlAttribute attr = xmlDoc.CreateAttribute(TSParser.PARAMETER_TYPE_ATTR);
    		    attr.Value = (p is TSParameterInt) ? TSParser.PARAMETER_TYPE_INT : TSParser.PARAMETER_TYPE_REAL;
    		    pNode.Attributes.Append(attr);

    		    attr = xmlDoc.CreateAttribute(TSParser.PARAMETER_VAL_ATTR);
    		    attr.Value = p.Value.ToString();
    		    pNode.Attributes.Append(attr);
		        
    		    attr = xmlDoc.CreateAttribute(TSParser.PARAMETER_MIN_ATTR);
    		    attr.Value = p.MinValue.ToString();
    		    pNode.Attributes.Append(attr);

    		    attr = xmlDoc.CreateAttribute(TSParser.PARAMETER_MAX_ATTR);
    		    attr.Value = p.MaxValue.ToString();
    		    pNode.Attributes.Append(attr);
    		    
    		    attr = xmlDoc.CreateAttribute(TSParser.PARAMETER_BITS);
    		    attr.Value = p.BitLength.ToString();
    		    pNode.Attributes.Append(attr);

    		    pNode.InnerText = p.Name;

    		    psNode.AppendChild(pNode);
		    }
		    
		    XmlNode rNode = xmlDoc.CreateNode(XmlNodeType.Element, TSParser.RULE_TAG, "");
		    if (ruleTree != null) {
		        rNode.InnerText = "\n" + ruleTree.ToString() + "\n";
		    }
		    tsNode.AppendChild(rNode);

		    XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.OmitXmlDeclaration = true;

            StringWriter writer = new StringWriter();
            XmlWriter xmlWriter = XmlWriter.Create(writer, settings);
            xmlDoc.WriteTo(xmlWriter);
            xmlWriter.Close();
            return writer.GetStringBuilder().ToString();
		}
	
		public void UpdateTimeSeries(TimeSeries timeSeries)
		{
		    List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
		    ruleTree.GetBasicRuleNodes(brNodes);
		    foreach (BasicRuleNode brNode in brNodes)
			{
		        brNode.UpdateTimeSeries(timeSeries);
		    }
		}
		
		public Decision Decide(int barIndex, TSStatus status)
		{
		    return ruleTree.Decide(barIndex, status);
		}
		
        public override string ToString()
        {
            return ToXmlString();
        }
		
	}
}
