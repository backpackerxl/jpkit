package cn.zzwl.jpkit.utils;

import cn.zzwl.jpkit.anno.JIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ReflectUtil {
    private ReflectUtil() {
    }

    public static String doBeanByField(Object obj, CallBack callBack) {
        StringBuilder s = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean tag = false;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(JIgnore.class)) {
                Object valueByMethod = getValueByMethod(obj, field);
                // 利用反射先通过方法获取属性值
                if (!Objects.isNull(valueByMethod)) {
                    if (!valueByMethod.equals("continue")) {
                        s.append(callBack.apply(field.getType(), field.getName(), valueByMethod));
                    }
                } else {
                    tag = true;
                }
                // 若利用方法获取不到属性值，就利用属性获取但此方法会打破属性的私有性
                if (tag) {
                    Object valueByField = getValueByField(obj, field);
                    if (!Objects.isNull(valueByField)) {
                        s.append(callBack.apply(field.getType(), field.getName(), valueByField));
                    }
                }
            }
        }
        return s.toString();
    }

    private static Object getValueByMethod(Object obj, Field field) {
        try {
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(field.getType(), field.getName()));
            if (!method.isAnnotationPresent(JIgnore.class)) {
                return method.invoke(obj);
            } else {
                return "continue";
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getValueByField(Object obj, Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
