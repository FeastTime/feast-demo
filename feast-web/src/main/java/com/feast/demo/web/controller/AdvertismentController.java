package com.feast.demo.web.controller;

import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.web.service.AdverstismentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
public class AdvertismentController {

    @Resource
    private AdverstismentService adverstismentService;

    @ResponseBody
    @RequestMapping(value = "/{type}_{w}_{h}/",method = RequestMethod.GET)
    public Map<String,Object> getHtmlAdvertisments(
            @PathVariable("type")AdTargetType type,
            @PathVariable("w") Integer width,
            @PathVariable("h") Integer height
    ){
        Map<String,Object> result = Maps.newHashMap();
        String url = adverstismentService.getRemontAdUrl(type,width,height);
        if(!StringUtils.isEmpty(url)){
            result.put("url",url);
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        return result;
    }
}
