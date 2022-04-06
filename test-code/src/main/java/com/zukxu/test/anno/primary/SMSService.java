package com.zukxu.test.anno.primary;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-06 9:56
 */
@Primary
@Service
public class SMSService implements MessageService {
    @Override
    public String sendMessage(String msg) {
        return "短信消息：" + msg;
    }
}
