package com.feast.demo.ad;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        String[] names = context.getBeanDefinitionNames();
        System.out.print("Beans:");
        for (String string : names)
            System.out.print(string + ",");
        while(true){
            try {
                TimeUnit.SECONDS.sleep(5l);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
