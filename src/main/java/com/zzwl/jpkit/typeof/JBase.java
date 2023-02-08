package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.ITypeof;

public abstract class JBase implements ITypeof<Object> {
    /**
     * 判断是否为基础数据类型的包装类型
     *
     * @param o 数据
     * @return 是否为 Integer Boolean Byte Long Short Double Float...
     */
    public static boolean isBase(Object o) {
        return o.getClass().getTypeName().startsWith("java.math") || o.getClass().getTypeName().startsWith("java.util") || o.getClass().getTypeName().startsWith("java.lang");
    }

    /**
     * 判断是否为基础数据类型的包装类型
     *
     * @param o 数据
     * @return 是否为 Integer Boolean Byte Long Short Double Float...
     */
    public static boolean isBase(Class<?> o) {
        return o.getTypeName().startsWith("java.math") || o.getTypeName().startsWith("java.util") || o.getTypeName().startsWith("java.lang");
    }

    /**
     * 为基础类型赋初值
     *
     * @param clazz 类型
     * @return 初值
     */
    public static Object getBaseValue(Class<?> clazz) {
        if (clazz.equals(long.class)) {
            return Long.MIN_VALUE;
        }
        if (clazz.equals(short.class)) {
            return Short.MIN_VALUE;
        }
        if (clazz.equals(byte.class)) {
            return Byte.MIN_VALUE;
        }
        if (clazz.equals(char.class)) {
            return Character.MIN_VALUE;
        }
        if (clazz.equals(boolean.class)) {
            return Boolean.FALSE;
        }
        if (clazz.equals(int.class)) {
            return Integer.MIN_VALUE;
        }
        if (clazz.equals(float.class)) {
            return Float.MIN_VALUE;
        }
        if (clazz.equals(double.class)) {
            return Double.MIN_VALUE;
        }
        return null;
    }
}
