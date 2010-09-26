using System;
using System.Collections.Generic;
using System.Text;

namespace TradingCommon.Util
{
    public static class DTUtil
    {
        public static DateTime ParseDate(String data)
        {
            int year = int.Parse(data.Substring(0, 4));
            int month = int.Parse(data.Substring(5, 2));
            int dayOfMonth = int.Parse(data.Substring(8, 2));
            return new DateTime(year, month, dayOfMonth, 0, 0, 0);
        }

        public static DateTime ParseTime(String data)
        {
            int hour = int.Parse(data.Substring(0, 2));
            int minute = int.Parse(data.Substring(3, 2));
            int second = int.Parse(data.Substring(6, 2));
            return new DateTime(1, 1, 1, hour, minute, second);
        }

        public static DateTime ParseDateTime(String data)
        {
            int year = int.Parse(data.Substring(0, 4));
            int month = int.Parse(data.Substring(5, 2));
            int dayOfMonth = int.Parse(data.Substring(8, 2));
            int hour = int.Parse(data.Substring(11, 2));
            int minute = int.Parse(data.Substring(14, 2));
            int second = int.Parse(data.Substring(17, 2));
            return new DateTime(year, month, dayOfMonth, hour, minute, second);
        }

        public static string ToDateString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(dt.Year);
            sb.Append((dt.Month < 10) ? "-0" : "-");
            sb.Append(dt.Month);
            sb.Append((dt.Day < 10) ? "-0" : "-");
            sb.Append(dt.Day);
            return sb.ToString();
        }

        public static string ToTimeString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append((dt.Hour < 10) ? "0" : "");
            sb.Append(dt.Hour);
            sb.Append((dt.Minute < 10) ? ":0" : ":");
            sb.Append(dt.Minute);
            sb.Append((dt.Second < 10) ? ":0" : ":");
            sb.Append(dt.Second);
            return sb.ToString();
        }

        public static string ToDateTimeString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder(ToDateString(dt));
            sb.Append(" ");
            sb.Append(ToTimeString(dt));
            return sb.ToString();
        }

        public static string ToCDateTimeString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder(ToCDateString(dt));
            sb.Append(ToCTimeString(dt));
            return sb.ToString();
        }

        public static string ToCTimeString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder();
            if (dt.Hour < 10) sb.Append("0");
            sb.Append(dt.Hour);
            if (dt.Minute < 10) sb.Append("0");
            sb.Append(dt.Minute);
            if (dt.Second < 10) sb.Append("0");
            sb.Append(dt.Second);
            return sb.ToString();
        }

        public static string ToCDateString(DateTime dt)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(dt.Year);
            if (dt.Month < 10) sb.Append("0");
            sb.Append(dt.Month);
            if (dt.Day < 10) sb.Append("0");
            sb.Append(dt.Day);
            return sb.ToString();
        }

        internal static DateTime ParseCDateTime(string data)
        {
            int year = int.Parse(data.Substring(0, 4));
            int month = int.Parse(data.Substring(4, 2));
            int dayOfMonth = int.Parse(data.Substring(6, 2));
            int hour = int.Parse(data.Substring(8, 2));
            int minute = int.Parse(data.Substring(10, 2));
            int second = int.Parse(data.Substring(12, 2));
            return new DateTime(year, month, dayOfMonth, hour, minute, second);
        }

        public static DateTime ParseDmyTime(string data)
        {
            int year = int.Parse(data.Substring(6, 4));
            int month = int.Parse(data.Substring(3, 2));
            int dayOfMonth = int.Parse(data.Substring(0, 2));
            int hour = int.Parse(data.Substring(11, 2));
            int minute = int.Parse(data.Substring(14, 2));
            int second = int.Parse(data.Substring(17, 2));
            return new DateTime(year, month, dayOfMonth, hour, minute, second);
        }

        public static DateTime GetContractExpirationDateFor(DateTime date)
        {
            int year = date.Year;
            int month = (int)(Math.Ceiling(date.Month / 3.0) * 3);
            int day = 31;
            if (month == 6 || month == 9)
                day = 30;
            return new DateTime(year, month, day);
        }

        public static string Date2Maturity(DateTime date)
        {
            DateTime expirationDate = GetContractExpirationDateFor(date);
            StringBuilder sb = new StringBuilder();
            switch (expirationDate.Month)
            {
                case 1: sb.Append("IAN"); break;
                case 2: sb.Append("FEB"); break;
                case 3: sb.Append("MAR"); break;
                case 4: sb.Append("APR"); break;
                case 5: sb.Append("MAI"); break;
                case 6: sb.Append("IUN"); break;
                case 7: sb.Append("IUL"); break;
                case 8: sb.Append("AUG"); break;
                case 9: sb.Append("SEP"); break;
                case 10: sb.Append("OCT"); break;
                case 11: sb.Append("NOV"); break;
                case 12: sb.Append("DEC"); break;
            }
            sb.Append(expirationDate.Year.ToString().Substring(2, 2));
            return sb.ToString();
        }  

        public static string CurrentMaturity()
        {
            return Date2Maturity(DateTime.Now);
        }

        public static DateTime Maturity2Date(string maturity)
        {
            int year = 2000 + int.Parse(maturity.Substring(3, 2));
            int month, day;
            
            string m = maturity.Substring(0, 3).ToUpper();
            if (m.Equals("IAN"))
            {
                month = 1;
                day = 31;
            }
            else if (m.Equals("FEB"))
            {
                month = 2;
                day = IsLeap(year) ? 29 : 28;
            }
            else if (m.Equals("MAR"))
            {
                month = 3;
                day = 31;
            }
            else if (m.Equals("APR"))
            {
                month = 4;
                day = 30;
            }
            else if (m.Equals("MAI"))
            {
                month = 5;
                day = 31;
            }
            else if (m.Equals("IUN"))
            {
                month = 6;
                day = 30;
            }
            else if (m.Equals("IUL"))
            {
                month = 7;
                day = 31;
            }
            else if (m.Equals("AUG"))
            {
                month = 8;
                day = 31;
            }
            else if (m.Equals("SEP"))
            {
                month = 9;
                day = 30;
            }
            else if (m.Equals("OCT"))
            {
                month = 10;
                day = 31;
            }
            else if (m.Equals("NOV"))
            {
                month = 11;
                day = 30;
            }
            else if (m.Equals("DEC"))
            {
                month = 12;
                day = 31;
            }
            else
                month = day = int.MaxValue;
            
            return new DateTime(year, month, day);
        }
        
        public static bool IsLeap(int year)
        {
            if ((year % 4) != 0) return false;
            if ((year % 400) == 0) return true;
            if ((year % 100) == 0) return false;
            return true;
        }        
    }
}
