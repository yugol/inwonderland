package aibroker.util;

public class ByteCodec {

    public static int readLittleEndianInteger(final byte[] buffer, int offset) {
        final int b0 = buffer[offset++] & 0x0ff;
        final int b1 = buffer[offset++] & 0x0ff;
        final int b2 = buffer[offset++] & 0x0ff;
        final int b3 = buffer[offset] & 0x0ff;
        final int i = b3 << 24 | b2 << 16 | b1 << 8 | b0;
        return i;
    }

    public static String readString(final byte[] buffer, final int offset, final int maxLen) {
        return new String(buffer, offset, maxLen).trim();
    }

    public static int readUnsignedByte(final byte[] buffer, final int offset) {
        final byte val = buffer[offset];
        return val < 0 ? val + 256 : val;
    }

}
