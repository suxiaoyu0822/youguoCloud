package com.example.demo;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 苏晓雨
 * @date 2020/7/1 下午4:03
 * @Description
 */
public class TTWebSocket {
//    private static Logger logger = LoggerFactory.getLogger(WebsocketClient.class);
    public static WebSocketClient client;
    public static void main(String[] args) {
        try {
//            client = new WebSocketClient(new URI("ws://121.40.165.18:8800"),new Draft_6455()) {
//            client = new WebSocketClient(new URI("ws://192.168.1.119:8080/websocket/consumer/null"),new Draft_6455()) {
            client = new WebSocketClient(new URI("ws://192.168.1.103:8087/ServiceWS"),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("握手成功");
                }

                @Override
                public void onMessage(String msg) {
                    System.out.println("收到消息=========="+msg);
                    if(msg.equals("over")){
                        client.close();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("链接已关闭");
                }

                @Override
                public void onError(Exception e){
                    e.printStackTrace();
                    System.out.println("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        //logger.info(client.getDraft());
        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            System.out.println("正在连接...");
        }
        //连接成功,发送信息
        client.send("哈喽,连接一下啊");
    }
}
