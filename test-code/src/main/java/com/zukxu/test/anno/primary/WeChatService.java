package com.zukxu.test.anno.primary;

import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-06 9:56
 */
@Service
public class WeChatService implements MessageService {
    @Override
    public String sendMessage(String msg) {
        return "微信消息：" + msg;
    }
}
