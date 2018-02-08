package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.store.entity.Store;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.*;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.entity.WebSocketMessageBean;
import com.feast.demo.web.util.CouponIdCreator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * Created by Administrator on 2017/10/22.
 */
@Service()
public class ComeinRestService {

    // 用户与商户关系
    private static Map<String, HashMap<String, UserBean>> user2Store = Maps.newHashMap();

    // 红包
    private static Map<String, List<Object>> redPackages = Maps.newHashMap();

    // 红包与用户关系
    private static Map<String,Set<String>> redId2UserId = Maps.newHashMap();

    //private static Map<String,List<DinnerInfo>> dinnerMap = Maps.newHashMap();
//    private static Map<String,List<DinnerInfo>> dinnerMap = Maps.newHashMap();

    // 店铺<几人桌<等待人数>>
    // private static Map<String,Map<Integer, Integer>> dinnerMap = Maps.newHashMap();
    private static Random random = new Random();

    private Lock lock = new ReentrantLock();

    @Autowired
    private CouponService couponService;

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

//    @Autowired
//    private BidRecordService bidRecordService;

//    @Autowired
//    private TableBidService tbService;

//    private static long bidTime = 40000L;
//    // 所有店铺缓存
//    private static HashMap<String, ArrayList<String>> storeMap= new HashMap<>();

//    // 桌位-用户缓存
//    private static HashMap<String, HashMap<String, UserBean>> desk_userMap = new HashMap<>();

//    // 桌位-用户缓存
//    private static HashMap<String, DeskInfoBean> desk_infoMap = new HashMap<>();


    /**
     * 长连接消息处理入口
     *
     * @param type 消息类型
     * @param jsonObject JSONObject
     * @param sender 发送者
     * @param storeId 店铺ID
     * @return 返回消息列表
     */
    public List<WebSocketMessageBean> WSInterfaceProc(int type, JSONObject jsonObject, User sender, String storeId){

        switch(type){

            case WebSocketEvent.ENTER_STORE:
                return userComeInProc(sender, storeId);

            case WebSocketEvent.SEND_RED_PACKAGE:
                return sendRedPackage(jsonObject,sender,storeId);

            case WebSocketEvent.OPEN_RED_PACKAGE:
                return takeRedPackage(jsonObject, sender);

            case WebSocketEvent.SEND_MESSAGE:
                return chat(jsonObject, sender, storeId);

            case WebSocketEvent.SET_NUMBER_OF_USER:
                return setNumberOfUser(jsonObject,sender,storeId);
        }

        return null;
    }

    /**
     * 设置就餐人数
     *
     * @param jsonObject JSONObject
     * @param user 用户
     * @param storeId 店铺ID
     * @return 消息列表
     */
    private List<WebSocketMessageBean> setNumberOfUser(JSONObject jsonObject, User user, String storeId) {

        String userId = user.getUserId().toString();

        if (userId.length() == 0) {
            return null;
        }

        Integer numberPerTable = jsonObject.getInteger("dinnerCount");

        user2Store.get(storeId).get(userId).setNumberPerTable(numberPerTable);

        return getDinnerListMessage(storeId);
    }


    /**
     * 获取 就餐人数返回消息
     *
     * @param storeId 商家ID
     * @return 消息列表
     */
    private List<WebSocketMessageBean> getDinnerListMessage(String storeId){

        List<Long> waiters = userService.findUserIdByStoreId(Long.parseLong(storeId));

        if (null == waiters) {
            return null;
        }

        Map<String,Object> result = Maps.newHashMap();

        result.put("dinnerList",getDinnerList(storeId));
        result.put("type", WebSocketEvent.WAITING_USER_CHANGED_NOTIFY);
        result.put("storeId",storeId);
        result.put("userId","system");

        String backMessage = JSON.toJSONString(result);

        List<WebSocketMessageBean> list = new ArrayList<>();

        for (Long id : waiters) {
            list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(id + ""));
        }

