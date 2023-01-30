package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

public class JLong extends JBase {
    private final Long value;

    public JLong(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    /**
     * JArray to Long[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Long[] res = new Long[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JLong) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to long[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            long[] res = new long[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JLong) value.get(i)).getValue();
            }
            return res;
        });
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return value.toString();
    }
}
