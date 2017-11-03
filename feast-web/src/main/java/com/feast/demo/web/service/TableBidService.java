package com.feast.demo.web.service;

import com.feast.demo.bid.core.BidRequest;
import com.feast.demo.bid.core.BidResponse;
import com.feast.demo.bid.core.BidResult;
import com.feast.demo.bid.service.BidService;
import com.feast.demo.cache.service.CacheService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ggke on 2017/10/27.
 */

@Service
public class TableBidService implements InitializingBean {

    @Resource
    private CacheService cacheService;

    @Resource
    private BidService bidService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //每秒一次，查询竞价结果
        Runnable heartBeat = new Runnable() {
            public void run() {
                while(true) {
                    List<BidResult> list = bidService.getAllBidResult();
                    if (CollectionUtils.isNotEmpty(list)) {
                        for (BidResult result : list) {
                            returnBidResult(result);
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread heartBeatThread = new Thread(heartBeat);
//        heartBeatThread.setDaemon(true);
        heartBeatThread.start();
    }

    /**
     * 开启一个竞价，默认持续15秒
     * @return
     */
    public String openBid(){
        return bidService.openBid();
    }

    /**
     * 开启一个竞价，持续指定时长
     * @return
     */
    public String openBid(Long limit){
        return bidService.openBid(limit);
    }

    public List<BidResult> getAllBidResult(){
        return bidService.getAllBidResult();
    }

    /**
     *参与竞价
     * @param bidActivityId
     * @param userId
     * @param bidPrice
     * @return
     */
    public BidResponse toBid(String bidActivityId, String userId, BigDecimal bidPrice){
        BidRequest bidRequest = new BidRequest();
        bidRequest.setBidActivityId(bidActivityId);
        bidRequest.setBidPrice(bidPrice);
        bidRequest.setUserId(userId);
        return bidService.pushBidRequest(bidRequest);
    }

    private void returnBidResult(BidResult bidResult){
        System.out.println(bidResult);
        /**
         * 调用webservice接口发送信息
         * 1、向店家发送竞价成功用户的信息
         * 2、向竞价成功的用户发送
         * 3、向竞价失败的用户发送竞价失败的信息
         * 4、保存竞价结果
         */
    }

    /**
     * 返回指定竞拍下当前的所有竞价请求
     * @param bidActivityId
     * @return
     */
    public Collection<BidRequest> getBidRequests(String bidActivityId){
        return bidService.getBidRequests(bidActivityId);
    }

    /**
     * 返回指定竞拍活动当前最高出价
     * @param bidActivityId
     * @return
     */
    public BigDecimal getMaxPrice(String bidActivityId){
        return bidService.getMaxPrice(bidActivityId);
    }

}
