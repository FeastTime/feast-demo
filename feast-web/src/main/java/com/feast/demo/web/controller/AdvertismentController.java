package com.feast.demo.web.controller;

import com.feast.demo.web.service.AdverstismentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/list/",method = RequestMethod.GET)
    public Map<String,Object> getHtmlAdvertisments(
            @RequestParam("q") Integer num,
            @RequestParam("w") Integer width,
            @RequestParam("h") Integer height
    ){
        Map<String,Object> result = Maps.newHashMap();
        List<String> ads = Lists.newArrayList();
        ads.add("./s/images/img1.jpg");
        result.put("total",ads.size());
        result.put("ads",ads);
        result.put("success","ok");
        return result;
    }
}
