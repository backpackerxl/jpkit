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
        return ArrayUtil.doArrayByArray(jBase, new Character[]{}, (jb) -> new JChar(jb).getValue());
    }

    /**
     * JArray to char[]
     *
     * @param jBase 数据源
     * @return char[]
     */
    public static char[] get_Arr(JBase jBase) {
        Character[] arr = getArr(jBase);
        char[] res = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * List<JBase> 转 List<Character>
     *
     * @param jBase 数据源
     * @return List<Character>
     */
    public static List<Character> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * char to json string
     *
     * @param c char
     * @return json string
     */
    public static String getJSONString(Character c) {
        switch (c) {
            case '\'':
                return "\"\\'\"";
            case '\"':
                return "\"\\\\\\\"\"";
            default:
                return String.format("\"%s\"", c);
        }
    }

    /**
     * Map<String, JBase> 转 Map<String,Character>
     *
     * @param jBase 数据源
     * @return Map<String, Character>
     */
    public static Map<String, Character> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JChar(jb.getValue()).getValue()));
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
