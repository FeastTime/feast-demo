package com.feast.demo.table.dao;

import com.feast.demo.table.entity.TableInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface TableInfoDao extends PagingAndSortingRepository<TableInfo,Long>{

    ArrayList<TableInfo> findByUserId(Long userId);

    TableInfo findByStoreIdAndTableId( Long storeId, Long tableId);

    ArrayList<TableInfo> findByUserIdAndStoreIdAndPassType(Long userId, Long storeId, Integer passType);

    ArrayList<TableInfo> findByStoreIdOrderByMaketableTimeDesc(Long storeId);

    @Query(value = "select * from table_info where user_id = ?1 and store_id = ?2 and" +
            " is_come = ?3 and unix_timestamp()*1000<(unix_timestamp(taketable_time)*1000+recieve_time*60*1000)",nativeQuery = true)
    TableInfo findTableInfoByUserIdAndStoreIdAndIsUseAndValidTime(Long userId, Long storeId, Integer isCome);
}
