package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JBool extends JBase {
    private final Boolean value;
    public final static String BOOLEAN = "boolean";

    public JBool(boolean value) {
        this.value = value;
    }

    /**
     * JArray to Boolean[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Boolean[] res = new Boolean[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JBool) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to boolean[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            boolean[] res = new boolean[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JBool) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Boolean>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Boolean> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(((JBool) base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Boolean>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Boolean> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, ((JBool) value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}