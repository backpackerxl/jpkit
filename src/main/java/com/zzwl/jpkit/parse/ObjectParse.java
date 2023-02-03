package com.zzwl.jpkit.parse;

import com.zzwl.jpkit.anno.JConfig;
import com.zzwl.jpkit.bean.AnnoConfig;
import com.zzwl.jpkit.bean.AnnoConfigContext;
import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.typeof.JObject;
import com.zzwl.jpkit.utils.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ObjectParse {
    private final JBase jBase;

    private volatile static List<Class<?>> classes;
    private volatile static Object target;

    private volatile static AnnoConfig annoConfig;

    public ObjectParse(ITypeof<Object> typeof) {
        this.jBase = (JBase) typeof;
    }


    public static List<Class<?>> getClasses() {
        return classes;
    }


    public static Object getTarget() {
        return target;
    }

    public static AnnoConfig getAnnoConfig() {
        return annoConfig;
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
        if (!AnnoConfigContext.getAnnoConfigContext().getContext().containsKey(clazz.getTypeName())) {
            init(clazz, bean);
        }
        JObject jo = (JObject) this.jBase;
        ReflectUtil.setBeanByField(bean, (name) -> jo.getValue().get(name));
        return (B) bean;
    }

    /**
     * 通过注解初始化自定义插件信息
     *
     * @param bean 初始化数据
     */
    private void init(Class<?> clazz, Object bean) {
        Map<String, AnnoConfig> context = AnnoConfigContext.getAnnoConfigContext().getContext();
        if (bean.getClass().isAnnotationPresent(JConfig.class)) {
            Map<String, Object> targets = new HashMap<>();
            Map<String, List<Class<?>>> types = new HashMap<>();
            JConfig config = bean.getClass().getDeclaredAnnotation(JConfig.class);
            if (Objects.isNull(config)) {
                JConfig.Group group = bean.getClass().getDeclaredAnnotation(JConfig.Group.class);
                for (JConfig jConfig : group.value()) {
                    Class<?> value = jConfig.value();
                    targets.put(value.getTypeName(), createBean(value));
                    types.put(value.getTypeName(), Arrays.asList(jConfig.typeof()));
                }
            } else {
                Class<?> value = config.value();
                targets.put(value.getTypeName(), createBean(value));
                types.put(value.getTypeName(), Arrays.asList(config.typeof()));
            }
            context.put(clazz.getTypeName(), new AnnoConfig(targets, types));
        }
    }

    public Object createBean(Class<?> clazz) {
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
