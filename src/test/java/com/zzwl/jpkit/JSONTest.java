package com.zzwl.jpkit;

import com.zzwl.jpkit.bean.Options;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.parse.ObjectParse;
import com.zzwl.jpkit.plugs.BigDecimalPlug;
import com.zzwl.jpkit.typeof.*;
import com.zzwl.jpkit.utils.JHighlightUtil;
import com.zzwl.jpkit.utils.StringUtil;
import com.zzwl.jpkit.vo.*;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zzwl.jpkit.utils.StringUtil.getMethodNameByFieldType;
import static com.zzwl.jpkit.utils.StringUtil.replace;
import static org.junit.Assert.assertEquals;

public class JSONTest {
    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Test
    public void start() {
        System.out.println("hello jpkit !!!");
    }

    @Test
    public void parse() {
        String json = "{\n" + "    \"title\":\"指挥平台\",\n" + "    \"englishTitle\": \"FLOODS DISASTERS DEFENSE DECISION COMMAND SYSTEM\",\n" + "    \"miniTitle\":\"防汛\",\n" + "    \"url\":\"/sfx/\",\n" + "    \"showCompanyInfo\": true,\n" + "    \"filingNo\":\"蜀ICP备2002458584号-2\",\n" + "    \"maintenanceUnit\":\"人类技术有限公司\",\n" + "    \"describe\":null,\n" + "    \"arr\":[],\n" + "    \"number\":[25,89.369,null, true,\"人类技术有限公司\",\"指挥平台\"],\n" + "    \"code\": 569\n," + "    \"time\": \"2023-1-9 18:00:00\"\n" + "}";
        JObject parse = (JObject) JSON.parse(json);
        JArray number = (JArray) parse.getValue().get("number");

        JDate date = new JDate(parse.getValue().get("time"), "yyyy-MM-dd HH:mm:SS");

        JShort code = new JShort(parse.getValue().get("code"));

        JChar jChar = new JChar(parse.getValue().get("title"));

        System.out.println(code.getValue());
        System.out.println(date.getValue());
        System.out.println(jChar.getValue());
        System.out.println(number);

        for (JBase jBase : number.getValue()) {
            System.out.println(jBase.getValue());
        }
    }

    @Test
    @PerfTest(invocations = 1000, threads = 40)
    @Required(max = 1200, average = 250, totalTime = 60000)
    public void testString() throws NoSuchFieldException {
        Field field = User.class.getDeclaredField("admin");
        System.out.println(getMethodNameByFieldType(StringUtil.basicGetPrefix, field.getType(), field.getName()));
    }

    @Test
    @PerfTest(invocations = 1000, threads = 40)
//    @Required(max = 1200, average = 250, totalTime = 60000)
    public void testStringify() {
        Integer[] nums = new Integer[]{4545, 2121, 3636};
        String[] ss = new String[]{"北京，你好！", "xx", "ww"};
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
        System.out.println("=================");
        String s1 = JSON.stringify(zzwl).pretty();
        System.out.println(s1);

    }

    @Test
//    @PerfTest(invocations = 1000, threads = 40)
    public void testSaveS() {
        Integer[] nums = new Integer[]{4545, 2121, 3636};
        String[] ss = new String[]{"ii北京 yes，欢迎你!", "北京", "上海"};
        User zzwl = new User(1L, "智中物联", 300, true, new Date(), nums, ss);
        long[] longs = new long[]{5164161651651165151L, 56156151655616556L, 165156516156156L};
        zzwl.setLongs(longs);
        List<Long> list = new ArrayList<>();
        list.add(1651465163113313131L);
        list.add(165146516423313131L);
        list.add(165146516453313131L);
        zzwl.setLongList(list);
        String json = JSON.stringify(zzwl).ucpJSON();
        System.out.println(json);
        System.out.println(JSON.parse(json));

    }

    @Test
    public void testReg() {
        System.out.println(StringUtil.isChinese("ii ui-op,"));
    }

    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testRead() {
        JBase load = (JBase) JSON.load("http://cmd.shzhfz.cn/config.json");
        System.out.println(load);
    }

