package com.zzwl.jpkit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JHighlightUtil {
    private static final Pattern JSON_REX = Pattern.compile("(\"(\\\\u[a-zA-Z0-9]{4}|\\\\[^u]|[^\\\\\"])*\"(\\s*:)?|\\b(true|false|null)\\b|-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?)");

    /**
     * 高亮显示json
     *
     * @param json json字符串
     * @return 高亮后的json字符串
     */
    public static String toHighlight(String json) {
        Matcher matcher = JSON_REX.matcher(json);
        StringBuilder builder = new StringBuilder(json);
        int offset = 0;
        int x, y;
        while (matcher.find()) {
            String group = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            if (offset == 0) {
                x = start;
                y = end - 1;
            } else {
                x = start + 10 * offset + offset;
                y = end + 10 * offset + offset - 1;
            }
            if (group.startsWith("\"")) {
                if (group.endsWith(":")) {
                    // key
                    builder.insert(y, "\033[0m");
                    builder.insert(x, "\033[1;35m");
                    offset++;
                } else {
                    // string
                    builder.insert(y + 1, "\033[0m");
                    builder.insert(x, "\033[1;32m");
                    offset++;
                }
            } else if (group.matches("true|false|null")) {
                // boolean or null
                builder.insert(y + 1, "\033[0m");
                builder.insert(x, "\033[1;34m");
                offset++;
            } else {
                // number
                builder.insert(y + 1, "\033[0m");
                builder.insert(x, "\033[1;36m");
                offset++;
            }
        }
        return builder.toString();
    }
}
