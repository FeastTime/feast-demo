package com.feast.demo.order;

import com.feast.demo.order.service.TOrderService;
import com.feast.demo.order.vo.OrderDetailVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ggke on 2017/8/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/META-INF/spring/spring-*.xml"})
public class OrderServiceTest {

    @Resource(name = "TOrderServiceImpl")
    private TOrderService tOrderServiceImpl;

    @Test
    public void test1(){
        List<OrderDetailVo> list = tOrderServiceImpl.findVoByOrderId(111112222200001l);
        for(OrderDetailVo vo:list){
            System.out.println(vo);
        }
    }
}
