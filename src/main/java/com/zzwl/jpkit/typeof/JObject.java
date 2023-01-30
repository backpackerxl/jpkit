package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.Map;

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
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Object[] res = new Object[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = value.get(i).getValue();
            }
            return res;
        });
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return JSON.stringify(value).terse();
    }
}
