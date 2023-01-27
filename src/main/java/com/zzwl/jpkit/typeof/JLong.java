package com.zzwl.jpkit.typeof;

import java.lang.reflect.Field;

public class JLong extends JBase {
    private final Long value;

    public JLong(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
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
