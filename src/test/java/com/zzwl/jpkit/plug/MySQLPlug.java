package com.zzwl.jpkit.plug;


import com.zzwl.jpkit.anno.JPMethod;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.BasePlug;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.vo.MySQL;
import com.zzwl.jpkit.vo.Type;

import java.util.ArrayList;
import java.util.List;

public class MySQLPlug {

    /**
     * List<JBase> to List<MySQL>
     *
     * @param jBase 数据源
     * @return Object
     */
    @JPMethod(BasePlug.GET_LIST)
    public List<MySQL> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<MySQL> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(JSON.parse(base.toString(), MySQL.class));
            }
            return res;
        });
    }

    /**
     * List<JBase> to Type[]
     *
     * @param jBase 数据源
     * @return Object
     */
    @JPMethod(BasePlug.GET_ARR)
    public Type[] getArray(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Type[] res = new Type[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = JSON.parse(value.get(i).toString(), Type.class);
            }
            return res;
        });
    }
}
