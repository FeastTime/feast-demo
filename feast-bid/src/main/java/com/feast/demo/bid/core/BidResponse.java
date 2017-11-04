package com.feast.demo.bid.core;

import java.io.Serializable;
import java.math.BigDecimal;

public class BidResponse implements Serializable{


    Boolean isWinner;

    BidRequest bidRequeat;

    public BidResponse(BidRequest bidRequeat){
        this.bidRequeat = bidRequeat;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public BidRequest getBidRequeat() {
        return bidRequeat;
    }

    public void setBidRequeat(BidRequest bidRequeat) {
        this.bidRequeat = bidRequeat;
    }
}
