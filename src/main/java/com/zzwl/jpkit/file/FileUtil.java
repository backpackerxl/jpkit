package com.zzwl.jpkit.file;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 获取本地的JSON文件内容
     *
     * @param path 路径
     * @return JSON内容
     */
    public static String getJSON(String path) {
        Path p = Paths.get(path);
        try {
            List<String> strings = Files.readAllLines(p);
            StringBuilder s = new StringBuilder();
            for (String string : strings) {
                s.append(string);
            }
            return s.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
