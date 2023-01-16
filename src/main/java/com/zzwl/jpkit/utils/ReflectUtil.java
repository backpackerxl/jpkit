package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.anno.JDateFormat;
import com.zzwl.jpkit.anno.JIgnore;
import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.bean.FieldBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                FieldBean fieldBean = getValueByMethod(obj, field);
                // 利用反射先通过方法获取属性值
                if (!Objects.isNull(fieldBean)) {
                    if (!fieldBean.getName().equals("continue")) {
                        s.append(callBack.apply(fieldBean.getName(), fieldBean.getObj()));
                    }
                } else {
                    tag = true;
                }
                // 若利用方法获取不到属性值，就利用属性获取但此方法会打破属性的私有性
                if (tag) {
                    fieldBean = getValueByField(obj, field);
                    if (!Objects.isNull(fieldBean)) {
                        s.append(callBack.apply(fieldBean.getName(), fieldBean.getObj()));
                    }
                }
            }
        }
        return s.toString();
    }

    private static FieldBean getValueByMethod(Object obj, Field field) {
        try {
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(field.getType(), field.getName()));
            if (!method.isAnnotationPresent(JIgnore.class)) {
                Object o = method.invoke(obj);
                o = getObject(field, o);
                if (method.isAnnotationPresent(JRename.class)) {
                    JRename rename = method.getDeclaredAnnotation(JRename.class);
                    return new FieldBean(rename.value(), o);
                } else {
                    if (field.isAnnotationPresent(JRename.class)) {
                        JRename rename = field.getDeclaredAnnotation(JRename.class);
                        return new FieldBean(rename.value(), o);
                    } else {
                        return new FieldBean(field.getName(), o);
                    }
                }
            } else {
                return new FieldBean("continue", null);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            //log输出： e.printStackTrace();
            return null;
        }
    }

    private static FieldBean getValueByField(Object obj, Field field) {
        try {
            field.setAccessible(true);
            Object o = field.get(obj);
            o = getObject(field, o);
            if (field.isAnnotationPresent(JRename.class)) {
                JRename rename = field.getDeclaredAnnotation(JRename.class);
                return new FieldBean(rename.value(), o);
            } else {
                return new FieldBean(field.getName(), o);
            }
        } catch (IllegalAccessException e) {
            //log输出： e.printStackTrace();
            return null;
        }
    }

    private static Object getObject(Field field, Object o) {
        if (field.isAnnotationPresent(JDateFormat.class) && field.getType().getTypeName().equals(Date.class.getTypeName())) {
            JDateFormat jDateFormat = field.getDeclaredAnnotation(JDateFormat.class);
            if (jDateFormat.value().equals("#")) {
                o = ((Date) o).getTime();
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(jDateFormat.value());
                o = String.format("\"%s\"", simpleDateFormat.format(o));
            }
        } else if (field.getType().getTypeName().equals(Date.class.getTypeName()) || field.getType().getTypeName().equals(String.class.getTypeName())) {
            o = String.format("\"%s\"", o.toString());
        } else if (ArrayUtil.isArray(o)) {
            o = ArrayUtil.compileArray(o);
        }
        return o;
    }
}