    @Test
    public void testArray() {
        System.out.println(JSON.stringify(true).terse());
        System.out.println(JSON.stringify(121231).terse());
        System.out.println(JSON.stringify(3.455554).terse());
        System.out.println(JSON.stringify(3.556f).terse());
        System.out.println(JSON.stringify((short) 4355).terse());
        System.out.println(JSON.stringify("dddd").terse());
        System.out.println(JSON.stringify('c').terse());
        System.out.println(JSON.stringify(454664684489L).terse());
        System.out.println(JSON.stringify(new Date()).terse());
        System.out.println(JSON.stringify((byte) 12).terse());
        int[] ints = new int[]{589, 456, 89};
        System.out.println(JSON.stringify(ints).terse());
        System.out.println(JSON.stringify(ints).pretty());

        String[] strings = new String[]{"sgsgssg", "gsfsgdgsd", "sdgdsfgfdg", "sdgfdgfdgs", "sdfgfsdggf"};

        System.out.println(JSON.stringify(strings).terse());
        System.out.println(JSON.stringify(strings).pretty());

        char[] chars = new char[]{'f', 'g', 'y', 'u', 'p'};

        System.out.println(JSON.stringify(chars).terse());
        System.out.println(JSON.stringify(chars).pretty());

        byte[] shorts = new byte[]{123, 34, 24};

        System.out.println(JSON.stringify(shorts).terse());
        System.out.println(JSON.stringify(shorts).pretty());

        boolean[] booleans = new boolean[]{true, false, true, true, false};

        System.out.println(JSON.stringify(booleans).pretty());
        System.out.println(JSON.stringify(booleans).terse());

        System.out.println(JSON.stringify("oop").pretty());
    }

    @Test
    public void testCollection() {
        List<Integer> list = new LinkedList<>();

        list.add(456);
        list.add(234);
        list.add(789);
        list.add(123);

        System.out.println(JSON.stringify(list).pretty());

        Map<String, Boolean> map = new HashMap<>();

        map.put("isAdmin", true);
        map.put("isPublish", false);
        map.put("isCopy", true);
        System.out.println(JSON.stringify(map).pretty(2));
        System.out.println(JSON.stringify(new Date()).terse());
    }

