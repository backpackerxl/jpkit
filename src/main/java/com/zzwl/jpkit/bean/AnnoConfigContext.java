package com.zzwl.jpkit.bean;

import java.util.HashMap;
import java.util.Map;

public final class AnnoConfigContext {
    private final Map<String, AnnoConfig> context = new HashMap<>();

    private AnnoConfigContext(){}

    private volatile static AnnoConfigContext annoConfigContext;

    public static AnnoConfigContext getAnnoConfigContext(){
        if (annoConfigContext == null){
            synchronized (AnnoConfigContext.class){
                if (annoConfigContext == null) {
                    annoConfigContext = new AnnoConfigContext();
                }
            }
        }
        return annoConfigContext;
    }

    public Map<String, AnnoConfig> getContext() {
        return context;
    }
}
