package com.zukxu.mybatis.flex;

import com.mybatisflex.core.query.QueryWrapper;
import com.zukxu.mybatis.flex.entity.SysUserEntity;
import com.zukxu.mybatis.flex.mapper.SysUserEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/7/26 14:56:47
 */
@SpringBootTest
public class MybatisFlexTestApplicationTests {

    @Autowired
    private SysUserEntityMapper sysUserEntityMapper;

    @Test
    void querySysUser() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                                                .select()
                                                .where("login_name=test1000000");
        SysUserEntity account = sysUserEntityMapper.selectOneByQuery(queryWrapper);
        System.out.println(account);
    }
}
