package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class JShort extends JBase {

    private final Short value;

    public JShort(JBase jBase) {
        try {
            JInteger jInteger = (JInteger) jBase;
            this.value = Short.valueOf(jInteger.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Short.class.getName(), e.getMessage()));
        }
    }

    /**
     * JArray to Short[]
     *
     * @param jBase 数据源
     * @return Short[]
     */
    public static Short[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Short[]{}, (jb) -> new JShort(jb).getValue());
    }

    /**
     * JArray to short[]
     *
     * @param jBase 数据源
     * @return short[]
     */
    public static short[] get_Arr(JBase jBase) {
        Short[] shorts = getArr(jBase);
        short[] res = new short[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            res[i] = shorts[i];
        }
        return res;
    }


    /**
     * List<JBase> 转 List<Short>
     *
     * @param jBase 数据源
     * @return List<Short>
     */
    public static List<Short> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String, Short>
     *
     * @param jBase 数据源
     * @return Map<String, Short>
     */
    public static Map<String, Short> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JShort(jb.getValue()).getValue()));
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
