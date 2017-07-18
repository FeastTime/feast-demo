package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.feast.demo.ad.entity.TAd;
import com.feast.demo.web.service.RemoteApiStatusService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/demo")
public class AppController {

    @Resource
    private RemoteApiStatusService remoteApiStatusService;

    @RequestMapping(value = "/status",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> c1(
            @RequestParam(value = "p",required = false) String p
    ) throws InterruptedException {
        Map<String,Object> result = Maps.newHashMap();
        if(StringUtils.isNotEmpty(p)){
            result.put("p",p);
        }
        System.out.println(Thread.currentThread().getId());
        result.put("status","OK!");
        Thread.sleep(15000);
        result.putAll(remoteApiStatusService.getStatus());
        return result;
    }

    @RequestMapping(value = "/status2",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> c2(
            @RequestParam(value = "p",required = false) String p
    ){
        Map<String,Object> result = Maps.newHashMap();
        if(StringUtils.isNotEmpty(p)){
            result.put("p",p);
        }
        result.put("status2","OK!");
        result.putAll(remoteApiStatusService.getStatus());
        return result;
    }

    @RequestMapping(value = "/transferEntity",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> transferEntity(
            @RequestParam("name") String name
    ){
        Map<String,Object> result = Maps.newHashMap();
        TAd t = new TAd();
        t.setName(name);

        result.put("msg",remoteApiStatusService.transferEntityToTAd(t));
        return result;
    }

    @RequestMapping(value = "/transferMsg",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> transferMsg(
            @RequestParam("msg") String msg
    ){
        Map<String,Object> result = Maps.newHashMap();

        result.put("msg",remoteApiStatusService.transferStringToTAd(msg));
        return result;
    }

    @RequestMapping(value = "/adlist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> transferMsg(
            @RequestParam("num") Integer num
    ){
        Map<String,Object> result = Maps.newHashMap();

        result.put("ads",remoteApiStatusService.getAdList(num));
        return result;
    }

    @RequestMapping(value = "/dbstate/",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAll(){
        Map<String,Object> result = Maps.newHashMap();
        result.put("data",remoteApiStatusService.getDbState());

        return result;
    }

}
