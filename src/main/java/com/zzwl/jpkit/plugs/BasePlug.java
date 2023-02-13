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
public class BasePlug {
    public final static String GET_OBJECT = "getObject";
    public final static String GET_ARR = "getArray";
    public final static String GET_LIST = "getList";
    public final static String GET_MAP = "getMap";

    /**
     * JBase to Object
     *
     * @param jBase 数据源
     * @return 对应类型的对象
     */
    public static <T> T getObject(JBase jBase, Class<T> clazz) {
        return JSON.parse(jBase, clazz);
    }

    /**
     * List<JBase> to Object[]
     *
     * @param jBase 数据源
     * @return 对应类型的数组
     */
    public static Object[] getArray(JBase jBase, Class<?> clazz) {
        return ArrayUtil.doArrayByArray(jBase, (Object[]) Array.newInstance(clazz, 0), (jb) -> JSON.parse(jb, clazz));
    }

    /**
     * List<JBase> to List<Object>
     *
     * @param jBase 数据源
     * @return 对应类型的List
     */
    public static <T> List<T> getList(JBase jBase, Class<T> clazz) {
        return ArrayUtil.doArrayByList(jBase, (jb) -> JSON.parse(jb, clazz));
    }

    /**
     * Map<String, JBase> to Map<String, Object>
     *
     * @param jBase 数据源
     * @return 对应类型的Map
     */
    public static <T> Map<String, T> getMap(JBase jBase, Class<T> target) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), JSON.parse(jb.getValue(), target)));
    }
}
