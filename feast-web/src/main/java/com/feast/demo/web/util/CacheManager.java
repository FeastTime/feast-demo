package com.feast.demo.web.util;

import com.feast.demo.bid.core.BidResult;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ggke on 2017/10/28.
 */
public class CacheManager {

    private static Map<Serializable,Serializable> cacheMap = Maps.newConcurrentMap();

    /**
     * 用来保存竞价结果的队列
     */
    private static BlockingQueue<BidResult> bidResultQueue = new LinkedBlockingQueue<BidResult>();

    public static Serializable putToCache(Serializable key, Serializable value){
        cacheMap.put(key,value);
        return cacheMap.get(key);
    }

    public static Serializable getFromCache(Serializable key){
        return cacheMap.get(key);
    }

}
