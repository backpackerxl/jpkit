package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return double[]
     */
    public static double[] get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            double[] res = new double[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JDouble) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Double>
     *
     * @param jBase 数据源
     * @return List<Double>
     */
    public static List<Double> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Double> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(((JDouble) base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Double>
     *
     * @param jBase 数据源
     * @return Map<String, Double>
     */
    public static Map<String, Double> getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Double> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, ((JDouble) value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
