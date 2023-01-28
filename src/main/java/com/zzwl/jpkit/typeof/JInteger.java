package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

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
     * @return Integer[]
     */
    public static Integer[] getArr(JBase jBase) {
        try {
            JArray jArray = (JArray) jBase;
            List<JBase> value = jArray.getValue();
            Integer[] res = new Integer[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JInteger) value.get(i)).getValue();
            }
            return res;
        } catch (Exception e) {
            // log: error the source not cast array
            throw new JTypeofException("error the source not cast array, because " + e.getMessage());
        }
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
