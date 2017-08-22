package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.web.service.AdverstismentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by gk on 17-5-9.
 * 广告信息controller
 */

@Controller
@RequestMapping(value = "/ad")
public class AdvertisementController {

    @Resource
    private AdverstismentService adverstismentService;

    private static final String path="http://47.94.16.58:9798/feast-web";

    public static final String AdmTypeDynamic = "dynamicCreative";

    @ResponseBody
    @RequestMapping(value = "/getSilentAD/",method = RequestMethod.POST)
    public Map<String,Object> getHtmlAdvertisments(
            @RequestBody String text
    ){
        text = com.feast.demo.web.util.StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        AdTargetType type = Enum.valueOf(AdTargetType.class, jsono.getString("type"));
        Integer width = jsono.getInteger("width");
        Integer height = jsono.getInteger("height");
        Map<String,Object> result = Maps.newHashMap();
        String url = adverstismentService.getRemontAdUrl(type,width,height);
        Map<String,Object> adInfo = Maps.newHashMap();
        adInfo.put("Adm",url);
        adInfo.put("AdmType",AdmTypeDynamic);
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
    @RequestMapping(value = "/silentads/",method = RequestMethod.POST)
    public Map<String,Object> getHtmlAdvertismentsArray(
            @RequestBody String text
    ){
        text = com.feast.demo.web.util.StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        AdTargetType type = Enum.valueOf(AdTargetType.class, jsono.getString("type"));
        Integer width = jsono.getInteger("width");
        Integer height = jsono.getInteger("height");
        Integer num = jsono.getInteger("num");
        Map<String,Object> result = Maps.newHashMap();
        List<Advertisement> list = adverstismentService.findByTypeAndSize(type.name(),width,height);
        if(CollectionUtils.isNotEmpty(list)){
            if(num == null || num <1){
                num = 3;
            }
            if(num < list.size()) {
                list = list.subList(0, num);
            }
            List<Object> ads = Lists.newArrayList();
            for(Advertisement ad:list){
                Map<String,Object> adInfo = Maps.newHashMap();
                adInfo.put("Adm",ad.getPath());
                adInfo.put("AdmType",AdmTypeDynamic);
                adInfo.put("Height",height);
                adInfo.put("Width",width);
                adInfo.put("Price",100);
                ads.add(adInfo);
            }
            result.put("Ads",ads);
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
        Integer width = paramsJson.getInteger("width");
        Integer height= paramsJson.getInteger("height");
        String token = paramsJson.getString("token");
        result.put("num",num);
        result.put("width",width);
        result.put("height",height);
        result.put("token",token);
        num = 24;//暂时固定为24
        List<Advertisement> ads = adverstismentService.findByTypeAndSize("html",width,height);
        List<String> _urls = Lists.newArrayList();
        for(Advertisement ad:ads){
            _urls.add(ad.getPath());
        }
        result.put("data",_urls);
        return result;
    }

}
