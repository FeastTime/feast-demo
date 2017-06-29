package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.web.service.AdverstismentService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gk on 17-5-9.
 * 广告信息controller
 */

@Controller
@RequestMapping(value = "/ad")
public class AdvertisementController {

    @Resource
    private AdverstismentService adverstismentService;

    @ResponseBody
    @RequestMapping(value = "/getSilentAD/",method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> getHtmlAdvertisments(
            @RequestParam("type")AdTargetType type,
            @RequestParam("AdWith") Integer width,
            @RequestParam("AdHeight") Integer height
    ){
        Map<String,Object> result = Maps.newHashMap();
        String url = adverstismentService.getRemontAdUrl(type,width,height);
        Map<String,Object> adInfo = Maps.newHashMap();
        adInfo.put("Adm",url);
        adInfo.put("AdmType","html");
        adInfo.put("Height",height);
        adInfo.put("Width",width);
        adInfo.put("Price",100);
        if(!StringUtils.isEmpty(url)){
            result.put("Ad",adInfo);
            result.put("ID","124567890");
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/adArray/",method = RequestMethod.POST)
    public Map<String,Object> getAdArray(@RequestBody String params){
        Map<String,Object> result = Maps.newHashMap();
        JSONObject paramsJson = JSON.parseObject( com.feast.demo.web.util.StringUtils.decode(params));
        int num = paramsJson.getInteger("num");
        int width = paramsJson.getInteger("width");
        int height= paramsJson.getInteger("height");
        String token = paramsJson.getString("token");
        result.put("num",num);
        result.put("width",width);
        result.put("height",height);
        result.put("token",token);
        result.put("data",adverstismentService.getAdArray(num,width+"",height+""));
        Executor single = Executors.newSingleThreadExecutor();
        Executor fix = Executors.newFixedThreadPool(15);
        single.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(123);
            }
        });
        return result;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAll(){
        Map<String,Object> result = Maps.newHashMap();
        result.put("data",adverstismentService.findAll());

        return result;
    }
}