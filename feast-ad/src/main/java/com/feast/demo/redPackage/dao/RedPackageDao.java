package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedPackageDao extends PagingAndSortingRepository<RedPackage,Long>{

    @Modifying
    @Query("update RedPackage rp set rp.autoSendTime = ?1 where rp.storeId = ?2")
    void updateByStoreId(Integer time,Long storeId);

    List<RedPackage> findByStoreIdIn(List<Long> storeIds);

    @Query()
    List<RedPackage> findByIsUseAndStoreIdIn(Integer isUse, List<Long> storeIds);

    List<RedPackage> findRedPackageByIsUse(Integer isUse);

    List<RedPackage> findByStoreIdOrderByCreateTimeDesc(Long storeId);

    @Modifying
    @Query("update RedPackage rp set rp.isUse = ?1 where rp.storeId = ?2 and rp.redPackageId <> ?3")
    void updateRedPackageId(Integer isUse,Long storeId, Long redPackageId);
}
