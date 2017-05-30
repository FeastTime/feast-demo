package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.QueryIngredientsService;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/menu")
public class QueryIngredientsController {

    @Resource
    private QueryIngredientsService queryIngredientsService;
    @ResponseBody
    @RequestMapping(value = "/queryIngredients",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String qryIngredients(@RequestBody String text){
        text = StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("dishID is:"+jsono.getString("dishID"));

        IngredientsObj resultObj = queryIngredientsService.getIngredientsInfo(jsono);

        return JSON.toJSONString(resultObj);
    }


}
