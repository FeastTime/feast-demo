package com.feast.demo.bid.core;

public class BidServer{

    /**
     * 暂定一次竞价开启时15秒
     */
    private static final Long TIME_LIMIT = 15000L;

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

    public static void putBidResult(BidResult result){
        StringBuffer sb = new StringBuffer();
        sb.append("活动"+result.getActivityInfo().getActivityId()+"结束\n"
                + "开始时间:"+result.getActivityInfo().getBeginTime() + "\n"
                + "结束时间:"+result.getActivityInfo().getBeginTime() + "\n");
        sb.append("胜者是:\n");
        for(BidRequest bid:result.getWinners()){
            sb.append(bid.getUserId()+",出价为:"+bid.getBidPrice()+"出价时间:"+bid.getBidTime()+"\n");
        }
        sb.append("以下是全部竞价信息:\n");
        for(BidRequest bid:result.getRequests()){
            sb.append(bid.getUserId()+",出价为:"+bid.getBidPrice()+"出价时间:"+bid.getBidTime()+"\n");
        }
        System.out.println(sb.toString());
    }

//    public static void main(String[] args) {
//
//        new Thread(new Runnable() {
//            public void run() {
//                pushForward();
//            }
//        }).start();
//
//        //1、添加一个竞价活动
//        Long nowtime = System.currentTimeMillis();
//        String id = UUID.randomUUID().toString();
//        BidWaiter bw = new BidWaiter(this);
//        bw.setActivityId(id);
//        bw.setEndTime(nowtime+TIME_LIMIT);
//        BidDispatcher.addWaiter(bw);
//        System.out.println("开启活动"+id);
//        //2、随机添加一些竞价
//        int uerId = 0;
//        while(BidDispatcher.getWaiterMap().get(id)!=null && BidDispatcher.getWaiterMap().get(id).getRun()){
//            BidRequest request = new BidRequest();
//            request.setBidActivityId(id);
//            request.setBidPrice(new BigDecimal((new Random()).nextInt(100)));
//            request.setBidTime(System.currentTimeMillis());
//            request.setUserId((uerId++)+"");
//            System.out.println("添加竞价");
//            BidDispatcher.joinBid(id,request);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("push over");
//    }

    public String openBid() {
        return null;
    }

    public String openBid(Long timeLimit) {
        return null;
    }

    public boolean pushBidRequest(BidRequest bidRequest) {
        return false;
    }
}
