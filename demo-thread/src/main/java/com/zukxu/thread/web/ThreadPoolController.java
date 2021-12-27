package com.zukxu.thread.web;

import com.zukxu.thread.service.IThreadPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/22 0022 17:05
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class ThreadPoolController {

	@Autowired
	IThreadPoolService poolService;

	@PostMapping("/thread")
	public List<String> thread() {
		long l1 = System.currentTimeMillis();
		String temp = "424,423,422,421,420,419,418,417,416,415";
		List<String> ids = Arrays.asList(temp.split(","));
		List<String> multiProcess = poolService.multiProcess(ids);

		System.out.println("运行时间");
		long l2 = System.currentTimeMillis() - l1;
		System.out.println(l2 + "ms");
		multiProcess.add(l2 + "ms");
		return multiProcess;

	}

	@PostMapping("/noThread")
	public List<String> noThread() {
		long l1 = System.currentTimeMillis();
		String temp = "424,423,422,421,420,419,418,417,416,415";
		String[] ids = temp.split(",");
		List<String> multiProcess = new ArrayList<>();
		for (String id : ids) {
			String s = poolService.singleProcess(id);
			multiProcess.add(s);
		}

		long l2 = System.currentTimeMillis() - l1;
		System.out.println("运行时间" + l2);
		multiProcess.add(l2 + "ms");
		return multiProcess;

	}

	@PostMapping("/async")
	public String asyncProcess() throws InterruptedException, ExecutionException {
		long l1 = System.currentTimeMillis();
		log.info("Async process start");
		Future<String> future = poolService.asyncProcess("22");
		while (true) {
			if (future.isDone()) {
				System.out.println(111 + future.get());
				log.info("Async process end");
				break;
			}
			log.info("Continue doing something else.");
			System.out.println(111111111 + "");
		}
		System.out.println("运行时间");
		long l2 = System.currentTimeMillis() - l1;
		System.out.println(l2 + "ms");
		return l2 + "ms";
	}
}
