package com.feast.demo.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="historyperson")
@Data
public class HistoryPerson implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    private Long storeId;
    private Long userId;
    private Date visitTime;
}
