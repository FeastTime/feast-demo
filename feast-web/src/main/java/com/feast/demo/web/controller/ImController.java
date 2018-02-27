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

        Map<String, String> map =  URLParser.urlSplit(message);
        String objectName = map.get("objectName");

        String content = map.get("content");
        JSONObject jsonObject = JSONObject.parseObject(content);

        String resultMsg;
        Byte resultCode;

        // 用户聊天
        if(objectName.equals(IMEvent.CHAT_TEXT)){
            String chatText = jsonObject.getString("message");
            String userId = jsonObject.getString("userId");
            String storeId = jsonObject.getString("storeId");

            if (null == message || message.length() == 0){
                return null;
            }

            imOperationService.chat(userId,storeId,chatText);

        }
        // 发红包
        else if(objectName.equals(IMEvent.SEND_RED_PACKAGE)){
            TableInfo tableInfo;
            try {

                JSONArray couponArray = jsonObject.getJSONArray("couponInfo");
                List<CouponTemplate> couponList = null;
                if(couponArray!=null) {
                    couponList = JSONArray.parseArray(JSON.toJSONString(couponArray), CouponTemplate.class);
                }
                tableInfo = jsonObject.getObject("tableInfo", TableInfo.class);
                String storeId = jsonObject.getString("storeId");
                String userId = jsonObject.getString("userId");

                imOperationService.sendRedPackage(userId,storeId,tableInfo,couponList);

                resultCode = 0;
                resultMsg = "发红包成功";

            } catch (Exception e) {
                e.printStackTrace();

                resultCode = 1;
                resultMsg = "发红包失败";
            }

            Map<String,Object> result = Maps.newHashMap();

            result.put("resultCode",resultCode);
            result.put("resultMsg",resultMsg);

            return JSON.toJSONString(result);
        }
        // 用餐人数变更通知
        else if(objectName.equals(IMEvent.WAITING_USER_CHANGED)){

            try {

                Integer numberPerTable = jsonObject.getInteger("dinnerCount");
                String storeId = jsonObject.getString("storeId");
                String userId = jsonObject.getString("userId");

                imOperationService.setNumberOfUser(numberPerTable, storeId, userId);

                resultCode = 0;
                resultMsg = "设置就餐人数成功";

            } catch (Exception e) {
                e.printStackTrace();

                resultCode = 1;
                resultMsg = "设置就餐人数失败";
            }

            Map<String,Object> result = Maps.newHashMap();

            result.put("resultCode",resultCode);
            result.put("resultMsg",resultMsg);

            return JSON.toJSONString(result);
        }

        return "";
    }
    //拆红包


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
