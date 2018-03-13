package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackageAutoSendTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RedPackageAutoSendTimeDao extends PagingAndSortingRepository<RedPackageAutoSendTime,Long> {


    @Modifying
    @Query("update RedPackageAutoSendTime rpast set rpast.autoSendTime = ?1 where rpast.storeId = ?2")
    void updateAutoSendTimeByStoreId(Integer time, Long storeId);

    @Query("select rpast.autoSendTime from RedPackageAutoSendTime rpast where rpast.storeId = ?1")
    int findAutoSendTimeByStoreId(Long storeId);

    RedPackageAutoSendTime findByStoreId(Long storeId);
}
