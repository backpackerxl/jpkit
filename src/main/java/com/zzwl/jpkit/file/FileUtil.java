package com.zzwl.jpkit.file;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @since 1.0
 */
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

    /**
     * 写文件操作
     *
     * @param res 内容
     * @param p   路径
     */
    public static void write(String res, String p) {
        Path path = Paths.get(p);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            byte[] bytes = res.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
