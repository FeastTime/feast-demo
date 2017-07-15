package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by matao on 2017/5/7.
 */

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/menu",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String qryMenu(@RequestBody String text){
        text = com.feast.demo.web.util.StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));
        System.out.println("token is:"+jsono.getString("token"));
        System.out.println("orderID is:"+jsono.getString("orderID"));
        System.out.println("classType is:"+jsono.getString("classType"));
        System.out.println("page is:"+jsono.getString("page"));

        MenuObj resultObj = menuService.getMenusInfo(jsono);

        return JSON.toJSONString(resultObj);
    }

}
