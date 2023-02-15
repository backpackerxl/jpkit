package com.zzwl.jpkit.conversion;

import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.file.FileUtil;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ArrayUtil;
import com.zzwl.jpkit.utils.ReflectUtil;
import com.zzwl.jpkit.utils.StringUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @since 1.0
 */
public final class BToJSON<B> {
    // 需要转化为json字符串的java bean
    private final B bean;
    // 转化json过程中的全局缩进单位存储量, 默认第一行和最后一行不缩进, 可通过set方法配置
    private static Integer tab = 0;
    // 转化json过程中的单位缩进长度, 可通过set方法配置, 默认为2个单位长度
    private static Integer beforeTab = 2;
    // 转化json过程中的缩进填充字符, 可通过set方法配置, 默认为空格
    private static char tabCharacter = ' ';
    // 是否将long类型当字符串处理
    private static boolean longToStr = false;

    public BToJSON(B bean) {
        this.bean = bean;
    }

    public static Integer getTab() {
        return tab;
    }

    public static void setTab(Integer tab) {
        BToJSON.tab = tab;
    }

    public static Integer getBeforeTab() {
        return beforeTab;
    }

    public static void setBeforeTab(Integer beforeTab) {
        BToJSON.beforeTab = beforeTab;
    }

    public static char getTabCharacter() {
        return tabCharacter;
    }

    public static void setTabCharacter(char tabCharacter) {
        BToJSON.tabCharacter = tabCharacter;
    }

    public static void setLongToStr(boolean longToStr) {
        BToJSON.longToStr = longToStr;
    }

    /**
     * 紧凑型JSON字符串
     * <blockquote><pre>
     *     JSON.stringify(obj).terse();
     * </pre></blockquote>
     *
     * @return JSON字符串
     */
    public String terse() {
        // 当格式化json字符串方法调用后, 再调用紧凑型json字符串方法会导致紧凑型json字符串方法输出样式错乱,
        // 因此必须重置ReflectUtil::isPretty变量
        ReflectUtil.setIsPretty(false);
        // 处理空对象
        if (Objects.isNull(bean)) {
            return "null";
        }
        // 处理JBase对象
        if (bean instanceof JBase) {
            return JSON.stringify(((JBase) bean).getValue()).terse();
        }
        // 处理数组对象
        if (bean.getClass().isArray()) {
            return ArrayUtil.compileArray(bean, false, false);
        }
        // 处理特殊类型
        if (bean instanceof String || bean instanceof Character || bean instanceof Date) {
            return String.format("\"%s\"", bean);
        }
        // 处理Class类型
        if (bean instanceof Class) {
            return String.format("\"%s\"", ((Class<?>) bean).getTypeName());
        }
        // 处理 List
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            if (longToStr) {
                for (B b : bs) {
                    s.append("\"").append(JSON.stringify(b).terse()).append("\",");
                }
            } else {
                for (B b : bs) {
                    s.append(JSON.stringify(b).terse()).append(",");
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            return String.format("[%s]", StringUtil.substringByNumber(s.toString(), 1));
        }
        // 处理 Map
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            if (longToStr) {
                for (String key : bs.keySet()) {
                    s.append("\"").append(key).append("\":\"").append(JSON.stringify(bs.get(key)).terse()).append("\",");
                }
            } else {
                for (String key : bs.keySet()) {
                    s.append("\"").append(key).append("\":").append(JSON.stringify(bs.get(key)).terse()).append(",");
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            return String.format("{%s}", StringUtil.substringByNumber(s.toString(), 1));
        }
        // 处理基础数据类型, 除 Date, String, char
        if (JBase.isBase(bean)) {
            return bean.toString();
        }
        // 处理普通类型
        return String.format("{%s}", StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> String.format("\"%s\":%s,", name, obj)), 1));
    }

