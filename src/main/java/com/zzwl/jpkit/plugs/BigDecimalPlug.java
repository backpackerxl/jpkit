package com.zzwl.jpkit.plugs;


import com.zzwl.jpkit.anno.JPMethod;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * @since 1.0
 */
public class BigDecimalPlug {
    /**
     * JBase to BigDecimal
     *
     * @param jb 数据源
     * @return BigDecimal
     */
    @JPMethod(BasePlug.GET_OBJECT)
    public BigDecimal getObject(JBase jb) {
        return new BigDecimal(jb.getValue().toString());
    }

    /**
     * List<JBase> to BigDecimal[]
     *
     * @param jBase 数据源
     * @return BigDecimal[]
     */
    @JPMethod(BasePlug.GET_ARR)
    public BigDecimal[] getArray(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new BigDecimal[0], (jb) -> new BigDecimal(jb.getValue().toString()));
    }

    /**
     * List<JBase> to List<BigDecimal>
     *
     * @param jBase 数据源
     * @return List<BigDecimal>
     */
    @JPMethod(BasePlug.GET_LIST)
    public List<BigDecimal> getList(JBase jBase) {
        return ArrayUtil.doArrayByList(jBase, (base) -> new BigDecimal(base.getValue().toString()));
    }

    /**
     * Map<String, JBase> to Map<String, BigDecimal>
     *
     * @param jBase 数据源
     * @return Map<String, BigDecimal>
     */
    @JPMethod(BasePlug.GET_MAP)
    public Map<String, BigDecimal> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new BigDecimal(jb.getValue().toString())));
    }
}
