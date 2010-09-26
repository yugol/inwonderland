using System;
using Tao.OpenGl;
using TradingVisualizer.Ui;

namespace TradingVisualizer.Charting
{
    public abstract class VisualElement
    {
        public const double ENTRY_HALF_WIDTH = 0.5;

        int id;
        public int Id { get { return id; } }

        int dataId;
        public int DataId { get { return dataId; } set { dataId = value; } }

        protected MultiChartFrame parentFrame;     // to be deleted

        protected VisualElement(int id, int dataId, MultiChartFrame parentFrame)
        {
            this.id = id;
            this.dataId = dataId;
            this.parentFrame = parentFrame;
        }

        abstract internal void GetMinMax(int first, int last, out double min, out double max);

        virtual internal void Paint(int first, int last, double min, double max)
        {
            Glu.gluOrtho2D(first - ENTRY_HALF_WIDTH, last + ENTRY_HALF_WIDTH, min, max);
            Gl.glDisable(Gl.GL_LINE_STIPPLE);
        }
    }
}
