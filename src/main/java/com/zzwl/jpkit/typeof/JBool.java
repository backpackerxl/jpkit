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
public class JBool extends JBase {
    private final Boolean value;

    public JBool(boolean value) {
        this.value = value;
    }

    /**
     * JArray to Boolean[]
     *
     * @param jBase 数据源
     * @return Boolean[]
     */
    public static Boolean[] getArr(JBase jBase) {
        return (Boolean[]) ArrayUtil.doArrayByArray(jBase, new Boolean[]{}, ITypeof::getValue);
    }

    /**
     * JArray to boolean[]
     *
     * @param jBase 数据源
     * @return boolean[]
     */
    public static boolean[] get_Arr(JBase jBase) {
        Boolean[] arr = getArr(jBase);
        boolean[] res = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * List<JBase> 转 List<Boolean>
     *
     * @param jBase 数据源
     * @return List<Boolean>
     */
    public static List<Boolean> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Boolean>
     *
     * @param jBase 数据源
     * @return Map<String, Boolean>
     */
    public static Map<String, Boolean> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), ((JBool) jb).getValue()));
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
