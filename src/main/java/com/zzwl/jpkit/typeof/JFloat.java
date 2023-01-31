package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JFloat extends JBase {

    private final Float value;

    public JFloat(JBase jBase) {
        try {
            JDouble jDouble = (JDouble) jBase;
            this.value = Float.valueOf(jDouble.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Float.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Float getValue() {
        return value;
    }

    /**
     * JArray to Float[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Float[] res = new Float[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JFloat(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to float[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            float[] res = new float[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JFloat(value.get(i)).getValue();
            }
            return res;
        });
    }


    /**
     * List<JBase> 转 List<Float>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Float> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JFloat(base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Float>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Float> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JFloat(value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
