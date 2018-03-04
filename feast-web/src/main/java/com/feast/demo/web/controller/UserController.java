package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.feedback.entity.Feedback;
import com.feast.demo.web.service.*;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.util.MD5Utils;
import com.feast.demo.web.util.StringUtils;
import com.feast.demo.web.util.TokenUtils;
import com.google.common.collect.Maps;
import io.rong.RYConfig;
import io.rong.RongCloud;
import io.rong.models.TokenResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * Created by ggke on 2017/4/10.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private UserService userService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private StoreService storeService;

    @Resource
    private IMOperationService imOperationService;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String register(@RequestBody String text) {
        logger.info(StringUtils.decode(text));
        return registe(text);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {

        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        logger.info(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);

        String resultMsg;
        int success = 1;//0:成功，1:失败,2:未注册
//        Device device = deviceService.findByDeviceId(user.getDeviceId());
//        if(device != null && device.getStore() != null){
//            result.put("storeId",device.getStore().getStoreId());
//        }else{
//            result.put("storeId",null);
//        }
        //访客
        if(user.getMobileNo() == null || StringUtils.isEmpty(user.getPassword())){
            resultMsg = "参数错误";
            //result.put("token","fangketoken:asieurqknro239480984234lkasj");
            success = 1;
        }else{
            //用户登录
            User _user = userService.findByMobileAndPwd(user.getMobileNo(),user.getPassword());
            if(_user == null){
                if(userService.fingByMobileNo(user.getMobileNo()) != null){
                    resultMsg = "手机号或密码错误";
                }else{
                    //没有注册
                    resultMsg = "未注册用户";
                }
                success = 2;
            }else {
                LoginMemory.set(_user.getMobileNo() + "", _user);
                resultMsg = "欢迎您登录成功!";
                System.out.println(_user.getUserId()+" "+_user.getDeviceId());
                result.put("token", TokenUtils.getToken(_user.getDeviceId()+"",_user.getUserId()+""));
                result.put("userType",_user.getUserType());
                success = 0;
            }
        }
        result.put("resultCode",success);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    /**
     *商家登录
     */
    @RequestMapping(value = "/storeLogin",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String storeLogin(@RequestBody String text) {

        User user;
        Map<String,Object> result = Maps.newHashMap();

        // 返回码 ： 0:成功，1:失败,2:未注册

        try {

            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject json = JSON.parseObject(text);

            String username = json.getString("username");
            String password = json.getString("password");
            String deviceId = json.getString("deviceId");

            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)|| StringUtils.isEmpty(deviceId)) {

                result.put("resultCode", 1);
                result.put("resultMsg", "参数错误");

                return JSON.toJSONString(result);

            }

            // 用户登录

            password = MD5Utils.encryptHMAC(password);
            user = userService.storeLogin(username, password);

            if (user == null || user.getStoreId() == null) {

                result.put("resultMsg", "账号或密码错误");
                result.put("resultCode", 2);

                return JSON.toJSONString(result);
            }

            // 查询商家信息
            Store store = storeService.getStoreInfo(user.getStoreId());

            if (store != null) {

                result.put("storeId", store.getStoreId());
                result.put("storeName", store.getStoreName());
            }

            LoginMemory.set(user.getUsername() + "", user);

            result.put("token", TokenUtils.getToken(deviceId + "", user.getUserId() + ""));
            result.put("userType", user.getUserType());
            result.put("userId", user.getUserId());

        } catch (Exception e) {

            e.printStackTrace();
            result.put("resultCode",1);
            result.put("resultMsg","服务端异常");

            return JSON.toJSONString(result);
        }

        if (!getRYToken(user, result)) {

            result.put("resultCode",3);
            result.put("resultMsg","服务端获取融云token 失败");
            return JSON.toJSONString(result);
        }

        result.put("resultMsg", "欢迎您登录成功!");
        result.put("resultCode", 0);
        return JSON.toJSONString(result);

    }

    @RequestMapping(value = "/registe",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String registe(@RequestBody String text){
        Map<Object,Object> result = Maps.newHashMap();
        text = StringUtils.decode(text);
        logger.info(text);
        User user = JSONObject.parseObject(text,User.class);

        String resultMsg = "";
        Integer success = 0;//0:成功，1:参数异常,
        if(user.getMobileNo()==null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            resultMsg = "参数错误";
            //result.put("token","fangketoken:asieurqknro239480984234lkasj");
            success = 1;
        }else{
            User dbUser = userService.fingByMobileNo(user.getMobileNo());
            if(dbUser != null){
                resultMsg = "该手机号已经注册,请登录";
                //result.put("token","fangketoken:asieurqknro239480984234lkasj");
                success = 1;
            }else if(CollectionUtils.isNotEmpty(userService.findByUsername(user.getUsername()))){
                resultMsg = "用户名已存在";
                //result.put("token","fangketoken:asieurqknro239480984234lkasj");
                success = 1;
            }else{
                userService.createUser(user);
                resultMsg = "注册成功";
            }
        }
        result.put("resultCode",success);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/logout",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String logoutUser(@RequestBody String text){
        text = StringUtils.decode(text);
        logger.info(text);
        JSONObject jsono  = JSON.parseObject(text);
        UserObj resultObj = userService.getStatus(jsono,"logout");
        if("0".equals(resultObj.getResultCode())){
            LoginMemory.remove(resultObj.getMobileNO());
        }
        return JSON.toJSONString(resultObj);
    }

    /**
     *保存微信用户信息
     */
    @RequestMapping(value="/saveWeChatUserInfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String saveWeChatUserInfo(@RequestBody String text){

        Map<String,Object> result = Maps.newHashMap();
        User user = new User();

        try{
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsonObject = JSONObject.parseObject(text);

            String deviceId = jsonObject.getString("deviceId");
            String mobileNo = jsonObject.getString("mobileNo");
            String nickName = jsonObject.getString("nickName");
            String userIcon = jsonObject.getString("userIcon");
            String openId = jsonObject.getString("openId");

            user.setUserIcon(userIcon);
            user.setNickName(nickName);
            user.setMobileNo(mobileNo);
            user.setDeviceId(deviceId);

            User user_ = userService.checkWeChatUserBindStatus(openId);

            if(user_ != null) {

                userService.updateUserInfo(deviceId,mobileNo,nickName,userIcon,openId);
                user.setUserId(user_.getUserId());
            } else {
                User user_1 = userService.saveWeChatUserInfo(user);
                user.setUserId(user_1.getUserId());
            }

        } catch (Exception e) {

            e.printStackTrace();
            result.put("resultMsg", "保存用户信息失败");
            result.put("resultCode",1);
            return JSON.toJSONString(result);
        }

        // 注册融云获取token

        if (!getRYToken(user, result)) {

            return JSON.toJSONString(result);
        }

        result.put("resultMsg", "保存成功");
        result.put("resultCode",0);
        result.put("userId",user.getUserId());
        result.put("token",TokenUtils.getToken(user.getDeviceId(),user.getUserId() + ""));

        return JSON.toJSONString(result);
    }

    /**
     *检查微信用户是否绑定
     */
    @RequestMapping(value="/checkWeChatUserBindStatus",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String checkWeChatUserBindStatus(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        byte resultCode;
        Byte status = null;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            String openId = jsono.getString("openId");
            User user = userService.checkWeChatUserBindStatus(openId);
            if(user!=null){
                resultMsg = "用户已绑定";
                status = 0;
            }else{
                resultMsg = "用户还未绑定";
                status = 1;
            }
            resultCode = 0;
        }catch(Exception e){
            resultCode = 1;
            e.printStackTrace();
        }
        result.put("resultCode", resultCode);
        result.put("resultMsg", resultMsg);
        result.put("status", status);

        return JSON.toJSONString(result);
    }

    /**
     *保存用户手机号
     */
    @RequestMapping(value="/saveUserPhone",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String saveUserPhone(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            Long userId = jsono.getLong("userId");
            String mobileNo = jsono.getString("mobileNo");
            userService.saveUserPhone(userId,mobileNo);
            resultMsg = "保存用户手机号成功";
            resultCode = 0;
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "保存用户手机号失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    /**
     *查询去过的商家
     */
    @RequestMapping(value = "/queryHadEatenStore",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryHadEatenStore(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<Store> storeList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long userId = obj.getLong("userId");
            Integer order = obj.getInteger("order");
            storeList = userService.queryHadEatenStore(userId,order);
            resultMsg = "查询去过的餐厅成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询去过的餐厅失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("storeList",storeList);
        return JSON.toJSONString(result);
    }


    /**
     * 查询用户信息
     */

    @RequestMapping(value = "/queryUserInfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryUserInfo(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg;
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long userId = obj.getLong("userId");
            User user = userService.queryUserInfo(userId);
            resultCode = 0;
            resultMsg = "查询用户信息成功";
            result.put("user",user);
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询用户信息失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }


    /**
     * 设置用户与商家关系
     *
     * @param servletRequest HttpServletRequest
     * @return 结果
     */
    @RequestMapping(value = "/setRelationshipWithStore",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String setRelationshipWithStore(HttpServletRequest servletRequest){

        Map<String,Object> result = Maps.newHashMap();

        try {

            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);

            String userId = obj.getString("userId");
            String storeId = obj.getString("storeId");
            int status = obj.getInteger("status");

            imOperationService.setRelationshipWithStore(userId, storeId, status);

            result.put("resultCode", 0);
            result.put("resultMsg", "设置用户和商家关系成功");

        } catch (Exception e) {

            e.printStackTrace();
            result.put("resultCode", 1);
            result.put("resultMsg", "设置用户和商家关系失败");
        }

        return JSON.toJSONString(result);

    }

    @RequestMapping(value = "/saveUserInfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String saveUserInfo(HttpServletRequest servletRequest){
        String resultMsg = "";
        Byte resultCode;
        try{

            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);

            long birthday = obj.getLong("birthday");
            Date birthday_ =new Date(birthday);

            User user = JSONObject.parseObject(text, User.class);

            User user_ = userService.findById(user.getUserId());
            user_.setArea(user.getArea());
            user_.setBirthday(birthday_);
            user_.setPersonalExplanation(user.getPersonalExplanation());
            user_.setSex(user.getSex());
            userService.saveUserInfo(user_);
            resultCode = 0;
            resultMsg = "保存用户信息成功";
        }catch (Exception e){
            e.printStackTrace();
            resultCode = 1;
            resultMsg = "保存用户信息失败";
        }

        Map<String,Object> result = Maps.newHashMap();
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/feedback",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String feedback(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;

        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            Feedback feedback = JSONObject.parseObject(text, Feedback.class);
            feedbackService.feedback(feedback);
            resultCode = 0;
            resultMsg = "添加意见反馈信息成功";
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "添加意见反馈信息失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }



    /**
     * 获取添加融云token
     *
     * @param user 用户信息
     * @param result 结果信息
     * @return boolean
     */
    private boolean getRYToken(User user, Map<String, Object> result) {
        // 注册融云获取token
        try {

            RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);

            TokenResult userGetTokenResult = rongCloud.user.getToken(user.getUserId()+"", user.getNickName(), user.getUserIcon());

            if (null != userGetTokenResult && null != userGetTokenResult.getToken()) {

                System.out.println("getToken:  " + userGetTokenResult.toString());
                result.put("imToken", userGetTokenResult.getToken());
            }

        } catch (Exception e) {

            e.printStackTrace();
            result.put("resultMsg", "获取融云token失败失败");
            result.put("resultCode", 3);
            return false;
        }

        return true;
    }


    /** 用户扫码进店
     *
     * @param servletRequest HttpServletRequest
     * @return 是否成功的消息
     */
    @RequestMapping(value = "/userComeInProc",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String userComeInProc(HttpServletRequest servletRequest){

        String resultMsg;
        Byte resultCode;

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            String userId = obj.getString("userId");
            String storeId = obj.getString("storeId");

            imOperationService.userComeInProc(userId,storeId);

            resultCode = 0;
            resultMsg = "用户进店成功";

        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "用户进店失败";
        }

        Map<String,Object> result = Maps.newHashMap();

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    /** 设置就餐人数
     *
     * @param servletRequest HttpServletRequest
     * @return 是否成功的消息
     */
    @RequestMapping(value = "/setTheNumberOfDiners",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String setTheNumberOfDiners(HttpServletRequest servletRequest){

        String resultMsg;
        Byte resultCode;

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            String userId = obj.getString("userId");
            int numberOfDiners = obj.getInteger("dinnerCount");
            String storeId = obj.getString("storeId");

            imOperationService.setNumberOfUser(numberOfDiners, storeId, userId);

            resultCode = 0;
            resultMsg = "设置用餐人数成功";

        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "设置用餐人数失败";
        }

        Map<String,Object> result = Maps.newHashMap();

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }


}