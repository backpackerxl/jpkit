package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.anno.*;
import com.zzwl.jpkit.bean.FieldBean;
import com.zzwl.jpkit.bean.JPConfigAnno;
import com.zzwl.jpkit.bean.JPConfigAnnoContext;
import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.parse.ObjectParse;
import com.zzwl.jpkit.plugs.BasePlug;
import com.zzwl.jpkit.typeof.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ReflectUtil {

    private static boolean isPretty = false;
    private static boolean getTag = false;
    private static boolean setTag = false;

    private ReflectUtil() {
    }

    public static void setIsPretty(boolean isPretty) {
        ReflectUtil.isPretty = isPretty;
    }

    /**
     * 利用反射处理自定义对象的JSON字符串化
     *
     * @param obj      待处理对象
     * @param callBack 回调函数
     * @return 半成品JSON字符串
     */
    public static String doBeanByField(Object obj, CallBack callBack) {
        StringBuilder s = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(JIgnore.class)) {
                // 利用反射先通过方法获取属性值
                FieldBean fieldBean = getValueByMethod(obj, field);
                if (!Objects.isNull(fieldBean)) {
                    s.append(callBack.apply(fieldBean.getName(), fieldBean.getObj()));
                }
                // 若利用方法获取不到属性值，就利用属性获取但此方法会打破属性的私有性
                if (getTag) {
                    fieldBean = getValueByField(obj, field);
                    if (!Objects.isNull(fieldBean)) {
                        s.append(callBack.apply(fieldBean.getName(), fieldBean.getObj()));
                    }
                }
            }
        }
        if (isPretty) {
            // 恢复缩进
            BToJSON.setTab(BToJSON.getTab() - BToJSON.getBeforeTab());
        }
        return s.toString();
    }

    /**
     * 通过对象的get方法获取属性值
     *
     * @param obj   对象
     * @param field 字段
     * @return 字段值
     */
    private static FieldBean getValueByMethod(Object obj, Field field) {
        try {
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(StringUtil.basicGetPrefix, field.getType(), field.getName()));
            getTag = false;
            if (!method.isAnnotationPresent(JIgnore.class)) {
                Object o = method.invoke(obj);
                o = getPlugsObject(field, o);
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
            }
            return null;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            //log输出： e.printStackTrace();
            getTag = true;
            return null;
        }
    }

    /**
     * 通过反射,破坏对象的属性的私有性获取属性值
     *
     * @param obj   对象
     * @param field 字段
     * @return 字段值
     */
    private static FieldBean getValueByField(Object obj, Field field) {
        try {
            field.setAccessible(true);
            Object o = field.get(obj);
            getTag = false;
            o = getPlugsObject(field, o);
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

    /**
     * 特殊字段的特殊处理方式
     *
     * @param field 字段
     * @param o     对象
     * @return 字段值
     */
    private static Object getPlugsObject(Field field, Object o) {
        if (Objects.isNull(o)) {
            return null;
        }
        if (field.isAnnotationPresent(JFormat.class) && o instanceof Date) {
            JFormat jDateFormat = field.getDeclaredAnnotation(JFormat.class);
            if (jDateFormat.value().equals("#")) {
                o = ((Date) o).getTime();
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(jDateFormat.value());
                o = String.format("\"%s\"", simpleDateFormat.format(o));
            }
        } else if (o instanceof Character || o instanceof Date || o instanceof String) {
            o = String.format("\"%s\"", o);
        } else if (o.getClass().isArray()) {
            o = ArrayUtil.compileArray(o, isPretty, (o instanceof Long[] || o instanceof long[]) && field.isAnnotationPresent(JFString.class));
        } else if (o instanceof Long && field.isAnnotationPresent(JFString.class)) {
            o = String.format("\"%s\"", o);
        } else if (o instanceof List) {
            if (field.isAnnotationPresent(JFString.class)) {
                JFString jfString = field.getDeclaredAnnotation(JFString.class);
                if (jfString.type().getTypeName().equals(Long.class.getTypeName())) {
                    // 打开Long to String
                    BToJSON.setLongToStr(true);
                }
            }
            if (isPretty) {
                o = JSON.stringify(o).pretty();
            } else {
                o = JSON.stringify(o).terse();
            }
        } else if (o instanceof Map) {
            if (field.isAnnotationPresent(JFString.class)) {
                JFString jfString = field.getDeclaredAnnotation(JFString.class);
                if (jfString.type().getTypeName().equals(Long.class.getTypeName())) {
                    // 打开Long to String
                    BToJSON.setLongToStr(true);
                }
            }
            if (isPretty) {
                o = JSON.stringify(o).pretty();
            } else {
                o = JSON.stringify(o).terse();
            }
        } else if (o instanceof Class) {
            o = String.format("\"%s\"", ((Class<?>) o).getTypeName());
        }
        return o;
    }

    /**
     * 通过方法的形式为属性设置值
     *
     * @param obj   对象
     * @param field 字段
     * @param value 设置值
     */
    public static void setValueByMethod(Object obj, Field field, Object value) {
        try {
            Class<?> type = field.getType();
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(StringUtil.basicSetPrefix, type, field.getName()), type);
            if (!method.isAnnotationPresent(JIgnore.class) && !Objects.isNull(value)) {
                method.invoke(obj, value);
            }
            setTag = false;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // log set error
            setTag = true;
            //throw new RuntimeException(e);
        }
    }

    /**
     * 处理为字段赋值的相关
     *
     * @param obj   对象
     * @param field 字段
     * @return 处理结果
     */
    private static FieldBean setValueByMethod(Object obj, Field field) {
        try {
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(StringUtil.basicGetPrefix, field.getType(), field.getName()));
            if (!method.isAnnotationPresent(JIgnore.class)) {
                if (method.isAnnotationPresent(JRename.class)) {
                    JRename rename = method.getDeclaredAnnotation(JRename.class);
                    return new FieldBean(rename.value(), null);
                } else {
                    if (field.isAnnotationPresent(JRename.class)) {
                        JRename rename = field.getDeclaredAnnotation(JRename.class);
                        return new FieldBean(rename.value(), null);
                    } else {
                        return new FieldBean(field.getName(), null);
                    }
                }
            } else {
                return new FieldBean(field.getName(), null);
            }
        } catch (NoSuchMethodException e) {
            // log No get Method
            return new FieldBean(field.getName(), null);
        }
    }

    /**
     * 为对象字段赋值
     *
     * @param obj  对象
     * @param func 自定义处理函数
     */
    public static void setBeanByField(Object obj, Function<String, JBase> func) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(JIgnore.class)) {
                FieldBean fieldBean = setValueByMethod(obj, field);
                // 利用反射先通过方法设置属性值
                JBase jBase = func.apply(fieldBean.getName());
                if (Objects.isNull(jBase) || Objects.isNull(jBase.getValue())) {
                    setValueByMethod(obj, field, null);
                    continue;
                }
                setValueByMethod(obj, field, getValue(obj, jBase, field));
                // 若利用方法设置不到属性值，就利用属性设置但此方法会打破属性的私有性
                if (setTag) {
                    try {
                        field.setAccessible(true);
                        jBase = func.apply(fieldBean.getName());
                        if (Objects.isNull(jBase) || Objects.isNull(jBase.getValue())) {
                            field.set(obj, null);
                            continue;
                        }
                        Object value = getValue(obj, jBase, field);
                        if (!Objects.isNull(value)) {
                            field.set(obj, value);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 为setValue提供准确的类型值
     *
     * @param source 包装对象
     * @param jBase  数据源
     * @param field  字段
     * @return 对应类型
     */
    private static Object getValue(Object source, JBase jBase, Field field) {
        Class<?> type = field.getType();
        String typeName = type.getName();
        if ((typeName.equals(Long.class.getTypeName()) || typeName.equals(long.class.getTypeName())) && field.isAnnotationPresent(JFString.class)) {
            return Long.valueOf(((JString) jBase).getValue());
        }

        if (isBaseTypeof(field)) {
            return jBase.getValue();
        }

        if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
            return new JByte(jBase).getValue();
        }

        if (field.getType().equals(Character.class) || field.getType().equals(char.class)) {
            return new JChar(jBase).getValue();
        }

        if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
            return new JFloat(jBase).getValue();
        }

        if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
            return new JShort(jBase).getValue();
        }
        if (typeName.equals(Class.class.getTypeName())) {
            // Object
            return new JClass(jBase).getValue();
        }
        if (typeName.equals(Date.class.getName())) {
            if (field.isAnnotationPresent(JFormat.class)) {
                JFormat format = field.getDeclaredAnnotation(JFormat.class);
                if (format.value().equals("#")) {
                    return new JDate(jBase).getValue();
                } else {
                    return new JDate(jBase, format.value()).getValue();
                }
            }
        }
        if (field.isAnnotationPresent(JParse.class)) {
            Class<?> sourceClass = source.getClass();
            return getPlugsObject(jBase, field, sourceClass);
        }
        if (field.getType().isArray()) {
            // Integer[] ...
            Class<?> mySelf = isMySelf(field);
            if (!Objects.isNull(mySelf) && !JBase.isBase(mySelf)) {
                return BasePlug.getArray(jBase, mySelf);
            }
            return ArrayUtil.getArr(jBase, field);
        }
        if (typeName.equals(List.class.getTypeName())) {
            // List
            Class<?> mySelf = isMySelf(field);
            if (!Objects.isNull(mySelf) && !JBase.isBase(mySelf)) {
                return BasePlug.getList(jBase, mySelf);
            }
            return ArrayUtil.getList(jBase, field);
        }
        if (typeName.equals(Map.class.getTypeName())) {
            // Map
            Class<?> mySelf = isMySelf(field);
            if (!Objects.isNull(mySelf) && !JBase.isBase(mySelf)) {
                return BasePlug.getMap(jBase, mySelf);
            }
            return ArrayUtil.getMap(jBase, field);
        }
        if (!JBase.isBase(field.getType())) {
            return BasePlug.getObject(jBase, field.getType());
        }
        return jBase.getValue();
    }

    /**
     * 判断是否为自身转化
     *
     * @param field 字段
     * @return 是否为自身转化
     */
    private static Class<?> isMySelf(Field field) {
        if (field.isAnnotationPresent(JCollectType.class)) {
            return field.getDeclaredAnnotation(JCollectType.class).type();
        }
        return null;
    }

    /**
     * 获取插件提供的类型值
     *
     * @param jBase       数据源
     * @param field       字段
     * @param sourceClass 包装对象的Class
     * @return 对应类型
     */
    private static Object getPlugsObject(JBase jBase, Field field, Class<?> sourceClass) {
        JPConfigAnno jpConfigAnno;
        jpConfigAnno = JPConfigAnnoContext.getAnnoConfigContext().getContext().get(sourceClass.getTypeName());
        if (Objects.isNull(jpConfigAnno)) {
            ObjectParse.init(sourceClass);
        }
        JParse jParse = field.getDeclaredAnnotation(JParse.class);
        if (!sourceClass.isAnnotationPresent(JPConfig.class)) {
            return null;
        }
        Class<?> plug = sourceClass.getDeclaredAnnotation(JPConfig.class).plugs()[jParse.pos()];
        String methodName = String.format(ObjectParse.TEMPLATE, plug.getTypeName(), field.getType().getTypeName(), jParse.method());
        Object[] objects = jpConfigAnno.getMethodStore().get(methodName);
        if (Objects.isNull(objects)) {
            return null;
        }
        Method method = (Method) objects[1];
        try {
            return method.invoke(objects[0], jBase);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // log
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否为基础数据类型
     *
     * @param field 当前字段
     * @return 是否为基础数据类型
     */
    private static boolean isBaseTypeof(Field field) {
        Class<?> type = field.getType();
        return type.equals(Boolean.class) || type.equals(boolean.class) || type.equals(Double.class) || type.equals(double.class) || type.equals(Integer.class) || type.equals(int.class) || type.equals(Long.class) || type.equals(long.class) || type.equals(String.class) || type.equals(Object.class);
    }
}
