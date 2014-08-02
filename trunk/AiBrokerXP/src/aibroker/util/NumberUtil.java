package aibroker.util;

public class NumberUtil {

    public static float parseFloat(String str) {
        if (str == null) { return 0; }
        str = str.trim();
        if (str.length() == 0) { return 0; }
        str = str.replace(".", "");
        str = str.replace(",", ".");
        return Float.parseFloat(str);
    }

    public static int parseInt(String str) {
        if (str == null) { return 0; }
        str = str.trim();
        if (str.length() == 0) { return 0; }
        str = str.replace(".", "");
        return Integer.parseInt(str);
    }

}
