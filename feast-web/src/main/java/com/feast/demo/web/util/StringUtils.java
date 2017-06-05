package com.feast.demo.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by aries on 2017/5/29.
 */
public class StringUtils {
    public static String decode(String input) {

        try {
            String result = URLDecoder.decode(input, "UTF-8");
            if("=".equals(result.substring(result.length() - 1))){
                return result.substring(0, result.length() - 1);
            }else{
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String encode(String input) {

        try {
            String result = URLEncoder.encode(input, "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
