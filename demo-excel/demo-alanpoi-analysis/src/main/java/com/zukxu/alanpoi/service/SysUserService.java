package com.zukxu.alanpoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.alanpoi.model.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 10:30:58
 */
public interface SysUserService extends IService<SysUser> {

    void export(String type, HttpServletRequest request, HttpServletResponse response, SysUser demoMybatisInserts) throws IOException;
}
