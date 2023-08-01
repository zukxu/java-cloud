package com.zukxu.email;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 定时发送邮件服务
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/2 0002 17:47
 */
@Component
public class MyScheduled {
    private final SendEmail sendMessage;

    public MyScheduled(SendEmail sendMessage) {
        this.sendMessage = sendMessage;
    }

    /**
     * 定时执行任务方法 每天5点20执行该任务
     */
    //@Scheduled(cron = "0 20 17 * * ? ")
    @Scheduled(cron = "* 0/1 * * * ? ")
    public void sendTask() {
        String message = "我喜欢你啊！！！";
        sendMessage.sendMessage("来自zukxu的消息！❤", message);
    }
}

