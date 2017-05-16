package com.feast.demo.web.controller;

import com.feast.demo.web.service.PersonalStatisticsService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ggke on 2017/5/15.
 */

@Controller
@RequestMapping(value = "/PersonalStatistics")
public class PersonalStatisticsController {

    @Resource
    private PersonalStatisticsService personalStatisticsService;

    @RequestMapping(value = "/getPersonalStatisticsDetail/",method = RequestMethod.GET)
    public Map<String,Object> getPersonalStatisticsDetail(
            @RequestParam(value = "imei",required = false) String imei,
            @RequestParam(value = "androidID",required = false) String androidID,
            @RequestParam(value = "ipv4",required = false) String ipv4,
            @RequestParam(value = "mac",required = false) String mac,
            @RequestParam(value = "mobileNO",required = false) String mobileNO,
            @RequestParam(value = "token",required = false) String token
    ){
        Map<String,Object> result = Maps.newHashMap();
        //测试数据
        result.put("resultCode",0);
        result.put("eatCount","20");
        result.put("healthTips","健康分析:您最近身体不错");
        result.put("consumeTips","消费分析:上个月花少了");
        result.put("lastMonthEatPercent","31%");
        result.put("thisMonthEatPercent","45%");
        result.put("eatAnalysis","外出就餐频率分析");
        result.put("eatType",personalStatisticsService.getEatDetail());

        //图表数据
        Map<String,Object> healthAnalysisChart = Maps.newHashMap();
        healthAnalysisChart.put("fat",personalStatisticsService.getCharts("fat"));
        healthAnalysisChart.put("carbohydrate",personalStatisticsService.getCharts("carbohydrate"));
        healthAnalysisChart.put("protein",personalStatisticsService.getCharts("protein"));
        healthAnalysisChart.put("sodium",personalStatisticsService.getCharts("sodium"));
        result.put("healthAnalysisChart",healthAnalysisChart);

        return result;
    }
}
