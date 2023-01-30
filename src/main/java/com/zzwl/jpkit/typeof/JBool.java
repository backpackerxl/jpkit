package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

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

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return value.toString();
    }
}
