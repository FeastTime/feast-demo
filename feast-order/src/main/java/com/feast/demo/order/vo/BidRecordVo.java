package com.feast.demo.order.vo;

import lombok.Data;

/**
 * Created by matao on 2017/11/5.
 */
@Data
public class BidRecordVo {
    private String bidRecordId;
    private String storeId;
    private String mobileNo;
    private String bid;
    private String maxPrice;
    private String recordTime;
    private String stt;
}

