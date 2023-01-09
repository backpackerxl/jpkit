package cn.zzwl.jpkit.core;

import cn.zzwl.jpkit.parse.JSONParse;

public class JSON {
    private JSON() {
    }

    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }
}
