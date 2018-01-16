package com.feast.demo.table.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "table_template")
@Entity
@Data
public class TableTemplate implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long storeId;

    private String description;

    private Integer recieveTime;
}
