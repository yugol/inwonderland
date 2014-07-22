package aibroker.model.drivers.amibroker;

public class BitUtil {

    public static byte apply(final int target, int mask, final boolean set) {
        if (set) { return (byte) (target | mask); }
        mask = ~mask;
        return (byte) (target & mask);
    }

}
