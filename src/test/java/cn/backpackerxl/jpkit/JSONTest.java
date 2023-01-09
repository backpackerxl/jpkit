package cn.backpackerxl.jpkit;

import cn.backpackerxl.jpkit.core.JSON;
import cn.backpackerxl.jpkit.typeof.JArray;
import cn.backpackerxl.jpkit.typeof.JBase;
import cn.backpackerxl.jpkit.typeof.JObject;
import org.junit.Test;

public class JSONTest {

    @Test
    public void start() {
        System.out.println("hello jsonkit !!!");
    }

    @Test
    public void parse() {
        String json = "{\n" +
                "    \"title\":\"水旱防御决策指挥平台\",\n" +
                "    \"englishTitle\": \"FLOODS DISASTERS DEFENSE DECISION COMMAND SYSTEM\",\n" +
                "    \"miniTitle\":\"防汛\",\n" +
                "    \"url\":\"/scfx/\",\n" +
                "    \"showCompanyInfo\": true,\n" +
                "    \"filingNo\":\"蜀ICP备20023387号-2\",\n" +
                "    \"maintenanceUnit\":\"智中物联技术有限公司\",\n" +
                "    \"describe\":null,\n" +
                "    \"arr\":[],\n" +
                "    \"number\":[25,89.369,true,\"智中物联技术有限公司\",\"水旱防御决策指挥平台\"],\n" +
                "    \"code\": 200\n" +
                "}";
        JObject parse = (JObject) JSON.parse(json);
        JArray number = (JArray) parse.getValue().get("number");
        for (JBase jBase : number.getValue()) {
            System.out.println(jBase.getValue());
        }
    }
}
