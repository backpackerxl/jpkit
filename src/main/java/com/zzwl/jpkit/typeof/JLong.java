package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.anno.JFString;
import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return Object
     */
    public static Object getArr(JBase jBase, Field field) {
        String typeName = field.getType().getTypeName();
        boolean tag = (typeName.equals(Long[].class.getTypeName()) || typeName.equals(long[].class.getTypeName())) && field.isAnnotationPresent(JFString.class);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Long[] res = new Long[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = tag ? Long.valueOf(((JString) value.get(i)).getValue()) : ((JLong) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to long[]
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return Object
     */
    public static Object get_Arr(JBase jBase, Field field) {
        String typeName = field.getType().getTypeName();
        boolean tag = (typeName.equals(Long[].class.getTypeName()) || typeName.equals(long[].class.getTypeName())) && field.isAnnotationPresent(JFString.class);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            long[] res = new long[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = tag ? Long.valueOf(((JString) value.get(i)).getValue()) : ((JLong) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Long>
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return Object
     */
    public static Object getList(JBase jBase, Field field) {
        boolean tag = isTag(field);
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Long> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                Long temp = tag ? Long.valueOf(((JString) base).getValue()) : ((JLong) base).getValue();
                res.add(temp);
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Long>
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return Object
     */
    public static Object getMap(JBase jBase, Field field) {
        boolean tag = isTag(field);
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Long> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                Long temp = tag ? Long.valueOf(((JString) value.get(base)).getValue()) : ((JLong) value.get(base)).getValue();
                res.put(base, temp);
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
