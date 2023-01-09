package cn.zzwl.jpkit;

import cn.zzwl.jpkit.core.JSON;
import cn.zzwl.jpkit.typeof.*;
import org.junit.Test;

public class JSONTest {

    @Test
    public void start() {
        System.out.println("hello jsonkit !!!");
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
        System.out.println(code.getValue());
        System.out.println(date.getValue());
        for (JBase jBase : number.getValue()) {
            System.out.println(jBase.getValue());
        }
    }
}
