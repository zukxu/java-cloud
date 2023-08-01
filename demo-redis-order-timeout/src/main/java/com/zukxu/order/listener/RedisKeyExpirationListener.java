package com.zukxu.order.listener;

import com.zukxu.order.entity.OrderInfo;
import com.zukxu.order.service.IOrderInfoService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

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
            //归还库存
            boolean stock = true;
            if (stock) {
                logger.info("归还库存成功");
                orderInfoService.updOrderStatus(byId);
            } else {
                throw new Exception("归还库存失败");
            }
        }
    }
}
