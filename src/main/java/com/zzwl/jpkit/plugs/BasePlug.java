package com.zzwl.jpkit.plugs;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Array;
import java.util.*;

public class BasePlug {
    public final static String GET_OBJECT = "getObject";
    public final static String GET_ARR = "getArray";
    public final static String GET_LIST = "getList";
    public final static String GET_MAP = "getMap";

    /**
     * JBase to Object
     *
     * @param jBase 数据源
     * @return Object
     */
    public static <T> T getObject(JBase jBase, Class<T> clazz) {
        try {
            return JSON.parse(jBase.toString(), clazz);
        } catch (ClassCastException e) {
            // log 记录转化失败 e.printStackTrace();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * List<JBase> to Object[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object[] getArray(JBase jBase, Class<?> clazz) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Object[] res = (Object[]) Array.newInstance(clazz, value.size());
            for (int i = 0; i < value.size(); i++) {
                res[i] = JSON.parse(value.get(i).toString(), clazz);
            }
            return res;
        });
    }

    /**
     * List<JBase> to List<Object>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static <T> List<T> getList(JBase jBase, Class<T> clazz) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<T> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(JSON.parse(base.toString(), clazz));
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> to Map<String, Object>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static <T> Map<String, T> getMap(JBase jBase, Class<T> target) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, T> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, JSON.parse(value.get(base).toString(), target));
            }
            return res;
        });
    }
}
