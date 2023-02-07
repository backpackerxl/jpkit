package com.zzwl.jpkit.parse;

import com.zzwl.jpkit.anno.JPConfig;
import com.zzwl.jpkit.anno.JPMethod;
import com.zzwl.jpkit.bean.JPConfigAnno;
import com.zzwl.jpkit.bean.JPConfigAnnoContext;
import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.typeof.JObject;
import com.zzwl.jpkit.utils.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectParse {
    private final JBase jBase;
    public final static String TEMPLATE = "%s$%s$%s";

    public ObjectParse(ITypeof<Object> typeof) {
        this.jBase = (JBase) typeof;
    }


    /**
     * JSON转化转化为对象
     *
     * @param clazz 类型
     * @param <B>   转化后的类型
     * @return 转化好的东西
     */
    public <B> B parse(Class<B> clazz) {
        Object bean = createBean(clazz);
        if (!JPConfigAnnoContext.getAnnoConfigContext().getContext().containsKey(clazz.getTypeName())) {
            init(clazz);
        }
        JObject jo = (JObject) this.jBase;
        ReflectUtil.setBeanByField(bean, (name) -> jo.getValue().get(name));
        return (B) bean;
    }

    /**
     * 通过注解初始化自定义插件信息
     */
    public static void init(Class<?> clazz) {
        if (clazz.isAnnotationPresent(JPConfig.class)) {
            Map<String, JPConfigAnno> context = JPConfigAnnoContext.getAnnoConfigContext().getContext();
            JPConfig jpConfig = clazz.getDeclaredAnnotation(JPConfig.class);
            JPConfigAnno configAnno = new JPConfigAnno();
            Map<String, Object[]> methodStore = configAnno.getMethodStore();
            for (Class<?> plug : jpConfig.plugs()) {
                for (Method method : plug.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(JPMethod.class)) {
                        Object[] objects = new Object[2];
                        objects[0] = createBean(plug);
                        objects[1] = method;
                        methodStore.put(String.format(TEMPLATE, plug.getTypeName(), method.getReturnType().getTypeName(), method.getDeclaredAnnotation(JPMethod.class).value()), objects);
                    }
                }
            }
            context.put(clazz.getTypeName(), configAnno);
        }
    }

    /**
     * 通过class获得一个对象
     *
     * @param clazz 源
     * @return 对象
     */
    public static Object createBean(Class<?> clazz) {
        Object o;
        try {
            Constructor<?> constructor = Class.forName(clazz.getName()).getDeclaredConstructor();
            o = constructor.newInstance();
            return o;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            // log 没有构造函数
            throw new RuntimeException(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // log 不能创建实例
            throw new RuntimeException(e);
        }
    }


}
