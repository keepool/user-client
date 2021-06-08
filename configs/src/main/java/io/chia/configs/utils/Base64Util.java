package io.chia.configs.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
    private final static Base64.Decoder decoder = Base64.getDecoder();
    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Charset UTF8 = StandardCharsets.UTF_8;

    public static String encode(String source) {
        if (source == null) {
            return null;
        }
        return encoder.encodeToString(source.getBytes(UTF8));
    }

    public static byte[] decode(String source) {
        if (source == null) {
            return null;
        }
        return decoder.decode(source.getBytes());
    }

    public static String decode2String(String source) {
        if (source == null) {
            return null;
        }
        return new String(decode(source), UTF8);
    }
}
