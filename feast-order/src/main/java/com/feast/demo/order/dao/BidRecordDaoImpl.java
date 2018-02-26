package com.feast.demo.order.dao;

import com.google.common.collect.Maps;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by matao on 2017/11/5.
 */
public class BidRecordDaoImpl implements BidRecordDaoCustom {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public ArrayList<?> findBidRecordByStoreId(String storeId, int pageNo, int pageNum) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = Maps.newHashMap();
        sb.append("select bidrecordid,storeid,mobileno,bid,maxprice,recordtime,stt");
        sb.append(" from BidRecord");
        sb.append(" where storeid=:storeId");
        sb.append(" order by recordtime desc");

        params.put("storeId", storeId);

        Query query = em.createQuery(sb.toString());
        Pageable pageable = new PageRequest(pageNo, pageNum);
        if (pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }
        return (ArrayList<?>)query.getResultList();
    }

    public String addBidRecord(String storeId, String mobileNo, String bid, String maxPrice, String stt) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = Maps.newHashMap();

        sb.append("insert into bidrecord(bidrecordid, storeid, mobileno, bid, maxprice, stt) ");
        sb.append(" values(_nextval('BIDRECORD_SEQ'),:storeId,:mobileNo,:bid,:maxPrice,:stt)");
        params.put("storeId", storeId);
        params.put("mobileNo", mobileNo);
        params.put("bid", bid);
        params.put("maxPrice", maxPrice);
        params.put("stt", stt);
        Query query = em.createNativeQuery(sb.toString());
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }
        return String.valueOf(query.executeUpdate());
    }

    public String updBidRecord(String mobileNo, String maxPrice, String stt, String bid) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        Map<String, Object> params = Maps.newHashMap();
        if (mobileNo != null && !"".equals(mobileNo) ||
                maxPrice != null && !"".equals(maxPrice) ||
                stt != null && !"".equals(stt)) {
            sb.append("update bidrecord set");
        } else {
            return "0";
        }
        if (mobileNo != null && !"".equals(mobileNo)) {
            sb.append(" mobileno = :mobileNo,");
            params.put("mobileNo", mobileNo);
        }
        if (maxPrice != null && !"".equals(maxPrice)) {
            sb.append(" maxprice = :maxPrice,");
            params.put("maxPrice", maxPrice);
        }
        if (stt != null && !"".equals(stt)) {
            sb.append(" stt = :stt,");
            params.put("stt", stt);
        }
        sb1.append(sb.toString().substring(0, sb.toString().length() - 1));
        sb1.append(" where bid = :bid");
        params.put("bid", bid);

        Query query = em.createNativeQuery(sb1.toString());
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }
        return String.valueOf(query.executeUpdate());
    }
}