package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.store.entity.Store;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.DinnerInfo;
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
 * Created by LiXQ on 2017/10/22.
 */
@Service()
public class IMOperationService {

    // 用户与商户关系
    private static Map<String, HashMap<String, UserBean>> user2Store = Maps.newHashMap();

    // 红包
    private static Map<String, List<Object>> redPackages = Maps.newHashMap();

    // 红包与用户关系
    private static Map<String,Set<String>> redId2UserId = Maps.newHashMap();


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


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
                return takeRedPackage(jsonObject, sender, storeId);

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
    private List<WebSocketMessageBean> takeRedPackage(JSONObject jsonObject, User sender,String storeId) {

        try {
            lock.lock();

            Long userId = sender.getUserId();
            String userIdStr = userId.toString();

            String redPackageId = jsonObject.getString("redPackageId");

            List<Object> redPackage = redPackages.get(redPackageId);
            redId2UserId.computeIfAbsent(redPackageId+"", k -> Sets.newHashSet());

            String message = "";
            Map<String, Object> result = Maps.newHashMap();

            String broadcastMessage = ""; //“昵称”手气太棒了，恭喜 得到 “详细奖项”！

            List<WebSocketMessageBean> list = new ArrayList<>();

            if (null == redPackages || redPackage.size() == 0) {

                message = "对不起，您来晚了";
                // 删除缓存数据
                redPackages.remove(redPackageId);
                redId2UserId.remove(redPackageId+"");

            } else if (redId2UserId.get(redPackageId+"").contains(userIdStr)) {

                message = "您已经抢过这个红包了";

            } else {

                Object lastObject = redPackage.get(redPackage.size() - 1);

                boolean hasGetTable = false;

                User user = userService.findById(userId);
                if (lastObject instanceof TableInfo) {

                    TableInfo tableInfo = (TableInfo) lastObject;
                    String[] supportSeatNumbers = tableInfo.getSuportSeatNumber().split(",");

                    for (String supportSeatNumber : supportSeatNumbers) {
                        System.out.println(supportSeatNumber+"===="+user2Store.get(storeId).get(userIdStr).getNumberPerTable());
                        if(Integer.parseInt(supportSeatNumber)==user2Store.get(storeId).get(userIdStr).getNumberPerTable()){

                            tableInfo.setUserPhone(user.getMobileNo());
                            tableInfo.setUserIcon(user.getUserIcon());
                            tableInfo.setUserNickname(user.getNickName());
                            broadcastMessage = user.getNickName()+"手气太棒了，恭喜获得一个桌位！";

                            tableInfo.setUserId(userId);
                            tableInfo.setIsCome(1);
                            tableInfo.setTaketableTime(new Date());

                            tableInfo= tableService.saveTableInfo(tableInfo);

                            redId2UserId.get(redPackageId+"").add(userIdStr);

                            result.put("tableInfo", tableInfo);
                            redPackage.remove(0);
                            message = "恭喜您获得一个桌位";
                            broadcastMessage = createChatMessage(sender,broadcastMessage);

                            HashMap<String, UserBean> map = user2Store.get(storeId);

                            if (null != map){

                                for (String receiverId : map.keySet()) {
                                    list.add(new WebSocketMessageBean().setMessage(broadcastMessage).toUser(receiverId));
                                }
                            }
                            hasGetTable = true;
                            break;
                        }
                    }
                }

                if (!hasGetTable) {

                    int randomSize = redPackage.size();
                    if (lastObject instanceof TableInfo){
                        randomSize-- ;
                    }
                    if(randomSize<=0){
                        message = "对不起，您来晚了";
                    }else{
                        int i = random.nextInt(randomSize);

                        CouponTemplate couponTemplate = (CouponTemplate) redPackage.get(i);
                        couponTemplate = couponService.findCouponTemplateById(couponTemplate.getId());
                        UserCoupon userCoupon = new UserCoupon();
                        userCoupon.setCouponCode(CouponIdCreator.nextId());
                        userCoupon.setCouponTitle(couponTemplate.getCouponTitle());
                        userCoupon.setCouponPicture(couponTemplate.getCouponPicture());
                        userCoupon.setCouponType(couponTemplate.getCouponType());
                        userCoupon.setStartTime(new Date());
                        userCoupon.setIsUse(UserCoupon.ISUSE_UNUSED);
                        userCoupon.setCouponValidity(new Date(new Date().getTime() + couponTemplate.getCouponValidity() * 24 * 60 * 60 * 1000));
                        userCoupon.setStoreId(couponTemplate.getStoreId());
                        userCoupon.setPermissionsDescribed(couponTemplate.getPermissionsDescribed());
                        userCoupon = couponService.saveUserCoupon(userCoupon);
                        userCoupon.setUserId(userId);
                        couponService.saveUserCoupon(userCoupon);

                        redId2UserId.get(redPackageId+"").add(userIdStr);
                        result.put("couponInfo", userCoupon);
                        message = "恭喜您获得一张优惠券";
                        broadcastMessage = user.getNickName()+"手气太棒了，恭喜获得一个优惠券！";
                        redPackage.remove(i);
                        broadcastMessage = createChatMessage(sender,broadcastMessage);

                        HashMap<String, UserBean> map = user2Store.get(storeId);

                        if (null != map){

                            for (String receiverId : map.keySet()) {
                                list.add(new WebSocketMessageBean().setMessage(broadcastMessage).toUser(receiverId));
                            }
                        }
                    }
                }
            }

            // 添加发送者
            result.put("userId", "system");
            result.put("message", message);
            result.put("type", WebSocketEvent.RECEIVED_RED_PACKAGE_SURPRISED);
            String backMessage = JSON.toJSONString(result);

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
        String backMessage;
        try {
            List<WebSocketMessageBean> list = new ArrayList<>();

            redPackage = Lists.newArrayList();

            //添加优惠券
            JSONArray couponArray = jsonObject.getJSONArray("couponInfo");
            String dateStr = format.format(new Date());
            if(couponArray!=null){
                couponList = JSONArray.parseArray(JSON.toJSONString(couponArray), CouponTemplate.class);
                for (CouponTemplate couponTemplate : couponList) {
                    Long count = couponTemplate.getCouponCount();
                    CouponTemplate couponTemplate_ = couponService.findCouponTemplateById(couponTemplate.getId());
                    if(null==count||null==couponTemplate_){
                        continue;
                    }
                    if(couponTemplate_.getCouponCount()<count){
                        // 提示商家优惠券不足
                        Map<String,Object> storeResult = Maps.newHashMap();
                        storeResult.put("type",WebSocketEvent.POPUP_MESSAGE);
                        storeResult.put("storeId",storeId);
                        storeResult.put("userId",sender.getUserId());
                        storeResult.put("title","库存不足");
                        storeResult.put("message",couponTemplate_.getCouponTitle()+"存量不足");
                        storeResult.put("promptInformation","请及时到\"优惠券管理\"补充");
                        storeResult.put("time",dateStr);
                        backMessage = JSON.toJSONString(storeResult);
                        HashMap<String, UserBean> userMap = user2Store.get(storeId);
                        for (String userId : userMap.keySet()) {
                            UserBean userBean = userMap.get(userId);
                            if(userBean.getUserType()==UserBean.STORE){
                                list.add(new WebSocketMessageBean().setMessage(backMessage).toUser(userId));
                            }
                        }

                    }else{
                        for (int i = 0; i < count; i++) {
                            redPackage.add(couponTemplate);
                        }
                        couponTemplate_.setCouponCount(couponTemplate_.getCouponCount()-count);
                        couponService.createCouponTemplate(couponTemplate_);
                    }
                }
            }

            //添加桌位
            tableInfo = jsonObject.getObject("tableInfo", TableInfo.class);
            if (null != tableInfo) {
                tableInfo.setMaketableTime(new Date());
                tableInfo = tableService.saveTableInfo(tableInfo);
                redPackage.add(tableInfo);
            }

            String redPackageId = UUID.randomUUID() + "";
            redPackages.put(redPackageId, redPackage);

            Map<String, Object> result = Maps.newHashMap();

            result.put("date", dateStr);
            result.put("userId", sender.getUserId());
            result.put("nickname", sender.getUsername());
            result.put("userIcon", sender.getUserIcon());
            result.put("redPackageId", redPackageId);
            result.put("type", WebSocketEvent.RECEIVED_RED_PACKAGE);

            backMessage = JSON.toJSONString(result);

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

        String backMessage = createChatMessage(sender, message);

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
     * 创建聊天消息
     * @param sender
     * @param message
     * @return
     */
    private String createChatMessage(User sender, String message) {
        String dateStr = format.format(new Date());

        HashMap<String,Object> map = new HashMap<>();

        map.put("userId", sender.getUserId());
        map.put("nickname", sender.getNickName());
        map.put("userIcon", sender.getUserIcon());
        map.put("date", dateStr);
        map.put("type", WebSocketEvent.RECEIVED_MESSAGE);
        map.put("message", message);

        return JSON.toJSONString(map);
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
        userBean.setUserType(1);

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

    // 存放最后一次发放时间
    private static Map<Long, Long> redPackageSendTime = new HashMap<>();

    private void autoSender(){

        List<Object> redPackage  = Lists.newArrayList();

        long nowTime = new Date().getTime();

        List<Long> storeIds = new ArrayList<>();

        for (String storeId : user2Store.keySet()) {

            if (null != storeId && storeId.length()>0){
                storeIds.add(Long.parseLong(storeId));
            }
        }

        // 查询开启的红包  storeIds
        List<RedPackage> redPackages_ = storeService.findRedPackageByStoreIdAndIsUse(storeIds,2);

        if (null == redPackages_){
            return;
        }

        for (RedPackage redPackage_ : redPackages_) {

            redPackageSendTime.putIfAbsent(redPackage_.getRedPackageId(), 0L);

            // 当前时间 - 最后一次发送时间  超过    设置的时间间隔   发红包
            if ((nowTime - redPackageSendTime.get(redPackage_.getRedPackageId())) > redPackage_.getAutoSendTime()*60*1000 ){

                // 发红包1创建红包，
                Long id = redPackage_.getRedPackageId();
                String userIds = "";
                List<RedPackageCouponTemplate> redPackageCouponTemplates = storeService.findRedPackageCouponTemplateByRedPackageId(id);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateStr = format.format(new Date());
                for (RedPackageCouponTemplate redPackageCouponTemplate : redPackageCouponTemplates) {
                    Long couponTemplateId = redPackageCouponTemplate.getCouponTemplateId();
                    Integer couponCount = redPackageCouponTemplate.getCouponCount();
                    CouponTemplate couponTemplate = couponService.findCouponTemplateById(couponTemplateId);
                    if(null == couponTemplate){
                        continue;
                    }
                    Set<String> ids = user2Store.get(redPackage_.getStoreId()+"").keySet();
                    int count = 1;
                    for (String id_ : ids) {
                        if(count==ids.size()){
                            userIds=userIds+id_;
                        }else{
                            userIds+=id_+",";
                            count++;
                        }
                    }
                    if(couponTemplate.getCouponCount()<couponCount){
                        // 提示商家优惠券不足
                        Map<String,Object> storeResult = Maps.newHashMap();
                        storeResult.put("type",WebSocketEvent.POPUP_MESSAGE);
                        storeResult.put("storeId",redPackage_.getStoreId());
                        storeResult.put("userId",userIds);
                        storeResult.put("title","库存不足");
                        storeResult.put("message",couponTemplate.getCouponTitle()+"存量不足");
                        storeResult.put("promptInformation","请及时到\"优惠券管理\"补充");
                        storeResult.put("time",dateStr);
                        String backMessage = JSON.toJSONString(storeResult);
                        HashMap<String, UserBean> userMap = user2Store.get(redPackage_.getStoreId()+"");
                        for (String userId : userMap.keySet()) {
                            UserBean userBean = userMap.get(userId);
                            if(userBean.getUserType()==UserBean.STORE){
//                                webSocketMessageQueue.offer(new WebSocketMessageBean().setMessage(backMessage).toUser(userId));
                            }
                        }
                    }else{
                        for (int i = 0; i < couponCount; i++) {
                            redPackage.add(couponTemplate);
                        }
                        couponTemplate.setCouponCount(couponTemplate.getCouponCount()-couponCount);
                        couponService.createCouponTemplate(couponTemplate);
                    }
                }
                String redPackageId = UUID.randomUUID() + "";
                redPackages.put(redPackageId, redPackage);
                String backMessage;
                //发送消息
                Map<String,Object> result = Maps.newHashMap();
                result.put("date", dateStr);
                result.put("userId", "system");

                //result.put("nickname", username);
               // result.put("userIcon", userIcon);
                result.put("redPackageId", redPackageId);
                result.put("type", WebSocketEvent.AUTO_SEND_RED_PACKAGE);
                HashMap<String, UserBean> map = user2Store.get(redPackage_.getStoreId()+"");
                backMessage = JSON.toJSONString(result);
                if (null != map){

                    for (String receiverId : map.keySet()) {
//                        webSocketMessageQueue.offer(new WebSocketMessageBean().setMessage(backMessage).toUser(receiverId));
                    }
                }


                redPackageSendTime.put(redPackage_.getRedPackageId(), nowTime);

            }
        }

    }




}
