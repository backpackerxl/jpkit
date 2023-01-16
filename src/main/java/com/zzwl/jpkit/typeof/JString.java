package com.zzwl.jpkit.typeof;

public class JString extends JBase {
    private final String value;

    public JString(String value) {
        this.value = value;
    }

    @Override
    public String apply(String name) {
        return String.format("%s %s", JString.class.getName(), name);
    }

    @Override
    public String getValue() {
        return value;
    }
}
