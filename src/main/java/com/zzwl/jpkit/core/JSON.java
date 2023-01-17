package com.zzwl.jpkit.core;

import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.parse.JSONParse;

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
     *     String json = "{\n" +
     *                 "    \"title\":\"指挥平台\",\n" +
     *                 "    \"showCompanyInfo\": true,\n" +
     *                 "    \"describe\":null,\n" +
     *                 "    \"arr\":[],\n" +
     *                 "    \"number\":[25,89.369,null, true,\"人类技术有限公司\",\"指挥平台\"],\n" +
     *                 "    \"code\": 569\n," +
     *                 "    \"time\": \"2023-1-9 18:00:00\"\n" +
     *                 "}";
     *    JObject parse = (JObject) JSON.parse(json);
     *    <ul>
     *      <li>
     *        <p>JArray number = (JArray) parse.getValue().get("number");</p>
     *        <p>
     *           for (JBase jBase : number.getValue()) {
     *                System.out.println(jBase.getValue());
     *           }
     *        </p>
     *      </li>
     *      <li>
     *          <p>JDate date = new JDate(parse.getValue().get("time"), "yyyy-MM-dd HH:mm:SS");</p>
     *          <p>System.out.println(date);</p>
     *      </li>
     *      <li>
     *          <p>JShort code = new JShort(parse.getValue().get("code"));</p>
     *          <p>System.out.println(code);</p>
     *      </li>
     *     <ul/>
     * </pre></blockquote>
     *
     * @param json JSON字符串
     * @return <b>ITypeof<Object></b>
     */
    public static ITypeof<Object> parse(String json) {
        return new JSONParse(json).parse();
    }
}
