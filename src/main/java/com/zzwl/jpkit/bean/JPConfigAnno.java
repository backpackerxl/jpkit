package com.zzwl.jpkit.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0
 */
public class JPConfigAnno {
    private final Map<String, Object[]> methodStore;

    public JPConfigAnno() {
        this.methodStore = new HashMap<>();
    }

    public Map<String, Object[]> getMethodStore() {
        return methodStore;
    }
}
