package com.feast.demo.order.dao;

import java.util.ArrayList;

/**
 * Created by matao on 2017/11/5.
 */
public interface BidRecordDaoCustom {
    public ArrayList<?> findBidRecordByStoreId(String storeId, int pageNo, int pageNum);

    public String addBidRecord(String storeId, String mobileNo, String bid, String maxPrice, String stt);

    public String updBidRecord(String mobileNo, String maxPrice, String stt, String bid);

}
