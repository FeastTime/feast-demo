package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedPackageDao extends PagingAndSortingRepository<RedPackage,Long>{

    @Query("select rp from RedPackage rp where rp.storeId=?1 and rp.redPackageId <> ?2")
    List<RedPackage> findByStoreIdFilterRedPackageId(Long storeId,Long redPackageId);

    List<RedPackage> findByStoreId(Long storeId);
}
