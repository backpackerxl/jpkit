package cn.zzwl.jpkit.utils;

import cn.zzwl.jpkit.core.JSON;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ArrayUtil {
    private ArrayUtil() {
    }

    public static boolean isArray(Object obj) {
        return obj instanceof int[] || obj instanceof Object[] || obj instanceof long[] || obj instanceof byte[] || obj instanceof char[] || obj instanceof boolean[] || obj instanceof short[] || obj instanceof double[] || obj instanceof float[];
    }

    public static boolean isArray(Field field) {
        return field.getType().getTypeName().contains("[]");
    }

    public static boolean isBasicArray(Field field) {
        return field.getType().getTypeName().contains("[]") && !field.getType().getTypeName().contains("java.lang") && !field.getType().getTypeName().contains("java.util");
    }

    /**
     * 处理数组的情况
     *
     * @param o 将数组值转化为JSON值
     * @return JSON字符串值
     */
    public static String compileArray(Object o) {
        if (o instanceof int[]) {
            return Arrays.toString((int[]) o);
        } else if (o instanceof String[]) {
            return buildArray((String[]) o);
        } else if (o instanceof char[]) {
            return buildArray((char[]) o);
        } else if (o instanceof long[]) {
            return Arrays.toString((long[]) o);
        } else if (o instanceof byte[]) {
            return Arrays.toString((byte[]) o);
        } else if (o instanceof short[]) {
            return Arrays.toString((short[]) o);
        } else if (o instanceof boolean[]) {
            return Arrays.toString((boolean[]) o);
        } else if (o instanceof double[]) {
            return Arrays.toString((double[]) o);
        } else if (o instanceof float[]) {
            return Arrays.toString((float[]) o);
        } else if (o instanceof Integer[]) {
            return Arrays.toString((Integer[]) o);
        } else if (o instanceof Long[]) {
            return Arrays.toString((Long[]) o);
        } else if (o instanceof Byte[]) {
            return Arrays.toString((Byte[]) o);
        } else if (o instanceof Boolean[]) {
            return Arrays.toString((Boolean[]) o);
        } else if (o instanceof Short[]) {
            return Arrays.toString((Short[]) o);
        } else if (o instanceof Double[]) {
            return Arrays.toString((Double[]) o);
        } else if (o instanceof Float[]) {
            return Arrays.toString((Float[]) o);
        } else if (o instanceof Object[]) {
            return buildArray((Object[]) o);
        }
        return "";
    }

    /**
     * 处理String[]
     *
     * @param obs String[]类型
     * @return JSON字符串
     */
    private static String buildArray(String[] obs) {
        StringBuilder out = new StringBuilder();
        for (String obj : obs) {
            out.append("\"").append(obj).append("\"").append(",");
        }
        String s = out.toString();
        return s.length() == 0 ? "[]" : String.format("[%s]", StringUtil.substringByNumber(s, 1));
    }

    /**
     * 处理char[]
     *
     * @param obs char[]类型
     * @return JSON字符串
     */
    private static String buildArray(char[] obs) {
        StringBuilder out = new StringBuilder();
        for (char obj : obs) {
            out.append("\"").append(obj).append("\"").append(",");
        }
        String s = out.toString();
        return s.length() == 0 ? "[]" : String.format("[%s]", StringUtil.substringByNumber(s, 1));
    }

    /**
     * 处理Object[]
     *
     * @param obs Object[]类型
     * @return JSON字符串
     */
    private static String buildArray(Object[] obs) {
        StringBuilder out = new StringBuilder();
        for (Object obj : obs) {
            out.append(JSON.stringify(obj)).append(",");
        }
        String s = out.toString();
        return s.length() == 0 ? "[]" : String.format("[%s]", StringUtil.substringByNumber(s, 1));
    }
}
