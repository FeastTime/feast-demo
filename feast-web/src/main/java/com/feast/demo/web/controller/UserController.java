package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String register(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
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

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));
        Long mobileNo = jsono.getLong("mobileNO");
      //  UserObj resultObj = userService.getStatus(jsono,"login");
        String resultMsg = "";
        Boolean success = true;
        //访客
        if(mobileNo == null){
            resultMsg = "访客登录成功";
            result.put("token","fangketoken:asieurqknro239480984234lkasj");
            success = true;
        }//用户登录
        else{
            User user = userService.fingByMobileNo(mobileNo);
            if(user == null){
                resultMsg = "您的手机号没有注册，请注册";
                success = false;
            }else{
                LoginMemory.set(user.getMobileNo()+"",user);
                resultMsg = "欢迎您登录成功!";
                result.put("token","token:asieurqknro239480984234lkasj");
            }
        }
        result.put("resultCode",success);
        result.put("resultMsg",resultMsg);

        return JSON.toJSONString(result);
    }


    @ResponseBody
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