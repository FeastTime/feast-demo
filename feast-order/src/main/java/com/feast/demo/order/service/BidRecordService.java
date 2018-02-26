package com.feast.demo.order.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.order.vo.BidRecordVo;

import java.util.ArrayList;

/**
 * Created by matao on 2017/11/5.
 */
public interface BidRecordService {
    public ArrayList<BidRecordVo> findBidRecordByStoreId(JSONObject jsonObj);

    public String addBidRecord(JSONObject jsonObj);

    public String updBidRecord(JSONObject jsonObj);
}
