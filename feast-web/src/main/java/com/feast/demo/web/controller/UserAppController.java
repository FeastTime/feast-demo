package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.feast.demo.ad.entity.TAd;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.service.UserRemoteApiStatusService;
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
public class UserAppController {

    @Resource
    private UserRemoteApiStatusService userRemoteApiStatusService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String regUser(@ModelAttribute("user") UserObj user) {
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());

        UserObj resultObj = userRemoteApiStatusService.getStatus(user,"register");
        return JSON.toJSONString(resultObj);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("user") UserObj user) {
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());

        UserObj resultObj = userRemoteApiStatusService.getStatus(user,"login");






        return JSON.toJSONString(resultObj);
    }


}
