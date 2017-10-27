package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.ComeinRestBean;
import com.feast.demo.web.service.ComeinRestService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/22.
 */
@Controller
@RequestMapping(value = "/desk")
public class ComeinRestController {
    @Resource
    private ComeinRestService comeinRestService;

    @RequestMapping(value = "/comeinRest",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String comeinRest(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.userComeinProc(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/addDeskList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String addDeskList(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.addDeskList(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/newDeskNotify",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String newDeskNotify(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.newDeskNotify(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/userOfferPrice",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String userOfferPrice(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.userOfferPrice(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }



    @RequestMapping(value = "/grabDesk",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String grabDesk(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.grabDesk(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/grabDeskNotify",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String grabDeskNotify(@RequestBody String text) {
        Map<Object,Object> result = Maps.newHashMap();
        System.out.println("转之前"+text);
        text = StringUtils.decode(text);
        System.out.println("转之后"+text);
        User user = JSONObject.parseObject(text,User.class);
        JSONObject jsono  = JSON.parseObject(text);
        ComeinRestBean comeinRestBean = comeinRestService.grabDeskNotify(jsono);
        result.put("resultCode",comeinRestBean.getResultCode());

        return JSON.toJSONString(result);
    }
}
