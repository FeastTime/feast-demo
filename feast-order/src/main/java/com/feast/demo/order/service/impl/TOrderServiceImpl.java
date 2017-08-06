package com.feast.demo.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.order.dao.TOrderDao;
import com.feast.demo.order.dao.TOrderDetailDao;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 * Update by wangpp on 2017/8/3.
 */
@Service()
public class TOrderServiceImpl implements TOrderService {

    @Autowired
    private TOrderDao tOrderDao;

    @Autowired
    private TOrderDetailDao tOrderDetailDao;

    public List<OrderInfo> findAll() {
        return (List<OrderInfo>) tOrderDao.findAll();
    }


    /**
     * 加入购物车
     * @param order
     */
    @Transactional(readOnly = false)
    public void create(OrderInfo order) {
        tOrderDao.save(order);
    }

    /**
     * 更新订单表
     * @param order
     */
    @Transactional(readOnly = false)
    public void update(OrderInfo order) {
        tOrderDao.save(order);
    }

    /**
     * 更新明细表
     * @param orderDetail
     */
    @Transactional(readOnly = false)
    public void update(OrderDetail orderDetail) {
        tOrderDetailDao.save(orderDetail);
    }

    /**
     * 删除明细表
     * @param orderID
     */
    @Transactional(readOnly = false)
    public void delete(Long orderID, Long dishID) {
        tOrderDetailDao.deleteDetailByDishID(orderID, dishID);
    }

    public String status() {
        return "From OrderService.OK!";
    }

    public List<OrderInfo> findByOrderId(Long orderID) {
        if(orderID == null){
            return null;
        }
        return tOrderDao.findByOrderId(orderID);
    }
}
