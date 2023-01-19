package com.zzwl.jpkit.conversion;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.utils.ReflectUtil;
import com.zzwl.jpkit.utils.StringUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BToJSON<B> {
    private final B bean;

    public BToJSON(B bean) {
        this.bean = bean;
    }

    /**
     * 紧凑型JSON字符串
     * <blockquote><pre>
     *     System.out.println(JSON.stringify(obj).terse());
     * </pre></blockquote>
     *
     * @return JSON字符串
     */
    public String terse() {
        if (Objects.isNull(bean)) {
            return "null";
        }

        if (ArrayUtil.isArray(bean)) {
            return ArrayUtil.compileArray(bean, false, -1);
        }

        if (JBase.isBase(bean)) {
            return bean.toString();
        }

        if (bean instanceof String || bean instanceof Character || bean instanceof Date) {
            return String.format("\"%s\"", bean);
        }

        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            for (B b : bs) {
                s.append(JSON.stringify(b).terse()).append(",");
            }
            return String.format("[%s]", StringUtil.substringByNumber(s.toString(), 1));
        }
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            for (String key : bs.keySet()) {
                s.append("\"").append(key).append("\":").append(JSON.stringify(bs.get(key)).terse()).append(",");
            }
            return String.format("{%s}", StringUtil.substringByNumber(s.toString(), 1));
        }
        return String.format("{%s}", StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> String.format("\"%s\":%s,", name, obj)), 1));
    }

    /**
     * 格式化JSON字符串
     * <blockquote><pre>
     *     System.out.println(JSON.stringify(obj).pretty());
     * </pre></blockquote>
     *
     * @return JSON字符串
     */
    public String pretty() {
        if (Objects.isNull(bean)) {
            return "null";
        }

        if (ArrayUtil.isArray(bean)) {
            return ArrayUtil.compileArray(bean, true, 1);
        }

        if (JBase.isBase(bean)) {
            return bean.toString();
        }

        if (bean instanceof String || bean instanceof Character || bean instanceof Date) {
            return String.format("\"%s\"", bean);
        }
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            for (B b : bs) {
                s.append("  ").append(JSON.stringify(b).pretty()).append(",\n");
            }
            return String.format("[\n%s\n]", StringUtil.substringByNumber(s.toString(), 2));
        }
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            for (String key : bs.keySet()) {
                s.append("  \"").append(key).append("\": ").append(JSON.stringify(bs.get(key)).pretty()).append(",\n");
            }
            return String.format("{\n%s\n}", StringUtil.substringByNumber(s.toString(), 2));
        }
        ReflectUtil.setIsPretty(true);
        return String.format("{\n%s\n}", StringUtil.substringByNumber(
                ReflectUtil.doBeanByField(
                        bean,
                        (name, obj) -> String.format("  \"%s\": %s,\n", name, obj)
                ), 2));
    }

    /**
     * 保存json文件
     * <blockquote><pre>
     *     JSON.stringify(user).save("D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json");
     * </pre></blockquote>
     *
     * @param p 路径
     */
    public void save(String p) {
        Path path = Paths.get(p);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            Files.write(path, terse().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
