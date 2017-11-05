package com.feast.demo.order;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.order.service.BidRecordService;
import com.feast.demo.order.vo.BidRecordVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by matao on 2017/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/META-INF/spring/spring-*.xml"})
public class BidRecordServiceTest {

    @Autowired
    private BidRecordService bidRecordService;

    //@Test
    public void test1(){
        JSONObject json = new JSONObject();
        json.put("storeId","1000000000");
        json.put("pageNo",0);
        json.put("pageNum",3);

        List<BidRecordVo> list = bidRecordService.findBidRecordByStoreId(json);
        for(BidRecordVo vo:list){
            System.out.println(vo);
        }
    }

    @Test
    public void test2(){
        JSONObject json = new JSONObject();
        json.put("storeId","1000000000");
        json.put("mobileNo","13999192949");
        json.put("bid","uussx789797978976867887");
        json.put("maxPrice","222");
        json.put("stt","0");

        String states = bidRecordService.addBidRecord(json);
        System.out.println(states);
    }

   // @Test
    public void test3(){
        JSONObject json = new JSONObject();
        json.put("mobileNo","13999192949");
        json.put("bid","uussx789797978976867887");
        json.put("maxPrice","222");
        json.put("stt","0");

        String states = bidRecordService.updBidRecord(json);
        System.out.println(states);
    }

}
