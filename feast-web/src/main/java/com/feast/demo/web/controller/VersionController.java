package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.service.VersionService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/version")
public class VersionController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "/upgradeReminding",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String upgradeReminding(HttpServletRequest servletRequest){
        Map<String,Object> result = Maps.newHashMap();
        String resultMsg;
        Byte resultCode;

        try {
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);

            JSONObject obj = JSONObject.parseObject(text);
            String clientType = obj.getString("clientType");
            Integer versionNumber = obj.getInteger("versionNumber");

            Map<String,Object> versionMessage = versionService.upgradeReminding(clientType,versionNumber);

            result.put("isUpgrade",versionMessage.get("isUpgrade"));
            result.put("downloadAddress",versionMessage.get("downloadAddress"));

            resultCode = 0;
            resultMsg = "版本升级成功";
        } catch (Exception e) {
            e.printStackTrace();

            resultCode = 1;
            resultMsg = "版本升级失败";
        }



        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);

        System.out.println(JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }
}
