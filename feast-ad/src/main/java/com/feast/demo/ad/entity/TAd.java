package com.feast.demo.ad.entity;

import java.io.Serializable;

/**
 * Created by ggke on 2017/4/11.
 */
public class TAd implements Serializable{

    public TAd(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
