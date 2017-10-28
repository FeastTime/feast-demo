package com.feast.demo.bid.core;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

import java.util.Map;

public class BidDispatcher {

    private static EventBus eventBus = new EventBus();

    private static Map<String,BidWaiter> waiterMap = Maps.newConcurrentMap();

    public static Map<String, BidWaiter> getWaiterMap() {
        return waiterMap;
    }

    /**
     * 添加竞价活动
     * @param waiter
     */
    public static void addWaiter(BidWaiter waiter){
        waiterMap.put(waiter.getActivityId().toString(),waiter);
        eventBus.register(waiter);
        beginBid(waiter);
    }

    /**
     * 开启活动
     * @param waiter
     */
    public static void beginBid(BidWaiter waiter){
        BidBeginEvent begin = new BidBeginEvent();
        begin.setTargetId(waiter.getActivityId());
        eventBus.post(begin);
    }

    /**
     * 发送停止事件
     * @param time
     */
    public static void timeOver(Long time){
        BidEndEvent end = new BidEndEvent();
        end.setTime(time);
        eventBus.post(end);
    }

    /**
     * 删除活动
     * @param id
     * @param waiterMap
     */
    public static void removeWaiter(String id){
        eventBus.unregister(waiterMap.get(id));
        waiterMap.remove(id);
    }

    /**
     * 加入bid
     * @param bidId
     * @param request
     * @return
     */
    public static boolean joinBid(String bidId,BidRequest request){
        BidWaiter waiter = (BidWaiter) waiterMap.get(bidId);
        if(waiter == null || !waiter.getRun()){
            return false;
        }

        waiter.joinBid(request);
        return true;
    }
}