        return list;
    }
    /**
     * 获取店铺食客人数列表
     *
     * @param storeId 店铺Id
     * @return 食客人数列表
     */
    private List<DinnerInfo> getDinnerList(String storeId) {

        Map<Integer, Integer> personMap = new HashMap<>();

        Integer oneNumberPerTable;

        for (UserBean oneUser : user2Store.get(storeId).values()) {

            oneNumberPerTable = oneUser.getNumberPerTable();

            personMap.putIfAbsent(oneNumberPerTable, 0);
            personMap.put(oneNumberPerTable, personMap.get(oneNumberPerTable) + 1);
        }

        List<DinnerInfo> dinnerInfoList = new ArrayList<>();
        DinnerInfo dinnerInfo;

        for (Integer noPerTable : personMap.keySet()) {

            dinnerInfo = new DinnerInfo();
            dinnerInfo.setNumberPerTable(noPerTable);
            dinnerInfo.setWaitingCount(personMap.get(noPerTable));

            dinnerInfoList.add(dinnerInfo);
        }
        return dinnerInfoList;
    }

    /**
     * 拆红包
     *
     * @param jsonObject json
     * @param sender 发送者
     * @return 返回消息列表
     */
    private List<WebSocketMessageBean> takeRedPackage(JSONObject jsonObject, User sender) {

        try {
            lock.lock();

            Long userId = sender.getUserId();
            String userIdStr = userId.toString();

            String redPackageId = jsonObject.getString("redPackageId");

            List<Object> redPackage = redPackages.get(redPackageId);
            redId2UserId.computeIfAbsent(redPackageId+"", k -> Sets.newHashSet());

            String message = "";
            Map<String, Object> result = Maps.newHashMap();

            if (null == redPackages || redPackage.size() == 0) {

                message = "对不起，您来晚了";
                // 删除缓存数据
                redPackages.remove(redPackageId);
                redId2UserId.remove(redPackageId+"");

            } else if (redId2UserId.get(redPackageId+"").contains(userIdStr)) {

                message = "您已经抢过这个红包了";

            } else {

                Object object = redPackage.get(0);

                if (object instanceof TableInfo) {

                    TableInfo tableInfo = (TableInfo) object;
                    tableInfo.setUserId(userId);
                    tableInfo.setIsCome(1);
                    tableService.saveTableInfo(tableInfo);

                    redId2UserId.get(redPackageId+"").add(userIdStr);

                    result.put("tableInfo", tableInfo);
                    redPackage.remove(0);
                    message = "恭喜您获得一个桌位";

                } else if (object instanceof CouponTemplate) {

                    int i = random.nextInt(redPackage.size());

                    CouponTemplate couponTemplate = (CouponTemplate) redPackage.get(i);

                    couponTemplate = couponService.findCouponTemplateById(couponTemplate.getId());
                    UserCoupon userCoupon = new UserCoupon();
                    userCoupon.setCouponCode(CouponIdCreator.nextId());
                    userCoupon.setCouponTitle(couponTemplate.getCouponTitle());
                    userCoupon.setCouponPicture(couponTemplate.getCouponPicture());
                    userCoupon.setCouponType(couponTemplate.getCouponType());
                    userCoupon.setStartTime(new Date());
                    userCoupon.setCouponValidity(new Date(new Date().getTime() + couponTemplate.getCouponValidity() * 24 * 60 * 60 * 1000));
                    userCoupon.setStoreId(couponTemplate.getStoreId());
                    userCoupon.setPermissionsDescribed(couponTemplate.getPermissionsDescribed());
                    userCoupon = couponService.saveUserCoupon(userCoupon);
                    userCoupon.setUserId(userId);
                    couponService.saveUserCoupon(userCoupon);

                    redId2UserId.get(redPackageId+"").add(userIdStr);
                    result.put("couponInfo", userCoupon);
                    message = "恭喜您获得一张优惠券";
                    redPackage.remove(i);
                }
            }

            // 添加发送者
            result.put("userId", "system");
            result.put("message", message);
            result.put("type", WebSocketEvent.RECEIVED_RED_PACKAGE_SURPRISED);
            String backMessage = JSON.toJSONString(result);

            List<WebSocketMessageBean> list = new ArrayList<>();
            list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(userIdStr));
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;

    }

    /**
     * 发红包
     *
     * @param jsonObject json
     * @param sender 发送用户
     * @param storeId 店铺ID
     * @return 消息列表
     */
    private List<WebSocketMessageBean> sendRedPackage(JSONObject jsonObject, User sender, String storeId) {

        List<Object> redPackage;
        TableInfo tableInfo;
        List<CouponTemplate> couponList;

        try {
            redPackage = Lists.newArrayList();
            tableInfo = jsonObject.getObject("tableInfo", TableInfo.class);

            if (null != tableInfo) {
                tableInfo = tableService.saveTableInfo(tableInfo);
                redPackage.add(tableInfo);
            }


            JSONArray couponArray = jsonObject.getJSONArray("couponInfo");
            couponList = JSONArray.parseArray(JSON.toJSONString(couponArray), CouponTemplate.class);
            for (CouponTemplate couponTemplate : couponList) {
                Long count = couponTemplate.getCouponCount();
                for (int i = 0; i < count; i++) {
                    redPackage.add(couponTemplate);
                }
            }
            String redPackageId = UUID.randomUUID() + "";
            redPackages.put(redPackageId, redPackage);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateStr = format.format(new Date());

            Map<String, Object> result = Maps.newHashMap();

            result.put("date", dateStr);
            result.put("userId", sender.getUserId());
            result.put("nickname", sender.getUsername());
            result.put("userIcon", sender.getUserIcon());
            result.put("redPackageId", redPackageId);
            result.put("type", WebSocketEvent.RECEIVED_RED_PACKAGE);

            String backMessage = JSON.toJSONString(result);

            List<WebSocketMessageBean> list = new ArrayList<>();

            HashMap<String, UserBean> map = user2Store.get(storeId);

            if (null != map){

                for (String receiverId : map.keySet()) {
                    list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(receiverId));
                }
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 聊天
     *
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

        List<WebSocketMessageBean> list = new ArrayList<>();

        HashMap<String, UserBean> concurrentHashMap = user2Store.get(storeId);

        if (null != concurrentHashMap) {

            for (String receiverId : concurrentHashMap.keySet()) {
                list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(receiverId));
            }
        }

        return list;
    }


    /**
     * 用户扫码进店
     *
     * @param user 用户信息
     * @param storeId 店铺id
     * @return 消息列表
     */
    private List<WebSocketMessageBean> userComeInProc(User user, String storeId) {

        Long userId = user.getUserId();

        if (null == storeId || null == userId) {
            return null;
        }

        // 添加用户与商家关系 -> cache
        UserBean userBean = new UserBean();
        userBean.setUserID(userId.toString());
        userBean.setNumberPerTable(0);

        user2Store.computeIfAbsent(storeId, k -> Maps.newHashMap());
        user2Store.get(storeId).put(userId + "", userBean);

        HashMap<String, Object> result = Maps.newHashMap();

        // 添加用户与商家关系 -> db
        try {
            UserStore userStore = userService.findUserStoreByUserIdAndStoreId(userId, Long.parseLong(storeId));

            Date date = new Date();

            if (userStore == null) {
                userStore = new UserStore();
                userStore.setCreateTime(date);
                userStore.setStoreId(Long.parseLong(storeId));
                userStore.setUserId(userId);
                userStore.setCount(1L);

            } else {
                userStore.setCount(userStore.getCount() + 1);
            }

            userStore.setStatus(3);
            userStore.setLastModified(date);

            userService.saveUserStore(userStore);

            Store store = storeService.getStoreInfo(Long.parseLong(storeId));

            result.put("type", WebSocketEvent.RECEIVED_MESSAGE);
            result.put("date", new Date());
            result.put("userId", userId);
            result.put("message", "欢迎" + user.getNickName() + "进店！店小二祝您用餐愉快");
            result.put("nickName", store.getStoreName());
            result.put("userIcon", store.getStoreIcon());

            String backMessage = JSON.toJSONString(result);
            List<WebSocketMessageBean> list = new ArrayList<>();

            HashMap<String, UserBean> map = user2Store.get(storeId);

            if (null != map) {

                for (String receiverId : map.keySet()) {
                    list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(receiverId));
                }
            }

            // 添加消息通知商家信息变更
            List<WebSocketMessageBean> dinnerListMessage = getDinnerListMessage(storeId);

            if (null != dinnerListMessage) {

                list.addAll(dinnerListMessage);
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("type", WebSocketEvent.RECEIVED_MESSAGE);
        result.put("date", new Date());
        result.put("userId", userId);
        result.put("message", "用户进店失败");

        String backMessage = JSON.toJSONString(result);
        List<WebSocketMessageBean> list = new ArrayList<>();
        list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(userId + ""));

        return list;
    }

    /**
     * 恢复用户与商家的关系
     *
     * @param userId 用户ID
     */
    public void repairRelationship(String userId){

        // 恢复用户与商家的关系
        Set<Long> storeIds = userService.findStoreIdByUserId(Long.parseLong(userId));

        if(storeIds!=null){

            String storeIdStr;
            UserBean userBean;

            for (Long storeId: storeIds) {

                storeIdStr = storeId + "";
                userBean = new UserBean();
                userBean.setUserID(userId);
                userBean.setNumberPerTable(0);

                user2Store.computeIfAbsent(storeIdStr, k -> Maps.newHashMap());
                user2Store.get(storeIdStr).put(userId, userBean);
            }
        }
    }


    /*public void autoSendRedPackage(){
        List<RedPackage> redPackages;

        try {
            redPackages = storeService.findRedPackageByIsUse(2);
            for (RedPackage redPackage : redPackages) {
                Long id = redPackage.getRedPackageId();
            }

            for (CouponTemplate couponTemplate : couponList) {
                Long count = couponTemplate.getCouponCount();
                for (int i = 0; i < count; i++) {
                    redPackage.add(couponTemplate);
                }
            }

            String redPackageId = UUID.randomUUID() + "";
            redPackages.put(redPackageId, redPackage);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateStr = format.format(new Date());

            Map<String, Object> result = Maps.newHashMap();

            result.put("date", dateStr);
            result.put("userId", sender.getUserId());
            result.put("nickname", sender.getUsername());
            result.put("userIcon", sender.getUserIcon());
            result.put("redPackageId", redPackageId);
            result.put("type", WebSocketEvent.RECEIVED_RED_PACKAGE);

            String backMessage = JSON.toJSONString(result);

            List<WebSocketMessageBean> list = new ArrayList<>();

            HashMap<String, UserBean> map = user2Store.get(storeId);

            if (null != map){

                for (String receiverId : map.keySet()) {
                    list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(receiverId));
                }
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/
    /**
     * 用户离店
     *
     * @param userId 用户ID
     */
    public List<WebSocketMessageBean> removeRelationship(String userId){

        List<WebSocketMessageBean> allDinnerListMessage = new ArrayList<>();
        List<WebSocketMessageBean> dinnerListMessage;

        for (String storeId : user2Store.keySet()) {

            // 将连接  从  用户与商户的关系结构中 删除
            user2Store.get(storeId).remove(userId);

            // 通知商家用户离店
            dinnerListMessage = getDinnerListMessage(storeId);

            if (null != dinnerListMessage){

                allDinnerListMessage.addAll(dinnerListMessage);
            }

        }

        return allDinnerListMessage;
    }


