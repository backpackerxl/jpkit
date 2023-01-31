package com.zzwl.jpkit.parse;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.typeof.JObject;
import com.zzwl.jpkit.utils.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
     * @return 转化好的东西
     */
    public <B> B parse(Class<B> clazz) {
        Object bean = createBean(clazz);
        JObject jo = (JObject) this.jBase;
        ReflectUtil.setBeanByField(bean, (name) -> jo.getValue().get(name));
        return (B) bean;
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
