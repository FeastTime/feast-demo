package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.device.entity.Device;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.DeviceService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.feast.demo.web.util.TokenUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ggke on 2017/4/10.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private UserService userService;

    @Resource
    private DeviceService deviceService;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String register(@RequestBody String text) {
        logger.info(StringUtils.decode(text));
        return registe(text);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {
        logger.info(StringUtils.decode(text));
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);

        String resultMsg = "";
        Integer success = 1;//0:成功，1:失败,2:未注册
        Device device = deviceService.findByDeviceId(user.getDeviceId());
        if(device != null && device.getStore() != null){
            result.put("storeId",device.getStore().getStoreId());
        }else{
            result.put("storeId",null);
        }
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
                result.put("token", TokenUtils.getToken(JSONObject.parseObject(text).getString("deviceId"),user.getUserId()+""));
                result.put("userType",_user.getUserType());
                success = 0;
            }
        }
        result.put("resultCode",success);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/storeLogin",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String storeLogin(@RequestBody String text) {
        Map<Object,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;//0:成功，1:失败,2:未注册
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            String username = jsono.getString("username");
            String password = jsono.getString("password");
            Long deviceId = jsono.getLong("deviceId");
            Device device = deviceService.findByDeviceId(deviceId);
            if(device != null && device.getStore() != null){
                result.put("storeId",device.getStore().getStoreId());
            }else{
                result.put("storeId",null);
            }
            if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
                resultMsg = "参数错误";
            }else{
                //用户登录
                User user = userService.storeLogin(username,password);
                if(user == null){
                    if(userService.findByUsername(username) != null){
                        resultMsg = "账号或密码错误";
                    }else{
                        //没有注册
                        resultMsg = "未注册用户";
                        resultCode = 2;
                    }
                }else {
                    LoginMemory.set(user.getUsername() + "", user);
                    resultMsg = "欢迎您登录成功!";
                    result.put("token", TokenUtils.getToken(jsono.getString("deviceId"),user.getUserId()+""));
                    result.put("userType",user.getUserType());
                    result.put("userId",user.getUserId());
                    resultCode = 0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/registe",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String registe(@RequestBody String text){
        logger.info(StringUtils.decode(text));
        Map<Object,Object> result = Maps.newHashMap();
        text = StringUtils.decode(text);
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
        logger.info(StringUtils.decode(text));
        text = StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));

        UserObj resultObj = userService.getStatus(jsono,"logout");

        if("0".equals(resultObj.getResultCode())){
            LoginMemory.remove(resultObj.getMobileNO());
        }
        return JSON.toJSONString(resultObj);
    }

    @RequestMapping(value="/saveWeChatUserInfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String saveWeChatUserInfo(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            logger.info(text);
            User user = JSONObject.parseObject(text,User.class);
            User user_ = userService.checkWeChatUserBindStatus(user.getOpenId());
            if(user_!=null){
                resultMsg = "用户已绑定";
            }else{
                userService.saveWeChatUserInfo(user);
                resultMsg = "保存用户信息成功";
            }
            resultCode = 0;
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "保存用户信息失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/checkWeChatUserBindStatus",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String checkWeChatUserBindStatus(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
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
            e.printStackTrace();
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("status",status);
        return JSON.toJSONString(result);
    }

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

    @RequestMapping(value = "/queryHadEatenStore",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryHadEatenStore(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<Store> storeList = null;
        try{
            result = Maps.newHashMap();
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

    @RequestMapping(value = "/selectVisitUser",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String selectVisitUser(@RequestBody String text){
        logger.info(StringUtils.decode(text));
        Map<Object,Object> result = null;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            List<User> list = userService.selectVisitUser(storeId);
            result.put("userList",list);
            result.put("resultCode","0");
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultCode","1");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 查询用户信息
     方法名 queryUserInfo
     */

    @RequestMapping(value = "/queryUserInfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryUserInfo(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long userId = obj.getLong("userId");
            User user = userService.queryUserInfo(userId);
            resultCode = 0;
            resultMsg = "查询用户信息成功";
            result.put("nickName",user.getNickName());
            result.put("mobileNo",user.getMobileNo());
            result.put("userIcon",user.getUserIcon());
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询用户信息失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/setRelationshipWithStore",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String setRelationshipWithStore(@RequestBody String text){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long userId = obj.getLong("userId");
            Long storeId = obj.getLong("storeId");
            Integer status = obj.getInteger("status");
            userService.setRelationshipWithStore(userId,storeId,status);
            resultCode = 0;
            resultMsg = "设置用户和商家关系成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "设置用户和商家关系失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }
}