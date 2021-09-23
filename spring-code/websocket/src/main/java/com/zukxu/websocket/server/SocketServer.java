package com.zukxu.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zukxu
 * CreateTime: 2021/7/11 0011 17:01
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/api/socket/{sid}")
public class SocketServer {
	//	静态变量，用来记录当前在线连接数，线程安全的
	private static int onlineCount = 0;
	//	线程安全的set，用来保存每一个客户端的对应的socket对象
	private static CopyOnWriteArraySet<SocketServer> socketSet = new CopyOnWriteArraySet<>();

	//	与客户端的链接回话，需要通过他给客户端发送数据
	private Session session;

	//接收sid
	private String sid;

	// websocket的生命周期

	/**
	 * 在线人数+1
	 */
	public static synchronized void addOnlineCount() {
		SocketServer.onlineCount++;
	}

	/**
	 * 在线人数-1
	 */
	public static synchronized void subOnlineCount() {
		SocketServer.onlineCount--;
	}

	/**
	 * 获取在线人数
	 */
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	/**
	 * 获取在线sid
	 */
	public static CopyOnWriteArraySet<SocketServer> getSocketSet() {
		return socketSet;
	}

	/**
	 * 实现全部推送
	 * @param message message
	 * @param sid sid
	 */
	public static void sendInfo(String message, @PathVariable("sid") String sid) {
		log.info("推送消息到{}, 推送消息为: {}", sid, message);
		for (SocketServer item : socketSet) {
			try {
				if (sid == null) {
					item.sendMessage(message);
				} else if (item.sid.equals(sid)) {
					item.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * onOpen 建立ws连接
	 *
	 * @param session session
	 * @param sid     sid
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid) {
		//	将session进行存储进行全局调用
		this.session = session;
		socketSet.add(this);
		this.sid = sid;
		//在线人数+1
		addOnlineCount();
		//	发送消息
	}

	/**
	 * OnClose 关闭连接
	 */
	@OnClose
	public void onClose() {
		socketSet.remove(this);
		subOnlineCount();
		log.info("释放的sid: {}", sid);
		log.info("当前在线人数为: {}", onlineCount);
	}

	/**
	 * OnMessage 客户端发送的消息
	 *
	 * @param message 消息
	 * @param session session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("收到来自{}的消息: {}", sid, message);
		//	群发消息
		for (SocketServer item : socketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * OnError 发生错误时
	 *
	 * @param session session
	 * @param error   error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	/**
	 * 服务器主动推送消息
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
}
