package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageAutoSendTime;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.entity.RedPackageDetail;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.service.IMOperationService;
import com.feast.demo.web.service.RedPackageDetailService;
import com.feast.demo.web.service.RedPackageService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/redPackage")
public class RedPackageController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private IMOperationService imOperationService;

    @Autowired
    private RedPackageService redPackageService;

    @Autowired
    private RedPackageDetailService redPackageDetailService;

    @Autowired
    private UserService userService;

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
            Long redPackageId = jsonObject.getLong("redPackageId");

            ArrayList<RedPackageCouponTemplate> couponTemplateList = redPackageService.findRedPackageCouponTemplateByRedPackageId(redPackageId);

            tableInfo = jsonObject.getObject("tableInfo", TableInfo.class);
            String storeId = jsonObject.getString("storeId");
            String userId = jsonObject.getString("userId");

            imOperationService.sendRedPackage(userId,storeId,tableInfo,couponTemplateList);

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

    //获取倒计时时间
    @RequestMapping(value = "/countDown",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String countDown(HttpServletRequest servletRequest){

        String resultMsg;
        Byte resultCode;
        Map<String,Object> result = Maps.newHashMap();

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");

            RedPackage redPackageInfo = redPackageService.findByIsUseAndStoreId(2,storeId);

            System.out.println(redPackageInfo+"00000000");
            if(redPackageInfo==null){
                result.put("isCountDown",false);
                result.put("countDownTime",null);
            }else{
                if(IMOperationService.redPackageSendTime.get(redPackageInfo.getStoreId())==null){
                    result.put("isCountDown",false);
                    result.put("countDownTime",null);
                }else{
                    long autoSendTime = redPackageService.findAutoSendTimeByStoreId(storeId)*60*1000;

                    long countDownTime =IMOperationService.redPackageSendTime.get(redPackageInfo.getStoreId()) + autoSendTime - new Date().getTime();
                    result.put("isCountDown",true);
                    result.put("countDownTime",countDownTime);
                }
            }

            resultCode = 0;
            resultMsg = "倒计时成功";

        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "倒计时失败";
        }

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryRedPackageDetail",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryRedPackageDetail(HttpServletRequest servletRequest){

        String resultMsg;
        Byte resultCode;
        Map<String,Object> result = Maps.newHashMap();

        try {
            String text = (String) servletRequest.getAttribute("json");
                 text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            String redPackageId = obj.getString("redPackageId");

            ArrayList<RedPackageDetail> redPackageDetailList = redPackageDetailService.queryRedPackageDetail(redPackageId);

            Map<String,RedPackageDetail> redPackageDetailMap = Maps.newHashMap();

            for (RedPackageDetail redPackageDetail : redPackageDetailList) {

                Long userId_ = redPackageDetail.getUserId();

                User user = userService.findById(userId_);
                redPackageDetail.setUserIcon(user.getUserIcon());
                redPackageDetail.setNickName(user.getNickName());

                redPackageDetailMap.put(userId_+"",redPackageDetail);
            }

            result.put("redPackageDetailMap",redPackageDetailMap);
            resultCode = 0;
            resultMsg = "查询红包详情成功";

        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "查询红包详情失败";
        }

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/createRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String createRedPackage(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        List<RedPackageCouponTemplate> redPackageCouponTemplates = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            JSONArray couponTemplateIdArray = obj.getJSONArray("couponTemplateIdsAndCount");
            redPackageCouponTemplates = JSONArray.parseArray(JSON.toJSONString(couponTemplateIdArray),RedPackageCouponTemplate.class);
            Long storeId = obj.getLong("storeId");
            String redPackageName = obj.getString("redPackageName");
            RedPackage redPackage = new RedPackage();
            redPackage.setStoreId(storeId);
            redPackage.setRedPackageName(redPackageName);
            redPackageService.createRedPackage(redPackage,redPackageCouponTemplates);
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

    @RequestMapping(value = "/openAutoSendRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String openAutoSendRedPackage(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long redPackageId = obj.getLong("redPackageId");
            int isCouponEnough = redPackageService.queryRedPackageIsCouponEnough(redPackageId);
            result = Maps.newHashMap();
            if(isCouponEnough==0){
                result.put("resultCode",2);
                result.put("resultMsg","红包中优惠券数量不足");
                return JSON.toJSONString(result);
            }
            Long storeId = obj.getLong("storeId");
            ArrayList<Long> waiterIds = userService.findWaitersIdByStoreIdAndUserType(storeId,2);

            System.out.println("storeId" + storeId);
            System.out.println("redPackageId   : " + redPackageId);

            redPackageService.setRedPackageIsUse(redPackageId,storeId);
            resultCode = 0;
            resultMsg = "开启自动发红包成功";
            if(!IMOperationService.redPackageSendTime.containsKey(storeId)){
                IMOperationService.redPackageSendTime.put(storeId,new Date().getTime());
                long autoSendTime = redPackageService.findAutoSendTimeByStoreId(storeId)*60*1000;
                long countDownTime = autoSendTime+60*1000;
                imOperationService.countDown(storeId,true,countDownTime,waiterIds);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "开启自动发红包失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/closeAutoSendRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String closeAutoSendRedPackage(HttpServletRequest servletRequest){
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
            redPackageService.setRedPackageIsNotUse(redPackageId);

            ArrayList<Long> waiterIds = userService.findWaitersIdByStoreIdAndUserType(storeId,2);
            IMOperationService.redPackageSendTime.remove(storeId);
            imOperationService.countDown(storeId,false,0l,waiterIds);
            resultCode = 0;
            resultMsg = "关闭自动发红包成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "关闭自动发红包失败";
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
            Integer isCouponEnough = obj.getInteger("isCouponEnough");
            List<RedPackage> redPackages = redPackageService.queryRedPackageList(storeId,isCouponEnough);
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
        String resultMsg;
        Integer resultCode = 1;
        try{
            String text = (String)servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            Integer time = obj.getInteger("time");
            RedPackageAutoSendTime redPackageAutoSendTime = redPackageService.findByStoreId(storeId);
            if(redPackageAutoSendTime==null){
                RedPackageAutoSendTime redPackageAutoSendTime_ = new RedPackageAutoSendTime();
                redPackageAutoSendTime_.setStoreId(storeId);
                redPackageAutoSendTime_.setAutoSendTime(time);
                redPackageService.save(redPackageAutoSendTime_);
            }else{
                redPackageService.setRedPackageAutoSendTime(time,storeId);
            }

            ArrayList<Long> waiterIds = userService.findWaitersIdByStoreIdAndUserType(storeId,2);
            if(!IMOperationService.redPackageSendTime.containsKey(storeId)){
                imOperationService.countDown(storeId,false,0l,waiterIds);
            }else{
                long countDownTime = IMOperationService.redPackageSendTime.get(storeId) + time*60*1000 - new Date().getTime();

                imOperationService.countDown(storeId,true,countDownTime,waiterIds);
            }
            resultCode = 0;
            resultMsg = "设置红包自动发送周期成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "设置红包自动发送周期失败";
        }
        Map<String,Object> result = Maps.newHashMap();
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryCouponInRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryCouponInRedPackage(HttpServletRequest servletRequest){
        String resultMsg;
        Integer resultCode = 1;
        Map<String,Object> result = Maps.newHashMap();

        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long redPackageId = obj.getLong("redPackageId");
            ArrayList<CouponTemplate> couponTemplateList = redPackageService.queryCouponInRedPackage(redPackageId);
            result.put("couponTemplateList",couponTemplateList);
            resultCode = 0;
            resultMsg = "查询自动发送红包中的优惠券成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询自动发送红包中的优惠券失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/deleteAutoRedPackage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String deleteAutoRedPackage(HttpServletRequest servletRequest){
        String resultMsg;
        Integer resultCode = 1;
        Map<String,Object> result = Maps.newHashMap();

        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long redPackageId = obj.getLong("redPackageId");
            redPackageService.deleteAutoRedPackage(redPackageId);
            resultCode = 0;
            resultMsg = "删除自动发送的红包成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "删除自动发送的红包失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }
}
