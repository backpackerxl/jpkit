package com.zzwl.jpkit.bean;


public class FieldBean {
    private String name;
    private Object obj;

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
