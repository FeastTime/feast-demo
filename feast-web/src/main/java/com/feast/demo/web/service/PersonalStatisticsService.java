package com.feast.demo.web.service;

import com.feast.demo.web.entity.CoordinateMultiPoint;
import com.feast.demo.web.entity.CoordinatePoint;
import com.feast.demo.web.entity.EatDetail;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ggke on 2017/5/15.
 */
@Service
public class PersonalStatisticsService {

    //测试数据
    private String[] testFatData = new String[]{"200,1,6月1日","263,2,6月2日","369,3,6月3日","125,4,6月5日","784,5,6月7日"};
    private String[] testCarbohydrateData = new String[]{"200,1,6月1日","263,2,6月2日","369,3,6月3日","125,4,6月5日","784,5,6月7日"};
    private String[] testProteinData = new String[]{"200,1,6月1日","263,2,6月2日","369,3,6月3日","125,4,6月5日","784,5,6月7日"};
    private String[] testSodiumData = new String[]{"200,1,6月1日","263,2,6月2日","369,3,6月3日","125,4,6月5日","784,5,6月7日"};
    private String[] consumeChartData = new String[]{ "200,354,1,6月1日","263,125,2,6月2日","369,854,3,6月3日","125,365,4,6月5日","784,254,5,6月7日"};

    public List<CoordinatePoint> getCoordinatePointCharts(String type){
        List<CoordinatePoint> points = Lists.newArrayList();
        String[] data = null;
        switch(type){
            case "fat":
                data = testFatData;
                break;
            case "carbohydrate":
                data = testCarbohydrateData;
                break;
            case "protein":
                data = testProteinData;
                break;
            case "sodium":
                data = testSodiumData;
                break;
            default:return points;
        }
        for(String str:data){
            CoordinatePoint point = new CoordinatePoint();
            point.setYAxisNum(str.split(",")[0]);
            point.setXAxisNum(str.split(",")[1]);
            point.setXAxisText(str.split(",")[2]);
            points.add(point);
        }
        return points;
    }

    public List<CoordinateMultiPoint> getCoordinateMultiPointCharts(String type){
        List<CoordinateMultiPoint> points = Lists.newArrayList();
        String[] data = null;
        switch(type){
            case "consumeChart":
                data = consumeChartData;
                break;
            default:return points;
        }
        for(String str:data){
            CoordinateMultiPoint point = new CoordinateMultiPoint();
            List<String> yList = Lists.newArrayList();
            yList.add(str.split(",")[0]);
            yList.add(str.split(",")[1]);
            point.setYAxisNum(yList);
            List<String> xList = Lists.newArrayList();
            xList.add(str.split(",")[2]);
            point.setXAxisNum(xList);
            point.setXAxisText(str.split(",")[3]);
            points.add(point);
        }
        return points;
    }

    public List<EatDetail> getEatDetail(){
        List<EatDetail> eatDetailList = Lists.newArrayList();
        //测试数据
        eatDetailList.add(new EatDetail("中餐","70%"));
        eatDetailList.add(new EatDetail("西餐","18%"));
        eatDetailList.add(new EatDetail("日残","12%"));

        return eatDetailList;
    }
}
