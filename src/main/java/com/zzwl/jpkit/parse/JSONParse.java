package com.zzwl.jpkit.parse;

import com.zzwl.jpkit.core.ITypeof;
import com.zzwl.jpkit.exception.JCharacterException;
import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.typeof.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParse {
    private int idx;
    private final char[] json_arr;

    public JSONParse(String json) {
        this.json_arr = json.toCharArray();
        this.idx = 0;
    }

    /**
     * 去除多余的空格或特殊字符
     */
    private void skip_white_char() {
        while (json_arr[idx] == ' ' || json_arr[idx] == '\n' || json_arr[idx] == '\r' || json_arr[idx] == '\t') {
            this.idx++;
        }
    }

    /**
     * 获取json字符串的下个字符
     *
     * @return 下一个字符
     */
    private char get_next_char() {
        this.skip_white_char();
        return this.json_arr[this.idx++];
    }

    /**
     * 解析json字符串
     *
     * @return <b>JSONObject</b>对象
     */
    public ITypeof<Object> parse() {
        char next_char = this.get_next_char();
        switch (next_char) {
            case 'n':
                return this.parse_null();
            case 't':
            case 'f':
                return this.parse_bool(next_char);
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return this.parse_number(next_char);
            case '"':
                return this.parse_string();
            case '{':
                return this.parse_object();
            case '[':
                return this.parse_array();
            default:
                throw new JTypeofException(next_char + " is not a JSON type the error occurs is " + this.idx);
        }
    }

    /**
     * 解析json数字类型
     *
     * @return <b>ITypeof<Object></b>类型
     */
    private ITypeof<Object> parse_number(char ch) {
        StringBuilder s = new StringBuilder();
        this.idx--;
        if (ch == '-') {
            s.append('-');
            this.idx++;
        }
        handleNumber(s);
        if (this.get_char_idx() != '.') {
            if (s.toString().length() > 10) {
                return new JLong(Long.parseLong(s.toString()));
            } else {
                return new JInteger(Integer.parseInt(s.toString()));
            }
        }
        s.append('.');
        this.idx++;
        handleNumber(s);
        return new JDouble(Double.parseDouble(s.toString()));
    }

    /**
     * 处理数字
     *
     * @param s 数字容器
     */
    private void handleNumber(StringBuilder s) {
        if (this.get_char_idx() < '0' || this.get_char_idx() > '9') {
            throw new JTypeofException(this.json_arr[this.idx] + " is not a number. the error occurs is " + this.idx);
        }
        while (this.get_char_idx() >= '0' && this.get_char_idx() <= '9') {
            s.append(this.json_arr[this.idx++]);
        }
    }

    /**
     * 解析json字符串类型
     *
     * @return <b>JString</b> 对象
     */
    private JString parse_string() {
        StringBuilder s = new StringBuilder();
        while (true) {
            if (idx == json_arr.length) {
                throw new JCharacterException(String.format("the json character string error at %s defect '\"' the error occurs is %s", s.toString(), idx));
            }
            char nextChar = this.json_arr[idx++];
            if (nextChar == '"') {
                break;
            }
            s.append(nextChar);
        }
        return new JString(s.toString());
    }

    /**
     * 解析json null类型
     *
     * @return <b>JBase</b> null 对象
     */
    private JBase parse_null() {
        if (this.json_arr.length < 4) {
            throw new JTypeofException(this.get_char_some(this.json_arr.length) + " is not a null, the error occurs is " + this.idx);
        }
        if (this.get_char_some(4).equals("null")) {
            this.idx = this.idx + 4;
            return new JBase() {
                @Override
                public Object getValue() {
                    return null;
                }

                @Override
                public String apply(String name) {
                    return null;
                }
            };
        } else {
            throw new JTypeofException(this.get_char_some(4) + " is not a null, the error occurs is " + this.idx);
        }
    }

    /**
     * 解析json数组类型
     *
     * @return <b>JArray</b>对象
     */
    private JArray parse_array() {
        List<JBase> list = new ArrayList<>();
        char ch = this.get_next_char();
        if (ch == ']') {
            return new JArray(list);
        }
        this.idx--;
        while (true) {
            JBase jBase = (JBase) this.parse();
            list.add(jBase);
            ch = this.get_next_char();
            if (ch == ']') {
                break;
            }
            if (ch != ',') {
                throw new JCharacterException(String.format("the json character string error at [... %s ...] defect ',' the error occurs is %s", jBase.getValue(), this.idx));
            }
        }
        return new JArray(list);
    }

    /**
     * 解析json布尔类型
     *
     * @return <b>JBool</b>对象
     */
    private JBool parse_bool(char ch) {
        switch (ch) {
            case 't':
                if (this.json_arr.length < 4) {
                    throw new JTypeofException(this.get_char_some(this.json_arr.length) + " is not a true the error occurs is " + this.idx);
                }
                if (this.get_char_some(4).equals("true")) {
                    this.idx = this.idx + 4;
                    return new JBool(true);
                } else {
                    throw new JTypeofException(this.get_char_some(4) + " is not a true the error occurs is " + this.idx);
                }
            case 'f':
                if (this.json_arr.length < 5) {
                    throw new JTypeofException(this.get_char_some(this.json_arr.length) + " is not a false the error occurs is " + this.idx);
                }
                if (this.get_char_some(5).equals("false")) {
                    this.idx = this.idx + 5;
                    return new JBool(false);
                } else {
                    throw new JTypeofException(this.get_char_some(5) + " is not a false the error occurs is " + this.idx);
                }
            default:
                throw new JTypeofException("parse boolean error the error occurs is " + this.idx);
        }
    }

    /**
     * 解析json对象类型
     *
     * @return <b>JObject</b>对象
     */
    private JObject parse_object() {
        Map<String, JBase> jsonObjectMap = new HashMap<>();
        char ch = this.get_next_char();
        String cacheKey = "{";
        if (ch == '}') {
            return new JObject(jsonObjectMap);
        }
        this.idx--;
        while (true) {
            ch = this.get_next_char();
            if (ch != '"') {
                throw new JCharacterException(String.format("the json character string error at {... \"%s\": %s, ...} following key defect '\"' the error occurs is %s", cacheKey, jsonObjectMap.get(cacheKey).getValue(), this.idx));
            }
            String key = this.parse_string().getValue();
            cacheKey = key;
            ch = this.get_next_char();
            if (ch != ':') {
                throw new JCharacterException(String.format("the json character string error at {... \"%s\" ... } after defect ':' the error occurs is %s", key, this.idx));
            }
            JBase jBase = (JBase) this.parse();
            jsonObjectMap.put(key, jBase);
            ch = this.get_next_char();
            if (ch == '}') {
                break;
            }
            if (ch != ',') {
                throw new JCharacterException(String.format("the json character string error at {... \"%s\": %s ...} after defect ',' the error occurs is %s", key, jBase.getValue(), this.idx));
            }
        }
        return new JObject(jsonObjectMap);
    }

    /**
     * 从json_arr中获取自定长度的字符串
     *
     * @return 获取后的字符串
     */
    private String get_char_some(int toIdx) {
        StringBuilder s = new StringBuilder();
        this.idx--;
        for (int i = this.idx; i < this.idx + toIdx; i++) {
            s.append(json_arr[i]);
        }
        return s.toString();
    }

    /**
     * 获取当前字符值
     *
     * @return 当前字符值
     */
    private char get_char_idx() {
        if (this.idx < this.json_arr.length) {
            return this.json_arr[this.idx];
        } else {
            return ' ';
        }
    }
}
