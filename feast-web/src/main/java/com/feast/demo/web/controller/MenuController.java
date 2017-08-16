package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.service.MenuService;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by matao on 2017/5/7.
 */

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/menu",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String qryMenu(@RequestBody String text){
        text = StringUtils.decode(text);
        JSONObject jsono  = JSON.parseObject(text);
        MenuObj resultObj = menuService.findMenuByCategoryIdAndStoreId(jsono);

        return JSON.toJSONString(resultObj);
    }

}
