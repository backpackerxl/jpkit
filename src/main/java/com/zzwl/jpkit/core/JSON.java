package com.zzwl.jpkit.core;

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

public class JSON {
    private JSON() {
    }

    /**
     * 对象转JSON字符串
     * <blockquote><pre>
     *    <ul>
     *      <li>
     *        <b>紧凑型JSON字符串<b/>
     *        <p>String json = JSON.stringify(obj).terse();</p>
     *        <p>System.out.println(json);</p>
     *      </li>
     *      <li>
     *          <b>格式化JSON字符串<b/>
     *          <p>String json = JSON.stringify(obj).pretty();</p>
     *          <p>System.out.println(json);</p>
     *      </li>
     *     <ul/>
     * </pre></blockquote>
     *
     * @param obj 待转化对象
     * @param <B> 泛型
     * @return <b>BToJSON<B><b/>
     */
    public static <B> BToJSON<B> stringify(B obj) {
        return new BToJSON<>(obj);
    }

    /**
     * JSON解析为对象
     * <blockquote><pre>
     * String json = "{
     *                      "username": "zzwl",
     *                      "user_code": 300,
     *                      "admin": true,
     *                      "create_time": "2023-01-13",
     *                      "nums": [
     *                         4545,
     *                         2121,
     *                         3636
     *                      ],
     *                      "strings": [
     *                         "zz",
     *                         "xx",
     *                         "ww"
     *                      ]
     *                    }"
     *    JObject parse = (JObject) JSON.parse(json);
     *    <ul>
     *      <li>
     *        <p>JArray number = (JArray) parse.getValue().get("nums");</p>
     *        <p>
     *           for (JBase jBase : number.getValue()) {
     *                System.out.println(jBase.getValue());
     *           }
     *        </p>
     *      </li>
     *      <li>
     *          <p>JDate date = new JDate(parse.getValue().get("create_time"), "yyyy-MM-dd");</p>
     *          <p>System.out.println(date);</p>
     *      </li>
     *      <li>
     *          <p>JShort code = new JShort(parse.getValue().get("user_code"));</p>
     *          <p>System.out.println(code);</p>
     *      </li>
     *     <ul/>
     * </pre></blockquote>
     *
     * @param json JSON字符串
     * @return <b>ITypeof<?></b>
     */
    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json = "{
     *                      "username": "zzwl",
     *                      "user_code": 300,
     *                      "admin": true,
     *                      "create_time": "2023-01-13",
     *                      "nums": [
     *                         4545,
     *                         2121,
     *                         3636
     *                      ],
     *                      "strings": [
     *                         "zz",
     *                         "xx",
     *                         "ww"
     *                      ]
     *                    }"
     *     User user = JSON.parse(json, User.class);
     *     System.out.println(user)
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转化对象的类型
     * @return JSON字符串转化成的Java对象
     */
    public static <B> B parse(String json, Class<B> clazz) {
        return parse(parse(json), clazz);
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json = "{
     *                      "username": "zzwl",
     *                      "user_code": 300,
     *                      "admin": true,
     *                      "create_time": "2023-01-13",
     *                      "nums": [
     *                         4545,
     *                         2121,
     *                         3636
     *                      ],
     *                      "strings": [
     *                         "zz",
     *                         "xx",
     *                         "ww"
     *                      ]
     *                    }"
     *     User user = JSON.parse(JSON.parse(json), User.class);
     *     System.out.println(user)
     * </pre></blockquote>
     *
     * @param typeof 第一次解析后的对象
     * @param clazz  类型
     * @param <B>    转成功后的类型
     * @return 解析好的对象
     */
    public static <B> B parse(ITypeof<Object> typeof, Class<B> clazz) {
        return new ObjectParse(typeof).parse(clazz);
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json = "[
     *                       {
     *                            "username": "zzwl",
     *                            "user_code": 300,
     *                            "admin": true,
     *                            "create_time": "2023-01-13",
     *                            "nums": [
     *                               4545,
     *                               2121,
     *                               3636
     *                            ],
     *                            "strings": [
     *                               "zz",
     *                               "xx",
     *                               "ww"
     *                            ]
     *                         },
     *                         {
     *                            "username": "zzwl",
     *                            "user_code": 300,
     *                            "admin": true,
     *                            "create_time": "2023-01-13",
     *                            "nums": [
     *                               4545,
     *                               2121,
     *                               3636
     *                            ],
     *                            "strings": [
     *                               "zz",
     *                               "xx",
     *                               "ww"
     *                            ]
     *                          }
     *                      ]"
     *     List<User> user = JSON.parseList(json, User.class);
     *     System.out.println(user)
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转成功后的类型
     * @return 解析好的对象
     */
    public static <B> List<B> parseList(String json, Class<B> clazz) {
        List<B> list = new ArrayList<>();
        JArray arr = (JArray) parse(json);
        for (JBase jBase : arr.getValue()) {
            list.add(parse(jBase, clazz));
        }
        return list;
    }

    /**
     * JSON解析为Java普通对象
     * <blockquote><pre>
     *     String json = "[
     *                       {
     *                        "user_1" : {
     *                                     "username": "zzwl",
     *                                     "user_code": 300,
     *                                     "admin": true,
     *                                     "create_time": "2023-01-13",
     *                                     "nums": [
     *                                       4545,
     *                                       2121,
     *                                       3636
     *                                     ],
     *                                     "strings": [
     *                                       "zz",
     *                                       "xx",
     *                                       "ww"
     *                                      ]
     *                                   }
     *                          },
     *                          {
     *                            "user_2": {
     *                                        "username": "zzwl",
     *                                        "user_code": 300,
     *                                        "admin": true,
     *                                        "create_time": "2023-01-13",
     *                                        "nums": [
     *                                          4545,
     *                                          2121,
     *                                          3636
     *                                         ],
     *                                         "strings": [
     *                                            "zz",
     *                                            "xx",
     *                                            "ww"
     *                                          ]
     *                                        }
     *                               }
     *                            ]"
     *     Map<String, User> user = JSON.parseMap(JSON.parse(json), User.class);
     *     System.out.println(user)
     * </pre></blockquote>
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <B>   转成功后的类型
     * @return 解析好的Map
     */
    public static <B> Map<String, B> parseMap(String json, Class<B> clazz) {
        Map<String, B> map = new HashMap<>();
        JObject jo = (JObject) parse(json);
        Map<String, JBase> value = jo.getValue();
        for (String s : value.keySet()) {
            map.put(s, parse(value.get(s), clazz));
        }
        return map;
    }

    /**
     * 加载json文件
     * <blockquote><pre>
     *     String local = "D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json"
     *     String network = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
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
     * <blockquote><pre>
     *     String local = "D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json"
     *     String network = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
     *     User load_local = JSON.load(local, User.class);
     *     BaiDu load_net = JSON.load(network, BaiDu.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的类型
     */
    public static <B> B load(String path, Class<B> clazz) {
        return parse(load(path), clazz);
    }

    /**
     * <blockquote><pre>
     *     String local = "D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json"
     *     String network = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
     *     List<User> list = JSON.loadList(local, User.class);
     *     List<BaiDu> list_baidu = JSON.loadList(network, BaiDu.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的List
     */
    public static <B> List<B> loadList(String path, Class<B> clazz) {
        List<B> list = new ArrayList<>();
        JArray arr = (JArray) load(path);
        for (JBase jBase : arr.getValue()) {
            list.add(parse(jBase, clazz));
        }
        return list;
    }

    /**
     * <blockquote><pre>
     *     String local = "D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json"
     *     String network = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
     *     Map<String,User> list = JSON.loadMap(local, User.class);
     *     Map<String,BaiDu> list_baidu = JSON.loadMap(network, BaiDu.class);
     * </pre></blockquote>
     *
     * @param path  路径
     * @param clazz 类型
     * @param <B>   转化的类型
     * @return 转化后的Map
     */
    public static <B> Map<String, B> loadMap(String path, Class<B> clazz) {
        Map<String, B> map = new HashMap<>();
        JObject jo = (JObject) load(path);
        Map<String, JBase> value = jo.getValue();
        for (String s : value.keySet()) {
            map.put(s, parse(value.get(s), clazz));
        }
        return map;
    }

    /**
     * 设置缩进量
     * <blockquote><pre>
     *     // 设置单位缩进量为1个单位, 默认为两个单位, 且默认缩进字符为 ' '
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
     *     // 设置单位缩进量为1个单位, 默认为两个单位, 且默认缩进字符为 ' '
     *     JSON.setBeforeTab(1);
     *     // 设置默认字符为 '\t'
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
     *     // 设置以字符串形式输出
     *     JSON.setLongToString(true);
     * </pre></blockquote>
     *
     * @param longToStr 是否以字符串形式输出， 默认为 false
     */
    public static void setLongToString(boolean longToStr) {
        BToJSON.setLongToStr(longToStr);
    }
}
