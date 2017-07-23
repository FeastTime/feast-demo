package com.feast.demo.order;

import com.feast.demo.order.entity.TOrder;
import com.feast.demo.order.service.TOrderService;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ggke on 2017/3/31.
 */
public class App {

    public static void main(String ...args){
        String configLocation = "classpath*:/META-INF/spring/spring-*.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
//        String[] names = context.getBeanDefinitionNames();
//        for(String name:names){
//            System.out.println(name);
//        }
        TOrderService ads = (TOrderService) context.getBean("TOrderServiceImpl");
        List<TOrder> list = ads.findAll();
        for(TOrder o: list){
            System.out.println(o);
        }
        UserService us = (UserService) context.getBean("userServiceImpl");
        User user = us.findByMobileNo(13800138000l);
        System.out.println(user);
        System.out.println("start serivce.");
        while(true){
            try {
                TimeUnit.SECONDS.sleep(60l);
                System.out.println("ok.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
