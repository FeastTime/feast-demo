package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ggke on 2017/6/24.
 */
public interface AdvertisementDao extends PagingAndSortingRepository<Advertisement,Long>,AdvertisementDaoCustom {
}
