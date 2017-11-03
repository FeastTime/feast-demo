package com.feast.demo.bid.service;

import com.feast.demo.bid.core.BidRequest;
import com.feast.demo.bid.core.BidResponse;
import com.feast.demo.bid.core.BidResult;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by ggke on 2017/10/24.
 */
public interface BidService {

    public String openBid();

    public String openBid(Long timeLimit);

    public BidResponse pushBidRequest(BidRequest bidRequest);

    public List<BidResult> getAllBidResult();

    public Integer getBidResultSize();

    public Collection<BidRequest> getBidRequests(String bidActivityId);

    public BigDecimal getMaxPrice(String bidActivityId);
}
