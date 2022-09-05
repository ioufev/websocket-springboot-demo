package com.ioufev.websocketspringbootdemo.ws;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        this.sid = sid;
        sendMessage("conn_success");

        System.out.println("新的窗口" + sid + "已连接！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1

        System.out.println("连接窗口" + sid + "关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        if(message.startsWith("target-")){
            int index = message.indexOf(":");
            String sid = message.substring(7,index);
            sendInfo(message.substring(index + 1), sid);
            return;
        }

        this.session = session;
        sendMessage("服务端收到来自窗口" + sid + "发送的消息：" + message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        this.session = session;
        error.printStackTrace();
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 群发消息
    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) {
        System.out.println("推送消息到窗口" + sid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if (sid == null) {
//                    item.sendMessage(message);
            } else if (item.sid.equals(sid)) {
                item.sendMessage(message);
            }
        }
    }

    public static void sendInfo2(String message) {
        System.out.println("推送消息到所有窗口" + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo2One(String message, @PathParam("sid") String sid) {
        System.out.println("推送消息到窗口" + sid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            if (item.sid.equals(sid)) {
                item.sendMessage(message);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

}
