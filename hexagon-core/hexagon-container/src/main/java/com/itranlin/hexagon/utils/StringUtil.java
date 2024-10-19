package com.itranlin.hexagon.utils;

/**
 * String util.
 */
public class StringUtil {
    /**
     * Is empty.
     *
     * @param value 值
     * @return 是否为空
     */
    public static boolean isEmpty(String value) {
        return null == value || value.isEmpty() || value.trim().isEmpty();
    }
}
