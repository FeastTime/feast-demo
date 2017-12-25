package com.feast.demo.history.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="history")
@Data
public class History implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    private Long storeId;
    private Long userId;
    private Long mobileNo;
}
