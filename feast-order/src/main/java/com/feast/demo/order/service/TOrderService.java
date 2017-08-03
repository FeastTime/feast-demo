package com.feast.demo.order.service;

import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.entity.TOrder;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 */
public interface TOrderService {

    public String status();

    public List<OrderInfo> findAll();

    public void create(OrderInfo orderInfo);

    public void update(OrderInfo orderInfo);

    public void delete(OrderInfo orderInfo);

    public List<OrderInfo> findByOrderId(Long orderID);

}
