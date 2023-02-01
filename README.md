# jpkit
> this is java json kit

[![Security Status](https://www.murphysec.com/platform3/v3/badge/1615761482176835584.svg?t=1)](https://www.murphysec.com/accept?code=a111c7f25ae06b96daa00627832e6b68&type=1&from=2&t=2)

#### use

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
     * parse to java bean
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
