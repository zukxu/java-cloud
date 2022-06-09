package com.zukxu.design.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/6/9 17:00:12
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SimpleContext simpleContext;

    @GetMapping("/choose")
    public String choose(@RequestParam String poolId) {
        return simpleContext.getResource(poolId);
    }

}