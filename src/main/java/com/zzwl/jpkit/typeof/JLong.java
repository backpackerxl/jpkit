package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.anno.JFString;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
public class JLong extends JBase {
    private final Long value;

    public JLong(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    /**
     * JArray to Long[]
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return Long[]
     */
    public static Long[] getArr(JBase jBase, Field field) {
        String typeName = field.getType().getTypeName();
        boolean tag = (typeName.equals(Long[].class.getTypeName()) || typeName.equals(long[].class.getTypeName())) && field.isAnnotationPresent(JFString.class);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Long[] res = new Long[value.size()];
            if (tag) {
                for (int i = 0; i < value.size(); i++) {
                    res[i] = Long.valueOf(((JString) value.get(i)).getValue());
                }
            } else {
                for (int i = 0; i < value.size(); i++) {
                    res[i] = ((JLong) value.get(i)).getValue();
                }
            }
            return res;
        });
    }

    /**
     * JArray to long[]
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return long[]
     */
    public static long[] get_Arr(JBase jBase, Field field) {
        String typeName = field.getType().getTypeName();
        boolean tag = (typeName.equals(Long[].class.getTypeName()) || typeName.equals(long[].class.getTypeName())) && field.isAnnotationPresent(JFString.class);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            long[] res = new long[value.size()];
            if (tag) {
                for (int i = 0; i < value.size(); i++) {
                    res[i] = Long.parseLong(((JString) value.get(i)).getValue());
                }
            } else {
                for (int i = 0; i < value.size(); i++) {
                    res[i] = ((JLong) value.get(i)).getValue();
                }
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Long>
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return List<Long>
     */
    public static List<Long> getList(JBase jBase, Field field) {
        boolean tag = isTag(field);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Long> res = new ArrayList<>(value.size());
            if (tag) {
                for (JBase base : value) {
                    res.add(Long.valueOf(((JString) base).getValue()));
                }
            } else {
                for (JBase base : value) {
                    res.add(((JLong) base).getValue());
                }
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Long>
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return Map<String, Long>
     */
    public static Map<String, Long> getMap(JBase jBase, Field field) {
        boolean tag = isTag(field);
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Long> res = new HashMap<>(value.size());
            if (tag) {
                for (String base : value.keySet()) {
                    res.put(base, Long.valueOf(((JString) value.get(base)).getValue()));
                }
            } else {
                for (String base : value.keySet()) {
                    res.put(base, ((JLong) value.get(base)).getValue());
                }
            }
            return res;
        });
    }

    /**
     * 判断是否需要Long To String
     *
     * @param field 当前字段
     * @return true or false
     */
    private static boolean isTag(Field field) {
        if (field.isAnnotationPresent(JFString.class)) {
            JFString jfString = field.getDeclaredAnnotation(JFString.class);
            // 打开Long to String
            return jfString.type().getTypeName().equals(Long.class.getTypeName());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
