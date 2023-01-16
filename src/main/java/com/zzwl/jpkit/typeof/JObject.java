package com.zzwl.jpkit.typeof;

import java.util.Map;

public class JObject extends JBase {
    private final Map<String, JBase> jObj;

    public JObject(Map<String, JBase> jObj) {
        this.jObj = jObj;
    }

    @Override
    public Map<String, JBase> getValue() {
        return jObj;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
