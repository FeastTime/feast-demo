package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.entity.DishesCategory;
import com.feast.demo.web.entity.DishesCategoryObj;
import com.feast.demo.web.service.DishesCategoryService;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by matao on 2017/8/6.
 */

@Controller
@RequestMapping(value = "/menu")
public class DishesCategoryController {

    @Resource
    private DishesCategoryService dishesCategoryService;

    @ResponseBody
    @RequestMapping(value = "/getDishesCategoryList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String regUser(@RequestBody String text){
        text = StringUtils.decode(text);
//        JSONObject jsono  = JSON.parseObject(text);
        DishesCategory dishesCategory  = JSONObject.parseObject(text,DishesCategory.class);
        DishesCategoryObj resultObj = dishesCategoryService.findDishesCategoryByStoreid(String.valueOf(dishesCategory.getStore().getId()));

        return JSON.toJSONString(resultObj);
    }

}