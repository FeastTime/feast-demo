package com.feast.demo.feedback.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedback_id;

    private String content;

    private String contact_way;

    private Long userId;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Date feedbackTime;

    private String site;

}
