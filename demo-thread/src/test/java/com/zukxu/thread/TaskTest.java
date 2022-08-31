package com.zukxu.thread;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/31 19:42:58
 */

import cn.hutool.core.date.StopWatch;
import com.zukxu.thread.service.impl.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TaskTest {

    @Resource
    private Task task;

    @Test
    void test1() throws Exception {

        StopWatch sw = new StopWatch();

        sw.start("任务1");
        Future<String> task1 = task.doTask1();
        sw.stop();
        sw.start("任务2");
        Future<String> task2 = task.doTask2();
        sw.stop();
        sw.start("任务3");
        Future<String> task3 = task.doTask3();
        sw.stop();
        int i = 1;
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                i++;
                System.out.println("任务执行中");
                break;
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
        System.out.println(sw.prettyPrint(TimeUnit.MILLISECONDS));

    }

}
