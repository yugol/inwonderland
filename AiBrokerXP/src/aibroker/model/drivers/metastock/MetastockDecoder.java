package aibroker.model.drivers.metastock;

import aibroker.util.ByteCodec;
import aibroker.util.Moment;

class MetastockDecoder {

    static byte readByte(final byte[] buffer, final int offset) {
        return buffer[offset];
    }

    static char readByteAsChar(final byte[] buffer, final int offset) {
        return (char) buffer[offset];
    }

    static short readByteAsShort(final byte[] buffer, final int offset) {
        final byte val = buffer[offset];
        return (short) (val < 0 ? val + 256 : val);
    }

    static int readLittleEndianShortAsInt(final byte[] buffer, int offset) {
        final int b0 = buffer[offset++] & 0x0ff;
        final int b1 = buffer[offset++] & 0x0ff;
        return b1 << 8 | b0;
    }

    static float readMsFloat(final byte[] buffer, final int offset) {
        final int msf = ByteCodec.readLittleEndianInteger(buffer, offset);
        int val = msf & 0x007fffff;
        final int exp = (msf >> 24 & 0x0ff) - 2;
        final int sig = msf >> 16 & 0x080;
        val |= exp << 23;
        val |= sig << 24;
        return Float.intBitsToFloat(val);
    }

    static Moment readMsMoment(final byte[] buffer, final int offset) {
        int date = (int) readMsFloat(buffer, offset);
        final int day = date % 100;
        date /= 100;
        final int month = date % 100 - 1;
        date /= 100;
        final int year = 1900 + date;
        return new Moment(year, month, day);
    }

}
