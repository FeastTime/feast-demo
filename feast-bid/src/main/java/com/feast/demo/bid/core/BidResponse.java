package com.feast.demo.bid.core;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ggke on 2017/11/2.
 */
@Data
public class BidResponse implements Serializable {

    private boolean isWinner;

    private BidRequest request;

    private String bidAcitvityId;

    BidResponse(BidRequest request){
        this.request = request;
        bidAcitvityId = request.getBidActivityId();
    }
}
