package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;

public class JChar extends JBase {

    private final Character value;

    public JChar(JBase jBase) {
        try {
            JString jString = (JString) jBase;
            this.value = jString.getValue().charAt(0);
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Character.class.getName(), e.getMessage()));
        }
    }


    /**
     * JArray to Character[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Character[] res = new Character[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JChar(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to char[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            char[] res = new char[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JChar(value.get(i)).getValue();
            }
            return res;
        });
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }
}
