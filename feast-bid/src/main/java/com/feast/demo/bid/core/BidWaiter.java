package com.feast.demo.bid.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BidWaiter {

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

    /**
     * 运行状态
     */
    private Boolean run;

    private BidHandle bidHandle;

    private List<BidRequest> winners = Lists.newArrayList();

    private BigDecimal maxPrice = new BigDecimal(-1);

    public BidWaiter(BidHandle bidHandle){
        this.bidHandle = bidHandle;
    }

    /**
     * 保存参与竞价的信息
     */
    private Map<String,BidRequest> requestMap = Maps.newConcurrentMap();

    public Boolean getRun() {
        return run;
    }

    public void setRun(Boolean run) {
        this.run = run;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void begin(BidBeginEvent event) {
            if(run == null) {
                beginTime = System.currentTimeMillis();
                System.out.println("bid:" + activityId + "开始," + (endTime - beginTime) / 1000 + "秒之后结束");
                run = true;
            }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void end(BidEndEvent event) {
        if(run && event.getTime() >= endTime) {
            System.out.println("bid:"+activityId+"结束,共运行"+(event.getTime()-beginTime)/1000+"秒");
            BidDispatcher.removeWaiter(activityId);
            run = false;//
            BidResult result = getBidResult();
            System.out.println(activityId+"竞拍结束");
            //BidServer.putBidResult(result);
            bidHandle.afterPorcess(result);
        }
    }

    /**
     * 参与竞价
     * @param request
     * @return
     */
    public synchronized BidResponse joinBid(BidRequest request){
        if(!run){
            return null;
        }
        requestMap.put(request.getUserId(),request);
        BidResponse bidResponse = new BidResponse(request);
        for(BidRequest bid:requestMap.values()){
            if(bid.getBidPrice().compareTo(maxPrice) > 0){
                maxPrice = bid.getBidPrice();
                winners.clear();
                winners.add(bid);
                bidResponse.setWinner(true);
            }else if(bid.getBidPrice().compareTo(maxPrice) == 0){
                winners.add(bid);
                bidResponse.setWinner(true);
            }else{
                bidResponse.setWinner(false);
            }
        }
        return bidResponse;
    }

    private BidResult getBidResult(){
        BidResult bidResult = new BidResult();
        if(!requestMap.isEmpty()){
            bidResult.setWinners(winners);
            bidResult.setStats(true);
            bidResult.setRequests(requestMap.values());
        }else{
            bidResult.setStats(false);
            bidResult.setMsg("无人参与本次竞价");
        }
        bidResult.setActivityInfo(new BidInfo(this));
        return bidResult;
    }

    /**
     * 获取当前最高出价
     * @return
     */
    public BigDecimal getMaxPrice(){
        return maxPrice;
    }

    /**
     * 获取当前参与竞价的请求列表
     * @return
     */
    public Collection<BidRequest> getRequests(){
        return requestMap.values();
    }
}
