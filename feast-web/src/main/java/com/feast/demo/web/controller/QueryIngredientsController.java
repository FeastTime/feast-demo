package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.service.QueryIngredientsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by ggke on 2017/4/10.
 */

@Controller
@RequestMapping(value = "/menu")
public class QueryIngredientsController {

    @Resource
    private QueryIngredientsService queryIngredientsService;

    @RequestMapping(value = "/queryIngredients",method = RequestMethod.POST)
    public String qryIngredients(@ModelAttribute("ingredientsObj") IngredientsObj ingredientsObj) {
        System.out.println("imei is:"+ingredientsObj.getImei());
        System.out.println("androidID is:"+ingredientsObj.getAndroidID());
        System.out.println("ipv4 is:"+ingredientsObj.getIpv4());
        System.out.println("mac is:"+ingredientsObj.getMac());
        System.out.println("dishID is:"+ingredientsObj.getDishID());

        UserObj resultObj = queryIngredientsService.getStatus(user,"register");





        return JSON.toJSONString(resultObj);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("user") UserObj user) {
        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());

        UserObj resultObj = userRemoteApiStatusService.getStatus(user,"login");

        if("0".equals(resultObj.getResultCode())){
            LoginMemory.set(user.getMobileNO(),user);
        }
        return JSON.toJSONString(resultObj);
    }


}
