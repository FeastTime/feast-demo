package com.feast.demo.table.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "desk_template")
@Entity
@Data
public class DeskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long storeId;

    private String description;

    private Integer recieveTime;
}
