package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.service.StoreService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * 查询商家详细信息
     方法名 getStoreInfo
     */

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/getStoreInfo",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getStoreInfo(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            Store store = storeService.getStoreInfo(storeId);
            resultCode = 0;
            resultMsg = "查询商家详细信息成功";
            result.put("storeId",store.getStoreId());
            result.put("name",store.getStoreName());
            result.put("phone",store.getPhone());
            result.put("locate",store.getLocate());
            result.put("longitude",store.getLongitude());
            result.put("latitude",store.getLatitude());
            result.put("storePicture",store.getStorePicture());
            result.put("storeIcon",store.getStoreIcon());
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "查询商家详细信息失败";
        }
        result.put("resultCode",resultCode);
        result.put("reslutMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryHadVisitUser",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryHadVisitUser(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        ArrayList<User> userList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            userList = storeService.queryHadVisitUser(storeId);
            resultCode = 0;
            resultMsg = "查询历史用户成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询历史用户失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("userList",userList);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/createRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String createRedPackage(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        List<Long> couponTemplateIds = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            JSONArray couponTemplateIdArray = obj.getJSONArray("couponTemplateIds");
            couponTemplateIds = JSONArray.parseArray(JSON.toJSONString(couponTemplateIdArray),Long.class);
            Long storeId = obj.getLong("storeId");
            String redPackageName = obj.getString("redPackageName");
            RedPackage redPackage = new RedPackage();
            redPackage.setStoreId(storeId);
            redPackage.setRedPackageName(redPackageName);
            storeService.createRedPackage(redPackage,couponTemplateIds);
            resultCode = 0;
            resultMsg = "创建红包成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "创建红包失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/setRedPackageIsUse",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String setRedPackageIsUse(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long redPackageId = obj.getLong("redPackageId");
            Long storeId = obj.getLong("storeId");
            storeService.setRedPackageIsUse(redPackageId,storeId);
            resultCode = 0;
            resultMsg = "设置红包成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "设置红包失败";
        }
        result = Maps.newHashMap();
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryRedPackageList",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryRedPackageList(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            List<RedPackage> redPackages = storeService.queryRedPackageList(storeId);
            result.put("redPackages",redPackages);
            resultCode = 0;
            resultMsg = "查询红包列表成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询红包列表失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/setRedPackageAutoSendTime",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String setRedPackageAutoSendTime(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            Integer time = obj.getInteger("time");
            storeService.setRedPackageAutoSendTime(time,storeId);
            resultCode = 0;
            resultMsg = "设置红包自动发送周期成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "设置红包自动发送周期失败";
        }

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }
}

