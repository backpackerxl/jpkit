package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.anno.*;
import com.zzwl.jpkit.bean.AnnoConfig;
import com.zzwl.jpkit.bean.AnnoConfigContext;
import com.zzwl.jpkit.bean.FieldBean;
import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.parse.ObjectParse;
import com.zzwl.jpkit.plugs.BasePlug;
import com.zzwl.jpkit.typeof.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 特殊字段的特殊处理方式
     *
     * @param field 字段
     * @param o     对象
     * @return 字段值
     */
    private static Object getObject(Field field, Object o) {
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
        } else if (ArrayUtil.isArray(o)) {
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

    public static void setValueByMethod(Object obj, Field field, Object value) {
        try {
            Class<?> type = field.getType();
            Method method = obj.getClass().getDeclaredMethod(StringUtil.getMethodNameByFieldType(StringUtil.basicSetPrefix, type, field.getName()), type);
            if (!method.isAnnotationPresent(JIgnore.class)) {
                method.invoke(obj, value);
            }
            setTag = false;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // log set error
            setTag = true;
            //throw new RuntimeException(e);
        }
    }

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
                        field.set(obj, getValue(obj, jBase, field));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

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
            try {
                return Class.forName(((JString) jBase).getValue());
            } catch (ClassNotFoundException e) {
                return null;
            }
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
        // 符合操作特殊类型
        AnnoConfig config = AnnoConfigContext.getAnnoConfigContext().getContext().get(source.getClass().getTypeName());
        Map<String, List<Class<?>>> types = config.getTypes();
        boolean tag = false;
        String key = "";
        if (ArrayUtil.isArray(field)) {
            // Integer[] ...
            for (Map.Entry<String, List<Class<?>>> entry : types.entrySet()) {
                if (entry.getValue().contains(type)) {
                    tag = true;
                    key = entry.getKey();
                    break;
                }
            }
            if (tag) {
                return getObj(jBase, BasePlug.GET_ARR, type, config.getTargets().get(key));
            } else {
                return ArrayUtil.getArr(jBase, field);
            }
        }
        if (typeName.equals(List.class.getTypeName())) {
            // List
            for (Map.Entry<String, List<Class<?>>> entry : types.entrySet()) {
                if (entry.getValue().contains(type) && field.isAnnotationPresent(JCollectType.class)) {
                    tag = true;
                    key = entry.getKey();
                    break;
                }
            }
            if (tag) {
                return getObj(jBase, BasePlug.GET_LIST, field.getDeclaredAnnotation(JCollectType.class).type(), config.getTargets().get(key));
            } else {
                return ArrayUtil.getList(jBase, field);
            }
        }
        if (typeName.equals(Map.class.getTypeName())) {
            for (Map.Entry<String, List<Class<?>>> entry : types.entrySet()) {
                if (entry.getValue().contains(type) && field.isAnnotationPresent(JCollectType.class)) {
                    tag = true;
                    key = entry.getKey();
                    break;
                }
            }
            // Map
            if (tag) {
                return getObj(jBase, BasePlug.GET_MAP, field.getDeclaredAnnotation(JCollectType.class).type(), config.getTargets().get(key));
            } else {
                return ArrayUtil.getMap(jBase, field);
            }
        }
        for (Map.Entry<String, List<Class<?>>> entry : types.entrySet()) {
            if (entry.getValue().contains(type)) {
                tag = true;
                key = entry.getKey();
                break;
            }
        }
        if (tag) {
            return getObj(jBase, BasePlug.GET_OBJECT, type, config.getTargets().get(key));
        }
        return jBase.getValue();
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

    private static Object getObj(JBase jBase, String name, Class<?> type, Object target) {
        try {
            Method method = target.getClass().getDeclaredMethod(name, JBase.class);
            if (method.isAnnotationPresent(JFieldType.class)) {
                JFieldType fieldType = method.getDeclaredAnnotation(JFieldType.class);
                if (Arrays.asList(fieldType.type()).contains(type)) {
                    return method.invoke(target, jBase);
                }
            }
            return null;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // log :失败
            return null;
        }
    }
}
