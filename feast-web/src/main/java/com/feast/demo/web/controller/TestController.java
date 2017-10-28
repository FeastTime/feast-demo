package com.feast.demo.web.controller;

import com.feast.demo.web.service.CacheService;
import com.feast.demo.web.service.TableBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 * Created by ggke on 2017/10/25.
 */

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private TableBidService tableBidService;

    @RequestMapping(value = "/cache/put", method = RequestMethod.GET)
    public String cachePut(@RequestParam("key") String key, @RequestParam("value") String value) {
        return (String) cacheService.put(key, value);
    }

    @RequestMapping(value = "/cache/get", method = RequestMethod.GET)
    public String cacheGet(@RequestParam("key") String key) {
        return (String) cacheService.get(key);
    }

    @RequestMapping(value = "/bid/open/", method = RequestMethod.GET)
    public String openBid() {
        return tableBidService.openBid(30000L);
    }

    @RequestMapping(value = "/bid/result/", method = RequestMethod.GET)
    public Object bidResult() {
        return tableBidService.getAllBidResult();
    }

    @RequestMapping(value = "/bid/tobid/", method = RequestMethod.GET)
    public Object bidResultSize(
            @RequestParam("bidActivityId") String bidActivityId) {
        Random random = new Random();
        String userId = UUID.randomUUID().toString().substring(25);

        return userId + "参与竞价"+ tableBidService.toBid(bidActivityId,userId,new BigDecimal(random.nextInt(100)));
    }
}
