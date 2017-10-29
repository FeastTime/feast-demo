package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.device.entity.Device;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.DeviceService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ggke on 2017/4/10.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private DeviceService deviceService;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String register(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);

        String msg = userService.createUser(user);
        if(StringUtils.isEmpty(msg)){
            result.put("resultCode",true);
            result.put("resultMsg",null);
            result.put("token","token:asieurqknro239480984234lkasj");
        }else{
            result.put("resultCode",false);
            result.put("resultMsg",msg);
        }

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);

        String resultMsg = "";
        Integer success = 1;//0:成功，1:失败,2:未注册
        Device device = deviceService.findDeviceInfoByImei(user.getImei());
        if(device != null && device.getStore() != null){
            result.put("storeId",device.getStore().getId());
        }else{
            result.put("storeId",null);
        }
        //访客
        if(user.getMobileNo() == null || StringUtils.isEmpty(user.getPwd())){
            resultMsg = "参数错误";
            //result.put("token","fangketoken:asieurqknro239480984234lkasj");
            success = 1;
        }else{
            //用户登录
            User _user = userService.findByMobileAndPwd(user.getMobileNo(),user.getPwd());
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
                result.put("token", "token:asieurqknro239480984234lkasj");
                result.put("userType",_user.getUserType());
                success = 0;
            }
        }
        result.put("resultCode",success);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/registe",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String registe(@RequestBody String text){
        Map<Object,Object> result = Maps.newHashMap();
        text = StringUtils.decode(text);
        User user = JSONObject.parseObject(text,User.class);

        String resultMsg = "";
        Integer success = 0;//0:成功，1:参数异常,
        if(user.getMobileNo()==null || StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPwd())){
            resultMsg = "参数错误";
            //result.put("token","fangketoken:asieurqknro239480984234lkasj");
            success = 1;
        }else{
            User dbUser = userService.fingByMobileNo(user.getMobileNo());
            if(dbUser != null){
                resultMsg = "该手机号已经注册,请登录";
                //result.put("token","fangketoken:asieurqknro239480984234lkasj");
                success = 1;
            }else if(CollectionUtils.isNotEmpty(userService.findByName(user.getName()))){
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
}