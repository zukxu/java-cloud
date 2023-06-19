package com.zukxu.mybatis.inserts;


import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        insertsSysUserService.export(new SysUser());
    }

}
