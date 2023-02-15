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
public class JByte extends JBase {

    private final Byte value;

    public JByte(JBase jBase) {
        try {
            JInteger jInteger = (JInteger) jBase;
            this.value = Byte.valueOf(jInteger.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Byte.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Byte getValue() {
        return value;
    }

    /**
     * JArray to Byte[]
     *
     * @param jBase 数据源
     * @return Byte[]
     */
    public static Byte[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Byte[]{}, (jb) -> new JByte(jb).getValue());
    }

    /**
     * JArray to byte[]
     *
     * @param jBase 数据源
     * @return byte[]
     */
    public static byte[] get_Arr(JBase jBase) {
        Byte[] arr = getArr(jBase);
        byte[] res = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * List<JBase> 转 List<Byte>
     *
     * @param jBase 数据源
     * @return List<Byte>
     */
    public static List<Byte> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Byte>
     *
     * @param jBase 数据源
     * @return Map<String, Byte>
     */
    public static Map<String, Byte> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JByte(jb.getValue()).getValue()));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
