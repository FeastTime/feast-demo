package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;
import com.feast.demo.web.service.TableService;
import com.feast.demo.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/table")
public class TableController {

    @Autowired
    private TableService tableService;

    //修改桌位记录
    @RequestMapping(value = "/setTableStatus",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String logoutUser(@RequestBody String text){
        Map<Object,Object> result = null;
        try{
            result = new HashMap<>();
            text = StringUtils.decode(text);
            JSONObject jsono  = JSON.parseObject(text);
            Byte operateType = jsono.getByte("operateType");
            Long userId = jsono.getLong("userId");
            Long storeId = jsono.getLong("storeId");
            Long tableId = jsono.getLong("tableId");
            Integer delayTime = jsono.getInteger("delayTime");
            //operateType==1延时，operateType==2到店
            DeskInfo desk = tableService.selectDeskByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
            if(operateType==1){
                //修改预留时间
                desk.setRecieveTime(desk.getRecieveTime()+delayTime);
                tableService.updateRecieveTime(desk);
                result.put("resultMsg","延时成功");
                result.put("resultCode","0");
            }else if(operateType==2){
                //修改是否到店标识
                desk.setIsCome(1);
                tableService.updateIsCome(desk);
                result.put("resultMsg","已经到店");
                result.put("resultCode","0");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("resultMsg","发生错误");
            result.put("resultCode","1");
        }
        return JSON.toJSONString(result);
    }

    //设置桌位基本信息
    @RequestMapping("/setBusinessInfo")
    public String setBusinessInfo(@RequestBody String text){
        Map<Object,Object> result = null;
        try{
            result = new HashMap<>();
            text = StringUtils.decode(text);
            DeskTemplate desk = JSONObject.parseObject(text, DeskTemplate.class);
            tableService.setBusinessInfo(desk);
            result.put("resultMsg","设置桌位信息成功");
            result.put("resultCode","0");
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultMsg","发生错误");
            result.put("resultCode","1");
        }
        return JSON.toJSONString(result);
    }

    //获取桌位信息
    @RequestMapping("/getBusinessInfo")
    public String getBusinessInfo(@RequestBody String text){
        Map<Object,Object> result = null;
        try{
            result = new HashMap<>();
            text = StringUtils.decode(text);
            JSONObject jsono = JSON.parseObject(text);
            String storeId = jsono.getString("storeId");
            String userId = jsono.getString("userId");
            DeskTemplate desk = tableService.getBusinessInfo(Long.parseLong(storeId),Long.parseLong(userId));
            result.put("resultMsg","取桌成功");
            result.put("resultCode","0");
            result.put("description",desk.getDescription());
            result.put("defaultDelayTime",desk.getRecieveTime());
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultMsg","发生错误");
            result.put("resultCode","1");
        }
        return JSON.toJSONString(result);
    }
}
