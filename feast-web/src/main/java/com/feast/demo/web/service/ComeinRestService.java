package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.bid.core.BidRequest;
import com.feast.demo.bid.core.BidResponse;
import com.feast.demo.order.service.BidRecordService;
import com.feast.demo.order.vo.BidRecordVo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.controller.WSService;
import com.feast.demo.web.entity.ComeinRestBean;
import com.feast.demo.web.entity.DeskInfoBean;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service()
public class ComeinRestService {

    @Autowired
    private BidRecordService bidRecordService;

    @Autowired
    private UserService userService;
    @Autowired
    private TableBidService tbService;

    private static long bidTime = 40000L;
    // 所有店铺缓存
    private static HashMap<String, ArrayList> storeMap= new HashMap<String, ArrayList>();

    // 桌位-用户缓存
    private static HashMap<String, HashMap<String, UserBean>> desk_userMap = new HashMap<String, HashMap<String, UserBean>>();
    // 桌位-用户缓存
    private static HashMap<String, DeskInfoBean> desk_infoMap = new HashMap<String, DeskInfoBean>();

    /**
     * 对接老马接口入口
     * @param message
     * @return
     */
    public String WSInterfaceProc(String message){
        System.out.println("转之前"+message);
        message = StringUtils.decode(message);
        System.out.println("转之后"+message);

        JSONObject jsono  = new JSONObject();

        try{
            jsono = JSON.parseObject(message);
        }catch(Exception e){
            e.printStackTrace();
        }

        int type = Integer.parseInt(jsono.getString("type"));

        String retMessage = "";
        switch(type){
            case 1:
                retMessage = userComeinProc(jsono);
                break;
            case 2:
                retMessage = addDeskList(jsono);
                break;
            case 3:
//              retMessage = newDeskNotify(jsono);
                break;
            case 4:
                retMessage = userOfferPrice(jsono);
                break;
            case 5:
                retMessage = grabDesk(jsono);
                break;
            case 6:
                retMessage = deskHistory(jsono);
                break;
            case 7:
                retMessage = chat(jsono);
                break;
        }
        return retMessage;
    }

    public String chat(JSONObject jsono) {
        User user =  userService.findById(Long.parseLong(jsono.getString("userId")));
        System.out.println(user+"111111111111");
        String userIcon = user.getUserIcon();
        Long userId = user.getId();
        String message = jsono.getString("message");
        String nickname = user.getNickName();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = format.format(date);
        HashMap<String,String> map = new HashMap<>();
        map.put("resultCode","0");
        map.put("userId",userId+"");
        map.put("nickname","张三");
        map.put("userIcon","http://www.jrfazh.cn/daikuan/ykjk/hdtui/?cid=B4-4208");
        map.put("date",dateStr);
        map.put("type","7");
        map.put("message",message);
        System.out.println(JSON.toJSONString(map)+"000000");
        return JSON.toJSONString(map);
    }

