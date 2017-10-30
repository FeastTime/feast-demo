package com.feast.demo.bid.core;

import java.io.Serializable;

public class BidInfo implements Serializable {
    /**
     * 本次竞价ID,使用uuid
     */
    private String activityId;

    /**
     *竞价结束时间
     */
    private Long endTime;

    /**
     * 竞价开始时间
     */
    private Long beginTime;

    public BidInfo(BidWaiter waiter){
        this.activityId = waiter.getActivityId();
        this.endTime = waiter.getEndTime();
        this.beginTime = waiter.getBeginTime();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }
}
