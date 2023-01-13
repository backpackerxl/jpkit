package cn.zzwl.jpkit.core;

import cn.zzwl.jpkit.change.BeanToJSON;
import cn.zzwl.jpkit.parse.JSONParse;

public class JSON {
    private JSON() {
    }

    public static <B> String stringify(B obj) {
        return new BeanToJSON<>(obj).basicStringify();
    }

    public static <B> String prettyStringify(B obj) {
        return new BeanToJSON<>(obj).prettyStringify();
    }

    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }
}