    /**
     * 用户进店
     * @param jsonObj
     * @return
     */
    public String userComeinProc(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));

        HashMap<String ,String> resultMap = new HashMap<>();
        resultMap.put("resultCode","0");
        resultMap.put("message","欢迎"+jsonObj.getString("userID") + "进店！店小二祝您用餐愉快！");
        resultMap.put("type","1");


        return JSON.toJSONString(resultMap);
    }

    /**
     * 老板放桌
     * @param jsonObj
     * @return
     */
    public String addDeskList(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        String maxPerson = jsonObj.getString("maxPerson");
        String minPerson = jsonObj.getString("minPerson");
        String storeID = jsonObj.getString("storeID");
        String desc = jsonObj.getString("desc");

        DeskInfoBean deskInfoBean = new DeskInfoBean();
        Map<Object,Object> result = Maps.newHashMap();
        // 开启竞价
        String bid = tbService.openBid(bidTime);

        startThread(bidTime - 3000L, storeID, bid);

        if(storeMap.size() != 0 && storeMap.containsKey(storeID)){
            ArrayList<String> deskList = storeMap.get(storeID);
            deskList.add(bid);
            storeMap.put(storeID, deskList);
        }else{
            ArrayList<String> deskList = new ArrayList<String>();
            deskList.add(bid);
            storeMap.put(storeID, deskList);
        }
        deskInfoBean.setResultCode("0");
        deskInfoBean.setDeskID(bid);
        deskInfoBean.setMaxPerson(maxPerson);
        deskInfoBean.setMinPerson(minPerson);
        deskInfoBean.setDesc(desc);
        deskInfoBean.setStoreID(storeID);

        deskInfoBean.setBid(bid);
        desk_infoMap.put(bid, deskInfoBean);

        JSONObject json = new JSONObject();
        json.put("storeId",deskInfoBean.getStoreID());
        json.put("mobileNo",deskInfoBean.getMobileNO());
        json.put("bid",deskInfoBean.getBid());
        json.put("maxPrice","");
        json.put("stt","1");

        bidRecordService.addBidRecord(json);

        result.put("resultCode", deskInfoBean.getResultCode());
        result.put("maxPerson", deskInfoBean.getMaxPerson());
        result.put("minPerson", deskInfoBean.getMinPerson());
        result.put("storeID", deskInfoBean.getStoreID());
        result.put("desc", deskInfoBean.getDesc());
        result.put("deskID", deskInfoBean.getDeskID());
        result.put("bid", deskInfoBean.getBid());
        result.put("timeLimit", bidTime);

        String personInfo = deskInfoBean.getMaxPerson() == deskInfoBean.getMinPerson()
                ? deskInfoBean.getMaxPerson() + "位"
                : deskInfoBean.getMinPerson() + " - " + deskInfoBean.getMaxPerson() + "位用餐的上宾，";

        result.put("message", "店小二放桌喽！" + personInfo + "里面请！");

        result.put("type", "3");
        return JSON.toJSONString(result);
    }

    public void startThread(Long time, String storeID, String bid) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start。。。");
                System.out.println("进入睡眠状态" + time + "毫秒");
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                JSONObject json = new JSONObject();
                json.put("mobileNo","");
                json.put("bid",bid);
                json.put("maxPrice","");
                json.put("stt","2");

                bidRecordService.updBidRecord(json);

                Collection<BidRequest> cbr = tbService.getBidRequests(bid);
                cbr = resultFilter(cbr);

                Map<String, Object> map = new HashMap<>();

                map.put("resultCode" , 0);
                map.put("type", "7");
                map.put("data", cbr);
                map.put("bid", bid);

                String message = JSON.toJSONString(map);
                // 通知客户端
                WSService.sendMessage(storeID, message);
                System.out.println("Thread End。。。");
            }
        });
        t.start();
    }

    /**
     * 新桌位通知
     * @param jsonObj
     * @return
     */
    public String newDeskNotify(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("maxPerson is:"+jsonObj.getString("maxPerson"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("minPerson is:"+jsonObj.getString("minPerson"));
        System.out.println("desc is:"+jsonObj.getString("desc"));
        System.out.println("type is:"+jsonObj.getString("type"));
        Map<Object,Object> result = Maps.newHashMap();

        ComeinRestBean crBean = new ComeinRestBean();

        crBean.setResultCode("0");

        result.put("resultCode", crBean.getResultCode());
        return JSON.toJSONString(result);
    }

    /**
     * 用户出价
     * @param jsonObj
     * @return
     */
    public String userOfferPrice(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("bid is:"+jsonObj.getString("bid"));
        System.out.println("userID is:"+jsonObj.getString("userID"));
        System.out.println("price is:"+jsonObj.getString("price"));


        // 用户相关信息
        String userID = jsonObj.getString("userID");
        String bid = jsonObj.getString("bid");
        Long price = Long.parseLong(jsonObj.getString("price"));
//
//        UserBean userBean = null;
//        if(desk_userMap.get(bid) != null){
//            userBean = desk_userMap.get(bid).get("userID");
//        }
//        if(userBean == null){
//            userBean = new UserBean();
//        }
//        Long price = Long.parseLong(jsonObj.getString("price"));
//        userBean.setUserID(userID);
//        userBean.setName(userID); // 暂时用手机号代替姓名
//        userBean.setPrice(price);
//        long highPrice = 0;
//        if(desk_userMap.get(bid)!=null){
//            highPrice = desk_userMap.get(bid).get(userID).getHighPrice();
//        }
//        if(highPrice<price){
//            highPrice = price;
//        }
//        userBean.setHighPrice(highPrice);

//        BidRequest br = new BidRequest();
//        br.setBidActivityId(bid);
//        br.setBidPrice(price);
//        br.setBidTime(System.currentTimeMillis());
//        br.setUserId(userID);
        System.out.println(bid + "---" + userID + "----" + price);
        BidResponse bres = tbService.toBid(bid, userID, new BigDecimal(price));
        System.out.println("----bres.toString() : " + bres.toString());
        Boolean isWinner = bres.isWinner();


//        HashMap<String, UserBean> tempUserMap = new HashMap<String, UserBean>();
//        tempUserMap.put(userID, userBean);
//        desk_userMap.put(bid, tempUserMap);

//        ComeinRestBean crBean = new ComeinRestBean();
//        crBean.setResultCode("0");

        Map<Object,Object> result = Maps.newHashMap();
        if(isWinner){
            result.put("resultCode" , "0");
            result.put("highPrice" , price);
            result.put("userID" , userID);
            result.put("type" , "6");
//            result.put("resultList", parseMapToJSONArray(desk_userMap.get(bid)));
        }

        return JSON.toJSONString(result);
    }

    /**
     * 抢桌位
     * @param jsonObj
     * @return
     */
    public String grabDesk(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));
        System.out.println("type is:"+jsonObj.getString("type"));

        Map<Object,Object> result = Maps.newHashMap();
        // 先到先得，结束后清除缓存桌位信息。
        String bid = jsonObj.getString("bid");
        String storeID = jsonObj.getString("storeID");
        if(storeID!=null && storeMap.get(storeID)!=null
                &&storeMap.get(storeID).contains(bid)){
            storeMap.get(storeID).remove(bid);
            result.put("resultCode" , "0");

            JSONObject json = new JSONObject();
            json.put("mobileNo",jsonObj.getString("userID"));
            json.put("bid",bid);
            json.put("maxPrice",jsonObj.getString("price"));
            json.put("stt","3");

            bidRecordService.updBidRecord(json);
        }else{
            result.put("resultCode" , "1");
        }

        return JSON.toJSONString(result);
    }

    /**
     * 历史桌位列表接口
     * @param jsonObj
     * @return
     */
    public String deskHistory(JSONObject jsonObj){
        Map<String,Object> result = Maps.newHashMap();
        JSONObject json = new JSONObject();
        json.put("storeId",jsonObj.getString("storeID"));
        json.put("pageNo",jsonObj.getString("pageNo"));
        json.put("pageNum",jsonObj.getString("pageNum"));

        List<BidRecordVo> list = bidRecordService.findBidRecordByStoreId(json);
        result.put("list",list);
        return JSON.toJSONString(result);
    }

    private Collection<BidRequest> resultFilter(Collection<BidRequest> cbr){
        Iterator it1 = cbr.iterator();
        Iterator it2 = cbr.iterator();
        BigDecimal price = new BigDecimal(0);
        Collection<BidRequest> tmpCbr = new ArrayList<BidRequest>();
        while(it1.hasNext()){
            BidRequest br = (BidRequest) it1.next();
            if(br.getBidPrice().compareTo(price)>0){
                price = br.getBidPrice();
            }
        }
        while(it2.hasNext()){
            BidRequest br = (BidRequest) it2.next();
            if(br.getBidPrice().compareTo(price)==0){
                tmpCbr.add(br);
            }
            System.out.println(tmpCbr.size());
        }
        return tmpCbr;
    }

}
