package com.feast.demo.menu.dao;

import com.google.common.collect.Maps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by matao on 2017/8/6.
 */
public class DishesCategoryDaoImpl implements DishesCategoryDaoCustom {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public ArrayList<?> findDishesCategoryByStoreId(String storeId) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select categoryid,categoryname from DishesCategory ");
        sb.append(" where categoryid != '9999' and storeid=:storeId");
        params.put("storeId",storeId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return (ArrayList<?>)query.getResultList();
    }






}
