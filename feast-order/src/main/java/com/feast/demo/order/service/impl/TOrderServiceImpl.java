package com.feast.demo.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.order.dao.TOrderDao;
import com.feast.demo.order.dao.TOrderDetailDao;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.service.TOrderService;
import com.feast.demo.order.vo.OrderDetailVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public List<OrderDetailVo> findVoByOrderId(Long orderId) {
        List<?> result = tOrderDao.findOrderDetailVoByOrderId(orderId);
        List<OrderDetailVo> list = Lists.newArrayList();
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

}
