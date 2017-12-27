package com.feast.demo.user.entity;

import com.feast.demo.store.entity.Store;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="historystore")
@Data
public class HistoryStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long storeId;
    private Character status;
    private Date visitTime;
}
