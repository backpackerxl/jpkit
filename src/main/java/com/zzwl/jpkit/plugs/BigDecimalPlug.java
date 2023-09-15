package com.zzwl.jpkit.plugs;


import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class BigDecimalPlug extends JBasePlug<BigDecimal> {
    /**
     * JBase to BigDecimal
     *
     * @param jb 数据源
     * @return BigDecimal
     */
    @Override
    public BigDecimal getObject(JBase jb) {
        return new BigDecimal(jb.getValue().toString());
    }

    /**
     * List<JBase> to BigDecimal[]
     *
     * @param jBase 数据源
     * @return BigDecimal[]
     */
    @Override
    public BigDecimal[] getArray(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new BigDecimal[0], (jb) -> new BigDecimal(jb.getValue().toString()));
    }

    /**
     * List<JBase> to List<BigDecimal>
     *
     * @param jBase 数据源
     * @return List<BigDecimal>
     */
    @Override
    public List<BigDecimal> getList(JBase jBase) {
        return Arrays.stream(getArray(jBase)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> to Map<String, BigDecimal>
     *
     * @param jBase 数据源
     * @return Map<String, BigDecimal>
     */
    @Override
    public Map<String, BigDecimal> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new BigDecimal(jb.getValue().toString())));
    }
}
