package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderInfo;

import java.util.ArrayList;


/**
 * Created by ggke on 2017/6/25.
 * Update by wangpp on 2017/8/3.
 */
public interface OrderDaoCustom {

    ArrayList<OrderInfo> findAll2();

    ArrayList<?> findOrderDetailVoByOrderId(Long orderId);

}
