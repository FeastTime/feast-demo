package com.feast.demo.menu;

import com.feast.demo.menu.entity.TMenu;
import com.feast.demo.menu.service.MenuService;
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
        ApplicationContext context = new ClassPathXmlApplicationContext(
                configLocation);
        MenuService menuService = (MenuService) context.getBean("menuServiceImpl");
        List<TMenu> list = menuService.findAll();
        for(TMenu menu: list){
            System.out.println(menu);
        }
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
