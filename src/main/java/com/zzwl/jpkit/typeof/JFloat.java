package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

public class JFloat extends JBase {

    private final Float value;

    public JFloat(JBase jBase) {
        try {
            JDouble jDouble = (JDouble) jBase;
            this.value = Float.valueOf(jDouble.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Float.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
