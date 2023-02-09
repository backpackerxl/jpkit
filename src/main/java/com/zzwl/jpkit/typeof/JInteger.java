package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
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
     * @return int[]
     */
    public static int[] get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            int[] res = new int[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JInteger) value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Integer>
     *
     * @param jBase 数据源
     * @return List<Integer>
     */
    public static List<Integer> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Integer> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(((JInteger) base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Integer>
     *
     * @param jBase 数据源
     * @return Map<String, Integer>
     */
    public static Map<String, Integer> getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Integer> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, ((JInteger) value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
