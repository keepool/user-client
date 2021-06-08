package io.chia.configs.utils;




import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AesPeculiarUtilAssist {
    /**
     * base64编码
     */
    private static final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * base64解码
     */
    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 填充字符
     */
    private static final char[] BASE64_PADDING;
    private static final int BASE64_PADDING_LENGTH;

    /**
     * 加密方式
     */
    static final String AES = "AES";

    /**
     * 需要16位长度
     */
    static final String IV_KEY = "sign_xxxx_iv_xxx";

    /**
     * 加盐前缀,
     */
    private static final Map<Integer, String> PADDING = new HashMap<>();

    /**
     * 校验标识
     */
    static final String SIGNBOARD = "_d_signboard";

    static {
        //填充padding
        PADDING.put(16, "");
        PADDING.put(15, "0");
        PADDING.put(14, "01");
        PADDING.put(13, "012");
        PADDING.put(12, "0123");
        PADDING.put(11, "01234");
        PADDING.put(10, "012345");
        PADDING.put(9, "0123456");
        PADDING.put(8, "01234567");
        PADDING.put(7, "012345678");
        PADDING.put(6, "0123456789");
        PADDING.put(5, "01234567890");
        PADDING.put(4, "012345678901");
        PADDING.put(3, "0123456789012");
        PADDING.put(2, "01234567890123");
        PADDING.put(1, "012345678901234");
        PADDING.put(0, "0123456789012345");

        byte[] base64Padding = new byte[9];
        base64Padding[0] = (byte) 47;
        base64Padding[1] = (byte) -88;
        base64Padding[2] = (byte) 44;
        base64Padding[3] = (byte) 69;
        base64Padding[4] = (byte) -24;
        base64Padding[5] = (byte) -79;
        base64Padding[6] = (byte) 106;
        base64Padding[7] = (byte) -18;
        base64Padding[8] = (byte) 23;
        BASE64_PADDING = encoder.encodeToString(base64Padding).toCharArray();
        BASE64_PADDING_LENGTH = BASE64_PADDING.length;

    }

    /**
     * 获取盐
     *
     * @param salt - 盐
     * @return -
     */
    static byte[] getSalt(String salt) {
        String result = PADDING.get(0);
        if (StringUtil.hasText(salt)) {
            int length = salt.length();
            if (length <= 16) {
                result = PADDING.get(length) + salt;
            } else {
                result = salt.substring(0, 16);
            }
        }
        return result.getBytes();
    }

    /**
     * 编码后填充
     *
     * @param target -
     * @return
     */
    static String base64Encode(byte[] target) {
        //这里加密出来的orig最少长度为16，所以可以和PADDING进行交叉插入
        String orig = encoder.encodeToString(target);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BASE64_PADDING_LENGTH; i++) {
            sb.append(BASE64_PADDING[i]).append(orig.charAt(i));
        }
        return sb.append(orig.substring(BASE64_PADDING_LENGTH)).toString();
    }

    /**
     * 去填充后解码
     *
     * @param target -
     * @return
     */
    static byte[] base64Decode(String target) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BASE64_PADDING_LENGTH; i++) {
            sb.append(target.charAt(2 * i + 1));
        }
        sb.append(target.substring(2 * BASE64_PADDING_LENGTH));
        return decoder.decode(sb.toString());
    }

    /**
     * 是否为合法的字符,包含BASE64_PADDING
     *
     * @param target - 源字符
     * @return
     */
    static boolean isValid(String target) {
        if (target != null && target.length() >= 2 * BASE64_PADDING_LENGTH) {
            for (int i = 0; i < BASE64_PADDING_LENGTH; i++) {
                if (BASE64_PADDING[i] != target.charAt(2 * i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
