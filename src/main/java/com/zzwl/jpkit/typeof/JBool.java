package com.zzwl.jpkit.typeof;

import java.lang.reflect.Field;

public class JBool extends JBase {
    private final Boolean value;
    public final static String BOOLEAN = "boolean";

    public JBool(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return value.toString();
    }
}
