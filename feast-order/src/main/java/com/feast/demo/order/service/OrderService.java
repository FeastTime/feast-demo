package com.feast.demo.order.service;

import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.vo.OrderDetailVo;

import java.util.ArrayList;

/**
 * Created by ggke on 2017/3/31.
 */
public interface OrderService {

    public String status();

    public ArrayList<OrderInfo> findAll();

    public void create(OrderInfo orderInfo);

    public void update(OrderInfo orderInfo);

    public void create(OrderDetail orderDetail);

    public void update(OrderDetail orderDetail);

    public void delete(Long orderID, Long dishID);

    public ArrayList<OrderInfo> findByOrderId(Long orderID);

    public OrderDetail findByOrderIdAndDishID(Long orderid, Long dishid);

    public ArrayList<OrderDetailVo> findVoByOrderId(Long orderId);

}
