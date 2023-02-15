package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
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
     * @return Double[]
     */
    public static Double[] getArr(JBase jBase) {
        return (Double[]) ArrayUtil.doArrayByArray(jBase, new Double[]{}, ITypeof::getValue);
    }

    /**
     * JArray to double[]
     *
     * @param jBase 数据源
     * @return double[]
     */
    public static double[] get_Arr(JBase jBase) {
        return Arrays.stream(getArr(jBase)).mapToDouble(Double::doubleValue).toArray();
    }

    /**
     * List<JBase> 转 List<Double>
     *
     * @param jBase 数据源
     * @return List<Double>
     */
    public static List<Double> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Double>
     *
     * @param jBase 数据源
     * @return Map<String, Double>
     */
    public static Map<String, Double> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), ((JDouble) jb.getValue()).getValue()));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
