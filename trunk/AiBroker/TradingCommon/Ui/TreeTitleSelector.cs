/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/26/2009
 * Time: 10:19 PM
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
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

using TradingCommon;
using TradingCommon.Storage;

namespace TradingCommon.Ui
{
    public delegate void TitleSelectedHandler(object sender, Title selectedTitle);

    public partial class TreeTitleSelector : UserControl
    {
        public event TitleSelectedHandler TitleSelected;
        
        public TreeTitleSelector()
        {
            InitializeComponent();
        }
        
        void TreeTitleSelectorLoad(object sender, EventArgs e)
        {
        	LoadSymbols();
        }
        
        void LoadSymbols()
        {
            titleTree.BeginUpdate();
            foreach (string symbol in DataUtil.GetSymbols(DataUtil.TICKS_SELECTION | DataUtil.EOD_SELECTION))
            {
                TreeNode symbolNode = new TreeNode(symbol, 0, 1);
                titleTree.Nodes.Add(symbolNode);
                foreach (string maturity in DataUtil.GetMaturities(symbol, DataUtil.TICKS_SELECTION | DataUtil.EOD_SELECTION))
                {
                    TreeNode maturityNode = new TreeNode(maturity, 0, 1);
                    symbolNode.Nodes.Add(maturityNode);
                }
                if (symbolNode.Nodes.Count > 0)
                {
                    symbolNode.ImageIndex = 8;
                    symbolNode.SelectedImageIndex = 9;
                }
            }
            titleTree.EndUpdate();
        }
        
        
        void TitleTreeAfterSelect(object sender, TreeViewEventArgs e)
        {
            Title title = null;

            if (e.Node.Nodes.Count == 0)
            {
                if (e.Node.Level == 0) {
                    title = new Title(e.Node.Text);
                }
                else {
                    title = new Title(e.Node.Parent.Text, e.Node.Text);
                }
            }

            if ((title != null) && (TitleSelected != null)) {
                TitleSelected(this, title);
            }
        }
        
        
        
    }
}
