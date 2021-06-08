package io.chia.configs.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static io.chia.configs.utils.AesPeculiarUtilAssist.*;

public class AesPeculiarUtil {

    /**
     * 是否为uid模式加密后的token串
     * @param token - 加密后token串
     * @return - true表示合法
     */
    public static boolean isValidToken(String token) {
        return isValid(token);
    }

    /**
     * 加密
     * @param orig -- 原始字符串
     * @return
     */
    public static String encode(String orig) throws Exception {
        return encode(orig, null);
    }

    /**
     * 加密
     * @param orig       -- 原始字符串
     * @param password   --
     * @return -
     */
    public static String encode(String orig, String password) throws Exception {
        if (orig == null) {
            return null;
        }
        orig = orig + SIGNBOARD;
        byte[] result = getCodeByte(orig.getBytes(StandardCharsets.UTF_8), password, Cipher.ENCRYPT_MODE);
        return base64Encode(result);
    }


    /**
     * 解密
     * @param orig - 原始字符串
     * @return -
     */
    public static String decode(String orig) throws Exception {
        return decode(orig, null);
    }

    /**
     * 解密
     * @param orig      -- 原始字符串
     * @param salt      -- 盐
     * @return -
     */
    public static String decode(String orig, String salt) throws Exception {
        byte[] content = base64Decode(orig);
        byte[] result = getCodeByte(content, salt, Cipher.DECRYPT_MODE);
        String s = new String(result, StandardCharsets.UTF_8);
        if (!s.endsWith(SIGNBOARD)) {
            throw new Exception("decode fail");
        }
        return s.substring(0, s.length() - SIGNBOARD.length());
    }

    /**
     * 统一处理
     * @param content  -- 字符串
     * @param salt  -- 盐
     * @param mode  -- 模式 : 加密、解密
     * @return -
     */
    private static byte[] getCodeByte(byte[] content, String salt, int mode) throws Exception {
        try {
            IvParameterSpec spec = new IvParameterSpec(IV_KEY.getBytes());
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            SecretKeySpec key = new SecretKeySpec(getSalt(salt), AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, key, spec);
            return cipher.doFinal(content);
        } catch (Throwable t) {
            throw new Exception("KEY_INVALID");
        }
    }

}
