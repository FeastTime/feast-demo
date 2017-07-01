package com.feast.demo.ad;

import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.ad.service.AdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String ...args){
        String configLocation = "classpath*:/META-INF/spring/spring-*.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(
                configLocation);
//        AdService ads = (AdService) context.getBean("adServiceImpl");
//        List<Advertisement> list = ads.findAll();
//        for(Advertisement ad: list){
//            System.out.println(ad);
//        }
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
