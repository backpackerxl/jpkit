package com.zzwl.jpkit.typeof;

import java.lang.reflect.Field;

public class JDouble extends JBase {
    private final Double value;

    public JDouble(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
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
