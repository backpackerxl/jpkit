package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Short[] res = new Short[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JShort(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to short[]
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            short[] res = new short[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JShort(value.get(i)).getValue();
            }
            return res;
        });
    }


    /**
     * List<JBase> 转 List<Short>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Object> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JShort(base));
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String, Short>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Short> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JShort(value.get(base)).getValue());
            }
            return res;
        });
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
