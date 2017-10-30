package com.feast.demo.cache;

import com.feast.demo.bid.core.BidResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ggke on 2017/10/24.
 */
public class CacheManager {

    private static Map<Serializable,Serializable> cacheMap = Maps.newConcurrentMap();

    /**
     * 用来保存竞价结果的队列
     */
    private static BlockingQueue<BidResult> bidResultQueue = new LinkedBlockingQueue<BidResult>();

    public static Serializable putToMap(Serializable key, Serializable value){
        cacheMap.put(key,value);
        return cacheMap.get(key);
    }

    public static Serializable getFromMap(Serializable key){
        return cacheMap.get(key);
    }

    public static void addBidResult(BidResult e){
        bidResultQueue.add(e);
    }

    public static List<BidResult> getAllBidResult(){
        List<BidResult> list = Lists.newArrayList();
        while(bidResultQueue.size()>0){
            list.add(bidResultQueue.poll());
        }
       System.out.println(list.size());
        return list;
    }

    public static Integer getBidResultSize(){
        return bidResultQueue.size();
    }
}
