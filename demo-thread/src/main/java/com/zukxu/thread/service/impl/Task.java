package com.zukxu.thread.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/31 19:42:08
 */
@Slf4j
@Component
public class Task {

    @Async
    public Future<String> doTask1() throws Exception {
        log.info("任务1执行...");
        TimeUnit.SECONDS.sleep(10);
        log.info("任务1执行完毕");
        return new AsyncResult<>("任务11111");
    }

    @Async
    public Future<String> doTask2() throws Exception {
        log.info("任务2执行...");
        TimeUnit.SECONDS.sleep(10);
        log.info("任务2执行完毕");
        return new AsyncResult<>("任务22222");
    }

    //@Async
    public Future<String> doTask3() throws Exception {
        log.info("任务3执行...");
        TimeUnit.SECONDS.sleep(10);
        log.info("任务3执行完毕");
        return new AsyncResult<>("任务33333");

    }

}