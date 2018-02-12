package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.table.entity.TableInfoExpand;
import com.feast.demo.table.entity.TableTemplate;
import com.feast.demo.web.service.TableService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/table")
public class TableController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private TableService tableService;

    //修改桌位记录
    @RequestMapping(value = "/setTableStatus",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String setTableStatus(HttpServletRequest servletRequest){
        Map<Object,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono  = JSON.parseObject(text);
            Byte operateType = jsono.getByte("operateType");
            Long userId = jsono.getLong("userId");
            Long storeId = jsono.getLong("storeId");
            Long tableId = jsono.getLong("tableId");
            Integer delayTime = jsono.getInteger("delayTime");
            //operateType==1延时，operateType==2到店
            TableInfo tableInfo = tableService.findTableInfoByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
            if(tableInfo!=null){
                if(operateType==1){
                    //修改预留时间
                    tableInfo.setRecieveTime(tableInfo.getRecieveTime()+delayTime);
                    tableService.updateTableInfo(tableInfo);
                    resultMsg = "延时成功";
                    resultCode = 0;
                }else if(operateType==2){
                    //修改是否到店标识
                    tableInfo.setIsCome(2);
                    tableService.updateTableInfo(tableInfo);
                    resultMsg = "已经到店";
                    resultCode = 0;
                }
            }else{
                resultMsg = "桌位不存在";
            }

        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "发生错误";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    //设置桌位基本信息
    @RequestMapping(value = "/setBusinessInfo",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String setBusinessInfo(HttpServletRequest servletRequest){
        Map<Object,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            TableTemplate tableTemplate = JSONObject.parseObject(text,TableTemplate.class);
            tableService.setBusinessInfo(tableTemplate);
            resultMsg = "设置桌位信息成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "设置桌位信息失败";
        }

        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    //获取桌位信息
    @RequestMapping(value = "/getBusinessInfo",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getBusinessInfo(HttpServletRequest servletRequest){
        Map<Object,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        String description = "";
        Integer recieveTime = null;
        try{
            result = new HashMap<>();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            Long storeId = jsono.getLong("storeId");
            Long userId = jsono.getLong("userId");
            TableTemplate tableTemplate = tableService.getBusinessInfo(storeId,userId);
            resultMsg = "获取桌位信息成功";
            resultCode = 0;
            description = tableTemplate.getDescription();
            recieveTime = tableTemplate.getRecieveTime();
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "获取桌位信息失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        result.put("description",description);
        result.put("recieveTime",recieveTime);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryPayTableDetail",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryPayTableDetail(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        TableInfo tableInfo = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            Long tableId = jsono.getLong("tableId");
            tableInfo = tableService.queryPayTableDetail(tableId);
            resultMsg = "查询付费桌位详情成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询付费桌位详情失败";
        }
        result.put("tableInfo",tableInfo);
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    //查询付费桌位列表
    @RequestMapping(value = "/queryPayTableList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryPayTableList(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<TableInfo> tableList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            JSONObject jsono = JSON.parseObject(text);
            Long storeId = jsono.getLong("storeId");
            Long userId = jsono.getLong("userId");
            tableList = tableService.queryPayTableList(userId,storeId);
            resultMsg = "查询付费桌位列表成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询付费桌位列表失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("tableList",tableList);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getHistoryTables",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getHistoryTables(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<TableInfo> tablesList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            Long storeId = jsono.getLong("storeId");
            tablesList = tableService.getHistoryTables(storeId);
            resultMsg = "查询今日历史桌位列表成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询今日历史桌位列表失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        result.put("tablesList",tablesList);
        return JSON.toJSONString(result);
    }

    /**
     * 查询已得到桌位列表
     方法名 queryMyTableList
     */

    @RequestMapping(value = "/queryMyTableList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryMyTableList(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<TableInfoExpand> tablesList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsono = JSON.parseObject(text);
            Long userId = jsono.getLong("userId");
            tablesList = tableService.queryMyTableList(userId);
            resultMsg = "查询已得到桌位列表成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询已得到桌位列表失败";
        }

        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("tablesList",tablesList);
        return JSON.toJSONString(result);
    }
}
