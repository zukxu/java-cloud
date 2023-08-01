package com.zukxu.thread.service.impl;

import com.zukxu.thread.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 异步线程
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/29 0029 14:56
 */
@Service
@Slf4j
public class AsyncServiceImpl implements IAsyncService {

    @Override
    @Async
    public void sentMsg(String msg) {
        log.info("子线程发送短信");
        int i = 0;
        while (i < 10) {
            i++;
            System.out.println("短信发送" + i);
        }
        log.info("子线程发送完毕");
    }
}
