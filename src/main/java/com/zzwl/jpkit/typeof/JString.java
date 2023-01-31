package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JString extends JBase {
    private final String value;

    public JString(String value) {
        this.value = value;
    }

    /**
     * JArray to String[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            String[] res = new String[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JString) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<String>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<String> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(((JString) base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,String>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, String> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, ((JString) value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }
}
