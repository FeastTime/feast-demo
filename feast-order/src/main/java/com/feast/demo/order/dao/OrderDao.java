package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;


/**
 * Created by ggke on 2017/6/24.
 * Update by wangpp on 2017/8/3.
 */
public interface OrderDao extends PagingAndSortingRepository<OrderInfo,Long>,OrderDaoCustom{

    @Query("select o from OrderInfo o where o.orderid=?1")
    ArrayList<OrderInfo> findByOrderId(Long orderID);


}
