package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.anno.JCollectType;
import com.zzwl.jpkit.anno.JFormat;
import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.typeof.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ArrayUtil {
    private ArrayUtil() {
    }

    /**
     * 处理数组的情况
     *
     * @param o 将数组值转化为JSON值
     * @return JSON字符串值
     */
    public static String compileArray(Object o, boolean isPretty, boolean longToString) {
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
            long[] o1 = (long[]) o;
            if (longToString) {
                for (long num : o1) {
                    s.append("\"").append(num).append("\"").append(",");
                }
                return String.format("%s%s", StringUtil.substringByNumber(s.toString(), 1), "]");
            } else {
                return Arrays.toString(o1);
            }
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
            if (longToString) {
                for (long i : nums) {
                    s.append(white).append("\"").append(i).append("\",\n");
                }
            } else {
                for (long i : nums) {
                    s.append(white).append(i).append(",\n");
                }
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
                    if (object instanceof String || object instanceof Character || longToString) {
                        s.append(white).append("\"").append(object).append("\",\n");
                    } else if (isBaseArray(object)) {
                        s.append(white).append(object).append(",\n");
                    } else {
                        s.append(white).append(JSON.stringify(object).pretty()).append(",\n");
                    }
                } else {
                    if (object instanceof String || object instanceof Character || longToString) {
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

    /**
     * List 转 Array
     *
     * @param jBase 转换源
     * @param field 当前字段
     * @return 转化后的包装类型数组
     */
    public static Object getArr(JBase jBase, Field field) {
        EuTypeof instance = EuTypeof.getInstance(field.getType().getTypeName());
        if (Objects.isNull(instance)) {
            return null;
        }
        switch (instance) {
            case INT_ARR:
                return JInteger.get_Arr(jBase);
            case INTEGER_ARR:
                return JInteger.getArr(jBase);
            case SHORT_ARR:
                return JShort.getArr(jBase);
            case SHORT__ARR:
                return JShort.get_Arr(jBase);
            case LONG_ARR:
                return JLong.getArr(jBase, field);
            case LONG__ARR:
                return JLong.get_Arr(jBase, field);
            case FLOAT__ARR:
                return JFloat.get_Arr(jBase);
            case FLOAT_ARR:
                return JFloat.getArr(jBase);
            case DOUBLE_ARR:
                return JDouble.getArr(jBase);
            case DOUBLE__ARR:
                return JDouble.get_Arr(jBase);
            case CHARACTER_ARR:
                return JChar.getArr(jBase);
            case CHARACTER__ARR:
                return JChar.get_Arr(jBase);
            case BYTE_ARR:
                return JByte.getArr(jBase);
            case BYTE__ARR:
                return JByte.get_Arr(jBase);
            case BOOLEAN_ARR:
                return JBool.getArr(jBase);
            case BOOLEAN__ARR:
                return JBool.get_Arr(jBase);
            case DATE_ARR:
                if (field.isAnnotationPresent(JFormat.class)) {
                    JFormat jDateFormat = field.getDeclaredAnnotation(JFormat.class);
                    if (jDateFormat.value().equals("#")) {
                        return JDate.getArr(jBase);
                    } else {
                        return JDate.getArr(jBase, jDateFormat.value());
                    }
                } else {
                    return JDate.getArr(jBase);
                }
            case OBJECT_ARR:
                return JObject.getArr(jBase);
            case STRING_ARR:
                return JString.getArr(jBase);
            case CLASS_ARR:
                return JClass.getArr(jBase);
            default:
                return null;
        }
    }


    /**
     * List 转对应类型
     *
     * @param jBase 转换源
     * @param field 当前字段
     * @return 转化后的包装类型List
     */
    public static Object getList(JBase jBase, Field field) {
        if (!field.isAnnotationPresent(JCollectType.class)) {
            return null;
        }
        JCollectType jCollectType = field.getDeclaredAnnotation(JCollectType.class);
        EuTypeof instance = EuTypeof.getInstance(String.format("%s%s", jCollectType.type().getTypeName(), "[]"));
        if (Objects.isNull(instance)) {
            return null;
        }
        switch (instance) {
            case INTEGER_ARR:
                return JInteger.getList(jBase);
            case SHORT_ARR:
                return JShort.getList(jBase);
            case LONG_ARR:
                return JLong.getList(jBase, field);
            case FLOAT_ARR:
                return JFloat.getList(jBase);
            case DOUBLE_ARR:
                return JDouble.getList(jBase);
            case CHARACTER_ARR:
                return JChar.getList(jBase);
            case BYTE_ARR:
                return JByte.getList(jBase);
            case BOOLEAN_ARR:
                return JBool.getList(jBase);
            case DATE_ARR:
                if (field.isAnnotationPresent(JFormat.class)) {
                    JFormat jDateFormat = field.getDeclaredAnnotation(JFormat.class);
                    if (jDateFormat.value().equals("#")) {
                        return JDate.getList(jBase);
                    } else {
                        return JDate.getList(jBase, jDateFormat.value());
                    }
                } else {
                    return JDate.getList(jBase);
                }
            case OBJECT_ARR:
                return JObject.getList(jBase);
            case STRING_ARR:
                return JString.getList(jBase);
            case CLASS_ARR:
                return JClass.getList(jBase);
            default:
                return null;
        }
    }

    /**
     * Map 转对应类型
     *
     * @param jBase 转换源
     * @param field 当前字段
     * @return 转化后的包装类型Map
     */
    public static Object getMap(JBase jBase, Field field) {
        if (field.isAnnotationPresent(JCollectType.class)) {
            return null;
        }
        JCollectType jCollectType = field.getDeclaredAnnotation(JCollectType.class);
        EuTypeof instance = EuTypeof.getInstance(String.format("%s%s", jCollectType.type().getTypeName(), "[]"));
        if (Objects.isNull(instance)) {
            return null;
        }
        switch (instance) {
            case INTEGER_ARR:
                return JInteger.getMap(jBase);
            case SHORT_ARR:
                return JShort.getMap(jBase);
            case LONG_ARR:
                return JLong.getMap(jBase, field);
            case FLOAT_ARR:
                return JFloat.getMap(jBase);
            case DOUBLE_ARR:
                return JDouble.getMap(jBase);
            case CHARACTER_ARR:
                return JChar.getMap(jBase);
            case BYTE_ARR:
                return JByte.getMap(jBase);
            case BOOLEAN_ARR:
                return JBool.getMap(jBase);
            case DATE_ARR:
                if (field.isAnnotationPresent(JFormat.class)) {
                    JFormat jDateFormat = field.getDeclaredAnnotation(JFormat.class);
                    if (jDateFormat.value().equals("#")) {
                        return JDate.getMap(jBase);
                    } else {
                        return JDate.getMap(jBase, jDateFormat.value());
                    }
                } else {
                    return JDate.getMap(jBase);
                }
            case OBJECT_ARR:
                return JObject.getMap(jBase);
            case STRING_ARR:
                return JString.getMap(jBase);
            case CLASS_ARR:
                return JClass.getMap(jBase);
            default:
                return null;
        }
    }

    /**
     * 处理数组和List的通用回调方法
     *
     * @param jBase 数据源
     * @param func  回调函数
     * @return 结果
     */
    public static <T> T doArrayByJArray(JBase jBase, Function<List<JBase>, T> func) {
        try {
            JArray jArray = (JArray) jBase;
            List<JBase> value = jArray.getValue();
            return func.apply(value);
        } catch (Exception e) {
            // log: error the source not cast array
            throw new JTypeofException("error the source not cast array, because " + e.getMessage());
        }
    }

    /**
     * 处理Map的通用回调方法
     *
     * @param jBase 数据源
     * @param func  回调函数
     * @return 结果
     */
    public static <T> T doMapByJObject(JBase jBase, Function<Map<String, JBase>, T> func) {
        try {
            JObject jObject = (JObject) jBase;
            Map<String, JBase> value = jObject.getValue();
            return func.apply(value);
        } catch (Exception e) {
            // log: error the source not cast array
            throw new JTypeofException("error the source not cast array, because " + e.getMessage());
        }
    }
}
