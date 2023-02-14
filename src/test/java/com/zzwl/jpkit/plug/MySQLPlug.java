package com.zzwl.jpkit.plug;


import com.zzwl.jpkit.anno.JPMethod;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.BasePlug;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.vo.MySQL;
import com.zzwl.jpkit.vo.Type;

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
        return ArrayUtil.doArrayByList(jBase, (jb) -> JSON.parse(jb, MySQL.class));
    }

    /**
     * List<JBase> to Type[]
     *
     * @param jBase 数据源
     * @return Object
     */
    @JPMethod(BasePlug.GET_ARR)
    public Type[] getArray(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Type[]{}, (jb) -> JSON.parse(jb, Type.class));
    }
}
