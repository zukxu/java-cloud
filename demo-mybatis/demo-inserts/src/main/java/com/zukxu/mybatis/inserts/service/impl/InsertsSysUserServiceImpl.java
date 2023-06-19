package com.zukxu.mybatis.inserts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.mybatis.inserts.mapper.SysUserMapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 10:32:26
 */
@Service
public class InsertsSysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements InsertsSysUserService {

    @Resource
    private SysUserMapper demoMybatisInsertsMapper;

    @Override
    public List<SysUser> list(Integer limit) throws IOException {
        try(Cursor<SysUser> cursor = demoMybatisInsertsMapper.scan(limit)) {
            cursor.forEach(rows -> {

            });
        }
        return null;
    }

    @Override
    public void export(SysUser demoMybatisInserts) {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
    }

}
