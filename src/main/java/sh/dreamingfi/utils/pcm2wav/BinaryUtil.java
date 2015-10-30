package sh.dreamingfi.utils.pcm2wav;

public class BinaryUtil {

    public static int byte2int_little(byte[] source, int offset) {
        int ret = source[offset + 0] & 0xff;
        ret += (source[offset + 1] & 0xff) << 8;
        ret += (source[offset + 2] & 0xff) << 16;
        ret += (source[offset + 3] & 0xff) << 24;
        return ret;
    }

    public static void int2byte_little(int source, byte[] bytes, int offset) {
        bytes[offset + 0] = (byte) (source & 0xff);
        bytes[offset + 1] = (byte) ((source >> 8) & 0xff);
        bytes[offset + 2] = (byte) ((source >> 16) & 0xff);
        bytes[offset + 3] = (byte) ((source >> 24) & 0xff);
    }

    public static short byte2short_little(byte[] source, int offset) {
        short ret = (short) (source[offset + 0] & 0xff);
        ret += (source[offset + 1] & 0xff) << 8;
        return ret;
    }

    public static void short2byte_little(short source, byte[] bytes, int offset) {
        bytes[offset + 0] = (byte) (source & 0xff);
        bytes[offset+1] = (byte) ((source >> 8) & 0xff);
    }
}
