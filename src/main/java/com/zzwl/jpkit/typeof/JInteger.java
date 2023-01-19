package com.zzwl.jpkit.typeof;

public class JInteger extends JBase {

    private final Integer value;

    public JInteger(int value) {
        this.value = value;
    }

    @Override
    public String apply(String name) {
        return String.format("%s %s", JInteger.class.getName(), name);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
