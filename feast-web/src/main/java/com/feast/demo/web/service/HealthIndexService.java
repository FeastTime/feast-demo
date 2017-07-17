package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.HealthIndexObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class HealthIndexService {

    public HealthIndexObj getHealthIndexInfo(JSONObject jsonObj) {

        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("orderID is:"+jsonObj.getString("orderID"));

        HealthIndexObj healthIndexObj = new HealthIndexObj();

        healthIndexObj.setResultCode("0");
        healthIndexObj.setResultMsg(StringUtils.encode(StringUtils.encode("成功")));
        healthIndexObj.setHeatQuantity(StringUtils.encode("3000卡"));

        healthIndexObj.setSodiumContent(StringUtils.encode("200克"));
        healthIndexObj.setFat(StringUtils.encode("200克"));
        healthIndexObj.setVitamine(StringUtils.encode("200克"));
        healthIndexObj.setProtein(StringUtils.encode("200克"));
        healthIndexObj.setSuitableNumber(StringUtils.encode("3人"));
        return healthIndexObj;
    }
}

