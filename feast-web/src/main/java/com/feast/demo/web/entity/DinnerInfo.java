package com.feast.demo.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DinnerInfo implements Serializable{

    private Integer waitingCount;
    private Integer numberPerTable;
}