//    /**
//     * 老板放桌
//     * @param jsonObj JSONObject
//     * @return String
//     */
//    @Deprecated
//    @SuppressWarnings("unused")
//    private String addDeskList(JSONObject jsonObj){
//        String maxPerson = jsonObj.getString("maxPerson");
//        String minPerson = jsonObj.getString("minPerson");
//        String storeID = jsonObj.getString("storeID");
//        String desc = jsonObj.getString("desc");
//
//        DeskInfoBean deskInfoBean = new DeskInfoBean();
//        Map<Object,Object> result = Maps.newHashMap();
//        // 开启竞价
//        String bid = tbService.openBid(bidTime);
//
////        startThread(bidTime - 3000L, storeID, bid);
//
//        if(storeMap.size() != 0 && storeMap.containsKey(storeID)){
//            ArrayList<String> deskList = storeMap.get(storeID);
//            deskList.add(bid);
//            storeMap.put(storeID, deskList);
//        }else{
//            ArrayList<String> deskList = new ArrayList<>();
//            deskList.add(bid);
//            storeMap.put(storeID, deskList);
//        }
//        deskInfoBean.setResultCode("0");
//        deskInfoBean.setDeskID(bid);
//        deskInfoBean.setMaxPerson(maxPerson);
//        deskInfoBean.setMinPerson(minPerson);
//        deskInfoBean.setDesc(desc);
//        deskInfoBean.setStoreID(storeID);
//
//        deskInfoBean.setBid(bid);
//        desk_infoMap.put(bid, deskInfoBean);
//
//        JSONObject json = new JSONObject();
//        json.put("storeId",deskInfoBean.getStoreID());
//        json.put("mobileNo",deskInfoBean.getMobileNO());
//        json.put("bid",deskInfoBean.getBid());
//        json.put("maxPrice","");
//        json.put("stt","1");
//
//        bidRecordService.addBidRecord(json);
//
//        result.put("resultCode", deskInfoBean.getResultCode());
//        result.put("maxPerson", deskInfoBean.getMaxPerson());
//        result.put("minPerson", deskInfoBean.getMinPerson());
//        result.put("storeID", deskInfoBean.getStoreID());
//        result.put("desc", deskInfoBean.getDesc());
//        result.put("deskID", deskInfoBean.getDeskID());
//        result.put("bid", deskInfoBean.getBid());
//        result.put("timeLimit", bidTime);
//
//        String personInfo = Objects.equals(deskInfoBean.getMaxPerson(), deskInfoBean.getMinPerson())
//                ? deskInfoBean.getMaxPerson() + "位"
//                : deskInfoBean.getMinPerson() + " - " + deskInfoBean.getMaxPerson() + "位用餐的上宾，";
//
//        result.put("message", "店小二放桌喽！" + personInfo + "里面请！");
//
//        result.put("type", "3");
//        return JSON.toJSONString(result);
//    }
//
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
//
//    /**
//     * 新桌位通知
//     * @param jsonObj JSONObject
//     * @return String
//     */
//    @Deprecated
//    @SuppressWarnings("unused")
//    public String newDeskNotify(JSONObject jsonObj){
//
//        Map<String,Object> result = Maps.newHashMap();
//
//        ComeinRestBean crBean = new ComeinRestBean();
//
//        crBean.setResultCode("0");
//
//        result.put("resultCode", crBean.getResultCode());
//        return JSON.toJSONString(result);
//    }
//
//    /**
//     * 用户出价
//     * @param jsonObj JSONObject
//     * @return String
//     */
//    @Deprecated
//    @SuppressWarnings("unused")
//    public String userOfferPrice(JSONObject jsonObj){
//        System.out.println("androidID is:"+jsonObj.getString("androidID"));
//        System.out.println("imei is:"+jsonObj.getString("imei"));
//        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
//        System.out.println("mac is:"+jsonObj.getString("mac"));
//        System.out.println("storeID is:"+jsonObj.getString("storeID"));
//        System.out.println("type is:"+jsonObj.getString("type"));
//        System.out.println("bid is:"+jsonObj.getString("bid"));
//        System.out.println("userID is:"+jsonObj.getString("userID"));
//        System.out.println("price is:"+jsonObj.getString("price"));
//
//
//        // 用户相关信息
//        String userID = jsonObj.getString("userID");
//        String bid = jsonObj.getString("bid");
//        Long price = Long.parseLong(jsonObj.getString("price"));
////
////        UserBean userBean = null;
////        if(desk_userMap.get(bid) != null){
////            userBean = desk_userMap.get(bid).get("userID");
////        }
////        if(userBean == null){
////            userBean = new UserBean();
////        }
////        Long price = Long.parseLong(jsonObj.getString("price"));
////        userBean.setUserID(userID);
////        userBean.setName(userID); // 暂时用手机号代替姓名
////        userBean.setPrice(price);
////        long highPrice = 0;
////        if(desk_userMap.get(bid)!=null){
////            highPrice = desk_userMap.get(bid).get(userID).getHighPrice();
////        }
////        if(highPrice<price){
////            highPrice = price;
////        }
////        userBean.setHighPrice(highPrice);
//
////        BidRequest br = new BidRequest();
////        br.setBidActivityId(bid);
////        br.setBidPrice(price);
////        br.setBidTime(System.currentTimeMillis());
////        br.setUserId(userID);
//        System.out.println(bid + "---" + userID + "----" + price);
//        BidResponse bres = tbService.toBid(bid, userID, new BigDecimal(price));
//        System.out.println("----bres.toString() : " + bres.toString());
//        Boolean isWinner = bres.isWinner();
//
//
////        HashMap<String, UserBean> tempUserMap = new HashMap<String, UserBean>();
////        tempUserMap.put(userID, userBean);
////        desk_userMap.put(bid, tempUserMap);
//
////        ComeinRestBean crBean = new ComeinRestBean();
////        crBean.setResultCode("0");
//
//        Map<Object,Object> result = Maps.newHashMap();
//        if(isWinner){
//            result.put("resultCode" , "0");
//            result.put("highPrice" , price);
//            result.put("userID" , userID);
//            result.put("type" , "6");
////            result.put("resultList", parseMapToJSONArray(desk_userMap.get(bid)));
//        }
//
//        return JSON.toJSONString(result);
//    }
//
//    /**
//     * 抢桌位
//     * @param jsonObj JSONObject
//     * @return String
//     */
//    @SuppressWarnings("unused")
//    public String grabDesk(JSONObject jsonObj){
//        System.out.println("androidID is:"+jsonObj.getString("androidID"));
//        System.out.println("imei is:"+jsonObj.getString("imei"));
//        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
//        System.out.println("mac is:"+jsonObj.getString("mac"));
//        System.out.println("storeID is:"+jsonObj.getString("storeID"));
//        System.out.println("type is:"+jsonObj.getString("type"));
//        System.out.println("deskID is:"+jsonObj.getString("deskID"));
//        System.out.println("userID is:"+jsonObj.getString("userID"));
//        System.out.println("type is:"+jsonObj.getString("type"));
//
//        Map<Object,Object> result = Maps.newHashMap();
//        // 先到先得，结束后清除缓存桌位信息。
//        String bid = jsonObj.getString("bid");
//        String storeID = jsonObj.getString("storeID");
//        if(storeID!=null && storeMap.get(storeID)!=null
//                &&storeMap.get(storeID).contains(bid)){
//            storeMap.get(storeID).remove(bid);
//            result.put("resultCode" , "0");
//
//            JSONObject json = new JSONObject();
//            json.put("mobileNo",jsonObj.getString("userID"));
//            json.put("bid",bid);
//            json.put("maxPrice",jsonObj.getString("price"));
//            json.put("stt","3");
//
//            bidRecordService.updBidRecord(json);
//        }else{
//            result.put("resultCode" , "1");
//        }
//
//        return JSON.toJSONString(result);
//    }
//
//    /**
//     * 历史桌位列表接口
//     * @param jsonObj JSONObject
//     * @return String
//     */
//    @SuppressWarnings("unused")
//    private String deskHistory(JSONObject jsonObj){
//        Map<String,Object> result = Maps.newHashMap();
//        JSONObject json = new JSONObject();
//        json.put("storeId",jsonObj.getString("storeID"));
//        json.put("pageNo",jsonObj.getString("pageNo"));
//        json.put("pageNum",jsonObj.getString("pageNum"));
//
//        List<BidRecordVo> list = bidRecordService.findBidRecordByStoreId(json);
//        result.put("list",list);
//        return JSON.toJSONString(result);
//    }
//
//    @SuppressWarnings("unused")
//    private Collection<BidRequest> resultFilter(Collection<BidRequest> cbr){
//        Iterator it1 = cbr.iterator();
//        Iterator it2 = cbr.iterator();
//        BigDecimal price = new BigDecimal(0);
//        Collection<BidRequest> tmpCbr = new ArrayList<>();
//        while(it1.hasNext()){
//            BidRequest br = (BidRequest) it1.next();
//            if(br.getBidPrice().compareTo(price)>0){
//                price = br.getBidPrice();
//            }
//        }
//        while(it2.hasNext()){
//            BidRequest br = (BidRequest) it2.next();
//            if(br.getBidPrice().compareTo(price)==0){
//                tmpCbr.add(br);
//            }
//            System.out.println(tmpCbr.size());
//        }
//        return tmpCbr;
//    }



}
