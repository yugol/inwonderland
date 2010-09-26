using System;
using System.IO;
using System.Xml;
using TradingCommon;
using TradingCommon.Indicators;
using TradingCommon.Storage;
using TradingCommon.Util;

namespace TradingVisualizer.Charting
{
    public class ChartLayout
    {
        static readonly string CHART = "chart";
        static readonly string PERIOD = "period";
        static readonly string LAST_DATE_TIME = "lastDateTime";
        static readonly string ZOOM = "zoom";
        static readonly string DELTA_TERM = "deltaTerm";
        static readonly string FREE_ID = "freeId";

        static readonly string WINDOW = "window";
        static readonly string RELATIVE_SIZE = "relativeSize";

        static readonly string VISUAL_ELEMENT = "visualElement";
        public static readonly string ID = "id";
        public static readonly string TYPE = "type";

        string fileName = null;
        XmlDocument layoutXml = new XmlDocument();

        public int Period
        {
            get { return int.Parse(layoutXml.ChildNodes[0].Attributes[PERIOD].Value); }
            set { layoutXml.ChildNodes[0].Attributes[PERIOD].Value = value.ToString(); }
        }

        public DateTime LastDateTime
        {
            get { return DTUtil.ParseDateTime(layoutXml.ChildNodes[0].Attributes[LAST_DATE_TIME].Value); }
            set { layoutXml.ChildNodes[0].Attributes[LAST_DATE_TIME].Value = DTUtil.ToDateTimeString(value); }
        }

        public int Zoom
        {
            get { return int.Parse(layoutXml.ChildNodes[0].Attributes[ZOOM].Value); }
            set { layoutXml.ChildNodes[0].Attributes[ZOOM].Value = value.ToString(); }
        }

        public Delta.Term DeltaTerm
        {
            get { return (Delta.Term)Enum.Parse(typeof(Delta.Term), layoutXml.ChildNodes[0].Attributes[DELTA_TERM].Value); }
            set { layoutXml.ChildNodes[0].Attributes[DELTA_TERM].Value = value.ToString(); }
        }

        private int FreeId
        {
            get { return int.Parse(layoutXml.ChildNodes[0].Attributes[FREE_ID].Value); }
            set { layoutXml.ChildNodes[0].Attributes[FREE_ID].Value = value.ToString(); }
        }

        public float[] WindowSplits
        {
            get
            {
                float[] splits = new float[layoutXml.ChildNodes[0].ChildNodes.Count];
                for (int i = 0; i < layoutXml.ChildNodes[0].ChildNodes.Count; ++i)
                    splits[i] = float.Parse(layoutXml.ChildNodes[0].ChildNodes[i].Attributes[RELATIVE_SIZE].Value);
                return splits;
            }
            set
            {
            }
        }

        public XmlNodeList GetVisualElements(int windowIndex)
        {
            return layoutXml.ChildNodes[0].ChildNodes[windowIndex].ChildNodes;
        }

        public ChartLayout(string symbol)
        {
            if (symbol == null)
                throw new NotImplementedException();

            fileName = BuildChartLayoutFileName(symbol);

            if (File.Exists(fileName))
            {
                layoutXml.Load(fileName);
            }
            else
            {
                XmlAttribute attr;
                XmlNode parent = layoutXml;
                XmlNode child = layoutXml.CreateNode(XmlNodeType.Element, CHART, "");
                parent.AppendChild(child);

                parent = child;

                attr = layoutXml.CreateAttribute(PERIOD);
                attr.Value = TimeSeries.DAY_PERIOD.ToString();
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(LAST_DATE_TIME);
                attr.Value = DTUtil.ToDateTimeString(DateTime.MaxValue);
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(ZOOM);
                attr.Value = "-1";
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(DELTA_TERM);
                attr.Value = Delta.Term.NONE.ToString();
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(FREE_ID);
                attr.Value = "10";
                parent.Attributes.Append(attr);

                child = layoutXml.CreateNode(XmlNodeType.Element, WINDOW, "");
                parent.AppendChild(child);

                parent = child;

                attr = layoutXml.CreateAttribute(RELATIVE_SIZE);
                attr.Value = "3";
                parent.Attributes.Append(attr);

                child = layoutXml.CreateNode(XmlNodeType.Element, VISUAL_ELEMENT, "");
                parent.AppendChild(child);

                parent = child;

                attr = layoutXml.CreateAttribute(ID);
                attr.Value = VisualizerData.DATETIMES_ID.ToString();
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(TYPE);
                attr.Value = typeof(VeOhlc).FullName;
                parent.Attributes.Append(attr);

                parent = child.ParentNode.ParentNode;

                child = layoutXml.CreateNode(XmlNodeType.Element, WINDOW, "");
                parent.AppendChild(child);

                parent = child;

                attr = layoutXml.CreateAttribute(RELATIVE_SIZE);
                attr.Value = "1";
                parent.Attributes.Append(attr);

                child = layoutXml.CreateNode(XmlNodeType.Element, VISUAL_ELEMENT, "");
                parent.AppendChild(child);

                parent = child;

                attr = layoutXml.CreateAttribute(ID);
                attr.Value = VisualizerData.VOLUMES_ID.ToString();
                parent.Attributes.Append(attr);

                attr = layoutXml.CreateAttribute(TYPE);
                attr.Value = typeof(VeVolume).FullName;
                parent.Attributes.Append(attr);
            }
        }

        private string BuildChartLayoutFileName(string symbol)
        {
            symbol = DataUtil.NormalizeSymbol(symbol);
            return (Configuration.LAYOUT_FOLDER + "\\" + symbol + ".layout");
        }

        public void Save()
        {
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.OmitXmlDeclaration = true;

            StreamWriter writer = new StreamWriter(fileName);
            XmlWriter xmlWriter = XmlWriter.Create(writer, settings);
            layoutXml.WriteTo(xmlWriter);
            xmlWriter.Close();
            writer.Dispose();
        }
    }
}
