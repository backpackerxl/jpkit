package com.zzwl.jpkit.bean;

/**
 * @since 1.0
 */
public class FieldBean {
    private final String name;
    private final Object obj;

    public FieldBean(String name, Object obj) {
        this.name = name;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public Object getObj() {
        return obj;
    }
}
