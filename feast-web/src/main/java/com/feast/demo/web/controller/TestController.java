package com.feast.demo.web.controller;

import com.feast.demo.web.service.CacheManagerService;
import com.feast.demo.web.service.TableBidService;
import com.feast.demo.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 *
 * Created by ggke on 2017/10/25.
 */

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private CacheManagerService cacheManagerService;

    @Autowired
    private TableBidService tableBidService;

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/cache/put", method = RequestMethod.GET)
    public String cachePut(@RequestParam("key") String key, @RequestParam("value") String value) {
        return (String) cacheManagerService.put(key, value);
    }

    @RequestMapping(value = "/cache/get", method = RequestMethod.GET)
    public String cacheGet(@RequestParam("key") String key) {
        return (String) cacheManagerService.get(key);
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

    @RequestMapping(value = "/ss/", method = RequestMethod.GET)
    public String cachePut() {
        return testService.m1();
    }


}
