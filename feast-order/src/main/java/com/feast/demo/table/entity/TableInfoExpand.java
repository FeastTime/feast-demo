package com.feast.demo.table.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TableInfoExpand extends  TableInfo{

    private String storeName;
}
