package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class JClass extends JBase {
    private final Class<?> value;

    public JClass(JBase jBase) {
        try {
            this.value = Class.forName(((JString) jBase).getValue());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JArray to Class[]
     *
     * @param jBase 数据源
     * @return Class<?>[]
     */
    public static Class<?>[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Class[]{}, (jb) -> new JClass(jb).getValue());
    }

    /**
     * List<JBase> 转 List<Class>
     *
     * @param jBase 数据源
     * @return List<Class < ?>>
     */
    public static List<Class<?>> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Class>
     *
     * @param jBase 数据源
     * @return Map<String, Class < ?>>
     */
    public static Map<String, Class<?>> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JClass(jb.getValue()).getValue()));
    }

    @Override
    public Class<?> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value.getTypeName());
    }
}
