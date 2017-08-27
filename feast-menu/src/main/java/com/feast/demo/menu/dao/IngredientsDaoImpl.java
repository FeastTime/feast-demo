package com.feast.demo.menu.dao;

import com.google.common.collect.Maps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created by matao on 2017/8/6.
 */
public class IngredientsDaoImpl implements IngredientsDaoCustom {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<?> findIngredientsByDishId(String dishId) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select ingredientname,number,weight,calories from Ingredients ");
        sb.append(" where dishid=:dishId");
        params.put("dishId",dishId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getResultList();
    }






}
