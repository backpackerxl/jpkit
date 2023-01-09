package cn.backpackerxl.jpkit.core;

import cn.backpackerxl.jpkit.parse.JSONParse;

public class JSON {
    private JSON() {
    }

    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }
}
