package com.feast.demo.menu.entity;

import java.io.Serializable;

/**
 * Created by ggke on 2017/4/11.
 */
public class TMenu implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
