package aibroker.util;

public class StringUtil {

    public static boolean isNullOrBlank(String str) {
        if (str == null) { return true; }
        str = str.trim();
        return str.isEmpty();
    }

}
