package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.DishesList;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class MenuService {

    public MenuObj getMenusInfo(JSONObject jsonObj) {

        System.out.println("androidID is:" + jsonObj.getString("androidID"));
        System.out.println("imei is:" + jsonObj.getString("imei"));
        System.out.println("ipv4 is:" + jsonObj.getString("ipv4"));
        System.out.println("mac is:" + jsonObj.getString("mac"));
        System.out.println("mobileNO is:" + jsonObj.getString("mobileNO"));
        System.out.println("token is:" + jsonObj.getString("token"));
        System.out.println("orderID is:" + jsonObj.getString("orderID"));
        System.out.println("classType is:" + jsonObj.getString("classType"));
        System.out.println("page is:" + jsonObj.getString("page"));


        String mobileNO = jsonObj.getString("mobileNO");

        MenuObj menuObj = new MenuObj();
//        if ("13388996666".equals(mobileNO)) {
        /**
         * 1001 鸡肉
         * 1002 牛肉
         * 1003 猪肉
         * 1004 鱼类
         */
        String classType = jsonObj.getString("classType");
        DishesList DishesBean = null;
        ArrayList dishesList = new ArrayList();
        String page = jsonObj.getString("page");

        menuObj.setResultCode("0");
        menuObj.setTmpId("A");
        if ("1000".equals(classType)) {
            if ("1".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000001");
                DishesBean.setDishNO("000000001");
                DishesBean.setDishImgUrl("http://img1.cache.netease.com/catchpic/9/92/92610F9BA2CD76B691A435FD30BF367A.jpg");
                DishesBean.setTvUrl("http://47.94.16.58:9799/static/mv/glcsjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("橄榄菜豌豆炒鸡柳"));
                //DishesBean.setDetail(StringUtils.encode("其实将橄榄菜炒四季豆换为豌豆亦可，豌豆更香甜，加入鸡柳就成为一道很好下饭的家常小菜。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000002");
                DishesBean.setDishNO("000000002");
                DishesBean.setDishImgUrl("http://rj1.douguo.net/upload/diet/0/4/b/04002261f7a14528457b78808fab749b.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("虾酱荷兰豆炒鲜鱿"));
                //DishesBean.setDetail(StringUtils.encode("荷兰豆香甜爽脆，与新鲜鱿鱼加入虾酱同炒，是一道十分惹味的家常小炒。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000003");
                DishesBean.setDishNO("000000003");
                DishesBean.setDishImgUrl("");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("云耳豌豆炒带子"));
                //DishesBean.setDetail(StringUtils.encode("云耳有很多种，其中以老鼠耳为最上乘，特别爽脆，与香甜碗豆及带子同炒，健康又易煮。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            } else if ("2".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000004");
                DishesBean.setDishNO("000000004");
                DishesBean.setDishImgUrl("https://note.youdao.com/yws/api/group/47100452/file/142972465?method=getImage&WLP=true&width=640&height=640&version=1&cstk=QXu4Rr3S");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("汁烧四季豆牛肉卷"));
                //DishesBean.setDetail(StringUtils.encode("四季豆富含蛋白质和多种氨基酸，用牛肉卷起卖相吸引，简单又美味。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000005");
                DishesBean.setDishNO("000000005");
                DishesBean.setDishImgUrl("http://img3.imgtn.bdimg.com/it/u=2489089878,1581352749&fm=26&gp=0.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("月亮虾饼"));
                //DishesBean.setDetail(StringUtils.encode("月亮虾饼是一种台湾小食，经油炸后，金黄色的香脆春卷皮就好像月亮一样，所以叫做月亮虾饼。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000006");
                DishesBean.setDishNO("000000006");
                DishesBean.setDishImgUrl("");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("香草烤杏鲍菇"));
                //DishesBean.setDetail(StringUtils.encode("热量低、纤维高的杏鲍菇，最适合做餐前小食。只要在杏鲍菇上洒上各种调味料，放入烤箱烤已经十分惹味！"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            } else if ("3".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000007");
                DishesBean.setDishNO("000000007");
                DishesBean.setDishImgUrl("https://note.youdao.com/yws/api/group/47100452/file/142972465?method=getImage&WLP=true&width=640&height=640&version=1&cstk=QXu4Rr3S");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("乌龙茶香东坡肉"));
                //DishesBean.setDetail(StringUtils.encode("用慢火炖煮五花肉，入口即溶，淡淡茶味齿颊留香。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000008");
                DishesBean.setDishNO("000000008");
                DishesBean.setDishImgUrl("");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("龙利鱼芦笋卷"));
                //DishesBean.setDetail(StringUtils.encode("用香嫩龙利鱼卷着爽脆的芦笋来煎，非常易做，口感丰富。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000009");
                DishesBean.setDishNO("000000009");
                DishesBean.setDishImgUrl("http://img2.imgtn.bdimg.com/it/u=1991198533,979030279&fm=26&gp=0.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("法式蘑菇汁鸡排"));
                //DishesBean.setDetail(StringUtils.encode("将经典的香浓蘑菇汁淋上嫩滑鸡排，卖相精致又美味。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            }
            menuObj.setDishesList(dishesList);

        } else if ("1002".equals(classType)) {
            if ("1".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000010");
                DishesBean.setDishNO("000000010");
                DishesBean.setDishImgUrl("http://www.tourbjxch.com.cn/upload/201503/20/201503201414205740.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("金银蒜瑶柱蒸丝瓜"));
                //DishesBean.setDetail(StringUtils.encode("丝瓜性平味甘，能祛湿健脾、清热去火，铺上炒香的蒜蓉、虾干及瑶柱，特别惹味。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000011");
                DishesBean.setDishNO("000000011");
                DishesBean.setDishImgUrl("http://zp1.douguo.net/upload/dish/d/5/1/600_d5408715de85ce06a331bef96f2457e1.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("冲绳风苦瓜炒猪肉"));
                //DishesBean.setDetail(StringUtils.encode("这道日式家庭菜特别用了味醂、清酒及日本酱油调味，苦瓜甘甘甜甜，配上猪肉片，吃起来清爽又美味。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000012");
                DishesBean.setDishNO("000000012");
                DishesBean.setDishImgUrl("http://image.lssp.com/969651/images/201604131638212137.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("柠檬薏米水"));
                //DishesBean.setDetail(StringUtils.encode("这个柠檬薏米水清热利尿，能改善水肿和排毒，自己煮简单又经济。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            } else if ("2".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000013");
                DishesBean.setDishNO("000000013");
                DishesBean.setDishImgUrl("http://img4.imgtn.bdimg.com/it/u=3009969574,1535784496&fm=26&gp=0.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("烟熏三文鱼牛油果班尼迪克蛋"));
                //DishesBean.setDetail(StringUtils.encode("班尼迪克蛋的精髓除了水波蛋外，香滑的荷兰酱亦十分重要，配上健康的烟熏三文鱼和牛油果，其实在家也能煮出咖啡店质素的早餐。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000014");
                DishesBean.setDishNO("000000014");
                DishesBean.setDishImgUrl("http://www.shcqn.com/UploadImage/edit/images/mrpancake%20house.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("美式香蕉榛子松饼"));
                //DishesBean.setDetail(StringUtils.encode("用低筋面粉做的美式松饼特别松软，配上香蕉、香浓榛子酱和枫糖，香甜美味。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();

                DishesBean.setDishID("00000015");
                DishesBean.setDishNO("0000000015");
                DishesBean.setDishImgUrl("http://img1.hoto.cn/share/40/05/1344_580.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("港式沙嗲牛肉通粉"));
                //DishesBean.setDetail(StringUtils.encode("跟着这个食谱就能煮出茶餐厅风味的沙嗲牛肉通粉，无须加梳打粉，就能煮到惹味又肉嫩的牛肉，配上奶油鸡汤汤底，十分满足。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            } else if ("3".equals(page)) {
                DishesBean = new DishesList();
                DishesBean.setDishID("00000016");
                DishesBean.setDishNO("000000016");
                DishesBean.setDishImgUrl("http://cp2.douguo.net/upload/caiku/8/6/b/yuan_86b34d156108ff446d446f62e28fc3cb.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("台式葱花培根蛋饼"));
                //DishesBean.setDetail(StringUtils.encode("蛋饼是台湾非常经典的早餐食物，饼皮软软糯糯，加上葱花特别香。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000017");
                DishesBean.setDishNO("000000017");
                DishesBean.setDishImgUrl("http://img.jiaodong.net/pic/0/11/59/79/11597945_165475.jpg");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("乌龙茶卤豆腐干"));
                //DishesBean.setDetail(StringUtils.encode("除了用来做果冻，乌龙茶还可以加入卤水做卤菜！带有阵阵乌龙茶香的卤豆腐干香嫩美味，非常好下饭。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("22.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();
                DishesBean.setDishID("00000018");
                DishesBean.setDishNO("000000018");
                DishesBean.setDishImgUrl("http://img.mp.sohu.com/upload/20170524/e0a5cc126dac4542917e48d77132b053_th.png");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("普洱茶熏乳鸽"));
                //DishesBean.setDetail(StringUtils.encode("除了将茶叶加入食材一同炖煮，亦可以利用茶叶的香气做熏鸽，令菜式色、香、味俱佳。"));
                DishesBean.setDetail("http://www.daydaycook.com/daydaycook/hk/website/index.do");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);
            }
            menuObj.setDishesList(dishesList);

            }else if ("1003".equals(classType)) {
                menuObj.setResultCode("0");
            }else if ("1004".equals(classType)) {
                menuObj.setResultCode("0");
            }

//        } else if ("13388998888".equals(mobileNO)) {
//            {
//                menuObj.setResultCode("0");
//            }
//        }

        return menuObj;
    }
}
