package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Class<?>[] res = new Class[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JClass(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Class>
     *
     * @param jBase 数据源
     * @return List<Class < ?>>
     */
    public static List<Class<?>> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Class<?>> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JClass(base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Class>
     *
     * @param jBase 数据源
     * @return Map<String, Class < ?>>
     */
    public static Map<String, Class<?>> getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Class<?>> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JClass(value.get(base)).getValue());
            }
            return res;
        });
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
