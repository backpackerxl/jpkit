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
        return (Integer[]) ArrayUtil.doArrayByArray(jBase, new Integer[]{}, ITypeof::getValue);
    }

    /**
     * JArray to int[]
     *
     * @param jBase 数据源
     * @return int[]
     */
    public static int[] get_Arr(JBase jBase) {
        return Arrays.stream(getArr(jBase)).mapToInt(Integer::intValue).toArray();
    }

    /**
     * List<JBase> 转 List<Integer>
     *
     * @param jBase 数据源
     * @return List<Integer>
     */
    public static List<Integer> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Integer>
     *
     * @param jBase 数据源
     * @return Map<String, Integer>
     */
    public static Map<String, Integer> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), ((JInteger) jb.getValue()).getValue()));
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
