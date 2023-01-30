package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

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

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }


    @Override
    public String toString() {
        return value.toString();
    }
}
