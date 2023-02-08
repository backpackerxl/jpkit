package com.zzwl.jpkit.typeof;


import java.util.Date;

public enum EuTypeof {
    INTEGER_ARR(Integer[].class.getTypeName()),
    INT_ARR(int[].class.getTypeName()),
    SHORT_ARR(Short[].class.getTypeName()),
    SHORT__ARR(short[].class.getTypeName()),
    BYTE_ARR(Byte[].class.getTypeName()),
    BYTE__ARR(byte[].class.getTypeName()),
    LONG_ARR(Long[].class.getTypeName()),
    LONG__ARR(long[].class.getTypeName()),
    DOUBLE_ARR(Double[].class.getTypeName()),
    DOUBLE__ARR(double[].class.getTypeName()),
    FLOAT_ARR(Float[].class.getTypeName()),
    FLOAT__ARR(float[].class.getTypeName()),
    CHARACTER_ARR(Character[].class.getTypeName()),
    CHARACTER__ARR(char[].class.getTypeName()),
    BOOLEAN_ARR(Boolean[].class.getTypeName()),
    BOOLEAN__ARR(boolean[].class.getTypeName()),
    OBJECT_ARR(Object[].class.getTypeName()),
    DATE_ARR(Date[].class.getTypeName()),
    STRING_ARR(String[].class.getTypeName()),
    CLASS_ARR(Class[].class.getTypeName());

    private final String typeName;

    EuTypeof(String typeName) {
        this.typeName = typeName;
    }

    public static EuTypeof getInstance(String name) {
        for (EuTypeof value : EuTypeof.values()) {
            if (value.typeName.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
