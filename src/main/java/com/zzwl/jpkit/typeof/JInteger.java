package com.zzwl.jpkit.typeof;

import java.lang.reflect.Field;

public class JInteger extends JBase {

    private final Integer value;

    public JInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
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
