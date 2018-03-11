package com.feast.demo.table.dao;

import com.feast.demo.table.entity.TableInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface TableInfoDao extends PagingAndSortingRepository<TableInfo,Long>{

    ArrayList<TableInfo> findByUserId(Long userId);

    TableInfo findByStoreIdAndTableId( Long storeId, Long tableId);

    ArrayList<TableInfo> findByUserIdAndStoreIdAndPassType(Long userId, Long storeId, Integer passType);

    ArrayList<TableInfo> findByStoreIdOrderByMaketableTimeDesc(Long storeId);
}
