package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedPackageDao extends PagingAndSortingRepository<RedPackage,Long>{

    @Query("select rp from RedPackage rp where rp.storeId=?1 and rp.redPackageId <> ?2")
    List<RedPackage> findByStoreIdFilterRedPackageId(Long storeId,Long redPackageId);


    List<RedPackage> findByStoreId(Long storeId);

    @Modifying
    @Query("update RedPackage rp set rp.autoSendTime = ?1 where rp.storeId = ?2")
    void updateByStoreId(Integer time,Long storeId);

    List<RedPackage> findByStoreIdIn(List<Long> storeIds);

    RedPackage findByStoreIdAndIsUse(String storeId, Integer isUse);

    @Query()
    List<RedPackage> findByIsUseAndStoreIdIn(Integer isUse, List<Long> storeIds);

    List<RedPackage> findByStoreIdOrderByCreateTimeDesc(Long storeId);
}
