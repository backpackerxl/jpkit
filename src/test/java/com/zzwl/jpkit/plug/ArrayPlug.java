package com.zzwl.jpkit.plug;


import com.zzwl.jpkit.anno.JFieldType;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayPlug {


    /**
     * List<JBase> to BigDecimal[]
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {Object.class})
    public Object getArray(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            BigDecimal[] res = new BigDecimal[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new BigDecimal(value.get(i).getValue().toString());
            }
            return res;
        });
    }
}
