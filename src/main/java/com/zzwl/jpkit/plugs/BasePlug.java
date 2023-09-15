package com.zzwl.jpkit.plugs;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
public abstract class BasePlug<E> {

    /**
     * JBase to Object
     *
     * @param jBase     数据源
     * @param clazz     数据类型
     * @param auxiliary 自定义插件
     * @return 对应类型的对象
     */
    @SafeVarargs
    public static <T> T getObject(JBase jBase, Class<T> clazz, Class<? extends JBasePlug<?>>... auxiliary) {
        return JSON.parse(jBase, clazz, auxiliary);
    }

    /**
     * List<JBase> to Object[]
     *
     * @param jBase     数据源
     * @param clazz     数据类型
     * @param auxiliary 自定义插件
     * @return 对应类型的数组
     */
    @SafeVarargs
    public static Object[] getArray(JBase jBase, Class<?> clazz, Class<? extends JBasePlug<?>>... auxiliary) {
        return ArrayUtil.doArrayByArray(jBase, (Object[]) Array.newInstance(clazz, 0), (jb) -> JSON.parse(jb, clazz, auxiliary));
    }

    /**
     * List<JBase> to List<Object>
     *
     * @param jBase     数据源
     * @param clazz     数据类型
     * @param auxiliary 自定义插件
     * @return 对应类型的List
     */
    @SafeVarargs
    public static <T> List<T> getList(JBase jBase, Class<T> clazz, Class<? extends JBasePlug<?>>... auxiliary) {
        return ArrayUtil.doArrayByList(jBase, (jb) -> JSON.parse(jb, clazz, auxiliary));
    }

    /**
     * Map<String, JBase> to Map<String, Object>
     *
     * @param jBase     数据源
     * @param target    数据类型
     * @param auxiliary 自定义插件
     * @return 对应类型的Map
     */
    @SafeVarargs
    public static <T> Map<String, T> getMap(JBase jBase, Class<T> target, Class<? extends JBasePlug<?>>... auxiliary) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), JSON.parse(jb.getValue(), target, auxiliary)));
    }

    /**
     * JBase to E
     *
     * @param jb 数据源
     * @return BigDecimal
     */
    public abstract E getObject(JBase jb);

    /**
     * List<JBase> to E[]
     *
     * @param jBase 数据源
     * @return E[]
     */
    public abstract E[] getArray(JBase jBase);

    /**
     * List<JBase> to List<E>
     *
     * @param jBase 数据源
     * @return List<E>
     */
    public abstract List<E> getList(JBase jBase);

    /**
     * Map<String,JBase> to Map<String, E>
     *
     * @param jBase 数据源
     * @return Map<String, E>
     */
    public abstract Map<String, E> getMap(JBase jBase);
}
