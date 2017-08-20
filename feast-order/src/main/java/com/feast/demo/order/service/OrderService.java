package com.feast.demo.order.service;

import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.vo.OrderDetailVo;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 */
public interface OrderService {

    public String status();

    public List<OrderInfo> findAll();

    public void create(OrderInfo orderInfo);

    public void update(OrderInfo orderInfo);

    public void create(OrderDetail orderDetail);

    public void update(OrderDetail orderDetail);

    public void delete(Long orderID, Long dishID);

    public List<OrderInfo> findByOrderId(Long orderID);

    public List<OrderDetailVo> findVoByOrderId(Long orderId);

}
