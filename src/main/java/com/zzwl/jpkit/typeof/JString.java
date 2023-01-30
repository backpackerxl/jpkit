package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

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

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

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
