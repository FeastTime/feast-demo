package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.entity.WebSocketMessageBean;
import com.feast.demo.web.service.IMEvent;
import com.feast.demo.web.service.IMOperationService;
import com.feast.demo.web.util.StringUtils;
import com.feast.demo.web.util.URLParser;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/im")
public class ImController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private IMOperationService imOperationService;

    @RequestMapping(value = "/message",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) throws Exception{

        if (null == text || text.length() == 0) {
            return "";
        }

        String message = getURLDecoderString(text);

        logger.info(message);

        return "";

    }

    /**
     * url 解码
     *
     * @param inputStr 输入字符串
     * @return 解码后字符串
     */
    private String getURLDecoderString(String inputStr) {

        try {

            return java.net.URLDecoder.decode(inputStr, "UTF-8");

        } catch (Exception e) {

            logger.info("url 解码 失败！");
            e.printStackTrace();
        }

        return "";
    }

    
}
