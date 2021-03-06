package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.HealthIndexObj;
import com.feast.demo.web.service.HealthIndexService;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/menu")
public class HealthIndexController {

    @Resource
    private HealthIndexService healthIndexService;

    @ResponseBody
    @RequestMapping(value = "/getHealthIndexAssessment",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String regUser(@RequestBody String text){
        text = StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        HealthIndexObj resultObj = healthIndexService.getHealthIndexInfo(jsono);

        return JSON.toJSONString(resultObj);
    }
}