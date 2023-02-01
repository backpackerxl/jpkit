package com.zzwl.jpkit.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class NetUtil {
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";


    private NetUtil() {
    }

    /**
     * 获取网络JSON内容
     *
     * @param url 网络地址
     * @return 网络JSON内容
     */
    public static String getJSON(String url) {
        return getJSON(url, new HashMap<>());
    }

    /**
     * 获取网络JSON内容
     *
     * @param url  网络地址
     * @param pram token认证
     * @return 网络JSON内容
     */
    public static String getJSON(String url, Map<String, String> pram) {
        try {
            URL u = new URL(url);
            URLConnection con = u.openConnection();
            for (String s : pram.keySet()) {
                con.setRequestProperty(s, pram.get(s));
            }
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder s = new StringBuilder();
            String bs;
            while (!Objects.isNull(bs = reader.readLine())) {
                s.append(bs);
            }
            reader.close();
            return s.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
