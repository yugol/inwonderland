using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TradingVisualizer.Ui;
using Tao.FreeGlut;

namespace TradingVisualizer
{
    static class Program
    {
        static VisualizerForm mainForm = null;
        internal static VisualizerForm MainForm { get { return mainForm; } }

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Glut.glutInit();
            Glut.glutInitDisplayMode(Glut.GLUT_RGB | Glut.GLUT_SINGLE);
            Glut.glutInitWindowSize(1, 1);
            Glut.glutInitWindowPosition(0, 0);
            Glut.glutCreateWindow("");
            Glut.glutHideWindow();

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            mainForm = new VisualizerForm();
            Application.Run(mainForm);
        }
    }
}
