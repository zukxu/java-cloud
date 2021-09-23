package com.zukxu.order.listener;

import com.zukxu.orderdemo.entity.OrderInfo;
import com.zukxu.orderdemo.service.IOrderInfoService;
import com.zukxu.orderdemo.service.IStockService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p>
 * redis过期事件监听器
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/25 0025 15:29
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
	private static final String PREFIX = "ORDER:";
	private final Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
	@Autowired
	IOrderInfoService orderInfoService;
	@Autowired
	IStockService stockService;

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	/**
	 * 针对 redis 失效key事件，进行数据处理
	 *
	 * @param message 消息
	 * @param pattern 匹配
	 */
	@SneakyThrows
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
		String expiredKey = message.toString();
		if (expiredKey.startsWith(PREFIX)) {
			String orderId = expiredKey.substring(PREFIX.length());
			OrderInfo byId = orderInfoService.getById(orderId);
			assert byId != null;
			byId.setOrderStatus(4);
			byId.setUpdateBy("1");
			byId.setUpdateTime(LocalDateTime.now());
			//归还库存
			boolean stock = stockService.calculateStock(byId.getCommodityId(), byId.getSpecId(), byId.getCount());
			if (stock) {
				logger.info("归还库存成功");
				orderInfoService.updOrderStatus(byId);
			}else {
				throw new Exception("归还库存失败");
			}
		}
	}
}