    @Test
    public void testListAndMap() {
        List<User> users = new ArrayList<>();
        String path = "src\\test\\resources\\db.json";

        users.add(new User(1004207420089456666L, "zzwl", 300, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(2L, "zzwl", 400, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(3L, "zzwl", 500, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(4L, "zzwl", 600, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(5L, "zzwl", 700, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));

        JSON.setTabLength(1);
        JSON.setTabCharacter('\t');
        JSON.stringify(users).save(path, 2);

        System.out.println(JSON.stringify(users).terse(1));
        System.out.println(JSON.stringify(users).pretty(1));
        System.out.println("=================================================");
        System.out.println(JSON.stringify(users).pretty(3));
    }

    @Test
    public void testSave() {
        User user = new User(1L, "zzwl_plus", 400, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"});
        JSON.stringify(user).save("src\\test\\resources\\db.json");


        String url = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
        String local = "src\\test\\resources\\db.json";

        JBase net = (JBase) JSON.load(url);
        JBase net_local = (JBase) JSON.load(local);

        System.out.println(JSON.stringify(net).pretty());
        System.out.println("===================================");
        System.out.println(net_local);

    }

    @Test
    public void testDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JDate.YYYY_MM_DD);
        Date parse = simpleDateFormat.parse("2023-1-27");
        System.out.println(parse);
    }


    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testObjectParse() {
        String json = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"username\": \"zzwl\",\n" +
                "  \"user_code\": 300,\n" +
                "  \"admin\": true,\n" +
                "  \"create_time\": \"2023-03-16\",\n" +
                "  \"nums\": [\n" +
                "    4545,\n" +
                "    2121,\n" +
                "    3636\n" +
                "  ],\n" + "  \"strings\": [\n" + "    \"zz\",\n" +
                "    \"xx\",\n" +
                "    \"ww\"\n" +
                "  ],\n" +
                "  \"ints\": null,\n" + "  \"longs\": [\n" +
                "    \"5164161651651165151\",\n" +
                "    \"56156151655616556\",\n" +
                "    \"165156516156156\"\n" + "  ],\n" +
                "  \"longList\": [\n" +
                "    \"1651465163113313131\",\n" + "    \"165146516423313131\",\n" + "  " +
                "  \"165146516453313131\"\n" + "  ]\n" + "}";
        JObject parse = (JObject) JSON.parse(json);
        System.out.println(parse);
        User user = JSON.parse(json, User.class);
        System.out.println(user);
    }

    @Test
    public void testArr() throws NoSuchFieldException {
        Field nums = User.class.getDeclaredField("nums");

        String typeName = nums.getType().getTypeName();
        String name = Integer[].class.getTypeName();
        String name_ = Integer.class.getTypeName();
        System.out.println(typeName + "===>" + name);
        System.out.println(name_);
    }

    @Test
    public void testParseList() {

        String list = "[\n" + "\t{\n" + "\t\t\"id\": \"1004207420089456666\",\n" + "\t\t\"username\": \"zzwl\",\n" + "\t\t\"user_code\": 300,\n" + "\t\t\"admin\": true,\n" + "\t\t\"create_time\": \"2023-01-31\",\n" + "\t\t\"nums\": [\n" + "\t\t\t789,\n" + "\t\t\t526\n" + "\t\t],\n" + "\t\t\"strings\": [\n" + "\t\t\t\"gg\",\n" + "\t\t\t\"hh\"\n" + "\t\t],\n" + "\t\t\"ints\": null,\n" + "\t\t\"longs\": null,\n" + "\t\t\"longList\": null\n" + "\t},\n" + "\t{\n" + "\t\t\"id\": \"2\",\n" + "\t\t\"username\": \"zzwl\",\n" + "\t\t\"user_code\": 400,\n" + "\t\t\"admin\": false,\n" + "\t\t\"create_time\": \"2023-01-31\",\n" + "\t\t\"nums\": [\n" + "\t\t\t789,\n" + "\t\t\t526\n" + "\t\t],\n" + "\t\t\"strings\": [\n" + "\t\t\t\"gg\",\n" + "\t\t\t\"hh\"\n" + "\t\t],\n" + "\t\t\"ints\": null,\n" + "\t\t\"longs\": null,\n" + "\t\t\"longList\": null\n" + "\t},\n" + "\t{\n" + "\t\t\"id\": \"3\",\n" + "\t\t\"username\": \"zzwl\",\n" + "\t\t\"user_code\": 500,\n" + "\t\t\"admin\": true,\n" + "\t\t\"create_time\": \"2023-01-31\",\n" + "\t\t\"nums\": [\n" + "\t\t\t789,\n" + "\t\t\t526\n" + "\t\t],\n" + "\t\t\"strings\": [\n" + "\t\t\t\"gg\",\n" + "\t\t\t\"hh\"\n" + "\t\t],\n" + "\t\t\"ints\": null,\n" + "\t\t\"longs\": null,\n" + "\t\t\"longList\": null\n" + "\t}\n" + "]";

        List<User> users = JSON.parseList(list, User.class);

        System.out.println(JSON.stringify(users).pretty());
    }

    @Test
    public void testSubStringify() {
        MySQL mySQL = new MySQL("mysql", "5.7.35", new BigDecimal("0.25689"));

        List<BigDecimal> bigDecimals = new ArrayList<>();
        bigDecimals.add(new BigDecimal("0.1"));
        bigDecimals.add(new BigDecimal("0.1566"));
        bigDecimals.add(new BigDecimal("0.2568"));
        bigDecimals.add(new BigDecimal("0.84894"));

        mySQL.setBigDecimals(bigDecimals);
        BigDecimal[] bigDecimals1 = new BigDecimal[]{new BigDecimal("0.316"), new BigDecimal("0.25")};
        mySQL.setBigs(bigDecimals1);

        Map<String, BigDecimal> map = new HashMap<>();

        map.put("one", new BigDecimal("5.26"));
        map.put("two", new BigDecimal("5.2556"));
        map.put("three", new BigDecimal("5.4426"));
        map.put("fore", new BigDecimal("5.2667"));

        mySQL.setMap(map);
        List<MySQL> mySQLList = new ArrayList<>();
        MySQL mysql = new MySQL("mysql", "8.0.23", new BigDecimal("0.25689"), bigDecimals1);
        Type type = new Type(1561645451651L, int.class.getTypeName(), Integer.class);
        mysql.setType(type);
        mysql.setBigDecimals(bigDecimals);
        mysql.setMap(map);
        mySQLList.add(mysql);
        mySQL.setMySQLList(mySQLList);
        Type[] types = new Type[]{new Type(156161651651651L, String.class.getTypeName(), String.class), new Type(4464646L, String.class.getTypeName(), String.class)};
        mySQL.setTypes(types);
        mySQL.setType(type);
        System.out.println(JSON.stringify(mySQL).terse());
        System.out.println("=======================");
//        System.out.println(mySQL);
    }

    @Test
    public void testSubParse() {
        long start = new Date().getTime();
        String example = "{\"server\":\"mysql\",\"version\":\"5.7.35\",\"bigDecimal\":0.25689,\"bugs\":[0.316,0.25],\"bigDecimals\":[0.1,0.1566,0.2568,0.84894],\"map\":{\"one\":5.26,\"fore\":5.2667,\"two\":5.2556,\"three\":5.4426},\"mySQLList\":[{\"server\":\"mysql\",\"version\":\"8.0.23\",\"bigDecimal\":0.25689,\"bugs\":[0.316,0.25],\"bigDecimals\":[0.1,0.1566,0.2568,0.84894],\"map\":{\"one\":5.26,\"fore\":5.2667,\"two\":5.2556,\"three\":5.4426},\"mySQLList\":null,\"types\":null,\"type\":{\"id\":\"1561645451651\",\"name\":\"int\",\"aClass\":\"java.lang.Integer\"}}],\"types\":[{\"id\":\"156161651651651\",\"name\":\"java.lang.String\",\"aClass\":\"java.lang.String\"},{\"id\":\"4464646\",\"name\":\"java.lang.String\",\"aClass\":\"java.lang.String\"}],\"type\":{\"id\":\"1561645451651\",\"name\":\"int\",\"aClass\":\"java.lang.Integer\"}}";
        String json = "{\n" + "  \"server\": \"mysql\",\n" + "  \"version\": \"5.7.35\",\n" + "  \"bigDecimal\": 0.25689,\n" + "  \"bugs\": [\n" + "    0.316,\n" + "    0.25\n" + "  ],\n" + "  \"bigDecimals\": [\n" + "    0.1,\n" + "    0.1566,\n" + "    0.2568,\n" + "    0.84894\n" + "  ],\n" + "  \"map\": {\n" + "    \"one\": 5.26,\n" + "    \"fore\": 5.2667,\n" + "    \"two\": 5.2556,\n" + "    \"three\": 5.4426\n" + "  },\n" + "  \"mySQLList\": [\n" + "    {\n" + "      \"server\": \"mysql\",\n" + "      \"version\": \"8.0.23\",\n" + "      \"bigDecimal\": 0.25689,\n" + "      \"bugs\": [\n" + "        0.316,\n" + "        0.25\n" + "      ],\n" + "      \"bigDecimals\": null,\n" + "      \"map\": null,\n" + "      \"mySQLList\": null,\n" + "      \"types\": null,\n" + "      \"type\": null\n" + "    }\n" + "  ],\n" + "  \"types\": [\n" + "    {\n" + "      \"id\": \"156161651651651\",\n" + "      \"name\": \"java.lang.String\",\n" + "      \"aClass\": \"java.lang.String\"\n" + "    },\n" + "    {\n" + "      \"id\": \"4464646\",\n" + "      \"name\": \"java.lang.String\",\n" + "      \"aClass\": \"java.lang.String\"\n" + "    }\n" + "  ],\n" + "  \"type\": {\n" + "    \"id\": \"1561645451651\",\n" + "    \"name\": \"int\",\n" + "    \"aClass\": \"java.lang.Integer\"\n" + "  }\n" + "}";
        System.out.println(json);
        System.out.println("=====================================");
        MySQL parse = JSON.parse(example, MySQL.class, BigDecimalPlug.class);
        long end = new Date().getTime();
        System.out.println("解析耗时: " + (end - start));
        System.out.println(parse);
    }

    @Test
    public void testAnn() {
        String json = "    {\n" + "  \"name\": \"java.lang.String\",\n" + "      \"aClass\": \"java.lang.String\"\n" + "    },\n";

        Type parse = JSON.parse(json, Type.class);
        System.out.println(parse);
    }

    /**
     * 网络JSON测试没有问题, 但是有些接口在打包部署时测试不通过,不能打包
     */

    @Test
    public void testNetWorkGET() {
        String url = "http://cmd.shzhfz.cn/config.json";
        JBase load = (JBase) JSON.load(url);
        System.out.println(JSON.stringify(load).pretty());
    }

    @Test
    public void testOption() {
        Options options = Options.getInstance().setPram("User-Agent", "aviuahviauhv").setPram("time", new Date().toString()).setPram("server", "Java VM").setData("key", "asv av5566ava");

        Options options2 = Options.getInstance().setPram("User-Agent", "aviuahviauhv").setPram("time", new Date().toString()).setData("name", "hy").setData("password", "465616");
        System.out.println(options.getPram());
        System.out.println(options.getData());

        System.out.println("===============");
        System.out.println(options2.getPram());
        System.out.println(options2.getData());

        System.out.println(StringUtil.getEncodeString("155656*-/哈哈-88jkjkjk哈哈"));
    }

    @Test
    public void getTestTypeof() {
        Use use = new Use(3.56f, 789456L, 569, (short) 545, (byte) 23, 'c', false, 2.568);

        String json_use = JSON.stringify(use).pretty();

        System.out.println(json_use);

        Use use1 = JSON.parse(json_use, Use.class);

        System.out.println(use1);

        UsePlus usePlus = new UsePlus();
        usePlus.setB(new Byte("45"));
        usePlus.setBl(true);
        usePlus.setS(new Short("456"));
        usePlus.setF(4.56f);
        usePlus.setD(4.5896);
        usePlus.setI(456);
        usePlus.setC('k');
        usePlus.setL(45689966L);

        String pretty = JSON.stringify(usePlus).pretty();

        UsePlus usePlus1 = JSON.parse(pretty, UsePlus.class);

        System.out.println(usePlus1);
    }

    @Test
    public void testMap() {
        Map<String, Integer> map = new HashMap<>(0, 1);
        map.put("fg", 455);
        map.put("df", 455);
//        map.put("vb", 455);
        System.out.println(map);
    }

    @Test
    public void testCreateBean() {
        Object bean = ObjectParse.createBean(Type.class);
        System.out.println(bean);
    }

    @Test
    public void testTypeOfLong() {
        LongVo longVo = new LongVo(546L, 1L);
        System.out.println(longVo);

        LongVo vo = JSON.parse(longVo.toString(), LongVo.class);

        System.out.println(vo);
    }

    @Test
    public void testUnicode() {
        StringVo stringVo = new StringVo(StringUtil.stringToUnicode("你好,北京!"));

        System.out.println(stringVo);
        StringVo stringVo1 = JSON.parse(stringVo.toString(), StringVo.class);

        System.out.println(stringVo1);
    }

    @Test
    public void testPS() {
        String res = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"username\": \"智中物联\",\n" +
                "  \"user_code\": 300,\n" +
                "  \"admin\": true,\n" +
                "  \"create_time\": \"2023-03-27\",\n" +
                "  \"nums\": [\n" +
                "    4545,\n" +
                "    2121,\n" +
                "    3636\n" +
                "  ],\n" +
                "  \"strings\": [\n" +
                "    \"北京，你好!\",\n" +
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
        String s = StringUtil.doChinese(res, false);
        System.out.println(s);
    }

    @Test
    public void testOppo() {
        Type type = new Type(12142657L, "oppo", Oppo.class);
        Oppo oppo = new Oppo(type);
        System.out.println(JSON.stringify(oppo).terse());
    }

    @Test
    public void testReplaceWithNullRegex() {
        String result = replace(null, "hello", String::toUpperCase);
        assertEquals("hello", result);
    }

    @Test
    public void testReplaceWithNullFunction() {
        String result = replace("hello", "hello", null);
        assertEquals("hello", result);
    }

    @Test
    public void testReplaceWithNoMatch() {
        String result = replace("hello", "world", String::toUpperCase);
        assertEquals("world", result);
    }

    @Test
    public void testReplaceWithMatch() {
        String result = replace("hello", "hello world", String::toUpperCase);
        assertEquals("HELLO world", result);
    }

    @Test
    public void testGetMethodNameByFieldType() {
        String result = getMethodNameByFieldType("set", int.class, "age");
        assertEquals("setAge", result);

        result = getMethodNameByFieldType("get", boolean.class, "married");
        assertEquals("isMarried", result);

        result = getMethodNameByFieldType("set", String.class, "name");
        assertEquals("setName", result);
    }

    @Test
    public void testJSON() {
        JBase load = (JBase) JSON.load("D:\\zz_deploy\\jpkit\\src\\test\\resources\\all.json");
        System.out.println(JHighlightUtil.toHighlight(JSON.stringify(load).pretty()));
    }

    @Test
    public void testJSON2() {
        String s = "{\"text\": \"I'm in the unique position of asking over 100 industry \\\"experts\\\" the following question on {\\\"hello\\\"}\"}";
        JBase load = (JBase) JSON.parse(s);
//        System.out.println(JHighlightUtil.toHighlight(JSON.stringify(load).pretty()));

        JBase base = (JBase) JSON.load("https://resources.jetbrains.com/storage/testimonials/data.json");

        System.out.println(JHighlightUtil.toHighlight(JSON.stringify(base).pretty()));
    }

}
