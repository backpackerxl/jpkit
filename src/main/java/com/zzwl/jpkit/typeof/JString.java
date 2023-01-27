package com.zzwl.jpkit.typeof;

import java.lang.reflect.Field;

public class JString extends JBase {
    private final String value;

    public JString(String value) {
        this.value = value;
    }

    private final static JString bean = new JString("");


    public static JString getBean() {
        return bean;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }
}
