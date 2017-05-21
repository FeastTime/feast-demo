package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.feast.demo.ad.entity.TAd;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.service.MenuService;
import com.feast.demo.web.service.QueryIngredientsService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by matao on 2017/5/7.
 */

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/menu",method = RequestMethod.POST)
    @ResponseBody
    public String qryIngredients(@ModelAttribute("ingredientsObj") MenuObj menuObj) {
        System.out.println("imei is:"+menuObj.getImei());
        System.out.println("androidID is:"+menuObj.getAndroidID());
        System.out.println("ipv4 is:"+menuObj.getIpv4());
        System.out.println("mac is:"+menuObj.getMac());
        System.out.println("mobileNO is:"+menuObj.getMobileNO());
        System.out.println("token is:"+menuObj.getToken());
        System.out.println("orderID is:"+menuObj.getOrderID());
        System.out.println("classType is:"+menuObj.getClassType());
        System.out.println("page is:"+menuObj.getPage());

        MenuObj resultObj = menuService.getMenusInfo(menuObj);

        return JSON.toJSONString(resultObj);
    }

}
