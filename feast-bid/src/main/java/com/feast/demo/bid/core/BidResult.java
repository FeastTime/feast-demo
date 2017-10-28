package com.feast.demo.bid.core;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class BidResult implements Serializable {

    /**
     * 参与竞价的请求
     */
    private Collection<BidRequest> requests;

    /**
     * 胜者,有可能有多人
     */
    private List<BidRequest> winners = Lists.newArrayList();

    /**
     * 本次竞价信息
     */
    private BidInfo activityInfo;

    /**
     * 状态
     */
    private Boolean stats;

    /**
     * msg
     */
    private String msg;

    public Collection<BidRequest> getRequests() {
        return requests;
    }

    public void setRequests(Collection<BidRequest> requests) {
        this.requests = requests;
    }

    public List<BidRequest> getWinners() {
        return winners;
    }

    public void setWinners(List<BidRequest> winners) {
        this.winners = winners;
    }

    public BidInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(BidInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public Boolean getStats() {
        return stats;
    }

    public void setStats(Boolean stats) {
        this.stats = stats;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
