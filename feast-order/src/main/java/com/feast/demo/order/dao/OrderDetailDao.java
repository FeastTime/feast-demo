package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;


/**
 * Created by ggke on 2017/6/24.
 * Update by wangpp on 2017/8/3.
 */
public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail,Long>{

    @Query("select o from OrderDetail o where o.orderid=?1")
    ArrayList<OrderDetail> findDetailByOrderId(Long orderID);

    @Query("select o from OrderDetail o where o.orderid=?1 and o.dishid =?2")
    OrderDetail findByOrderIdAndDishID(Long orderID, Long dishID);


}
