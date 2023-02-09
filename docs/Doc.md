# jpkit Use Doc

- 这里有更多jpkit详细的用法

```java

public class Test {
    @Test
    /**
     * json to java Object
     */
    void parse() {
        String json = "{" +
                "  \"username\": \"zzwl\"," +
                "  \"user_code\": 300," +
                "  \"admin\": true,\n" +
                "  \"create_time\": \"2023-01-13\"," +
                "  \"nums\": [" +
                "    4545," +
                "    2121," +
                "    3636" +
                "  ]," +
                "  \"strings\": [" +
                "    \"zz\"," +
                "    \"xx\"," +
                "    \"ww\"" +
                "  ]" +
                "}";
        JObject parse = (JObject) JSON.parse(json);
        JArray number = (JArray) parse.getValue().get("nums");

        JDate date = new JDate(parse.getValue().get("create_time"), "yyyy-MM-dd");

        JShort code = new JShort(parse.getValue().get("user_code"));
        System.out.println(code.getValue());
        System.out.println(date.getValue());
        for (JBase jBase : number.getValue()) {
            System.out.println(jBase.getValue());
        }
    }

    /**
     * java to json
     */
    @Test
    void stringify() {
        Integer[] nums = new Integer[]{4545, 2121, 3636};
        String[] ss = new String[]{"zz", "xx", "ww"};
        User zzwl = new User(1L, "zzwl", 300, true, new Date(), nums, ss);
        long[] longs = new long[]{5164161651651165151L, 56156151655616556L, 165156516156156L};
        zzwl.setLongs(longs);
        List<Long> list = new ArrayList<>();
        list.add(1651465163113313131L);
        list.add(165146516423313131L);
        list.add(165146516453313131L);
        zzwl.setLongList(list);
        String s = JSON.stringify(zzwl).terse();
        System.out.println(s);
    }

    /**
     * json to save
     */
    @Test
    void save() {
        List<User> users = new ArrayList<>();
        String path = "src\\main\\resources\\db.json";

        users.add(new User(1004207420089456666L, "zzwl", 300, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(2L, "zzwl", 400, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(3L, "zzwl", 500, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(4L, "zzwl", 600, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(5L, "zzwl", 700, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        JSON.stringify(users).save(path, 1);
        // 限制转化条数为1条
        String s = JSON.stringify(users).terse(1);
        // 限制转化条数为2条
        String s = JSON.stringify(users).pretty(2);
    }

    /**
     * json load to java
     */
    @Test
    void load() {
        String path = "src\\main\\resources\\test.json";
        String user_path = "src\\main\\resources\\db.json";
        String url = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
        JBase net_local = (JBase) JSON.load(path);
        // 加载网络json
        JBase net_local = (JBase) JSON.load(url);
        // 加载为java对象
        User net_local = JSON.load(user_path, User.class);
    }

    /**
     * JSON转java bean
     */
    @Test
    void parsePlus() {
        String json = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"username\": \"zzwl\",\n" +
                "  \"user_code\": 300,\n" +
                "  \"admin\": true,\n" +
                "  \"create_time\": \"2023-01-31\",\n" +
                "  \"nums\": [\n" +
                "    4545,\n" +
                "    2121,\n" +
                "    3636\n" +
                "  ],\n" +
                "  \"strings\": [\n" +
                "    \"zz\",\n" +
                "    \"xx\",\n" +
                "    \"ww\"\n" +
                "  ],\n" +
                "  \"ints\": null,\n" +
                "  \"longs\": [\n" +
                "    \"5164161651651165151\",\n" +
                "    \"56156151655616556\",\n" +
                "    \"165156516156156\"\n" +
                "  ],\n" +
                "  \"longList\": [\n" +
                "    \"1651465163113313131\",\n" +
                "    \"165146516423313131\",\n" +
                "    \"165146516453313131\"\n" +
                "  ]\n" +
                "}";
        User user = JSON.parse(json, User.class);
        System.out.println(user);
    }
}
```

- 现在你可以使用自定义插件体验不一样的JSON解析

