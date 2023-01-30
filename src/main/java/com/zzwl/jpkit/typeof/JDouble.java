package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

public class JDouble extends JBase {
    private final Double value;

    public JDouble(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    /**
     * JArray to Double[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Double[] res = new Double[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JDouble) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to double[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            double[] res = new double[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JDouble) value.get(i)).getValue();
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
