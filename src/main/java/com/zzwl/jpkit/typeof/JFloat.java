package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
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
     * @return Float[]
     */
    public static Float[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Float[]{}, (jb) -> new JFloat(jb).getValue());
    }

    /**
     * JArray to float[]
     *
     * @param jBase 数据源
     * @return float[]
     */
    public static float[] get_Arr(JBase jBase) {
        Float[] arr = getArr(jBase);
        float[] res = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }


    /**
     * List<JBase> 转 List<Float>
     *
     * @param jBase 数据源
     * @return List<Float>
     */
    public static List<Float> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Float>
     *
     * @param jBase 数据源
     * @return Map<String, Float>
     */
    public static Map<String, Float> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JFloat(jb.getValue()).getValue()));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
