package com.zukxu.mybatis.inserts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.mybatis.inserts.model.SysUser;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 10:30:58
 */
public interface InsertsSysUserService extends IService<SysUser> {

    List<SysUser> list(Integer limit) throws IOException;

    void export(SysUser demoMybatisInserts) throws IOException;
}
