package com.feast.demo.web.ad2.ad;

import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.ad.service.AdService;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.service.CouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/META-INF/spring/spring-*.xml"})
public class AppTest {

    @Autowired
    private CouponService couponServiceImpl;

    @Autowired
    private AdService adService;

    //@Test
    public void testApp()
    {
        for(CouponTemplate c: couponServiceImpl.findAll())
        System.out.println(c);
    }
    @Test
    public void test2(){
        List<Advertisement> list = adService.findBySizeUseNativeSql(243,146,5,true);
        for(Advertisement ad:list){
            System.out.println(ad);
        }
    }
}
