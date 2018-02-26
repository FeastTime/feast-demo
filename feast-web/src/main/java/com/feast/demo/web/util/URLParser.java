package com.feast.demo.web.util;

import java.util.HashMap;
import java.util.Map;

public class URLParser {

    /**
     * 解析出url参数中的键值对
     *
     * @param strUrlParam url地址参数
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String strUrlParam) {

        Map<String, String> mapRequest = new HashMap<>();

        if (strUrlParam == null) {
            return mapRequest;
        }

        String[] arrSplit = strUrlParam.split("[&]");
        String[] arrSplitEqual;

        for (String strSplit : arrSplit) {

            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0].length() != 0) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }

        return mapRequest;
    }
}