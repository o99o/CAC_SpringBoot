package com.zit.cac.websockettest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/websocket/{info}")
public class WebSocketTest {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	// 实现服务端与单一客户端通信，使用map来存储，key为session来标识连接，value作为标记来对应session
	private static ConcurrentHashMap<Session, String> webSocketMap = new ConcurrentHashMap<Session, String>();
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	public Session getSession(){
		return this.session;
	}
	private String username;
	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "info") String param, Session session){
		this.session = session;
		username = param;
		webSocketMap.put(session, username);//加入map中
		addOnlineCount(); //在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}
	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		webSocketMap.remove(this.session); //从Map中删除
		subOnlineCount(); //在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}
	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		// System.out.println("来自客户端的消息:" + message);
		// 接收与发送消息
		String uString= null;
		for(Session item: webSocketMap.keySet()){
				if (item.equals(session)) {
					
					uString = webSocketMap.get(item);
					if (uString.equals("xiaoming")) {
						
						try {
							item.getBasicRemote().sendText(message);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						try {
							item.getBasicRemote().sendText(message);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		}
	}
	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}
	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}
	
	public static synchronized void addOnlineCount() {
		WebSocketTest.onlineCount++;
	}
	
	public static synchronized void subOnlineCount() {
		WebSocketTest.onlineCount--;
	}

}
