package com.zzwl.jpkit.typeof;


import java.util.Date;

public enum EuTypeof {
    /**
     * Integer[] 数组的类型名称
     */
    INTEGER_ARR(Integer[].class.getTypeName()),
    /**
     * int[] 数组的类型名称
     */
    INT_ARR(int[].class.getTypeName()),
    /**
     * Short[] 数组的类型名称
     */
    SHORT_ARR(Short[].class.getTypeName()),
    /**
     * short[] 数组的类型名称
     */
    SHORT__ARR(short[].class.getTypeName()),
    /**
     * Byte[] 数组的类型名称
     */
    BYTE_ARR(Byte[].class.getTypeName()),
    /**
     * byte[] 数组的类型名称
     */
    BYTE__ARR(byte[].class.getTypeName()),
    /**
     * Long[] 数组的类型名称
     */
    LONG_ARR(Long[].class.getTypeName()),
    /**
     * long[] 数组的类型名称
     */
    LONG__ARR(long[].class.getTypeName()),
    /**
     * Double[] 数组的类型名称
     */
    DOUBLE_ARR(Double[].class.getTypeName()),
    /**
     * double[] 数组的类型名称
     */
    DOUBLE__ARR(double[].class.getTypeName()),
    /**
     * Float[] 数组的类型名称
     */
    FLOAT_ARR(Float[].class.getTypeName()),
    /**
     * float[] 数组的类型名称
     */
    FLOAT__ARR(float[].class.getTypeName()),
    /**
     * Character[] 数组的类型名称
     */
    CHARACTER_ARR(Character[].class.getTypeName()),
    /**
     * char[] 数组的类型名称
     */
    CHARACTER__ARR(char[].class.getTypeName()),
    /**
     * Boolean[] 数组的类型名称
     */
    BOOLEAN_ARR(Boolean[].class.getTypeName()),
    /**
     * boolean[] 数组的类型名称
     */
    BOOLEAN__ARR(boolean[].class.getTypeName()),
    /**
     * Object[] 数组的类型名称
     */
    OBJECT_ARR(Object[].class.getTypeName()),
    /**
     * Date[] 数组的类型名称
     */
    DATE_ARR(Date[].class.getTypeName()),
    /**
     * String[] 数组的类型名称
     */
    STRING_ARR(String[].class.getTypeName()),
    /**
     * Class[] 数组的类型名称
     */
    CLASS_ARR(Class[].class.getTypeName());

    /**
     * 类型名称
     */
    private final String typeName;

    EuTypeof(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取类型实例
     *
     * @param name 对应的类型名称
     * @return 实例
     */
    public static EuTypeof getInstance(String name) {
        for (EuTypeof value : EuTypeof.values()) {
            if (value.typeName.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
