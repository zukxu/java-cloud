package com.zukxu.mybatis.inserts;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zukxu.mybatis.inserts.handler.DemoResultHandler;
import com.zukxu.mybatis.inserts.handler.ExcelResultHandler;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
import com.zukxu.mybatis.inserts.service.InsertsService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/26 16:48:44
 */
@RestController
@RequestMapping("/test")
public class TestFluentQueryController {

    @Resource
    private InsertsService insertsService;
    @SneakyThrows
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        insertsService.export(new DemoMybatisInserts());
    }

}
