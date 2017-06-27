package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 */
public class AdvertisementDaoImpl implements AdvertisementDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<Advertisement> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from Advertisement a");
        TypedQuery<Advertisement> query = em.createQuery( sb.toString(), Advertisement.class);
        List<Advertisement> list = query.getResultList();
        return list;
    }
}
