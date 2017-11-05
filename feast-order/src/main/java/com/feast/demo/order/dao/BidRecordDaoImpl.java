package com.feast.demo.order.dao;

import com.google.common.collect.Maps;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created by matao on 2017/11/5.
 */
public class BidRecordDaoImpl implements BidRecordDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<?> findBidRecordByStoreId(String storeId, int pageNo, int pageNum) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select bidrecordid,storeid,mobileno,bid,maxprice,recordtime,stt");
        sb.append(" from BidRecord");
        sb.append(" where storeid=:storeId");
        sb.append(" order by recordtime desc");

        params.put("storeId",storeId);

        Query query = em.createQuery(sb.toString());
        Pageable pageable = new PageRequest(pageNo, pageNum);
        if(pageable != null){
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getResultList();
    }

    public String addBidRecord(String storeId, String mobileNo, String bid, String maxPrice, String stt){
        StringBuilder sb = new StringBuilder();
//        Map<String,Object> params = Maps.newHashMap();
        Map<Integer,Object> params = Maps.newHashMap();

        sb.append("insert into BidRecord(bidrecordid, storeid, mobileno, bid, maxprice, stt) ");
//        sb.append(" values(_nextval('BIDRECORD_SEQ'),:storeId,:mobileNo,:bid,:maxPrice,:stt)");
        sb.append(" values({?=call _nextval('BIDRECORD_SEQ')},?,?,?,?,?)");
//        params.put("storeId",storeId);
//        params.put("mobileNo",mobileNo);
//        params.put("bid",bid);
//        params.put("maxPrice",maxPrice);
//        params.put("stt",stt);
        params.put(1,storeId);
        params.put(2,mobileNo);
        params.put(3,bid);
        params.put(4,maxPrice);
        params.put(5,stt);
        Query query = em.createNativeQuery(sb.toString());
//        for(int key:params.keySet()){
//            query.setParameter(key,params.get(key));
//        }
        return String.valueOf(query.executeUpdate());
    }

    public String updBidRecord(String mobileNo, String maxPrice, String stt, String bid){
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();

        sb.append("update BidRecord set" );
        if(mobileNo !=null && !"".equals(mobileNo)){
            sb.append(" mobileno = :mobileNo");
            params.put("mobileNo",mobileNo);
        }
        if(maxPrice !=null && !"".equals(maxPrice)){
            sb.append(" maxprice = :maxPrice");
            params.put("maxPrice",maxPrice);
        }
        if(stt !=null && !"".equals(stt)){
            sb.append(" stt = :stt");
            params.put("stt",stt);
        }

        sb.append(" where bid = :bid");
        params.put("bid",bid);

        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return String.valueOf(query.executeUpdate());
    }
}