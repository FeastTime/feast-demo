package com.feast.demo.bid.core;

/**
 * 结束事件
 */

public class BidEndEvent extends BidEvent{

    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
