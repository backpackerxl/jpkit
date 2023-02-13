package com.zzwl.jpkit.plugs.bytecode;

import java.lang.reflect.Field;

/**
 * @since 1.0
 */
public class SerializablePlug {

    private final Object obj;

    public SerializablePlug(Object obj) {
        this.obj = obj;
    }

    public void writeObjectClassToClasses() {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String name = clazz.getName();
        String oldName = name.substring(name.lastIndexOf('.') + 1);
        String newName = name.substring(name.lastIndexOf('.') + 1) + "Serializable";
        String format = String.format("public class %s{public String write(Object obj){ %s bean = (%s) obj; return \"%s\";}}", newName, oldName, oldName, writeMethod("bean", fields));
        System.out.println(format);
    }

    public String writeMethod(String prefix, Field[] fields) {
        StringBuilder s = new StringBuilder("{");
        for (Field field : fields) {
            s.append("\\").append("\"").append(field.getName()).append("\\").append("\":\"+").append(prefix).append(".get").append(field.getName().toUpperCase()).append("()");
        }
        return s + "+\"}";
    }
}
