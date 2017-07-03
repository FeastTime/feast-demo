package com.feast.demo.order.dao;

import com.feast.demo.order.entity.TOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 */
public class TOrderDaoImpl implements TOrderDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<TOrder> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from TOrder a");
        TypedQuery<TOrder> query = em.createQuery( sb.toString(), TOrder.class);
        List<TOrder> list = query.getResultList();
        return list;
    }
}
