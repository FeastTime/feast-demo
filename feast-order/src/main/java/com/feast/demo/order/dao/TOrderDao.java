package com.feast.demo.order.dao;

import com.feast.demo.order.entity.TOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ggke on 2017/6/24.
 */
public interface TOrderDao extends PagingAndSortingRepository<TOrder,Long>,TOrderDaoCustom {
}
