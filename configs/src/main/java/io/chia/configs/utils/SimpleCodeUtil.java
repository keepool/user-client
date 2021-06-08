package io.chia.configs.utils;


import java.util.Random;
import java.util.UUID;

/**
 */
public class SimpleCodeUtil {
    private static final char[] CODES = "lm9ab1c3def6ghi7jk5no0pq8rst4uvw2xyz".toCharArray();
    private static final int length = CODES.length;
    private static final char[] ALL_CODES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int ALL_LENGTH = ALL_CODES.length;
    private static final Random random = new Random();

    /**
     * 邀请码以及层级生成
     * @param value -
     * @return -
     */
    public static String get(long value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(CODES[(int)(value % length)]);
            value = value / length;
        }
        return sb.reverse().toString();
    }

    public static String getVerifyCode(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public static String getSimpleCode(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(ALL_CODES[random.nextInt(ALL_LENGTH)]);
        }
        return builder.toString();
    }

    public static String getApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
