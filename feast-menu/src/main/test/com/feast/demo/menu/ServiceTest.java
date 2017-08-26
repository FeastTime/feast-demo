package com.feast.demo.menu;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.service.MenuService;
import com.feast.demo.menu.vo.MenuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ggke on 2017/8/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/META-INF/spring/spring-*.xml"})
public class ServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void test1(){
        JSONObject json = new JSONObject();
        json.put("categoryId","1001");
        json.put("storeId","1000000000");
        json.put("pageNo",0);
        json.put("pageNum",3);
        String str = menuService.getMenuCountByCategoryIdAndStoreId(json);

        System.out.println(str);
        List<MenuVo> list = menuService.findMenuByCategoryIdAndStoreId(json);
        for(MenuVo vo:list){
            System.out.println(vo);
        }
    }
}
