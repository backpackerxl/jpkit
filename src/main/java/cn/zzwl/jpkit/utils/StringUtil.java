package cn.zzwl.jpkit.utils;

import cn.zzwl.jpkit.typeof.JBool;

import java.lang.reflect.Type;

public class StringUtil {

    private final static String boolPrefix = "is";
    private final static String basicPrefix = "get";


    private StringUtil() {
    }

    public static String getMethodNameByFieldType(Type type, String name) {
        String typeName = type.getTypeName();
        if (typeName.equals(JBool.BOOLEAN)) {
            return String.format("%s%s%s", boolPrefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        } else {
            return String.format("%s%s%s", basicPrefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        }
    }

    public static String substringByNumber(String target, int num) {
        return target.substring(0, target.length() - num);
    }
}
