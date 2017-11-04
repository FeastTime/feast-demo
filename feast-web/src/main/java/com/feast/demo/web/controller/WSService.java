package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.WsBean;
import com.feast.demo.web.service.ComeinRestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{mobileNo}/{storeId}")
public class WSService {

    private ComeinRestService comeinRestService;


    void setComeinRestService() {
        String configLocation = "classpath*:/spring*/*.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(
                configLocation);
        comeinRestService = context.getBean(ComeinRestService.class);
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<WSService> webSocketSet = new CopyOnWriteArraySet<WSService>();
    CopyOnWriteArraySet<WsBean> webSocketSet;
    private static HashMap<String,CopyOnWriteArraySet> hm =new HashMap<String,CopyOnWriteArraySet>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String mobileNo;
    private String storeId;

    public WSService() {
        webSocketSet = new CopyOnWriteArraySet<WsBean>();
        //hm =new HashMap<String,CopyOnWriteArraySet>();
    }

    /**
     * 接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("mobileNo") String mobileNo,@PathParam("storeId") String storeId) {

        this.session = session;
        this.mobileNo = mobileNo;
        this.storeId = storeId;

        // 如果店铺id 或者手机号为空 关闭连接
        if(storeId == null || "".equals(storeId) || mobileNo == null || "".equals(mobileNo)){
            onClose();
        }

        // 回消息
        try {
            this.sendMessage("success666success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        WsBean wsb = new WsBean();

        wsb.setWsService(this);
        wsb.setMobileNo(mobileNo);
        wsb.setStoreId(storeId);

        if(hm.containsKey(storeId)){
            webSocketSet = hm.get(storeId);
            webSocketSet.add(wsb);

        } else {
            webSocketSet = new CopyOnWriteArraySet<WsBean>();
            webSocketSet.add(wsb);
            hm.put(storeId, webSocketSet);
        }

        addOnlineCount();           //在线数加1

        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     *连接关闭调用的方法
     */
    @OnClose
    public void onClose() {

        System.out.println(storeId + "--------------------storeId");
        System.out.println(mobileNo + "--------------------mobileNo");

        webSocketSet = hm.get(storeId);

        for (WsBean wsBean: webSocketSet) {
            if (null != mobileNo && mobileNo.equals(wsBean.getMobileNo())){
                webSocketSet.remove(wsBean);
                break;
            }
        }
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if(comeinRestService == null) {
            setComeinRestService();
        }

        System.out.println("来自客户端的消息:" + message);
        //群发消息
        String storeId = null;

        try{
            JSONObject jsono = JSON.parseObject(message);
            storeId = jsono.getString("storeID");

        } catch (Exception e){
            e.printStackTrace();
        }

        if (null == storeId){
            return;
        }

        String resultMessage = comeinRestService.WSInterfaceProc(message);

        webSocketSet = hm.get(storeId);

        for (WsBean item : webSocketSet) {
            try {
                item.getWsService().sendMessage(resultMessage);
                //item.sedMessage(resultMessage);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     *发生错误时调用
     *@param session
     *@param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     *这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *@param message
     *@throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WSService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WSService.onlineCount--;
    }
}
