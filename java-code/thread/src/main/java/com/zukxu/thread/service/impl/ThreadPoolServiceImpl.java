package com.zukxu.thread.service.impl;

import com.zukxu.thread.service.IThreadPoolService;
import com.zukxu.threadpool.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/22 0022 17:05
 */
@Slf4j
@Service
public class ThreadPoolServiceImpl implements IThreadPoolService {

	@Override
	public String singleProcess(String input) {
		log.info("Processing...");
		int i = Integer.parseInt(input) * 1000 / 24 + 3;
		return "success" + i;
	}

	/**
	 * 批量处理
	 *
	 * @param inputList 输入对象列表
	 * @return 输出对象列表
	 */
	@Override
	public List<String> multiProcess(List<String> inputList) {
		//创建线程池
		ThreadPoolTaskExecutor executor = SpringUtils.getBean("threadPoolTaskExecutor", ThreadPoolTaskExecutor.class);
		//创建并使用CountDownLatch等待所有线程执行结束：
		CountDownLatch latch = new CountDownLatch(inputList.size());
		//每个线程把执行结果添加到线程安全的List中。这里List应当使用SynchronizedList
		List<String> outputList = Collections.synchronizedList(new ArrayList<>(inputList.size()));

		for (String input : inputList) {
			//分配线程进行执行
			executor.execute(() -> {
				try {
					//执行单个线程方法进行执行
					String output = singleProcess(input);
					outputList.add(output);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		}
		try {
			//等待所有线程执行完毕
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return outputList;
	}

	/**
	 * 异步处理
	 *
	 * @param input 输入对象
	 * @return 输出Future对象
	 */
	@Async("threadPoolTaskExecutor")
	@Override
	public Future<String> asyncProcess(String input) {
		return new AsyncResult<>(singleProcess(input));
	}
}
