using System;
using System.Drawing;
using Tao.OpenGl;
using TradingCommon.Indicators;

namespace TradingVisualizer.Charting
{
    class DeltaGrid
    {
        internal static void Paint(DateTime[] dateTimes, int first, int last, Delta.Term deltaTerm)
        {
            Gl.glLoadIdentity();
            Glu.gluOrtho2D(first - VisualElement.ENTRY_HALF_WIDTH, last + VisualElement.ENTRY_HALF_WIDTH, 0, 1);
            Gl.glPolygonMode(Gl.GL_FRONT_AND_BACK, Gl.GL_FILL);

            Gl.glBegin(Gl.GL_QUADS);
            for (int x = first; x <= last; ++x)
            {
                Color col = Delta.Colors[Delta.TermColor(deltaTerm, dateTimes[x])];
                Gl.glColor3d(col.R / 255D, col.G / 255D, col.B / 255D);

                Gl.glVertex2d(x - VisualElement.ENTRY_HALF_WIDTH, 0);
                Gl.glVertex2d(x + VisualElement.ENTRY_HALF_WIDTH, 0);
                Gl.glVertex2d(x + VisualElement.ENTRY_HALF_WIDTH, 1);
                Gl.glVertex2d(x - VisualElement.ENTRY_HALF_WIDTH, 1);
            }
            Gl.glEnd();
        }
    }
}
