package com.feast.demo.table.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="table_info")
@Data
public class TableInfo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    private String suportSeatNumber;

    private Integer passType;

    private Date maketableTime;

    private Date taketableTime;

    private Integer recieveTime;

    private Long userId;

    private Long storeId;

    private String userPhone;

    private String userIcon;

    private String userNickname;

    private Double price;

    private Integer isCome;

    private String description;

}
