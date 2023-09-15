package com.zzwl.jpkit.parse;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.plugs.JBasePlug;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.typeof.JObject;
import com.zzwl.jpkit.utils.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0
 */
public class ObjectParse {
    private final JBase jBase;

    public ObjectParse(ITypeof<Object> typeof) {
        this.jBase = (JBase) typeof;
    }


    /**
     * JSON转化转化为对象
     *
     * @param clazz 类型
     * @param <B>   转化后的类型
     * @return 转化好的对象
     */
    @SafeVarargs
    public final <B> B parse(Class<B> clazz, Class<? extends JBasePlug<?>>... auxiliary) {
        Object bean = createBean(clazz);
        Map<String, JBasePlug<?>> res = new HashMap<>();
        if (auxiliary.length > 0) {
            init(res, auxiliary);
        }
        JObject jo = (JObject) this.jBase;
        ReflectUtil.setBeanByField(bean, res, (name) -> jo.getValue().get(name), auxiliary);
        return (B) bean;
    }

    /**
     * 获取父类的泛型类型
     *
     * @param clazz 泛型类型
     * @return 泛型类型
     */
    private static String getSuperClazz(Class<? extends JBasePlug<?>> clazz) {
        String name = clazz.getGenericSuperclass().getTypeName();
        Pattern compile = Pattern.compile(".*?<(.*?)>");
        Matcher matcher = compile.matcher(name);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return Object.class.getTypeName();
    }

    /**
     * 通过注解初始化自定义插件信息
     */
    @SafeVarargs
    public static void init(Map<String, JBasePlug<?>> res, Class<? extends JBasePlug<?>>... auxiliary) {
        for (Class<? extends JBasePlug<?>> jBasePlugClazz : auxiliary) {
            res.put(getSuperClazz(jBasePlugClazz), (JBasePlug<?>) createBean(jBasePlugClazz));
        }
    }

    /**
     * 通过class获得一个对象
     *
     * @param clazz 源
     * @return 根据Class创建的对象
     */
    public static Object createBean(Class<?> clazz) {
        try {
            // 使用无参构造函数
            Constructor<?> constructor = Class.forName(clazz.getName()).getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            // log 没有无参构造函数
            try {
                Constructor<?>[] constructors = Class.forName(clazz.getName()).getDeclaredConstructors();
                Constructor<?> constructor = constructors[0];
                Parameter[] parameters = constructor.getParameters();
                Object[] objects = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    objects[i] = JBase.getBaseValue(parameters[i].getType());
                }
                return constructor.newInstance(objects);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                     InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // log 不能创建实例
            throw new RuntimeException(e);
        }
    }
}
