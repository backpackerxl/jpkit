package com.zzwl.jpkit.core;

import com.zzwl.jpkit.bean.Options;
import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.file.FileUtil;
import com.zzwl.jpkit.network.NetUtil;
import com.zzwl.jpkit.parse.JSONParse;
import com.zzwl.jpkit.parse.ObjectParse;
import com.zzwl.jpkit.typeof.JArray;
import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.typeof.JObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 */
public class JSON {
    private JSON() {
    }

    /**
     * 对象转JSON字符串
     * <blockquote><pre>
     *    <ul>
     *      <li>
     *        <p>紧凑型JSON字符串</p>
     *        <p>String json = JSON.stringify(obj).terse();</p>
     *      </li>
     *      <li>
     *          <p>格式化JSON字符串</p>
     *          <p>String json = JSON.stringify(obj).pretty();</p>
     *      </li>
     *     </ul>
     * </pre></blockquote>
     *
     * @param obj 待转化对象
     * @param <B> 泛型
     * @return BToJSON<B>
     */
    public static <B> BToJSON<B> stringify(B obj) {
        return new BToJSON<>(obj);
    }

    /**
     * JSON解析为JBase对象
     * <blockquote><pre>
     * String json =
     * "{
     *    "username": "zzwl",
     *    "user_code": 300,
     *    "admin": true
     * }";
     *    JObject parse = (JObject) JSON.parse(json);
     *    <ul>
     *      <li>
     *          <p>JString username = (JString) parse.getValue().get("username");</p>
     *      </li>
     *      <li>
     *          <p>JShort code = new JShort(parse.getValue().get("user_code"));</p>
     *      </li>
     *      <li>
     *          <p>JBool isAdmin = (JBool) parse.getValue().get("admin");</p>
     *      </li>
     *     </ul>
     * </pre></blockquote>
     *
     * @param json JSON字符串
     * @return ITypeof<Object>
     */
    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json =
     *     "{
     *        "username": "zzwl",
     *        "user_code": 300,
     *        "admin": true
     *      }";
     *     B user = JSON.parse(json, B.class);
     *
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转化对象的类型
     * @return JSON字符串转化成的Java对象
     */
    public static <B> B parse(String json, Class<B> clazz) {
        ITypeof<Object> typeof = parse(json);
        if (typeof instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.parseList(String json, Class<B> clazz)");
        }
        return parse(typeof, clazz);
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json =
     *     "{
     *        "username": "zzwl",
     *        "user_code": 300,
     *        "admin": true
     *      }";
     *
     *     B user = JSON.parse(JSON.parse(json), B.class);
     *
     * </pre></blockquote>
     *
     * @param typeof JBase 对象
     * @param clazz  类型
     * @param <B>    转换成功后的类型
     * @return 解析好的对象
     */
    public static <B> B parse(ITypeof<Object> typeof, Class<B> clazz) {
        if (typeof instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.parseList(String json, Class<B> clazz)");
        }
        return new ObjectParse(typeof).parse(clazz);
    }

    /**
     * JSON解析为Java的List<B>对象
     * <blockquote><pre>
     *     String json =
     *     "[
     *        {
     *          "username": "zzwl",
     *          "user_code": 300,
     *          "admin": true,
     *        },
     *        {
     *          "username": "zzwl",
     *          "user_code": 300,
     *          "admin": true,
     *         }
     *       ]";
     *
     *     List<B> user = JSON.parseList(json, B.class);
     *
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转成功后的类型
     * @return 解析好的对象
     */
    public static <B> List<B> parseList(String json, Class<B> clazz) {
        ITypeof<Object> parse = parse(json);
        if (parse instanceof JObject) {
            throw new RuntimeException("load error, please use JSON.parseMap(String json, Class<B> clazz)");
        }
        List<B> list = new ArrayList<>();
        JArray arr = (JArray) parse;
        for (JBase jBase : arr.getValue()) {
            list.add(parse(jBase, clazz));
        }
        return list;
    }

    /**
     * JSON解析为Java的Map<String ,B> 对象
     * <blockquote><pre>
     *     String json =
     *     "[
     *        {
     *          "user_1" : {
     *                        "username": "zzwl",
     *                        "user_code": 300,
     *                        "admin": true,
     *                      }
     *        },
     *        {
     *           "user_2": {
     *                        "username": "zzwl",
     *                        "user_code": 300,
     *                        "admin": true,
     *                      }
     *         }
     *      ]";
     *
     *     Map<String, B> user = JSON.parseMap(JSON.parse(json), B.class);
     *
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转成功后的类型
     * @return 解析好的Map
     */
    public static <B> Map<String, B> parseMap(String json, Class<B> clazz) {
        ITypeof<Object> parse = parse(json);
        if (parse instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.parseList(String json, Class<B> clazz)");
        }
        Map<String, B> map = new HashMap<>();
        JObject jo = (JObject) parse;
        Map<String, JBase> value = jo.getValue();
        for (String s : value.keySet()) {
            map.put(s, parse(value.get(s), clazz));
        }
        return map;
    }

    /**
     * 加载本地或网络JSON资源为对应的JBase对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     JBase load_local = JSON.load(local);
     *     JBase load_net = JSON.load(network);
     * </pre></blockquote>
     *
     * @param path 路径
     * @return ITypeof<?>
     */
    public static ITypeof<Object> load(String path) {
        if (path.startsWith(NetUtil.HTTP) || path.startsWith(NetUtil.HTTPS)) {
            return new JSONParse(NetUtil.getJSON(path)).parse();
        } else {
            return new JSONParse(FileUtil.getJSON(path)).parse();
        }
    }

    /**
     * 加载本地或网络(需要请求参数)JSON资源为对应的JBase对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     JBase load_local = JSON.load(local);
     *     JBase load_net = JSON.load(network);
     * </pre></blockquote>
     *
     * @param path 路径
     * @param pram pram
     * @return ITypeof<?>
     */
    public static ITypeof<Object> load(String path, Options pram) {
        if (path.startsWith(NetUtil.HTTP) || path.startsWith(NetUtil.HTTPS)) {
            return new JSONParse(NetUtil.getJSON(path, pram)).parse();
        } else {
            return new JSONParse(FileUtil.getJSON(path)).parse();
        }
    }

    /**
     * 加载本地或网络JSON资源为对应的Java Bean对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     B load_local = JSON.load(local, B.class);
     *     B load_net = JSON.load(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的类型
     */
    public static <B> B load(String path, Class<B> clazz) {
        ITypeof<Object> load = load(path);
        if (load instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.loadList(path, Class<B> clazz)");
        }
        return parse(load, clazz);
    }

    /**
     * 加载本地或网络(需要请求参数)JSON资源为对应的Java Bean对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     B load_local = JSON.load(local, B.class);
     *     B load_net = JSON.load(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param pram  requestPram
     * @param <B>   转化的类型
     * @return 转化后的类型
     */
    public static <B> B load(String path, Options pram, Class<B> clazz) {
        ITypeof<Object> load = load(path, pram);
        if (load instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.loadList(path, Class<B> clazz)");
        }
        return parse(load, clazz);
    }

    /**
     * 加载本地或网络JSON资源为对应的List对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     List<B> list = JSON.loadList(local, B.class);
     *     List<B> list_baidu = JSON.loadList(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的List
     */
    public static <B> List<B> loadList(String path, Class<B> clazz) {
        ITypeof<Object> load = load(path);
        if (load instanceof JObject) {
            throw new RuntimeException("load error, please use JSON.load(path, Class<B> clazz)");
        }
        List<B> list = new ArrayList<>();
        JArray arr = (JArray) load;
        for (JBase jBase : arr.getValue()) {
            list.add(parse(jBase, clazz));
        }
        return list;
    }

    /**
     * 加载本地或网络(需要请求参数)JSON资源为对应的List对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     List<B> list = JSON.loadList(local, B.class);
     *     List<B> list_baidu = JSON.loadList(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param pram  pram
     * @param <B>   转化的类型
     * @return 转化后的List
     */
    public static <B> List<B> loadList(String path, Options pram, Class<B> clazz) {
        ITypeof<Object> load = load(path, pram);
        if (load instanceof JObject) {
            throw new RuntimeException("load error, please use JSON.load(path, Class<B> clazz)");
        }
        List<B> list = new ArrayList<>();
        JArray arr = (JArray) load;
        for (JBase jBase : arr.getValue()) {
            list.add(parse(jBase, clazz));
        }
        return list;
    }

    /**
     * 加载本地或网络JSON资源为对应的Map对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     Map<String,B> list = JSON.loadMap(local, B.class);
     *     Map<String,B> list_baidu = JSON.loadMap(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的Map
     */
    public static <B> Map<String, B> loadMap(String path, Class<B> clazz) {
        ITypeof<Object> load = load(path);
        if (load instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.loadList(path, Class<B> clazz)");
        }
        Map<String, B> map = new HashMap<>();
        JObject jo = (JObject) load;
        Map<String, JBase> value = jo.getValue();
        for (String s : value.keySet()) {
            map.put(s, parse(value.get(s), clazz));
        }
        return map;
    }

    /**
     * 加载本地或网络(需要请求参数)JSON资源为对应的Map对象
     * <blockquote><pre>
     *     String local = "src\\test\\resources\\db.json";
     *
     *     String network = "network_url";
     *
     *     Map<String,B> list = JSON.loadMap(local, B.class);
     *     Map<String,B> list_baidu = JSON.loadMap(network, B.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param pram  pram
     * @param <B>   转化的类型
     * @return 转化后的Map
     */
    public static <B> Map<String, B> loadMap(String path, Options pram, Class<B> clazz) {
        ITypeof<Object> load = load(path, pram);
        if (load instanceof JArray) {
            throw new RuntimeException("load error, please use JSON.loadList(path, Class<B> clazz)");
        }
        Map<String, B> map = new HashMap<>();
        JObject jo = (JObject) load;
        Map<String, JBase> value = jo.getValue();
        for (String s : value.keySet()) {
            map.put(s, parse(value.get(s), clazz));
        }
        return map;
    }

    /**
     * 设置缩进量
     * <blockquote><pre>
     *     设置单位缩进量为1个单位, 默认为两个单位, 且默认缩进字符为 ' '
     *     JSON.setBeforeTab(1);
     * </pre></blockquote>
     *
     * @param beforeTab 单位缩进量
     */
    public static void setTabLength(Integer beforeTab) {
        BToJSON.setBeforeTab(beforeTab);
    }

    /**
     * 设置缩进字符
     * <blockquote><pre>
     *     设置单位缩进量为1个单位, 默认为两个单位, 且默认缩进字符为 ' '
     *     JSON.setBeforeTab(1);
     *     设置默认字符为 '\t'
     *     JSON.setTabCharacter('\t');
     * </pre></blockquote>
     *
     * @param tabCharacter 缩进字符
     */
    public static void setTabCharacter(char tabCharacter) {
        BToJSON.setTabCharacter(tabCharacter);
    }


    /**
     * 设置Long类型的Map或List以字符串输出
     * <blockquote><pre>
     *     设置以字符串形式输出
     *     JSON.setLongToString(true);
     * </pre></blockquote>
     *
     * @param longToStr 是否以字符串形式输出， 默认为 false
     */
    public static void setLongToString(boolean longToStr) {
        BToJSON.setLongToStr(longToStr);
    }
}
