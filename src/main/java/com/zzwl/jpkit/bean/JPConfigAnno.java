package com.zzwl.jpkit.bean;

import java.util.HashMap;
import java.util.Map;

public class JPConfigAnno {
    private final Map<String, Object[]> methodStore;

    public JPConfigAnno() {
        this.methodStore = new HashMap<>();
    }

    public Map<String, Object[]> getMethodStore() {
        return methodStore;
    }
}
