package com.zzwl.jpkit.plugs;


import com.zzwl.jpkit.anno.JFieldType;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BigDecimalPlug {

    /**
     * JBase to BigDecimal
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {BigDecimal.class})
    public Object getObject(JBase jBase) {
        try {
            return new BigDecimal(jBase.getValue().toString());
        } catch (ClassCastException e) {
            // log 记录转化失败 e.printStackTrace();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * List<JBase> to BigDecimal[]
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {BigDecimal.class})
    public Object getArray(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            BigDecimal[] res = new BigDecimal[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new BigDecimal(value.get(i).getValue().toString());
            }
            return res;
        });
    }

    /**
     * List<JBase> to List<BigDecimal>
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {BigDecimal.class})
    public Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<BigDecimal> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new BigDecimal(base.getValue().toString()));
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> to Map<String, BigDecimal>
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {BigDecimal.class})
    public Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, BigDecimal> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new BigDecimal(value.get(base).getValue().toString()));
            }
            return res;
        });
    }
}
