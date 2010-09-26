/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 19/06/2008
 * Time: 07:12
 */

using System;
using System.IO;

namespace TradingDataCenter
{
    /// <summary>
    /// Description of DataTrimmer.
    /// </summary>
    class DataTrimmer
    {
        internal static void Trim(string fileName)
        {
            string[] lines = File.ReadAllLines(fileName);
            File.Delete(fileName);
            StreamWriter writer = File.AppendText(fileName);
            foreach(string line in lines)
            {
                if (!line.Contains(",2008-"))
                    writer.WriteLine(line);
            }
            writer.Close();
        }
    }
}
