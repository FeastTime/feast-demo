//package com.feast.demo.web.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.feast.demo.user.entity.User;
//import com.feast.demo.web.entity.WebSocketMessageBean;
//import com.feast.demo.web.entity.WsBean;
//import com.feast.demo.web.service.ComeinRestService;
//import com.feast.demo.web.service.UserService;
//import com.feast.demo.web.util.StringUtils;
//import com.google.common.collect.Maps;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.util.List;
//import java.util.Map;
//import java.util.PriorityQueue;
//import java.util.Queue;
//import java.util.logging.Logger;
//
//
///**
// * ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
// * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
// */
//@ServerEndpoint("/websocket/{userId}")
//public class WSService {
//
//    private ComeinRestService comeinRestService;
//
//    private UserService userService;
//
//    private String userId;
//
//    private Logger logger = Logger.getLogger(this.getClass().getName());
//
//    private Session session;
//
//    // 用户与server关系
//    private static Map<String, WsBean> user2Server = Maps.newConcurrentMap();
//
//    // 需要发送的消息队列
//    private static Queue<WebSocketMessageBean> webSocketMessageQueue = new PriorityQueue<>();
//
//    private static Thread sendMessageThread;
//
//    private static boolean isSendMessageThreadRunning;
//
//    public WSService() {
//        sendMessageFromQueue();
//    }
//
//    private synchronized static void sendMessageFromQueue(){
//
//        if (null == sendMessageThread){
//
//            sendMessageThread = new Thread(() -> {
//
//                WebSocketMessageBean webSocketMessageBean;
//                isSendMessageThreadRunning = true;
//
//                while(isSendMessageThreadRunning){
//
//                    webSocketMessageBean = webSocketMessageQueue.poll();
//
//                    if(webSocketMessageBean == null){
//
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        sendMessageToUser(webSocketMessageBean);
//                    }
//                }
//            });
//
//            sendMessageThread.start();
//        }
//    }
//
//    private void setComeInRestService() {
//
//        String configLocation = "classpath*:/spring*/*.xml";
//        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
//        comeinRestService = context.getBean(ComeinRestService.class);
//
//        comeinRestService.setWebSocketMessageQueue(webSocketMessageQueue);
//        userService = context.getBean(UserService.class);
//    }
//
//    /**
//     * 接建立成功调用的方法
//     *
//     * @param session 连接
//     * @param userId 用户ID
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
//
//        System.out.println("onOpen"  + this.userId);
//
//        // 如果用户ID为空， 关闭连接
//        if(userId == null || "".equals(userId)){
//
//            try {
//                session.close();
//            } catch (Exception ignored) {
//            }
//
//            return;
//        }
//
//        this.session = session;
//        this.userId = userId;
//
//        // 启动 Spring Service
//        if(comeinRestService == null) {
//            setComeInRestService();
//        }
//
//        // 回消息 告诉客户端连接成功
//        sendMessage("success666success");
//
//        User user = userService.findById(Long.parseLong(userId));
//
//        WsBean wsBean = new WsBean();
//        wsBean.setWsService(this);
//        wsBean.setUser(user);
//
//        if (!user2Server.containsKey(userId)){
//            user2Server.put(userId, wsBean);
//        }
//
//        // 恢复用户与商家的关系
//        comeinRestService.repairRelationship(userId);
//    }
//
//    /**
//     *连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//
//        System.out.println("onClose"  + this.userId);
//        // 将连接  从  用户与服务端的关系结构中 删除
//        user2Server.remove(this.userId);
//
//        // 启动 Spring Service
//        if(comeinRestService == null) {
//            setComeInRestService();
//        }
//
//        List<WebSocketMessageBean> messageList = comeinRestService.removeRelationship(userId);
//
//        if (null != messageList){
//
//            for (WebSocketMessageBean webSocketMessageBean : messageList) {
//
//                sendMessageToUser(webSocketMessageBean);
//            }
//        }
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     * @param message 客户端发送过来的消息
//     * @param session 可选的参数
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//
//        if(comeinRestService == null) {
//            setComeInRestService();
//        }
//
//        logger.info("来自客户端的消息:" + message + (null == session));
//
//        System.out.println("转之前 -- 来自客户端的消息"+message);
//        message = StringUtils.decode(message);
//        System.out.println("转之后 -- 来自客户端的消息"+message);
//
//        JSONObject jsonObject  = JSON.parseObject(message);
//
//        int type = jsonObject.getInteger("type");
//
//        String storeId = jsonObject.getString("storeId");
//        String userId = jsonObject.getString("userId");
//
//        if (null == userId){
//            System.out.println("没有userId 无效消息！   " + message);
//            return;
//        }
//
//
//        try {
//            System.out.println(type);
//            System.out.println(jsonObject);
//            System.out.println(user2Server.get(userId));
//            System.out.println(storeId);
//            List<WebSocketMessageBean> list = comeinRestService.WSInterfaceProc(type, jsonObject, user2Server.get(userId).getUser(), storeId);
//
//            if (null != list){
//
//                for (WebSocketMessageBean webSocketMessageBean: list) {
//                    sendMessageToUser(webSocketMessageBean);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 对用户发送消息
//     *
//     * @param webSocketMessageBean WebSocketMessageBean
//     */
//    private static void sendMessageToUser(WebSocketMessageBean webSocketMessageBean) {
//
//        if (null == webSocketMessageBean
//                || null == webSocketMessageBean.getMessage()
//                || 0 == webSocketMessageBean.getMessage().length()
//                || null == webSocketMessageBean.getUserId()
//                || 0 == webSocketMessageBean.getUserId().length()
//                || null == user2Server.get(webSocketMessageBean.getUserId())
//                || null == user2Server.get(webSocketMessageBean.getUserId()).getWsService()
//                || null == user2Server.get(webSocketMessageBean.getUserId()).getWsService().session){
//
//            return;
//        }
//
//        try {
//            System.out.println("send to user : " + webSocketMessageBean.getUserId() + "  --    message : " + webSocketMessageBean.getMessage());
//
////            sendMessage(webSocketMessageBean.getMessage(), user2Server.get(webSocketMessageBean.getUserId()).getWsService().session);
//
//            user2Server.get(webSocketMessageBean.getUserId()).getWsService().sendMessage(webSocketMessageBean.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("发送消息异常");
//        }
//    }
//
//    /**
//     *发生错误时调用
//     *
//     *@param session Session
//     *@param error Throwable
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误" + (null == session));
//        error.printStackTrace();
//    }
//
//    /**
//     *这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
//     *
//     *@param message 消息
//     */
//    private synchronized void sendMessage(String message) {
//
//        //  同步发送消息
//        // session.getBasicRemote().sendText(message);
//
//        //  异步发送消息
//        session.getAsyncRemote().sendText(message);
//    }
//
//}
