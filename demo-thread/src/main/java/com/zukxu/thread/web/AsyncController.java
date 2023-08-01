package com.zukxu.thread.web;

import com.zukxu.thread.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 异步线程
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/29 0029 14:55
 */
@RestController
@RequestMapping("async")
@Slf4j
public class AsyncController {

    @Autowired
    IAsyncService asyncService;

    @GetMapping
    public String test(String msg) {
        log.info("主线程start*****************");
        asyncService.sentMsg(msg);
        log.info("主线程end*****************");
        return "success";
    }
}
