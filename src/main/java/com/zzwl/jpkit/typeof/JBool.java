package com.zzwl.jpkit.typeof;

public class JBool extends JBase {
    private final Boolean value;
    public final static String BOOLEAN = "boolean";

    public JBool(boolean value) {
        this.value = value;
    }

    @Override
    public String apply(String name) {
        return String.format("%s %s", JBool.class.getName(), name);
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
