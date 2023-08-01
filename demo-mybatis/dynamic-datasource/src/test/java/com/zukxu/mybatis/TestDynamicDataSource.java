package com.zukxu.mybatis;

import com.zukxu.mybatis.mapper.SysUserMapper;
import com.zukxu.mybatis.mapper.SysUserMapper2;
import com.zukxu.mybatis.model.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:33:35
 */
@SpringBootTest
public class TestDynamicDataSource {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserMapper2 sysUserMapper2;

    @Test
    void testDynamic() {
        List<SysUser> listUser1 = sysUserMapper.listUser1();
        List<SysUser> listUser2 = sysUserMapper2.listUser2();
        System.out.println(listUser1);
        System.out.println(listUser2);
    }

}
