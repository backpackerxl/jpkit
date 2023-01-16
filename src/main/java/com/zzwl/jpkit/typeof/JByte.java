package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

public class JByte extends JBase {

    private final Byte value;

    public JByte(JBase jBase) {
        try {
            JInteger jInteger = (JInteger) jBase;
            this.value = Byte.valueOf(jInteger.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Byte.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
