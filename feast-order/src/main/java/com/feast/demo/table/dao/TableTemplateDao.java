package com.feast.demo.table.dao;

import com.feast.demo.table.entity.TableTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TableTemplateDao extends PagingAndSortingRepository<TableTemplate, Long> {

    @Query("select d from TableTemplate d where d.storeId = ?1 and d.userId =?2")
    TableTemplate selectDeskByStoreIdAndUserd(long storeId, long userId);
}
