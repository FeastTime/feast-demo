package com.feast.demo.ad.service.impl;

import com.feast.demo.ad.entity.TAd;
import com.feast.demo.ad.service.AdService;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by pinyou on 17-4-11.
 */
public class AdServiceImpl implements AdService {

    /**
     * 无参调用
     * @return
     */
    public String status() {
        return "AD service OK!";
    }

    public String transferEntity(TAd tad) {
        System.out.println(tad.getName());
        return "tad's name " + tad.getName();
    }

    /**
     * 字符串做参数并有字符串返回值
     * @param msg
     * @return
     */
    public String transferString(String msg) {
        return "AdService-->" + msg;
    }

    /**
     * 返回列表的调用
     * @param num
     * @return
     */
    public List<TAd> getAdList(Integer num) {
        List<TAd> list = Lists.newArrayList();
        if (num == null || num <= 0) {
            return list;
        }
        //随即生成num个TA的对象，name为随即字符串
        for (int i = 0; i < num; i++) {
            TAd ad = new TAd();
            ad.setName(getRandomString());

            list.add(ad);
        }

        return list;
    }

    private String getRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(String.valueOf((char) Math.round(Math.random() * 25 + 97)));
        }
        return sb.toString();
    }
}
