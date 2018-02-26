package com.feast.demo.bid.service.Impl;

import com.feast.demo.bid.core.*;
import com.feast.demo.bid.service.BidService;
import com.feast.demo.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by ggke on 2017/10/24.
 */
@Service
public class BidServiceImpl implements BidService, InitializingBean,BidHandle {

    /**
     * 默认竞价开启时15秒
     */
    private static final Long TIME_LIMIT = 15000L;

    private Thread heartBeatThread = null;

    public void afterPropertiesSet() throws Exception {

        Runnable heartBeat = new Runnable() {
            public void run() {
                pushForward();
            }
        };
        heartBeatThread = new Thread(heartBeat);
//        heartBeatThread.setDaemon(true);
        heartBeatThread.start();
    }

    /**
     * 给竞价服务每秒发送心跳信息，驱动竞价服务
     */
    public static void pushForward(){
        while(true){
            if(!BidDispatcher.getWaiterMap().isEmpty()){
                System.out.print(".");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BidDispatcher.timeOver(System.currentTimeMillis());
            }else{
                try {
                    System.out.println("waiterMap is empty,sleep "+TIME_LIMIT/1000 + "秒");
                    Thread.sleep(TIME_LIMIT-1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String openBid() {
       return openBid(TIME_LIMIT);
    }

    /**
     * 开启竞价，返回本次竞价id
     * @param timeLimit
     * @return
     */
    public String openBid(Long timeLimit) {
        Long nowtime = System.currentTimeMillis();
        String id = UUID.randomUUID().toString();
        BidWaiter bw = new BidWaiter(this);
        bw.setActivityId(id);
        bw.setEndTime(nowtime+timeLimit);
        BidDispatcher.addWaiter(bw);
        System.out.println("开启bid："+id);
        return id;
    }

    /**
     * 参与竞价
     * @param bidRequest
     * @return
     */
    public BidResponse pushBidRequest(BidRequest bidRequest) {
        bidRequest.setBidTime(System.currentTimeMillis());
        return BidDispatcher.joinBid(bidRequest.getBidActivityId(),bidRequest);
    }

    public ArrayList<BidResult> getAllBidResult() {
        return CacheManager.getAllBidResult();
    }

    public Integer getBidResultSize() {
        return CacheManager.getBidResultSize();
    }

    /**
     * 竞价完成后的处理
     * @param bidResult
     */
    public void afterPorcess(BidResult bidResult) {
        CacheManager.addBidResult(bidResult);
    }

    public Collection<BidRequest> getBidRequests(String bidAcivityId){
        return BidDispatcher.getBidRequests(bidAcivityId);
    }

    public BigDecimal getMaxPrice(String bidActivityId){
        return BidDispatcher.getMaxPrice(bidActivityId);
    }
}
