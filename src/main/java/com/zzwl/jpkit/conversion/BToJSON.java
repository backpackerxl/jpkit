package com.zzwl.jpkit.conversion;

import com.zzwl.jpkit.utils.ReflectUtil;
import com.zzwl.jpkit.utils.StringUtil;

public class BToJSON<B> {
    private final B bean;

    public BToJSON(B bean) {
        this.bean = bean;
    }

    public String terse() {
        return String.format("{%s}", StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> String.format("\"%s\": %s,", name, obj)), 1));
    }

    public String pretty() {
        return String.format("{\n%s\n}", StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> String.format("  \"%s\": %s,\n", name, obj)), 2));
    }
}
