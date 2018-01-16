package com.feast.demo.table.dao;

import com.feast.demo.table.entity.TableInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface TableInfoDao extends PagingAndSortingRepository<TableInfo,Long>{

    @Query("select d from TableInfo d where d.userId = ?1 and d.storeId = ?2 and d.id = ?3 ")
    TableInfo selectDeskByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId);


    ArrayList<TableInfo> findByStoreId(Long storeId);

    ArrayList<TableInfo> findByStoreIdAndPassType(Long storeId, int passtype);

    ArrayList<TableInfo> findByUserId(Long userId);

    TableInfo findByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId);

    ArrayList<TableInfo> findByUserIdAndStoreIdAndPassType(Long userId, Long storeId,Integer passType);
}
