package com.zzwl.jpkit.typeof;

public class JLong extends JBase {
    private final Long value;

    public JLong(long value) {
        this.value = value;
    }

    @Override
    public String apply(String name) {
        return String.format("%s %s", JLong.class.getName(), name);
    }

    @Override
    public Long getValue() {
        return value;
    }
}
