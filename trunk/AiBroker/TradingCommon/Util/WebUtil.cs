using System;
using System.IO;
using System.Net;

namespace TradingCommon.Util
{
    public class WebUtil
    {
        public static string GetWebPage(string url)
        {
            HttpWebRequest httpRequest = (HttpWebRequest)WebRequest.Create(url);
            WebResponse httpResponse = null;
            StreamReader sr = null;
            string page = null;

            try
            {
                httpResponse = httpRequest.GetResponse();
                sr = new StreamReader(httpResponse.GetResponseStream(), System.Text.Encoding.UTF8);
                page = sr.ReadToEnd();
            }
            finally
            {
                if (sr != null) 
                    sr.Close();
                if (httpResponse != null) 
                    httpResponse.Close();
            }
            
            return page;
        }
        
        public static void DownloadFile(string uri, string destinationPath)
        {
            WebClient webClient = new WebClient();
            webClient.DownloadFile(uri, destinationPath);
        }
        
    }
}
