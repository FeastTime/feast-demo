package com.feast.demo.bid.core;

import java.io.Serializable;
import java.math.BigDecimal;

public class BidRequest implements Serializable{

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 参与的活动ID
     */
    private String bidActivityId;

    /**
     *出价
     */
    private BigDecimal bidPrice;

    /**
     * 出价时间
     */
    private Long bidTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBidActivityId() {
        return bidActivityId;
    }

    public void setBidActivityId(String bidActivityId) {
        this.bidActivityId = bidActivityId;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getBidTime() {
        return bidTime;
    }

    public void setBidTime(Long bidTime) {
        this.bidTime = bidTime;
    }
}
