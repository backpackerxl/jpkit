package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
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
     * @return Character[]
     */
    public static Character[] getArr(JBase jBase) {
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
     * @return char[]
     */
    public static char[] get_Arr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            char[] res = new char[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JChar(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Character>
     *
     * @param jBase 数据源
     * @return List<Character>
     */
    public static List<Character> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Character> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JChar(base).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Character>
     *
     * @param jBase 数据源
     * @return Map<String, Character>
     */
    public static Map<String, Character> getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Character> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JChar(value.get(base)).getValue());
            }
            return res;
        });
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }
}
