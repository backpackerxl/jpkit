package com.zzwl.jpkit.plug;


import com.zzwl.jpkit.anno.JFieldType;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.vo.MySQL;

import java.util.ArrayList;
import java.util.List;

public class MySQLPlug {

    /**
     * List<JBase> to List<MySQL>
     *
     * @param jBase 数据源
     * @return Object
     */
    @JFieldType(type = {MySQL.class})
    public Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<MySQL> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(JSON.parse(base.toString(), MySQL.class));
            }
            return res;
        });
    }
}
