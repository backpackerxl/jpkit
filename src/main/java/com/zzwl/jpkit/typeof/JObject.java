package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.core.JSON;

import java.lang.reflect.Field;
import java.util.Map;

public class JObject extends JBase {
    private final Map<String, JBase> value;

    public JObject(Map<String, JBase> value) {
        this.value = value;
    }

    @Override
    public Map<String, JBase> getValue() {
        return value;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return JSON.stringify(value).terse();
    }
}
