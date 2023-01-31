package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Byte[] res = new Byte[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JByte(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to byte[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            byte[] res = new byte[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JByte(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Byte>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Byte> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JByte(base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Byte>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Byte> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JByte(value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
