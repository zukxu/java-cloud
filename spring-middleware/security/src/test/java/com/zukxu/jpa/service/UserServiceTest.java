package com.zukxu.jpa.service;

import com.zukxu.jpa.dao.UserDao;
import com.zukxu.jpa.entity.RoleEntity;
import com.zukxu.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
        UserEntity u1 = new UserEntity();
        u1.setUsername("admin");
        u1.setPassword("123456");
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);
        List<RoleEntity> rs1 = new ArrayList<>();
        RoleEntity r1 = new RoleEntity();
        r1.setName("ROLE_admin");
        r1.setNameZh("管理员");
        rs1.add(r1);
        u1.setRoleEntities(rs1);
        userDao.save(u1);
        UserEntity u2 = new UserEntity();
        u2.setUsername("user");
        u2.setPassword("123456");
        u2.setAccountNonExpired(true);
        u2.setAccountNonLocked(true);
        u2.setCredentialsNonExpired(true);
        u2.setEnabled(true);
        List<RoleEntity> rs2 = new ArrayList<>();
        RoleEntity r2 = new RoleEntity();
        r2.setName("ROLE_user");
        r2.setNameZh("普通用户");
        rs2.add(r2);
        u2.setRoleEntities(rs2);
        userDao.save(u2);
    }
}