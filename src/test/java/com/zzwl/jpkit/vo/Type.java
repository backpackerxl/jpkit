package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JFString;

public class Type {
    @JFString
    private long id;
    private String name;
    private Class<?> aClass;

    public Type(long id, String name, Class<?> aClass) {
        this.id = id;
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
