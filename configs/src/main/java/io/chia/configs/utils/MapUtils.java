package io.chia.configs.utils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author XXX
 */
public class MapUtils {
    private static Method MAP_GET;
    private static Method MAP_SET;
    static {
        try {
            MAP_GET = Map.class.getDeclaredMethod("get", Object.class);
            MAP_SET = Map.class.getDeclaredMethod("put", Object.class, Object.class);
        } catch (NoSuchMethodException e) {
            // skip
        }
    }

    public static Object invokeGet(Object obj, Object key) {
        try {
            return MAP_GET.invoke(obj, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void invokePut(Object obj, Object key, Object value) {
        try {
            MAP_SET.invoke(obj, key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
