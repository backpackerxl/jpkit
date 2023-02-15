package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class JObject extends JBase {
    private final Map<String, JBase> value;

    public JObject(Map<String, JBase> value) {
        this.value = value;
    }

    @Override
    public Map<String, JBase> getValue() {
        return value;
    }

    /**
     * JArray to Object[]
     *
     * @param jBase 数据源
     * @return Object[]
     */
    public static Object[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Object[]{}, ITypeof::getValue);
    }

    /**
     * List<JBase> 转 List<Object>
     *
     * @param jBase 数据源
     * @return List<Object>
     */
    public static List<Object> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Object>
     *
     * @param jBase 数据源
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), jb.getValue().getValue()));
    }

    @Override
    public String toString() {
        return JSON.stringify(value).terse();
    }
}
