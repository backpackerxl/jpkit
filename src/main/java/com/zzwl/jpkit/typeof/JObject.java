package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
public class JObject extends JBase {
    private final Map<String, JBase> value;

    public JObject(Map<String, JBase> value) {
        this.value = value;
    }

    @Override
    public Map<String, JBase> getValue() {
        return value;
    }

    /**
     * JArray to Object[]
     *
     * @param jBase 数据源
     * @return Object[]
     */
    public static Object[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Object[] res = new Object[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = value.get(i).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Object>
     *
     * @param jBase 数据源
     * @return List<Object>
     */
    public static List<Object> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Object> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(base.getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Object>
     *
     * @param jBase 数据源
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Object> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, value.get(base).getValue());
            }
            return res;
        });
    }

    @Override
    public String toString() {
        return JSON.stringify(value).terse();
    }
}
