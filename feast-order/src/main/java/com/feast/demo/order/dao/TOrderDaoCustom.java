package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.entity.TOrder;

import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 * Update by wangpp on 2017/8/3.
 */
public interface TOrderDaoCustom {

    public List<OrderInfo> findAll2();

}
