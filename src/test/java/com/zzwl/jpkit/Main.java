package com.zzwl.jpkit;

import com.zzwl.jpkit.bean.Options;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.typeof.JBase;

public class Main {
    private static final String HEAD = "-H";
    private static final String PRETTY = "-P";
    private static final String DATA = "-D";
    private static final String HELP = "--help";


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("jcurl: try 'jcurl --help' for more information");
            return;
        }
        handleHttpRequest(args);
    }

    private static void handleHttpRequest(String[] args) {
        if (args[0].equals(HELP)) {
            printHelp();
            return;
        }
        Options instance = Options.getInstance();
        int len = args.length - 1;
        boolean isPretty = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("http://") || args[i].startsWith("https://")) {
                len = i;
            }
            switch (args[i]) {
                case HEAD:
                    String[] res = args[++i].split(":");
                    if (res.length != 2) {
                        System.out.println("参数输入有误！");
                        return;
                    }
                    instance.setPram(res[0].trim(), res[1].trim());
                    break;
                case PRETTY:
                    isPretty = true;
                    break;
                case DATA:
                    String[] ros = args[++i].split(":");
                    if (ros.length != 2) {
                        System.out.println("参数输入有误！");
                        return;
                    }
                    instance.setData(ros[0].trim(), ros[1].trim());
                    break;
                default:
                    break;
            }
        }
        String endArg = args[len];
        if (!(endArg.startsWith("http://") || endArg.startsWith("https://"))) {
            System.out.println("请传入请求网址！");
            return;
        }
        JBase jbase = (JBase) JSON.load(endArg, instance);
        if (isPretty) {
            System.out.println(JSON.stringify(jbase).pretty());
        } else {
            System.out.println(JSON.stringify(jbase).terse());
        }
    }

    private static void printHelp() {
        System.out.println("-H\t设置请求头信息\n  \t使用方式：-H \"key: value\" 注意若请求为POST且需要传JSON数据请指明Content-Type为JSON类型, 加上\"write: json\"即可传入特定JSON字符串");
        System.out.println("-P\t设置是否格式化输出\n  \t使用方式：-P");
        System.out.println("-D\t设置POST请求的请求数据\n  \t使用方式：-D \"key: value\" 注意有此参数程序将以POST请求方式发出");
    }
}