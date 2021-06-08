package io.chia.configs.utils;

/**
 */
public class StringUtil {
    private static final String DESEN = "****";
    public static String desensitization(String target) {
        // 默认脱敏隐藏4位
        if (target == null || target.length() <= 4) {
            return target;
        } else if (target.length() < 7) {
            return target.substring(0, 2) + DESEN + target.substring(target.length() - 2);
        } else {
            return target.substring(0, 3) + DESEN + target.substring(target.length() - 4);
        }
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
