package com.feast.demo.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by matao on 2017/5/29.
 *继承apache common的StringUtils类，在能够使用已有的String操作的方法基础上添加一些自己的方法
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
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
