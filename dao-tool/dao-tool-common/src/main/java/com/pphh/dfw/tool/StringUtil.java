package com.pphh.dfw.tool;


/**
 * 字符串转换工具类
 *
 * @author huangyinhuang
 * @date 2019/5/5
 */
public class StringUtil {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param s 输入的字符串
     * @return 下划线格式字符串
     */
    public static String camelToUnderline(String s) {
        if (s == null || "".equals(s.trim())) {
            return "";
        }
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param s 输入的字符串
     * @return 驼峰格式字符串
     */
    public static String underlineToCamel(String s) {
        if (s == null || "".equals(s.trim())) {
            return "";
        }
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(s.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 首字母转小写
     *
     * @param s 输入的字符串
     * @return 字符串首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    /**
     * 首字母转大写
     *
     * @param s 输入的字符串
     * @return 字符串首字母转大写
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
