package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.web.service.IMOperationService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/redPackage")
public class RedPackageController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private IMOperationService imOperationService;

    @RequestMapping(value = "/sendRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String sendRedPackage(HttpServletRequest servletRequest){

        Byte resultCode;
        String resultMsg;
        TableInfo tableInfo;

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject jsonObject = JSONObject.parseObject(text);
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


    /**
     * 拆红包
     *
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/takeRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String takeRedPackage(HttpServletRequest servletRequest){

        String resultMsg;
        Byte resultCode;
        Map<String,Object> result = Maps.newHashMap();

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            String redPackageId = obj.getString("redPackageId");
            String userId = obj.getString("userId");
            String storeId = obj.getString("storeId");

            result.putAll(imOperationService.takeRedPackage(redPackageId,userId,storeId));

            resultCode = 0;
            resultMsg = "拆红包成功";

        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "拆红包失败";
        }



        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }
}
