package com.zzwl.jpkit.bean;

import java.util.List;
import java.util.Map;

public class AnnoConfig {
    private final Map<String, Object> targets;

    private final Map<String, List<Class<?>>> types;

    public AnnoConfig(Map<String, Object> targets, Map<String, List<Class<?>>> types) {
        this.targets = targets;
        this.types = types;
    }

    public Map<String, Object> getTargets() {
        return targets;
    }

    public Map<String, List<Class<?>>> getTypes() {
        return types;
    }
}
