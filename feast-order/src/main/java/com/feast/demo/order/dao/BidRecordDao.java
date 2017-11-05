package com.feast.demo.order.dao;

import com.feast.demo.order.entity.BidRecord;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matao on 2017/8/12.
 */
public interface BidRecordDao extends PagingAndSortingRepository<BidRecord,String>,BidRecordDaoCustom {

}
