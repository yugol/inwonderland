package aibroker.model.drivers.amibroker;

import java.util.Calendar;
import aibroker.util.ByteCodec;
import aibroker.util.Moment;

class AmibrokerCodec extends ByteCodec {

    static float readBigEndianFloat(final byte[] buffer, final int offset) {
        final int val = readBigEndianInteger(buffer, offset);
        return Float.intBitsToFloat(val);
    }

    static int readBigEndianInteger(final byte[] buffer, int offset) {
        final int b0 = buffer[offset++] & 0x0ff;
        final int b1 = buffer[offset++] & 0x0ff;
        final int b2 = buffer[offset++] & 0x0ff;
        final int b3 = buffer[offset] & 0x0ff;
        final int i = b0 << 24 | b1 << 16 | b2 << 8 | b3;
        return i;
    }

    static float readLittleEndianFloat(final byte[] buffer, final int offset) {
        final int val = readLittleEndianInteger(buffer, offset);
        return Float.intBitsToFloat(val);
    }

    static Moment readMoment(final byte[] buffer, final int offset) {
        int timeStamp = readBigEndianInteger(buffer, offset);
        final int second = (timeStamp & 0xF) * 5;
        timeStamp >>>= 4;
        final int minute = timeStamp & 0x3F;
        timeStamp >>>= 6;
        final int hourOfDay = timeStamp & 0x1F;
        timeStamp >>>= 5;
        final int dayOfMonth = timeStamp & 0x1F;
        timeStamp >>>= 5;
        final int month = (timeStamp & 0xF) - 1;
        final int year = 1900 + (timeStamp >> 4);
        return new Moment(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    static void writeBigEndianFloat(final byte[] buffer, final int offset, final float data) {
        final int tmp = Float.floatToIntBits(data);
        writeBigEndianInteger(buffer, offset, tmp);
    }

    static void writeBigEndianInteger(final byte[] buffer, int offset, final int data) {
        buffer[offset++] = (byte) ((data & 0xFF000000) >>> 24);
        buffer[offset++] = (byte) ((data & 0xFF0000) >>> 16);
        buffer[offset++] = (byte) ((data & 0xFF00) >>> 8);
        buffer[offset] = (byte) (data & 0xFF);
    }

    static void writeBytes(final byte[] buffer, final int offset, final int length, final byte[] data) {
        for (int i = 0; i < length; ++i) {
            byte tmp = 0;
            if (i < data.length) {
                tmp = data[i];
            }
            buffer[offset + i] = tmp;
        }
    }

    static void writeIntAsByte(final byte[] buffer, final int offset, final int data) {
        buffer[offset] = (byte) data;
    }

    static void writeLittleEndianFloat(final byte[] buffer, final int offset, final float data) {
        final int tmp = Float.floatToIntBits(data);
        writeLittleEndianInteger(buffer, offset, tmp);
    }

    static void writeLittleEndianInteger(final byte[] buffer, int offset, final int data) {
        buffer[offset++] = (byte) (data & 0xFF);
        buffer[offset++] = (byte) ((data & 0xFF00) >>> 8);
        buffer[offset++] = (byte) ((data & 0xFF0000) >>> 16);
        buffer[offset] = (byte) ((data & 0xFF000000) >>> 24);
    }

    static void writeMoment(final byte[] buffer, final int offset, final Moment data) {
        int timeStamp = data.get(Calendar.YEAR) - 1900;
        timeStamp <<= 4;
        timeStamp += data.get(Calendar.MONTH) + 1;
        timeStamp <<= 5;
        timeStamp += data.get(Calendar.DAY_OF_MONTH);
        timeStamp <<= 5;
        timeStamp += data.get(Calendar.HOUR_OF_DAY);
        timeStamp <<= 6;
        timeStamp += data.get(Calendar.MINUTE);
        timeStamp <<= 4;
        timeStamp += data.get(Calendar.SECOND) / 5;
        writeBigEndianInteger(buffer, offset, timeStamp);
    }

    static void writeString(final byte[] buffer, final int offset, final int length, final String data) {
        for (int i = 0; i < length; ++i) {
            byte tmp = 0;
            if (i < data.length()) {
                tmp = (byte) data.charAt(i);
            }
            buffer[offset + i] = tmp;
        }
    }

}
