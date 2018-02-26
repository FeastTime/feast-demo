package com.feast.demo.hibernate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/7/29.
 */
@MappedSuperclass
@Data
@ToString(callSuper = true)
@EntityListeners({ TimedEntityListener.class })
public abstract class TimedEntity{
    /**
     *
     */
    private static final long serialVersionUID = 5968742044806210654L;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(updatable = false, nullable = false)
    protected Timestamp creation;

    /**
     * 最后修改时间
     */
    @Index(name="lastModified")
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(nullable = false)
    protected Timestamp lastModified;

}