```java
// BigDecimalPlug 是jpkit内置的解析插件，
// 当自定义解析插件与jpkit解析行为同时存在，优先使用自定义解析插件
@JPConfig(plugs = {BigDecimalPlug.class})
public class MySQL {
    @JRename("server")
    private String serverName;
    private String version;
    @JParse(method = BasePlug.GET_OBJECT)
    private BigDecimal bigDecimal;
    @JParse(method = BasePlug.GET_ARR)
    private BigDecimal[] bigs;
    @JParse(method = BasePlug.GET_LIST)
    private List<BigDecimal> bigDecimals;
    @JParse(method = BasePlug.GET_MAP)
    private Map<String, BigDecimal> map;
    //@JParse(method = BasePlug.GET_LIST, pos = 1)
    @JCollectType(type = MySQL.class)
    private List<MySQL> mySQLList;
    // method名字必须与插件的方法名对应， pos指定使用@JPConfig数组中第几个插件解析，默认从0开始计算
    //@JParse(method = BasePlug.GET_ARR, pos = 1)
    @JCollectType(type = Type.class)
    private Type[] types;
    // get set 构造
}

class Type {
    @JFString
    private long id;
    private String name;
    private Class<?> aClass;
    // get set 构造
}

// 你可以这样写插件，对于一些简单类型组合的对象类型
// jpkit内置了一个通用解析插件BasePlug
class MySQLPlug {
    /**
     * List<JBase> to List<MySQL>
     *
     * @param jBase 数据源
     * @return Object
     */
    @JPMethod(BasePlug.GET_LIST)
    public List<MySQL> getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<MySQL> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(JSON.parse(base.toString(), MySQL.class));
            }
            return res;
        });
    }

    /**
     * List<JBase> to Type[]
     *
     * @param jBase 数据源
     * @return Object
     */
    @JPMethod(BasePlug.GET_ARR)
    public Type[] getArray(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Type[] res = new Type[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = JSON.parse(value.get(i).toString(), Type.class);
            }
            return res;
        });
    }
}

class Test {
    @Test
    public void testSubParse() {
        String json = "{\n" +
                "  \"server\": \"mysql\",\n" +
                "  \"version\": \"5.7.35\",\n" +
                "  \"bigDecimal\": 0.25689,\n" +
                "  \"bigs\": [\n" +
                "    0.316,\n" +
                "    0.25\n" +
                "  ],\n" +
                "  \"bigDecimals\": [\n" +
                "    0.1,\n" +
                "    0.1566,\n" +
                "    0.2568,\n" +
                "    0.84894\n" +
                "  ],\n" +
                "  \"map\": {\n" +
                "    \"one\": 5.26,\n" +
                "    \"fore\": 5.2667,\n" +
                "    \"two\": 5.2556,\n" +
                "    \"three\": 5.4426\n" +
                "  },\n" +
                "  \"mySQLList\": [\n" +
                "    {\n" +
                "      \"server\": \"mysql\",\n" +
                "      \"version\": \"8.0.23\",\n" +
                "      \"bigDecimal\": 0.25689,\n" +
                "      \"bigs\": [\n" +
                "        0.316,\n" +
                "        0.25\n" +
                "      ],\n" +
                "      \"bigDecimals\": null,\n" +
                "      \"map\": null,\n" +
                "      \"mySQLList\": null,\n" +
                "      \"types\": null,\n" +
                "      \"type\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"types\": [\n" +
                "    {\n" +
                "      \"id\": \"156161651651651\",\n" +
                "      \"name\": \"java.lang.String\",\n" +
                "      \"aClass\": \"java.lang.String\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"4464646\",\n" +
                "      \"name\": \"java.lang.String\",\n" +
                "      \"aClass\": \"java.lang.String\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"type\": {\n" +
                "    \"id\": \"1561645451651\",\n" +
                "    \"name\": \"int\",\n" +
                "    \"aClass\": \"java.lang.Integer\"\n" +
                "  }\n" +
                "}";
        MySQL parse = JSON.parse(json, MySQL.class);
        System.out.println(parse);
    }
}

```

#### 更多使用或测试方法 [`JSONTest.java`](../src/test/java/com/zzwl/jpkit/JSONTest.java)
