package com.feast.demo.web.ad2.ad;

import com.feast.demo.coupon.entity.Coupon;
import com.feast.demo.coupon.service.CouponService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/META-INF/spring/spring-*.xml"})
public class AppTest {

    @Autowired
    private CouponService couponServiceImpl;

    @org.junit.Test
    public void testApp()
    {
        for(Coupon c: couponServiceImpl.findAll())
        System.out.println(c);
    }
}
