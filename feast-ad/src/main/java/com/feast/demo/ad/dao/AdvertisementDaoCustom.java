package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 */
public interface AdvertisementDaoCustom {

    public List<Advertisement> findAll2();

    public Page<Advertisement> findByPage(Pageable pageable);
}
