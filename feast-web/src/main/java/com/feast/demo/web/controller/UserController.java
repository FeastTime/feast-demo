package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public String regUser(@RequestBody String text){
        text = StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));

        UserObj resultObj = userService.getStatus(jsono,"register");

        return JSON.toJSONString(resultObj);
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@ModelAttribute("user")UserObj user){
//        text = StringUtils.decode(text);
//        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());

        UserObj resultObj = userService.getStatus(JSON.parseObject(JSON.toJSONString(user)),"login");

        if("0".equals(resultObj.getResultCode())){
            LoginMemory.set(resultObj.getMobileNO(),resultObj);
        }

        return JSON.toJSONString(resultObj);
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