    /**
     * 紧凑型JSON字符串, 按量转化
     * <blockquote><pre>
     *     1.返回第一条数据的json字符串
     *     JSON.stringify(obj).terse(1);
     *     2.当limit为负数或者超过数据存储量时返回所有数据的json字符串
     * </pre></blockquote>
     *
     * @param limit 返回条数
     * @return JSON字符串
     */
    public String terse(int limit) {
        // 处理 List
        // 当格式化json字符串方法调用后, 再调用紧凑型json字符串方法会导致紧凑型json字符串方法输出样式错乱,
        // 因此必须重置ReflectUtil::isPretty变量
        ReflectUtil.setIsPretty(false);
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            if (bs.size() <= limit || limit <= 0) {
                return terse();
            }
            int i = 0;
            if (longToStr) {
                for (B b : bs) {
                    if (i == limit) {
                        break;
                    }
                    s.append("\"").append(JSON.stringify(b).terse()).append("\",");
                    i++;
                }
            } else {
                for (B b : bs) {
                    if (i == limit) {
                        break;
                    }
                    s.append(JSON.stringify(b).terse()).append(",");
                    i++;
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            return String.format("[%s]", StringUtil.substringByNumber(s.toString(), 1));
        }
        // 处理 Map
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            if (bs.size() <= limit || limit <= 0) {
                return terse();
            }
            int i = 0;
            if (longToStr) {
                for (String key : bs.keySet()) {
                    if (i == limit) {
                        break;
                    }
                    s.append("\"").append(key).append("\":\"").append(JSON.stringify(bs.get(key)).terse()).append("\",");
                    i++;
                }
            } else {
                for (String key : bs.keySet()) {
                    if (i == limit) {
                        break;
                    }
                    s.append("\"").append(key).append("\":").append(JSON.stringify(bs.get(key)).terse()).append(",");
                    i++;
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            return String.format("{%s}", StringUtil.substringByNumber(s.toString(), 1));
        }
        return terse();
    }

    /**
     * 格式化JSON字符串
     * <blockquote><pre>
     *     JSON.stringify(obj).pretty();
     * </pre></blockquote>
     *
     * @return JSON字符串
     */
    public String pretty() {
        // 处理空对象
        if (Objects.isNull(bean)) {
            return "null";
        }
        // 处理JBase对象
        if (bean instanceof JBase) {
            return JSON.stringify(((JBase) bean).getValue()).pretty();
        }
        // 处理数组对象
        if (bean.getClass().isArray()) {
            return ArrayUtil.compileArray(bean, true, false);
        }
        // 处理特殊类型
        if (bean instanceof String || bean instanceof Character || bean instanceof Date) {
            return String.format("\"%s\"", bean);
        }
        // 处理Class类型
        if (bean instanceof Class) {
            return String.format("\"%s\"", ((Class<?>) bean).getTypeName());
        }
        // 处理 List 类型
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            s.append("[\n");
            ReflectUtil.setIsPretty(true);
            // 设置缩进
            setTab(getTab() + getBeforeTab());
            // 获取缩进
            String white = StringUtil.getWhiteByNumber(getTab());
            if (longToStr) {
                for (B b : bs) {
                    s.append(white).append("\"").append(JSON.stringify(b).pretty()).append("\",\n");
                }
            } else {
                for (B b : bs) {
                    s.append(white).append(JSON.stringify(b).pretty()).append(",\n");
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            // 将缩进恢复
            setTab(getTab() - getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(getTab()));
        }
        // 处理 Map 类型
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            s.append("{\n");
            ReflectUtil.setIsPretty(true);
            // 设置缩进
            setTab(getTab() + getBeforeTab());
            // 获取缩进
            String white = StringUtil.getWhiteByNumber(getTab());
            if (longToStr) {
                for (String key : bs.keySet()) {
                    s.append(white).append("\"").append(key).append("\": \"").append(JSON.stringify(bs.get(key)).pretty()).append("\",\n");
                }
            } else {
                for (String key : bs.keySet()) {
                    s.append(white).append("\"").append(key).append("\": ").append(JSON.stringify(bs.get(key)).pretty()).append(",\n");
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            // 将缩进恢复
            setTab(getTab() - getBeforeTab());
            return String.format("%s\n%s}", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(getTab()));
        }
        // 处理基础数据类型,除 String, char, Date
        if (JBase.isBase(bean)) {
            return bean.toString();
        }
        // 处理普通类型
        ReflectUtil.setIsPretty(true);
        // 设置缩进
        setTab(getTab() + getBeforeTab());
        String white = StringUtil.getWhiteByNumber(getTab());
        return String.format("{\n%s\n%s}", StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> String.format("%s\"%s\": %s,\n", white, name, obj)), 2), StringUtil.getWhiteByNumber(getTab()));
    }

    /**
     * 格式化JSON字符串, 按量转化
     * <blockquote><pre>
     *     1.返回第一条数据的格式化json字符串
     *     JSON.stringify(obj).pretty(1);
     *     2.当limit为负数或者超过数据存储量时返回所有数据的格式化json字符串
     * </pre></blockquote>
     *
     * @param limit 返回条数
     * @return JSON字符串
     */
    public String pretty(int limit) {
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(List.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            List<B> bs = (List<B>) bean;
            if (bs.size() == 0) {
                return "[]";
            }
            s.append("[\n");
            ReflectUtil.setIsPretty(true);
            // 设置缩进
            setTab(getTab() + getBeforeTab());
            // 获取缩进
            String white = StringUtil.getWhiteByNumber(getTab());
            if (bs.size() <= limit || limit <= 0) {
                return pretty();
            }
            int i = 0;
            if (longToStr) {
                for (B b : bs) {
                    if (i == limit) {
                        break;
                    }
                    String pretty = JSON.stringify(b).pretty();
                    s.append(white).append("\"").append(pretty).append("\",\n");
                    i++;
                }
            } else {
                for (B b : bs) {
                    if (i == limit) {
                        break;
                    }
                    String pretty = JSON.stringify(b).pretty();
                    s.append(white).append(pretty).append(",\n");
                    i++;
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            // 将缩进恢复
            setTab(getTab() - getBeforeTab());
            return String.format("%s\n%s]", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(getTab()));
        }
        if (Arrays.stream(bean.getClass().getInterfaces()).anyMatch(aClass -> aClass.getTypeName().equals(Map.class.getTypeName()))) {
            StringBuilder s = new StringBuilder();
            Map<String, B> bs = (Map<String, B>) bean;
            if (bs.size() == 0) {
                return "{}";
            }
            s.append("{\n");
            ReflectUtil.setIsPretty(true);
            // 设置缩进
            setTab(getTab() + getBeforeTab());
            // 获取缩进
            String white = StringUtil.getWhiteByNumber(getTab());
            if (bs.size() <= limit || limit <= 0) {
                return terse();
            }
            int i = 0;
            if (longToStr) {
                for (String key : bs.keySet()) {
                    if (i == limit) {
                        break;
                    }
                    s.append(white).append("\"").append(key).append("\": \"").append(JSON.stringify(bs.get(key)).pretty()).append("\",\n");
                    i++;
                }
            } else {
                for (String key : bs.keySet()) {
                    if (i == limit) {
                        break;
                    }
                    s.append(white).append("\"").append(key).append("\": ").append(JSON.stringify(bs.get(key)).pretty()).append(",\n");
                    i++;
                }
            }
            // 关闭Long to String
            setLongToStr(false);
            // 将缩进恢复
            setTab(getTab() - getBeforeTab());
            return String.format("%s\n%s}", StringUtil.substringByNumber(s.toString(), 2), StringUtil.getWhiteByNumber(getTab()));
        }
        return pretty();
    }

    /**
     * 保存json文件
     * <blockquote><pre>
     *     1.当 savePretty 为true时保存为格式化的json, 反之则为紧凑型json
     *     JSON.stringify(user).save("src\\test\\resources\\db.json", false);
     * </pre></blockquote>
     *
     * @param p      路径
     * @param pretty 是否格式化
     */
    public void save(String p, boolean pretty) {
        String res = terse();
        if (pretty) {
            res = pretty();
            tab = 0;
        }
        FileUtil.write(res, p);
    }

    /**
     * 保存json文件
     * <blockquote><pre>
     *     1.默认格式化保存
     *     JSON.stringify(user).save("src\\test\\resources\\db.json");
     * </pre></blockquote>
     *
     * @param p 路径
     */
    public void save(String p) {
        save(p, true);
    }

    /**
     * 按量保存json文件
     * <blockquote><pre>
     *     1.当 savePretty 为true时保存为格式化的json, 反之则为紧凑型json
     *     2.保存第一条
     *     JSON.stringify(user).save("src\\test\\resources\\db.json", false, 1);
     * </pre></blockquote>
     *
     * @param p      路径
     * @param pretty 是否格式化
     * @param limit  保存条数
     */
    public void save(String p, boolean pretty, int limit) {
        String res = terse(limit);
        if (pretty) {
            res = pretty(limit);
            tab = 0;
        }
        FileUtil.write(res, p);
    }

    /**
     * 按量保存json文件
     * <blockquote><pre>
     *     1.默认格式化保存
     *     2.保存第一条
     *     JSON.stringify(user).save("src\\test\\resources\\db.json", 1);
     * </pre></blockquote>
     *
     * @param p     路径
     * @param limit 保存条数
     */
    public void save(String p, int limit) {
        save(p, true, limit);
    }
}
