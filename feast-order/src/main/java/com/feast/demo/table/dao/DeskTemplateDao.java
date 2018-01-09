package com.feast.demo.table.dao;

import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeskTemplateDao extends PagingAndSortingRepository<DeskTemplate, Long> {

    @Query("select d from DeskTemplate d where d.storeId = ?1 and d.userId =?2")
    DeskTemplate selectDeskByStoreIdAndUserd(long storeId, long userId);
}
