package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by matao on 2017/8/27.
 */
@Entity
@Data
@Table(name = "ingredients")
public class Ingredients implements Serializable {
    @Id
    private String dishid;
    private String ingredientname;
    private String number;
    private String weight;
    private String calories;

}
