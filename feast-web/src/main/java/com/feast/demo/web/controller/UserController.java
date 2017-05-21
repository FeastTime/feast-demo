package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

//    @RequestMapping(value = "/register",method = RequestMethod.POST)
//    @ResponseBody
//    public String regUser(@ModelAttribute("user") UserObj user) {
//        System.out.println("androidID is:"+user.getAndroidID());
//        System.out.println("imei is:"+user.getImei());
//        System.out.println("ipv4 is:"+user.getIpv4());
//        System.out.println("mac is:"+user.getMac());
//        System.out.println("mobileNO is:"+user.getMobileNO());
//
//        UserObj resultObj = userService.getStatus(user,"register");
//        return JSON.toJSONString(resultObj);
//    }


    @RequestMapping(value = "/register",method = RequestMethod.GET)
    @ResponseBody
    public String regUser(@RequestParam(value = "mobileNO",required = false) String mobileNO) {

        UserObj u = new UserObj();
        u.setMobileNO(mobileNO);

        UserObj resultObj = userService.getStatus(u,"register");
        return JSON.toJSONString(resultObj);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(@ModelAttribute("user") UserObj user) {
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());

        UserObj resultObj = userService.getStatus(user,"login");

        if("0".equals(resultObj.getResultCode())){
            LoginMemory.set(user.getMobileNO(),user);
        }
        return JSON.toJSONString(resultObj);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public String logoutUser(@ModelAttribute("user") UserObj user) {
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("token is:"+user.getToken());

        UserObj resultObj = userService.getStatus(user,"logout");

        if("0".equals(resultObj.getResultCode())){
            LoginMemory.set(user.getMobileNO(),user);
        }
        return JSON.toJSONString(resultObj);
    }

}
