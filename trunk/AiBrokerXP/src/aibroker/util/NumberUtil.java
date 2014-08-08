package aibroker.util;

public class NumberUtil {

    public static double parseDoubleRo(final String str) {
        return Double.parseDouble(normalizeString(str));
    }

    public static float parseFloatRo(final String str) {
        return Float.parseFloat(normalizeString(str));
    }

    public static int parseInt(String str) {
        if (str == null) { return 0; }
        str = str.trim();
        if (str.length() == 0) { return 0; }
        str = str.replace(".", "");
        return Integer.parseInt(str);
    }

    private static String normalizeString(String str) {
        if (str == null) { return "0"; }
        str = str.trim();
        if (str.length() == 0) { return "0"; }
        str = str.replace(".", "");
        str = str.replace(",", ".");
        return str;
    }

}
