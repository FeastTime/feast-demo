package com.feast.demo.order.service.impl;

import com.feast.demo.order.dao.OrderDao;
import com.feast.demo.order.dao.OrderDetailDao;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.service.OrderService;
import com.feast.demo.order.vo.OrderDetailVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by ggke on 2017/3/31.
 * Update by wangpp on 2017/8/3.
 */
@Service()
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    public ArrayList<OrderInfo> findAll() {
        return (ArrayList<OrderInfo>) orderDao.findAll();
    }


    /**
     * 加入购物车
     * @param order
     */
    @Transactional(readOnly = false)
    public void create(OrderInfo order) {
        orderDao.save(order);
    }

    /**
     * 更新订单表
     * @param order
     */
    @Transactional(readOnly = false)
    public void update(OrderInfo order) {
        orderDao.save(order);
    }

    /**
     * 添加明细表
     * @param orderDetail
     */
    @Transactional(readOnly = false)
    public void create(OrderDetail orderDetail) {
        System.out.println("ssssssssssssssssssssssssss=="+orderDetailDao);
        orderDetailDao.save(orderDetail);
    }

    /**
     * 更新明细表
     * @param orderDetail
     */
    @Transactional(readOnly = false)
    public void update(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
    }

    /**
     * 删除明细表
     * @param orderID
     */
    @Transactional(readOnly = false)
    public void delete(Long orderID, Long dishID) {
        OrderDetail detail = orderDetailDao.findByOrderIdAndDishID(orderID, dishID);
        orderDetailDao.delete(detail);
    }

    public String status() {
        return "From OrderService.OK!";
    }

    public ArrayList<OrderInfo> findByOrderId(Long orderID) {
        if(orderID == null){
            return null;
        }
        return orderDao.findByOrderId(orderID);
    }

    public ArrayList<OrderDetailVo> findVoByOrderId(Long orderId) {
        ArrayList<?> result = orderDao.findOrderDetailVoByOrderId(orderId);
        ArrayList<OrderDetailVo> list = Lists.newArrayList();
        for(Object o:result){
            OrderDetailVo vo = convertOrderDetailVo((Object[]) o);//查询结果set到vo上
            list.add(vo);
        }
        return list;
    }

    private OrderDetailVo convertOrderDetailVo(Object[] o){
        //s.name,detail.dishname,detail.originalprice,orderInfo.status
        OrderDetailVo vo = new OrderDetailVo();
        vo.setStoreName((String) o[0]);
        vo.setDishName((String) o[1]);
        vo.setOriginalprice((BigDecimal)o[2]);
        vo.setStatus((Integer) o[3]);
        return vo;
    }

    public OrderDetail findByOrderIdAndDishID(Long orderid, Long dishid) {
        if(orderid == null || dishid == null){
            return null;
        }
        return orderDetailDao.findByOrderIdAndDishID(orderid, dishid);
    }

}
