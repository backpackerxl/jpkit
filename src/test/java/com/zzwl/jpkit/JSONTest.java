package com.zzwl.jpkit;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.network.NetUtil;
import com.zzwl.jpkit.typeof.*;
import com.zzwl.jpkit.utils.StringUtil;
import com.zzwl.jpkit.vo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.*;

public class JSONTest {

    @Test
    public void start() {
        System.out.println("hello jpkit !!!");
    }

    @Test
    public void parse() {
        String json = "{\n" + "    \"title\":\"指挥平台\",\n" + "    \"englishTitle\": \"FLOODS DISASTERS DEFENSE DECISION COMMAND SYSTEM\",\n" + "    \"miniTitle\":\"防汛\",\n" + "    \"url\":\"/scfx/\",\n" + "    \"showCompanyInfo\": true,\n" + "    \"filingNo\":\"蜀ICP备2002458584号-2\",\n" + "    \"maintenanceUnit\":\"人类技术有限公司\",\n" + "    \"describe\":null,\n" + "    \"arr\":[],\n" + "    \"number\":[25,89.369,null, true,\"人类技术有限公司\",\"指挥平台\"],\n" + "    \"code\": 569\n," + "    \"time\": \"2023-1-9 18:00:00\"\n" + "}";
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
    public void testString() throws NoSuchFieldException {
        Field field = User.class.getDeclaredField("admin");
        System.out.println(StringUtil.getMethodNameByFieldType(field.getType(), field.getName()));
    }

    @Test
    public void testStringify() {
        Integer[] nums = new Integer[]{4545, 2121, 3636};
        String[] ss = new String[]{"zz", "xx", "ww"};
        User zzwl = new User(1L, "zzwl", 300, true, new Date(), nums, ss);
        String s = JSON.stringify(zzwl).terse();
        System.out.println(s);
        System.out.println(zzwl);


    }

    @Test
    public void testArray() {
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
        List<Integer> list = new ArrayList<>();

        list.add(456);
        list.add(234);
        list.add(789);
        list.add(123);

        System.out.println(JSON.stringify(list).terse());

        Map<String, Boolean> map = new HashMap<>();

        map.put("isAdmin", true);
        map.put("isPublish", false);
        map.put("isCopy", true);
        System.out.println(JSON.stringify(map).pretty());
    }

    @Test
    public void testListAndMap() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "zzwl", 300, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(2L, "zzwl", 400, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(3L, "zzwl", 500, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(4L, "zzwl", 600, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));
        users.add(new User(5L, "zzwl", 700, false, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"}));

        System.out.println(JSON.stringify(users).terse());
        System.out.println(JSON.stringify(users).pretty());

    }

    @Test
    public void testSave() {
        User user = new User(1L, "zzwl_plus", 400, true, new Date(), new Integer[]{789, 526}, new String[]{"gg", "hh"});
//        JSON.stringify(user).save("D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json");


        String url = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=36547_37647_37556_38057_36920_37989_37920_38040_26350_22157_37881&hisdata=&_t=1674049868387&csor=0";
        String local = "D:\\user\\backpackerxl\\jpkit\\src\\main\\resources\\db.json";

        JBase net = (JBase) JSON.load(url);
        JBase net_local = (JBase) JSON.load(local);

        System.out.println(JSON.stringify(net.getValue()).pretty());
        System.out.println("===================================");
        System.out.println(JSON.stringify(net_local.getValue()).pretty());

    }
}
