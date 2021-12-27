package com.zukxu.thread.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/22 0022 17:05
 */
public interface IThreadPoolService {
	/**
	 * 单线程
	 *
	 * @param input
	 * @return
	 */
	String singleProcess(String input);

	/**
	 * 批量处理
	 *
	 * @param list
	 * @return 输出对象列表
	 */

	List<String> multiProcess(List<String> list);

	/**
	 * 异步处理
	 *
	 * @param input 输入对象
	 * @return 输出Future对象
	 */
	@Async("threadPoolTaskExecutor")
	Future<String> asyncProcess(String input);
}
