package com.feast.demo.version.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "version")
public class Version implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    private String clientType;

    private String versionName;

    private Integer versionNumber;

    private String downloadAddress;
}
