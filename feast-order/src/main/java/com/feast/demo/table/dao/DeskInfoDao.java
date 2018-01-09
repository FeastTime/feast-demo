package com.feast.demo.table.dao;

import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeskInfoDao extends PagingAndSortingRepository<DeskInfo,Long>{

    @Query("select d from DeskInfo d where d.userId = ?1 and d.storeId = ?2 and d.id = ?3 ")
    DeskInfo selectDeskByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId);


}
