package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

public class JChar extends JBase {

    private final Character value;

    public JChar(JBase jBase) {
        try {
            JString jString = (JString) jBase;
            this.value = jString.getValue().charAt(0);
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Character.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
