package com.zukxu.mybatis.demo1.service.impl;

import com.zukxu.mybatis.demo1.entity.SysUser;
import com.zukxu.mybatis.demo1.mapper.SysUserMapper;
import com.zukxu.mybatis.demo1.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zukxu
 * @since 2021-12-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
