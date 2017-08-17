package com.feast.demo.order;

import com.feast.demo.order.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        OrderService ads = (OrderService) context.getBean("orderServiceImpl");
        System.out.println(ads);
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
