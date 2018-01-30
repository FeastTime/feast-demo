package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.bid.core.BidRequest;
import com.feast.demo.bid.core.BidResponse;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.order.service.BidRecordService;
import com.feast.demo.order.vo.BidRecordVo;
import com.feast.demo.store.entity.Store;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.controller.WSService;
import com.feast.demo.web.entity.ComeinRestBean;
import com.feast.demo.web.entity.DeskInfoBean;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.entity.WebSocketMessageBean;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service()
public class ComeinRestService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static Map<String,Object> redPackages = Maps.newHashMap();

    private static Map<String,String> redId2UserId = Maps.newHashMap();


    private Lock lock = new ReentrantLock();

    @Autowired
    private CouponService couponService;

    @Autowired
    private TableService tableService;

    @Autowired
    private StoreService storeService;

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
     *
     */
    public List<WebSocketMessageBean> WSInterfaceProc(int type, JSONObject jsonObject, User sender, String storeId){

        String retMessage = "";
        switch(type){
//            case 1:
                //retMessage = userComeinProc(jsonObject);
            case WebSocketEvent.ENTER_STORE:
//                retMessage = userComeinProc(jsonObject);
                break;
            case 2:
                retMessage = addDeskList(jsonObject);
                break;
            case 3:
                retMessage = newDeskNotify(jsonObject);
                break;
            case 4:
                retMessage = userOfferPrice(jsonObject);
                break;
            case 5:
                retMessage = grabDesk(jsonObject);
                break;
            case 6:
                retMessage = deskHistory(jsonObject);
                break;
            case WebSocketEvent.SEND_MESSAGE:
                return chat(jsonObject, sender, storeId);
                //break;
        }

        List<WebSocketMessageBean> list = new ArrayList<>();
        list.add(new WebSocketMessageBean().setMessage(retMessage).toStore(""));

        return list;
    }

    private String takeRedPackage(JSONObject jsono) {
        Map<String,Object> result = null;
        String message = "";
        Long userId = null;
        TableInfo tableInfo = null;
        CouponTemplate couponTemplate = null;
        Integer type = 6;
        try{
            result = Maps.newHashMap();
            userId = jsono.getLong("userId");
            String redPackageId = jsono.getString("redPackageId");
            List<Object> redPackage = (List<Object>)redPackages.get(redPackageId);
            System.out.println(redPackage);
            lock.lock();
            String userIds = redId2UserId.get(redPackageId+"");
            if(userIds!=null&&userIds.contains(userId+",")){
                message = "您已经抢过这个红包了";
            }else if(redPackage.size()==0){
                message = "对不起，您来晚了";
            }else{
                Object o = redPackage.get(0);
                if(o instanceof TableInfo){
                    tableInfo = (TableInfo)o;
                    tableInfo.setUserId(userId);
                    tableService.saveTableInfo(tableInfo);
                    String oldUserId = redId2UserId.get("userId");
                    if(oldUserId!=null&&!oldUserId.equals("")){
                        redId2UserId.put(redPackageId,oldUserId+userId+",");

                    }else{
                        redId2UserId.put(redPackageId,userId+",");
                    }
                    result.put("tableInfo",tableInfo);
                    redPackage.remove(0);
                    message = "恭喜您获得一个桌位";
                }else if(o instanceof CouponTemplate){
                    Random random = new Random();
                    int i = random.nextInt(redPackage.size());
                    couponTemplate = (CouponTemplate)redPackage.get(i);
                    couponTemplate = couponService.findCouponTemplateById(couponTemplate.getId());
                    UserCoupon userCoupon = new UserCoupon();
                    userCoupon.setCouponCode(UUID.randomUUID()+"");
                    userCoupon.setCouponTitle(couponTemplate.getCouponTitle());
                    userCoupon.setCouponPicture(couponTemplate.getCouponPicture());
                    userCoupon.setCouponType(couponTemplate.getCouponType());
                    userCoupon.setStartTime(new Date());
                    userCoupon.setCouponValidity(new Date(new Date().getTime()+couponTemplate.getCouponValidity()*24*60*60*1000));
                    userCoupon.setStoreId(couponTemplate.getStoreId());
                    userCoupon.setPermissionsDescribed(couponTemplate.getPermissionsDescribed());
                    userCoupon = couponService.saveUserCoupon(userCoupon);
                    userCoupon.setUserId(userId);
                    couponService.saveUserCoupon(userCoupon);
                    String oldUserId = redId2UserId.get("userId");
                    if(oldUserId!=null&&!oldUserId.equals("")){
                        redId2UserId.put(redPackageId,oldUserId+userId+",");

                    }else{
                        redId2UserId.put(redPackageId,userId+",");
                    }
                    result.put("couponInfo",userCoupon);
                    message = "恭喜您获得一张优惠券";
                    redPackage.remove(i);
                }
            }
            result.put("message",message);
            result.put("type",type);
            lock.unlock();
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }

    /**
     * 发红包
     * @param jsono
     * @return
     */
    public String sendRedPackage(JSONObject jsono) {
        List<Object> redPackage = null;
        Map<String,Object> result = null;
        Long storeId = null;
        TableInfo tableInfo = null;
        List<CouponTemplate> couponList = null;
        Date date = null;
        try{
            storeId = jsono.getLong("storeId");
            Store storeInfo = storeService.getStoreInfo(storeId);
            redPackage = Lists.newArrayList();
            result = Maps.newHashMap();
            tableInfo = jsono.getObject("tableInfo",TableInfo.class);
            tableInfo = tableService.saveTableInfo(tableInfo);
            redPackage.add(tableInfo);
            JSONArray couponArray = jsono.getJSONArray("couponInfo");
            couponList = JSONArray.parseArray(JSON.toJSONString(couponArray),CouponTemplate.class);
            for (CouponTemplate couponTemplate : couponList) {
                Long count = couponTemplate.getCouponCount();
                for (int i = 0; i < count; i++) {
                    redPackage.add(couponTemplate);
                }
            }
            String redPackageId = UUID.randomUUID()+"";
            redPackages.put(redPackageId,redPackage);
            result.put("resultCode",0);
            result.put("redPackageId",redPackageId);
            result.put("storeIcon",storeInfo.getStoreIcon());
            result.put("storeName",storeInfo.getStoreName());
            result.put("date",new Date());
            result.put("type",4);
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultCode",1);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 聊天
     * @param jsonObject 发送json
     * @param sender 发送者
     * @return jsonString
     */
    private List<WebSocketMessageBean> chat(JSONObject jsonObject, User sender, String storeId) {

        String message = jsonObject.getString("message");

        if (null == message || message.length() == 0){
            return null;
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = format.format(new Date());

        HashMap<String,Object> map = new HashMap<>();

        map.put("userId", sender.getUserId());
        map.put("nickname", sender.getNickName());
        map.put("userIcon", sender.getUserIcon());
        map.put("date", dateStr);
        map.put("type", WebSocketEvent.RECEIVED_MESSAGE);
        map.put("message", message);

        String backMessage = JSON.toJSONString(map);

        List list = new ArrayList<WebSocketMessageBean>();
        list.add(new WebSocketMessageBean().setMessage(backMessage).toStore(storeId));

        return list;

    }

    /**
     * 用户进店
     * @param jsonObj JSONObject
     * @return webSocketMessageBean WebSocketMessageBean
     */
    private WebSocketMessageBean userComeinProc(JSONObject jsonObj){

        HashMap<String,Object> resultMap = null;
        Byte resultCode = 1;
        String resultMsg = "";
        Integer type = 1;
        Long storeId = null;
        try{
            resultMap = Maps.newHashMap();
            Long userId = jsonObj.getLong("userId");
            storeId = jsonObj.getLong("storeId");

            UserStore us = userService.findUserStoreByUserIdAndStoreId(userId,storeId);
            Date date = new Date();
            if(us==null){
                us = new UserStore();
                us.setCreateTime(date);
                us.setStoreId(storeId);
                us.setUserId(userId);
                us.setCount(1l);
                us.setStatus(3);
                us.setLastModified(date);
                userService.saveUserStore(us);
            }else{
                us.setLastModified(date);
                us.setCount(us.getCount()+1);
                userService.saveUserStore(us);
            }

            resultCode = 0;
            resultMsg = "欢迎"+userId + "进店！店小二祝您用餐愉快";

        }catch (Exception e){
            e.printStackTrace();
            resultCode = 1;
            resultMsg = "用户进店失败";
        }

        resultMap.put("resultCode",resultCode);
        resultMap.put("type",type);
        resultMap.put("resultMsg",resultMsg);

        return new WebSocketMessageBean().setMessage(JSON.toJSONString(resultMap)).toStore(storeId + "");
//        return JSON.toJSONString(resultMap);
    }

    /**
     * 老板放桌
     * @param jsonObj
     * @return
     */
    public String addDeskList(JSONObject jsonObj){
        String maxPerson = jsonObj.getString("maxPerson");
        String minPerson = jsonObj.getString("minPerson");
        String storeID = jsonObj.getString("storeID");
        String desc = jsonObj.getString("desc");

        DeskInfoBean deskInfoBean = new DeskInfoBean();
        Map<Object,Object> result = Maps.newHashMap();
        // 开启竞价
        String bid = tbService.openBid(bidTime);

//        startThread(bidTime - 3000L, storeID, bid);

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

//    public void startThread(Long time, String storeID, String bid) {
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Start。。。");
//                System.out.println("进入睡眠状态" + time + "毫秒");
//                try {
//                    Thread.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                JSONObject json = new JSONObject();
//                json.put("mobileNo","");
//                json.put("bid",bid);
//                json.put("maxPrice","");
//                json.put("stt","2");
//
//                bidRecordService.updBidRecord(json);
//
//                Collection<BidRequest> cbr = tbService.getBidRequests(bid);
//                cbr = resultFilter(cbr);
//
//                Map<String, Object> map = new HashMap<>();
//
//                map.put("resultCode" , 0);
//                map.put("type", "7");
//                map.put("data", cbr);
//                map.put("bid", bid);
//
//                String message = JSON.toJSONString(map);
//                // 通知客户端
//                WSService.sendMessage(storeID, message);
//                System.out.println("Thread End。。。");
//            }
//        });
//        t.start();
//    }

    /**
     * 新桌位通知
     * @param jsonObj
     * @return
     */
    public String newDeskNotify(JSONObject jsonObj){

        Map<String,Object> result = Maps.newHashMap();

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
