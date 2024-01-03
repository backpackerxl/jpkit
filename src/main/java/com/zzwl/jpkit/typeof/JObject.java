package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.utils.ReflectUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class JObject extends JBase {
    private final Map<String, JBase> value;

    public JObject(Map<String, JBase> value) {
        this.value = value;
    }

    public JObject() {
        this.value = new HashMap<>();
    }

    @Override
    public Map<String, JBase> getValue() {
        return value;
    }

    /**
     * 基础数据的快数JSON结构化
     *
     * @param key   key值
     * @param value value值，目前支持String, Byte,Short, Int, Char, Long,double,float,boolean, jbase, BigDecimal
     */
    public void put(String key, Object value) {
        if (value instanceof String) {
            this.value.put(key, new JString((String) value));
        }

        if (value instanceof Byte) {
            this.value.put(key, new JInteger((Byte) value));
        }

        if (value instanceof Short) {
            this.value.put(key, new JInteger((Short) value));
        }

        if (value instanceof Integer) {
            this.value.put(key, new JInteger((Integer) value));
        }

        if (value instanceof Character) {
            this.value.put(key, new JChar((Character) value));
        }

        if (value instanceof Long) {
            this.value.put(key, new JLong((Long) value));
        }

        if (value instanceof Double) {
            this.value.put(key, new JDouble((Double) value));
        }

        if (value instanceof Float) {
            this.value.put(key, new JFloat((Float) value));
        }

        if (value instanceof Boolean) {
            this.value.put(key, new JBool((Boolean) value));
        }

        if (value instanceof JBase) {
            this.value.put(key, (JBase) value);
        }

        if (value instanceof BigDecimal) {
            this.value.put(key, new JDouble(((BigDecimal) value).doubleValue()));
        }

    }

    /**
     * 复杂类型处理
     *
     * @param key   关键字
     * @param value 值
     * @param func  转换操作
     */
    public <T> void put(String key, T value, Function<T, JBase> func) {
        this.value.put(key, func.apply(value));
    }

    /**
     * 删除
     *
     * @param key 关键字
     */
    public void remove(String key) {
        this.value.remove(key);
    }

    /**
     * 查询
     *
     * @param key 关键字
     * @return 结果
     */
    public JBase get(String key) {
        return this.value.get(key);
    }

    /**
     * 修改
     *
     * @param key 关键字
     */
    public <T> void replace(String key, T value, Function<T, JBase> func) {
        put(key, value, func);
    }

    /**
     * 修改
     *
     * @param key 关键字
     */
    public void replace(String key, Object value) {
        put(key, value);
    }

    /**
     * JArray to Object[]
     *
     * @param jBase 数据源
     * @return Object[]
     */
    public static Object[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Object[]{}, ITypeof::getValue);
    }

    /**
     * List<JBase> 转 List<Object>
     *
     * @param jBase 数据源
     * @return List<Object>
     */
    public static List<Object> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Object>
     *
     * @param jBase 数据源
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), jb.getValue().getValue()));
    }

    @Override
    public String toString() {
        return JSON.stringify(value).terse();
    }
}
