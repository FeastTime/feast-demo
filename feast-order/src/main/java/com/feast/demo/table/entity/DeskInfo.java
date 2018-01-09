package com.feast.demo.table.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="desk_info")
@Data
public class DeskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer personCount;

    private Integer passtype;

    private Date makedeskTime;

    private Date takedeskTime;

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
