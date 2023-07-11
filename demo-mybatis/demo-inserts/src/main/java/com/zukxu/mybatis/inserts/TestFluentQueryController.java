package com.zukxu.mybatis.inserts;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    private InsertsSysUserService insertsSysUserService;

    @SneakyThrows
    @GetMapping("/list")
    public String list() {
        return String.valueOf(insertsSysUserService.list(new LambdaQueryWrapper<>(SysUser.class).last("limit 10")));
    }

    @SneakyThrows
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        insertsSysUserService.export(new SysUser());
    }

    /**
     * 流式下载
     */
    @GetMapping("/streamDownload")
    public void streamDownload(HttpServletResponse response) throws IOException {
        insertsSysUserService.streamDownload(response);
    }

    /**
     * 传统下载
     */
    @GetMapping("/traditionDownload")
    public void traditionDownload(HttpServletResponse response) throws IOException {
        insertsSysUserService.traditionDownload(response);
    }
}
