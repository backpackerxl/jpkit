package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.core.JSON;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ArrayUtil {
    private ArrayUtil() {
    }

    /**
     * 判断对象是否为数组
     *
     * @param obj 未知对象
     * @return 是否为数组
     */
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
    public static String compileArray(Object o, boolean isPretty) {
        StringBuilder s = new StringBuilder("[");
        String white = "";

        // 若为美化输出json字符串
        if (isPretty) {
            // 设置缩进
            BToJSON.setTab(BToJSON.getTab() + BToJSON.getBeforeTab());
            white = StringUtil.getWhiteByNumber(BToJSON.getTab());
            s.append("\n");
        }
        if (o instanceof int[] && !isPretty) {
            return Arrays.toString((int[]) o);
        }

        if (o instanceof long[] && !isPretty) {
            return Arrays.toString((long[]) o);
        }

        if (o instanceof short[] && !isPretty) {
            return Arrays.toString((short[]) o);
        }

        if (o instanceof byte[] && !isPretty) {
            return Arrays.toString((byte[]) o);
        }

        if (o instanceof char[] && !isPretty) {
            char[] nums = (char[]) o;
            for (char num : nums) {
                s.append("\"").append(num).append("\"").append(",");
            }
            return String.format("%s%s", StringUtil.substringByNumber(s.toString(), 1), "]");
        }

        if (o instanceof boolean[] && !isPretty) {
            return Arrays.toString((boolean[]) o);
        }

        if (o instanceof double[] && !isPretty) {
            return Arrays.toString((double[]) o);
        }

        if (o instanceof float[] && !isPretty) {
            return Arrays.toString((float[]) o);
        }


        if (o instanceof int[]) {
            int[] nums = (int[]) o;
            for (int i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof long[]) {
            long[] nums = (long[]) o;
            for (long i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof short[]) {
            short[] nums = (short[]) o;
            for (short i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof double[]) {
            double[] nums = (double[]) o;
            for (double i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof float[]) {
            float[] nums = (float[]) o;
            for (float i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof byte[]) {
            byte[] nums = (byte[]) o;
            for (byte i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof char[]) {
            char[] nums = (char[]) o;
            for (char i : nums) {
                s.append(white).append("\"").append(i).append("\",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        if (o instanceof boolean[]) {
            boolean[] nums = (boolean[]) o;
            for (boolean i : nums) {
                s.append(white).append(i).append(",\n");
            }
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
        }

        try {
            assert o instanceof Object[];
            Object[] objects = (Object[]) o;
            for (Object object : objects) {
                if (isPretty) {
                    if (object instanceof String || object instanceof Character) {
                        s.append(white).append("\"").append(object).append("\",\n");
                    } else if (isBaseArray(object)) {
                        s.append(white).append(object).append(",\n");
                    } else {
                        s.append(white).append(JSON.stringify(object).pretty()).append(",\n");
                    }
                } else {
                    if (object instanceof String || object instanceof Character) {
                        s.append("\"").append(object).append("\",");
                    } else if (isBaseArray(object)) {
                        s.append(object).append(",");
                    } else {
                        s.append(JSON.stringify(object).terse()).append(",");
                    }
                }
            }
            if (isPretty) {
                // 恢复缩进
                BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
                return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(BToJSON.getTab()));
            } else {
                return String.format("%s%s", StringUtil.substringByNumber(s.toString(), 1), "]");
            }
        } catch (Exception e) {
            throw new ClassCastException(String.format("%s can't cast array", o));
        }
    }

    /**
     * 判断是否为基础类型数组
     *
     * @param object 数据
     * @return 是或否
     */
    public static boolean isBaseArray(Object object) {
        return object instanceof Integer || object instanceof Boolean || object instanceof Long || object instanceof Short || object instanceof Byte || object instanceof Double || object instanceof Float;
    }

}
