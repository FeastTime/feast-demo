package com.feast.demo.web.controller;

import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.web.service.AdverstismentService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by gk on 17-5-9.
 * 广告信息controller
 */

@Controller
@RequestMapping(value = "/ad")
public class AdvertismentController {

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
}
