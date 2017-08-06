package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.entity.TOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by ggke on 2017/6/24.
 * Update by wangpp on 2017/8/3.
 */
public interface TOrderDao extends PagingAndSortingRepository<OrderInfo,Long>{

    @Query("select o from OrderInfo o where o.orderid=?1")
    List<OrderInfo> findByOrderId(Long orderID);

    @Query("select o from OrderDetail o where o.orderid=?1")
    List<OrderDetail> findDetailByOrderId(Long orderID);
}
