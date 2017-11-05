package com.feast.demo.order.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.order.dao.BidRecordDao;
import com.feast.demo.order.service.BidRecordService;
import com.feast.demo.order.vo.BidRecordVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by matao on 2017/11/5.
 */
@Service()
public class BidRecordServiceImpl implements BidRecordService {
    @Autowired
    private BidRecordDao bidRecordDao;

    public List<BidRecordVo> findBidRecordByStoreId(JSONObject jsonObj) {
        String storeId = jsonObj.getString("storeId");
        String pageNo = jsonObj.getString("pageNo");
        String pageNum = jsonObj.getString("pageNum");
        if (StringUtils.isNotEmpty(storeId) && StringUtils.isNotEmpty(pageNo) && StringUtils.isNotEmpty(pageNum)) {
            int pageNoInt = Integer.parseInt(pageNo);
            int pageNumInt = Integer.parseInt(pageNum);
            List<?> result = bidRecordDao.findBidRecordByStoreId(storeId, pageNoInt, pageNumInt);
            List<BidRecordVo> list = Lists.newArrayList();
            for (Object o : result) {
                BidRecordVo vo = convertBidRecordVo((Object[]) o);//查询结果set到vo上
                list.add(vo);
            }
            return list;
        } else {
            return null;
        }
    }

    private BidRecordVo convertBidRecordVo(Object[] o) {
        BidRecordVo vo = new BidRecordVo();
        vo.setBidRecordId((String) o[0]);
        vo.setStoreId((String) o[1]);
        vo.setMobileNo((String) o[2]);
        vo.setBid((String) o[3]);
        vo.setMaxPrice((String) o[4]);
        vo.setRecordTime((String) o[5]);
        vo.setStt((String) o[6]);
        return vo;
    }

    @Transactional(readOnly = false)
    public String addBidRecord(JSONObject jsonObj) {
        String storeId = jsonObj.getString("storeId");
        String mobileNo = jsonObj.getString("mobileNo");
        String bid = jsonObj.getString("bid");
        String maxPrice = jsonObj.getString("maxPrice");
        String stt = jsonObj.getString("stt");

        if (StringUtils.isNotEmpty(storeId) && StringUtils.isNotEmpty(mobileNo)
                && StringUtils.isNotEmpty(bid) && StringUtils.isNotEmpty(maxPrice)
                && StringUtils.isNotEmpty(stt)) {
            return (String) bidRecordDao.addBidRecord(storeId, mobileNo, bid, maxPrice, stt);
        }
        return "1";
    }

    public String updBidRecord(JSONObject jsonObj) {

        String mobileNo = jsonObj.getString("mobileNo") == null ? "" : jsonObj.getString("mobileNo");
        String bid = jsonObj.getString("bid") == null ? "" : jsonObj.getString("bid");
        String maxPrice = jsonObj.getString("maxPrice") == null ? "" : jsonObj.getString("maxPrice");
        String stt = jsonObj.getString("stt") == null ? "" : jsonObj.getString("stt");
        if (StringUtils.isNotEmpty(bid)) {
            return (String) bidRecordDao.updBidRecord(mobileNo, maxPrice, stt, bid);
        }
        return "1";
    }
}