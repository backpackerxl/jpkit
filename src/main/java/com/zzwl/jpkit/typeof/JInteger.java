package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.util.List;

public class JInteger extends JBase {

    private final Integer value;

    public JInteger(int value) {
        this.value = value;
    }

    /**
     * JArray to Integer[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Integer[] res = new Integer[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JInteger) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to int[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            int[] res = new int[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JInteger) value.get(i)).getValue();
            }
            return res;
        });
    }

    @Override
    public Integer getValue() {
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
