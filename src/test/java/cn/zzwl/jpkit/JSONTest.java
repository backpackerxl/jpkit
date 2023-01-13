package cn.zzwl.jpkit;

import cn.zzwl.jpkit.core.JSON;
import cn.zzwl.jpkit.typeof.*;
import cn.zzwl.jpkit.utils.StringUtil;
import cn.zzwl.jpkit.vo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

public class JSONTest {

    @Test
    public void start() {
        System.out.println("hello jpkit !!!");
    }

    @Test
    public void parse() {
        String json = "{\n" +
                "    \"title\":\"指挥平台\",\n" +
                "    \"englishTitle\": \"FLOODS DISASTERS DEFENSE DECISION COMMAND SYSTEM\",\n" +
                "    \"miniTitle\":\"防汛\",\n" +
                "    \"url\":\"/scfx/\",\n" +
                "    \"showCompanyInfo\": true,\n" +
                "    \"filingNo\":\"蜀ICP备2002458584号-2\",\n" +
                "    \"maintenanceUnit\":\"人类技术有限公司\",\n" +
                "    \"describe\":null,\n" +
                "    \"arr\":[],\n" +
                "    \"number\":[25,89.369,null, true,\"人类技术有限公司\",\"指挥平台\"],\n" +
                "    \"code\": 569\n," +
                "    \"time\": \"2023-1-9 18:00:00\"\n" +
                "}";
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
        String s = JSON.stringify(zzwl);
        System.out.println(s);
        System.out.println(zzwl);
    }
}
