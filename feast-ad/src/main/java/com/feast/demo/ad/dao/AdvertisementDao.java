package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by ggke on 2017/6/24.
 */
public interface AdvertisementDao extends PagingAndSortingRepository<Advertisement,Long>,AdvertisementDaoCustom {

    @Query("Select ad from Advertisement ad where ad.type=?1 and ad.width=?2 and ad.height=?3")
    List<Advertisement> findByTypeAndSize(String type, Integer width, Integer height);

    @Query("Select ad from Advertisement ad where ad.type=?1 and ad.width=?2 and ad.height=?3")
    Page<Advertisement> findPageByTypeAndSize(String type, Integer width, Integer height, Pageable pageable);

}
