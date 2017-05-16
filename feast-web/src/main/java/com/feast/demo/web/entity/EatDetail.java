package com.feast.demo.web.entity;

import lombok.Data;

/**
 * Created by pinyou on 17-5-16.
 * 二元组
 */

@Data
public class EatDetail {

    public String type;

    public String percent;

    public EatDetail(){

    }

    public EatDetail(String type,String percent){
        this.type = type;
        this.percent = percent;
    }
}
