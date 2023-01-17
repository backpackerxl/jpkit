package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.ITypeof;

public abstract class JBase implements ITypeof<Object> {
    public static boolean isBase(Object o) {
        return o instanceof Integer || o instanceof Boolean || o instanceof Byte || o instanceof Long || o instanceof Short || o instanceof Double || o instanceof Float;
    }
}
