package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.anno.JCollectType;
import com.zzwl.jpkit.anno.JFString;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        boolean tag = isTag(field);
        return ArrayUtil.doArrayByArray(jBase, new Long[]{}, (jb) -> tag ? Long.valueOf(((JString) jb).getValue()) : ((JLong) jb).getValue());
    }

    /**
     * JArray to long[]
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return long[]
     */
    public static long[] get_Arr(JBase jBase, Field field) {
        return Arrays.stream(getArr(jBase, field)).mapToLong(Long::longValue).toArray();
    }

    /**
     * List<JBase> 转 List<Long>
     *
     * @param jBase 数据源
     * @param field 当前字段
     * @return List<Long>
     */
    public static List<Long> getList(JBase jBase, Field field) {
        return Arrays.stream(getArr(jBase, field)).collect(Collectors.toList());
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
        return ArrayUtil.doArrayByMap(jBase, (jb) -> tag ? new JBaseEntryImpl<>(jb.getKey(), Long.valueOf(((JString) jb.getValue()).getValue())) : new JBaseEntryImpl<>(jb.getKey(), ((JLong) jb).getValue()));
    }

    /**
     * 判断是否需要Long To String
     *
     * @param field 当前字段
     * @return true or false
     */
    private static boolean isTag(Field field) {
        String typeName = field.getType().getTypeName();
        if (field.isAnnotationPresent(JCollectType.class)) {
            return field.isAnnotationPresent(JFString.class) && field.getDeclaredAnnotation(JCollectType.class).type().equals(Long.class);
        }
        return (typeName.equals(Long[].class.getTypeName()) || typeName.equals(long[].class.getTypeName())) && field.isAnnotationPresent(JFString.class);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
