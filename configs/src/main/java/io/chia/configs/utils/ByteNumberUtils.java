package io.chia.configs.utils;

public abstract class ByteNumberUtils {
    /**
     * 将int装换成byte数组
     * @param v
     * @return
     */
    public static byte[] int2bytes(int v) {
        byte[] targets = new byte[4];
        int2bytes(v, targets);
        return targets;
    }

    public static void int2bytes(int v, byte[] targets) {
        int2bytes(v, targets, 0);
    }

    public static void int2bytes(int v, byte[] targets, int offset) {
        targets[offset + 3] = (byte) (v & 0xFF);
        targets[offset + 2] = (byte) (v >> 8 & 0xFF);
        targets[offset + 1] = (byte) (v >> 16 & 0xFF);
        targets[offset] = (byte) (v >> 24 & 0xFF);
    }

    /**
     * 将byte数组转换成int
     * @param bts
     * @return
     */
    public static int byte2int(byte[] bts) {
        return byte2int(bts, 0);
    }

    public static int byte2int(byte[] bts, int offset) {
        int b0 = bts[offset] & 0xFF;
        int b1 = bts[offset + 1] & 0xFF;
        int b2 = bts[offset + 2] & 0xFF;
        int b3 = bts[offset + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }
}
