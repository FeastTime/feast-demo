package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail,Long>{


    @Query("select o from OrderDetail o where o.orderid=?1 and o.dishid =?2")
    OrderDetail findByOrderIdAndDishID(Long orderid, Long dishid);


}
