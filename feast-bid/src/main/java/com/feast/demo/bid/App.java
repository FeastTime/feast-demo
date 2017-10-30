package com.feast.demo.bid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by ggke on 2017/10/25.
 */
public class App {
    public static void main(String ...args){
        String configLocation = "classpath*:/META-INF/spring/spring-*.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
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
