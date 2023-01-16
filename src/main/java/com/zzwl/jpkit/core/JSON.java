package com.zzwl.jpkit.core;

import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.parse.JSONParse;

public class JSON {
    private JSON() {
    }

    public static <B> BToJSON<B> stringify(B obj) {
        return new BToJSON<>(obj);
    }

    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }
}
